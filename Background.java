/**
	This class creates the background for the game canvas. This background is formed through a GradientPaint and goes from cyan to blue.
	
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

public class Background {
    private int x, y, width, height;

    /** Constructs the background from the specified coordinates and dimensions
     * @param x The background's x-position
     * @param y The background's y-position
     */
    public Background(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = GameFrame.WIDTH;
        this.height = GameFrame.HEIGHT;
    }

    /** Draws the background onto the canvas
     * @param g2d The canvas's Graphics2D object
    */
    public void draw(Graphics2D g2d) {
        Rectangle2D.Double bg = new Rectangle2D.Double(x, y, width, height);
        GradientPaint gp = new GradientPaint(0, 0, Color.CYAN, 0, 600, Color.BLUE);
        g2d.setPaint(gp);
        g2d.fill(bg);
    }
}
