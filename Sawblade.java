/**
	This class creates a sawblade that spins around.
    Its CollisionBox covers only the sawblade's middle part and does not overlap with the spikes.
	
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

public class Sawblade extends Sprite {
    private double x, y, size, angle, turnSpeed;

    /** Constructs the Sawblade using the parameters to determine its position 
     * @param seed An integer to determine the randomization
    */
    public Sawblade(int seed) {
        Random random = new Random(seed);
        x = random.nextDouble(10, GameFrame.WIDTH-10);
        y = random.nextDouble(10, GameFrame.HEIGHT*3/4);
        size = 30;
        turnSpeed = 0.175;
        angle = 0;

        formCollisionBoxSet();
    }

    /** Creates the sawblade's CollisionBox at the center of its graphic */
    public void formCollisionBoxSet() {
        cbSet = new CollisionBox[] {new CollisionBox(x, y, size, size)};
    }

    /** Draws the sawblade onto the canvas
     * @param g2d The canvas's Graphics2D object
     */
    public void draw(Graphics2D g2d) {
        g2d.rotate(angle, x+size/2, y+size/2);
        // Spikes
        double spikeCount = size*1.5;
        double angleIncrement = 2 * Math.PI / spikeCount; // Angle between spikes
        for (int i = 0; i < spikeCount; i++) {
            g2d.setColor(new Color(80, 80, 80));
            Path2D.Double triangle = new Path2D.Double();
            triangle.moveTo(x + size / 2, y + size / 2); // Move to the center of the circle

            double x1 = x + size / 2 + (size / 2 + 25) * Math.cos(i * angleIncrement);
            double y1 = y + size / 2 + (size / 2 + 25) * Math.sin(i * angleIncrement);
            double x2 = x + size / 2 + (size / 2 - 25) * Math.cos((i + 10) * angleIncrement);
            double y2 = y + size / 2 + (size / 2 - 25) * Math.sin((i + 10) * angleIncrement);

            triangle.lineTo(x1, y1); // Draw line to outer spike point
            triangle.lineTo(x2, y2); // Draw line to inner spike point
            triangle.closePath(); // Close the path to complete the triangle
            g2d.fill(triangle);
        }
        g2d.rotate(-angle, x+size/2, y+size/2);
        g2d.setColor(new Color(112, 109, 109));
        g2d.fillOval((int)(x+size/3.25), (int)(y+size/3.25), (int)size/2, (int)size/2);
    }

    /** Moves the sawblade */
    public void move() {
        angle += turnSpeed;
    }
}