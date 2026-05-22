/**
	This class contains a treasure that slowly spins around. They are to be collected by the player during the game.

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

public class Treasure extends Sprite {
    private double x, y, size, angle, turnSpeed;

    /** Constructs the treasure by randomly determining its position, size, and movement 
     * @param seed An integer to determine the randomization
    */
    public Treasure(int seed) {
        Random random = new Random(seed);
        size = random.nextDouble(20, 30);
        x = random.nextDouble(size*2, GameFrame.WIDTH-size*2);
        y = random.nextDouble(size*2, GameFrame.HEIGHT*0.75);
        angle = Math.toRadians(random.nextDouble(0, 360));
        turnSpeed = random.nextDouble(0.005, 0.015);
        if (random.nextBoolean()) turnSpeed *= -1;
        formCollisionBoxSet();
    }

    /** Creates an array of the treasure's CollisionBoxes */
    public void formCollisionBoxSet() {
        cbSet = new CollisionBox[] {new CollisionBox(x-size*0.3, y-size*0.3, size*1.6, size*1.6)};
    }

    /** Draws the treasure onto the canvas
     * @param g2d The canvas's Graphics2D object
     */
    public void draw(Graphics2D g2d) {
        AffineTransform reset = g2d.getTransform();
        g2d.translate(size/2, -size/4);
        g2d.rotate(angle, x, y+size*0.7);

        Path2D.Double pentagon = new Path2D.Double();
        Path2D.Double star = new Path2D.Double();

        double xVertex = x+size/2;
        double yVertex = y;
        double theta = 0;

        star.moveTo(xVertex, yVertex);
        pentagon.moveTo(xVertex, yVertex);        
        for (int i = 0; i < 10; i++) {
            theta += Math.toRadians(72);
            xVertex += size * Math.cos(theta);
            yVertex += size * Math.sin(theta);
            pentagon.lineTo(xVertex, yVertex);
            if (i%2 == 1) star.lineTo(xVertex, yVertex);
        }
        pentagon.closePath();
        star.closePath();

        g2d.setColor(Color.ORANGE);
        g2d.fill(pentagon);
        g2d.setColor(Color.YELLOW);
        g2d.fill(star);

        g2d.setTransform(reset);
    }

    /** Moves the treasure */
    public void move() {
        angle += turnSpeed;
    }
}
