/**
	This class contains the base for a turret, containing methods to fire and aim.
    It is placed around the edges of the screen and fires bullets at a certain rate.


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

public class Turret extends Sprite {
    private final double RADIUS = 30;
    private final double DIAMETER = RADIUS*2;
    private Random random;

    private double x, y;
    private double angle;

    private Color color;
    private double fireChance;
    private double bulletHeight, bulletSpeed;
    private Sound sfx;

    /** Constructs the turret and initializes its fields
     * @param color The turret's color
     * @param fireChance The chance over 100 that it fires when its method is called
     * @param bulletHeight The height of this turret's bullets
     * @param bulletSpeed The speed of this turret's bullets
     * @param sfx the sound that the turret makes when it fires a bullet
     * @param seed An integer to determine the randomization
     */
    public Turret(Color color, double fireChance, double bulletHeight, double bulletSpeed, Sound sfx, int seed) {
        random = new Random(seed);
        switch (random.nextInt(3)) {
            case 0: // Left Side
                x = -RADIUS*0.75;
                y = random.nextDouble(DIAMETER, GameFrame.HEIGHT/2);
                break;
            case 1: // Right Side
                x = GameFrame.WIDTH-RADIUS;
                y = random.nextDouble(DIAMETER, GameFrame.HEIGHT/2);
                break;
            case 2: // Top Side
                x = random.nextDouble(DIAMETER, GameFrame.WIDTH-DIAMETER);
                y = -RADIUS*0.75;
                break;
        }

        angle = 0;
        this.fireChance = fireChance;
        this.bulletHeight = bulletHeight;
        this.bulletSpeed = bulletSpeed;
        this.color = color;
        formCollisionBoxSet();
        this.sfx = sfx;
    }

    /** Creates a CollisionBox for the turret's body and places it into an array */
    public void formCollisionBoxSet() {
        cbSet = new CollisionBox[] {new CollisionBox(x, y, RADIUS, RADIUS)};
    }

    /** Draws the turret onto the canvas
     * @param g2d The canvas's Graphics2D object
     */
    public void draw(Graphics2D g2d) {
        // Barrel
        g2d.rotate(angle, x+RADIUS, y+RADIUS);
        g2d.setColor(color.darker());
        g2d.fill(new Rectangle2D.Double(x+RADIUS, y+RADIUS*0.75, DIAMETER*0.75, RADIUS/2));
        g2d.rotate(-angle, x+RADIUS, y+RADIUS);

        // Body
        g2d.setColor(color);
        g2d.fill(new Ellipse2D.Double(x, y, DIAMETER, DIAMETER));
        g2d.setColor(color.brighter());
        g2d.fill(new Ellipse2D.Double(x+DIAMETER*0.025, y+DIAMETER*0.025, DIAMETER*0.95, DIAMETER*0.95));
        g2d.setColor(color);
        g2d.fill(new Ellipse2D.Double(x+DIAMETER*0.05, y+DIAMETER*0.05, DIAMETER*0.9, DIAMETER*0.9));
    }

    /** Aims at a CollisionBox
     * @param cbSetTarget An array of the target's CollisionBoxes
     */
    public void aim(CollisionBox[] cbSetTarget) {
        int middle = cbSetTarget.length/2;
        angle = Math.atan2((cbSetTarget[middle].getY() - (y+RADIUS)), (cbSetTarget[middle].getX() - (x+RADIUS)));
    }

    /** Has a chance of returning a bullet object in the angle its barrel is aimed at
     * @return A bullet with the specified position, size, and angle if it succeeds, null if not
     */
    public Bullet fire() {
        if (random.nextDouble(100) < fireChance) {
            sfx.play();
            return new Bullet(x+RADIUS + DIAMETER*Math.cos(angle), y+RADIUS*0.75 + DIAMETER*Math.sin(angle),
                            DIAMETER*0.15, bulletHeight, angle, bulletSpeed, color.darker().darker());
        }
        else return null;
    }
}
