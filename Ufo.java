/**
	This class contains a UFO that randomly moves sideways for its movement.
    It stops when it collides with a border and can randomly stop its own movement.

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
import java.util.Random;

public class Ufo extends Sprite {
    private Random random;
    private final double SPEED = 2;
    
    private double x, y;
    private double width, height;
    private double direction;

    /** Constructs the UFO with a random position and size 
     * @param seed An integer to determine the randomization
    */
    public Ufo(int seed) {
        random = new Random(seed);
        width = random.nextDouble(100, 125);
        height = random.nextDouble(35, 50);
        x = random.nextDouble(width, GameFrame.WIDTH-width);
        y = random.nextDouble(height, GameFrame.HEIGHT*0.5);
        direction = 0; // -1 means left, 0 means no movement, 1 means right
        formCollisionBoxSet();
    }

    /** Forms the CollisionBoxes of the UFO, one for its head and another for its base */
    public void formCollisionBoxSet() {
        cbSet = new CollisionBox[] {
            new CollisionBox(x+width*0.35, y+height*0.1, width*0.3, height*0.9),
            new CollisionBox(x+width*0.5, y+height*0.4, width*0.4, height*0.4)
        };
    }

    /** Draws the UFO onto the canvas
     * @param g2d The canvas's Graphics2D object
     */
    public void draw(Graphics2D g2d) {
        Ellipse2D.Double head = new Ellipse2D.Double(x+width*0.3, y, width*0.4, height*0.8);
        
        Path2D.Double base = new Path2D.Double();
        base.moveTo(x, y+height*4/7);
        base.curveTo(x+width*0.3, y+height*0.15, x+width*0.7, y+height*0.15, x+width, y+height*4/7);
        base.curveTo(x+width*0.7, y+height, x+width*0.3, y+height, x, y+height*4/7);
        base.closePath();
        
        int dotsNum = 5;
        Ellipse2D.Double[] dots = new Ellipse2D.Double[dotsNum];
        for (int i = 0; i < dotsNum; i++)
            dots[i] = new Ellipse2D.Double(x + width/(dotsNum + 1)*(i + 1) - width*0.025,
                                            y + height*4/7 - width*0.025,
                                            width*0.05, width*0.05);

        // Drawing
        g2d.setColor(new Color(150, 225, 100));
        g2d.fill(head);
        g2d.setColor(new Color(125, 175, 25));
        g2d.fill(base);
        g2d.setColor(new Color(225, 250, 150));
        for (Ellipse2D.Double dot : dots) g2d.fill(dot);
    }

    /** Moves the UFO unless it collides with a barrier
     * @param cbSetBorders An array of the borders' CollisionBoxes
     */
    public void move(CollisionBox[] cbSetBorders) {
        x += SPEED*direction;
        formCollisionBoxSet();
        if (setCollided(cbSetBorders)) x -= SPEED*direction;
        setDirection();
    }

    /** Has a chance to set the direction of the UFO to the left, the right, or none. */
    private void setDirection() {
        if (random.nextInt(100) > 96)
            direction = random.nextInt(3) - 1;
    }
}
