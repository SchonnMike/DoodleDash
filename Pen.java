/**
	This class creates the pen with an ink gauge that is visible through its graphic. The pen's coordinates are that of its tip's.
	
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

public class Pen {
    private final int INK_CAPACITY = 1500;
    private final double LINE_WIDTH = 10;
    
    private int inkAmount;
    private double x, y, width, height;
    private Color color;
    private boolean penDown;

    /** Constructs the pen with the specified position and dimensions
     * @param x The pen's x-position
     * @param y The pen's y-position
     * @param width The pen's width
     * @param height The pen's height
     * @param color The pen's color
     */
    public Pen(double x, double y, double width, double height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        penDown = false;
        inkAmount = INK_CAPACITY;
    }

    /** Draws the pen onto the canvas
     * @param g2d The canvas's Graphics2D object
     */
    public void draw(Graphics2D g2d) {
        // Setting the shapes
        RoundRectangle2D.Double body = new RoundRectangle2D.Double(x, y, width, height*0.9, width, width);
        RoundRectangle2D.Double inkGauge = new RoundRectangle2D.Double(x+width*0.1, y + width*0.1, width*0.8, height * 0.75 * inkAmount/INK_CAPACITY, width, width);
        Path2D.Double tip = new Path2D.Double();
        tip.moveTo(x, y+height*0.85);
        tip.curveTo(x+width*0.1, y+height*0.8, x+width*0.9, y+height*0.8, x+width, y+height*0.85);
        tip.lineTo(x+width/2, y+height);
        tip.closePath();
        
        // Adding the shapes onto the canvas (including rotation and readjustment)
        AffineTransform reset = g2d.getTransform();
        g2d.rotate(Math.toRadians(45), x, y);
        g2d.translate(-width/2, -height);
        g2d.setColor(new Color(200, 200, 200));
        g2d.fill(body);
        
        g2d.setColor(color);
        g2d.rotate(Math.PI, x+width/2, y+(width*0.1 + height*0.8)/2);
        g2d.fill(inkGauge);
        g2d.rotate(Math.PI, x+width/2, y+(width*0.1 + height*0.8)/2);

        g2d.setColor(color.darker());
        g2d.fill(tip);
        g2d.setTransform(reset);
    }

    /** Decreases the amount of ink in the pen by 1 until it reaches 0 */
    public void useInk() {
        if (hasInk()) inkAmount--;
    }


    /** Renews the pen's x-position
     * @param x The pen's new x-position
    */
    public void setX(double x) {
        this.x = x;

    }

    /** Renews' the pen's y-position
     * @param y The pen's new y position
     */
    public void setY(double y) {
        this.y = y;
    }

    /** Sets the pen's state if it is down or not
     * @param penDown determiner for if the pen is up or down
     */
    public void setPenDown(boolean penDown) {
        this.penDown = penDown;
    }

    /** Sets the pen's ink amount
     * @param inkAmount The pen's new ink amount
     */
    public void setInkAmount(int inkAmount) {
        this.inkAmount = inkAmount;
    }  
    
    /** Returns the x-position of the pen's tip 
     * @return The pen's current x-position
    */
    public double getX() {
        return x;
    }

    /** Returns the y-position of the pen's tip 
     * @return The pen's current y-position
    */
    public double getY() {
        return y;
    }

    /** Returns the amount of ink left in the pen
     * @return The pen's ink amount
     */
    public int getInkAmount() {
        return inkAmount;
    }

    /** Returns the pen's set line width
     * @return The pen's line width constant
     */
    public double getLineWidth() {
        return LINE_WIDTH;
    }

    /** Returns the pen's color
     * @return The pen's color
     */
    public Color getInkColor() {
        return color;
    }

    /** Returns true if the pen still has ink
     * @return True if the amount of ink in the pen is above 0
     */
    public boolean hasInk() {
        return inkAmount > 0;
    }

    /** Returns the pen's pen-down state
     * @return The pen's pen-down state
     */
    public boolean getPenDown() {
        return penDown;
    }
}