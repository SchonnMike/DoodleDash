/**
	This class extends Sprite and draws a character with the use of different objects.
    It also contains methods for the player's movement and interaction with the platforms in the game.
	
	@author Kelvin M. Cai (231181)
	@author Schonn Michael L. Serrano (235771)
	@version 15 May 2024
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/

import java.awt.*;
import java.awt.geom.*;

public class Player extends Sprite { 
    private final double SPEED = 3.25;
    private final double GRAVITY = 0.25;
    
    private double x, y, scale;
    private double ySpeed, headAngle;
    private boolean isMovingLeft, isMovingRight;
    private boolean isFacingLeft;
    private boolean isInAir;
    
    private Sound jumpFX;

    /** Sets all of the fields for the player's size, position, and movement
     * @param x The player's initial x position
     * @param y The player's initial y position
     * @param scale The player's size scaling
     */
    public Player(double x, double y, double scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        ySpeed = 0;
        headAngle = 0;
        
        isInAir = true;
        isMovingLeft = false;
        isMovingRight = false;
        isFacingLeft = false;

        jumpFX = new Sound("jump.wav", -15);

        formCollisionBoxSet();
    }

    /** Creates two CollisionBoxes for the player's head and body*/
    public void formCollisionBoxSet() {
        cbSet = new CollisionBox[] {
            new CollisionBox(x+scale*55, y, scale*100, scale*100),
            new CollisionBox(x+scale*80, y+scale*100, scale*50, scale*180)
        };
    }

    // MOVEMENT METHODS
    /** Changes the player's position as it walks and jumps
     * @param cbSetBorders An array containing all CollisionBoxes of the screen's borders
     * @param cbSetScribbles An array containing all CollisionBoxes of the pen's scribbles 
     */
    public void move(CollisionBox[] cbSetBorders, CollisionBox[] cbSetScribbles) {
        // Aerial Movement
        if (isInAir) {
            y -= ySpeed;
            ySpeed -= GRAVITY;
            if (headAngle < Math.toRadians(45)) headAngle += Math.toRadians(2);
            else headAngle = Math.toRadians(45);
        } else {
            headAngle = 0;
        }
        formCollisionBoxSet();
        
        // Ground Movement
        detectCollisions(cbSetBorders, cbSetScribbles);
        if (isMovingLeft) x -= SPEED;
        if (isMovingRight) x += SPEED;
        formCollisionBoxSet();
    }

    /** Handles the player's collision logic with the borders and scribbles
     * @param cbSetBorders An array containing all CollisionBoxes of the canvas's borders
     * @param cbSetScribbles An array containing all CollisionBoxes of the canvas's scribbles 
     */
    private void detectCollisions(CollisionBox[] cbSetBorders, CollisionBox[] cbSetScribbles) {
        // Ceiling Collision
        if (cbSet[0].collidingFromAbove(cbSetBorders) || cbSet[0].collidingFromAbove(cbSetScribbles)) {
            ySpeed = -1;
            y -= ySpeed;
        }

        // Side Collision
        if (setCollidedFromLeft(cbSetBorders)) isMovingLeft = false;
        if (setCollidedFromRight(cbSetBorders)) isMovingRight = false;

        // Floor & Side Collision
        if (ySpeed <= 0) {
            if (cbSet[1].collidingFromBelow(cbSetBorders) || cbSet[1].collidingFromBelow(cbSetScribbles)) {
                isInAir = false;
                ySpeed = 0;
                riseAttempt(cbSetScribbles, SPEED);
                riseAttempt(cbSetBorders, 200);
            }
        } else {
            if (setCollidedFromLeft(cbSetScribbles)) isMovingLeft = false;
            if (setCollidedFromRight(cbSetScribbles)) isMovingRight = false;
        }
        
        if (!(setCollided(cbSetBorders) || setCollided(cbSetScribbles))) isInAir = true;
    }

    /** Raises the player a certain amount to check if it can rise up from all CollisionBoxes below it.
     * If so, the player stays in that position. If not, its y-position is reset.
     * @param cbSet2 The array of CollisionBoxes for the player to rise from
     * @param step The maximum height to raise the player at
     * @return true if the player succeeds in rising from the CollisionBoxes, false if not
     */
    private void riseAttempt(CollisionBox[] cbSet2, double step) {
        double origX = x, origY = y;
        boolean canRise = false;

        for (int i = 0; i < 10*step*1.5; i++) {
            y -= 0.1;
            formCollisionBoxSet();

            if (!cbSet[1].collidingFromBelow(cbSet2)) {
                y += 0.1;
                formCollisionBoxSet();
                canRise = true;
                break;
            }
        }
        
        if (!canRise) {
            x = origX;
            y = origY;
            if (setCollidedFromLeft(cbSet2)) isMovingLeft = false;
            if (setCollidedFromRight(cbSet2)) isMovingRight = false;
        }
    }

    /** Toggles and presets the player's jump */
    public void jump() {
        if (!isInAir) {
            isInAir = true;
            ySpeed = SPEED*1.5;
            headAngle = -Math.toRadians(45);
            jumpFX.play();
        }
    }

    // DRAWING METHODS
    /** Draws the overall player onto the canvas
     * @param g2d The canvas's Graphics2D object
     */
    public void draw(Graphics2D g2d) {
        AffineTransform reset = g2d.getTransform();
        if (isFacingLeft) {
            g2d.scale(-1, 1);
            g2d.translate(-2*x - scale*200, 0);
        }

        drawBody(g2d);
        drawLegs(g2d);
        drawHead(g2d);

        g2d.setTransform(reset);
    }

    /** Draws the player's head onto the canvas
     * @param g2d The canvas's Graphics2D object
     */
    private void drawHead(Graphics2D g2d) {
        AffineTransform reset = g2d.getTransform();
        g2d.rotate(headAngle, x+scale*100, y+scale*90);

        // Head
        g2d.setColor(new Color(255, 217, 194));
        g2d.fillOval((int)(x+scale*55), (int)(y+scale*10), (int)(scale*100), (int)(scale*100));

        // Eye
        g2d.setColor(new Color(69, 24, 0));
        g2d.fillOval((int)(x+scale*115), (int)(y+scale*40), (int)(scale*15), (int)(scale*25));

        // Hair
        g2d.setColor(new Color(69, 24, 0));
        g2d.rotate(Math.toRadians(270), (int)(x+scale*50), (int)(y+scale*90));
        g2d.fillArc((int)(x+scale*50), (int)(y+scale*90), (int)(scale*60), (int)(scale*60), 0, 180);
        g2d.rotate(Math.toRadians(-270), (int)(x+scale*50), (int)(y+scale*90));
        g2d.rotate(Math.toRadians(110), (int)(x+scale*110), (int)(y+scale*45));
        g2d.fillArc((int)(x+scale*110), (int)(y+scale*45), (int)(scale*60), (int)(scale*60), 0, 180);
        g2d.rotate(Math.toRadians(-110), (int)(x+scale*110), (int)(y+scale*45));

        // Hat
        g2d.setColor(Color.RED);
        g2d.fillArc((int)(x+scale*60), (int)(y+scale*0), (int)(scale*93), (int)(scale*80), 0, 180);
        g2d.fillArc((int)(x+scale*135), (int)(y+scale*22), (int)(scale*40), (int)(scale*35), 0, 180);

        g2d.setTransform(reset);
    }

    /** Draws the player's body onto the canvas
     * @param g2d The canvas's Graphics2D object
     */
    private void drawBody(Graphics2D g2d) {
        // Shirt
        g2d.setColor(Color.RED);
        g2d.fillRect((int)(x+scale*85), (int)(y+scale*100), (int)(scale*40), (int)(scale*100)); //doubled

        // Strap
        g2d.setColor(Color.BLUE.darker());
        g2d.fillRect((int)(x+scale*95), (int)(y+scale*100), (int)(scale*20), (int)(scale*20));
        g2d.fillRect((int)(x+scale*85), (int)(y+scale*120), (int)(scale*40), (int)(scale*80)); //doubled

        // Button
        g2d.setColor(Color.YELLOW);
        g2d.fillOval((int)(x+scale*115), (int)(y+scale*120), (int)(scale*15), (int)(scale*15));
        
        // Arm
        g2d.setColor(new Color(255, 217, 194));
        g2d.fillRect((int)(x+scale*90), (int)(y+scale*115), (int)(scale*25), (int)(scale*60)); 
    }
        
    /** Draws the player's legs onto the canvas
     * @param g2d The canvas's Graphics2D object
     */
    private void drawLegs(Graphics2D g2d) {
        // Pants
        g2d.setColor(Color.BLUE.darker());
        g2d.fillRect((int)(x+scale*85), (int)(y+scale*200), (int)(scale*40), (int)(scale*80)); //doubled
        // Shoes
        g2d.setColor(new Color(69, 24, 0));
        g2d.fillRect((int)(x+scale*85), (int)(y+scale*270), (int)(scale*40), (int)(scale*10)); //doubled
        g2d.rotate(Math.toRadians(90),(int)(x+scale*130), (int)(y+scale*265));
        g2d.fillArc((int)(x+scale*130), (int)(y+scale*265), (int)(scale*15), (int)(scale*15), 0, 180);
        g2d.rotate(Math.toRadians(-90), (int)(x+scale*130), (int)(y+scale*265));
    }

    /** Sets the player's x-position
     * @param x The player's new x-position
     */
    public void setX(double x) {
        this.x = x;
    }

    /** Sets the player's y-position
     * @param y The player's new y-position
     */
    public void setY(double y) {
        this.y = y;
    }

    /** Sets the player's left movement and makes it face left
     * @param isMovingLeft A boolean to set the player's left movement to
     */
    public void setLeftMotion(boolean isMovingLeft) {
        this.isMovingLeft = isMovingLeft;
        if (isMovingLeft) isFacingLeft = true;
    }

    /** Sets the player's right movement and makes it face right
     * @param isMovingRight A boolean to set the player's right movement to
     */
    public void setRightMotion(boolean isMovingRight) {
        this.isMovingRight = isMovingRight;
        if (isMovingRight) isFacingLeft = false;
    }

    /** Gets the player's x-position
     * @return The player's x-position
     */
    public double getX() {
        return x;
    }

    /** Gets the player's y-position
     * @return The player's y-position
     */
    public double getY() {
        return y;
    }

    /** Returns the player's flag for left motion
     * @return true if the player is moving left, false if not
     */
    public boolean isMovingLeft() {
        return isMovingLeft;
    }

    /** Returns the player's flag for right motion
     * @return true if the player is moving right, false if not
     */
    public boolean isMovingRight() {
        return isMovingRight;
    }
}