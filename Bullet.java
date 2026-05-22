/**
	This class creates a bullet with the specified size and color. It moves in a forward direction based on its angle.
	
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

public class Bullet extends Sprite {
	private double x, y, width, height, angle, speed;
	private Color color;

	/** Constructs the Bullet with the necessary position, size, color, and angle
    * @param x The x-position of the bullet
    * @param y The y-position of the bullet
    * @param width The width of the bullet
    * @param height The height of the bullet
	* @param angle The angle of the bullet
	* @param speed The speed of the bullet
    * @param color The color of the bullet
    *
     */
	public Bullet(double x, double y, double width, double height, double angle, double speed, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
        this.angle = angle;
        this.speed = speed;
		this.color = color;

		formCollisionBoxSet();
	}

    /** Creates a CollisionBox for the bullet */
    public void formCollisionBoxSet() {
        double boxWidth = (width*Math.abs(Math.sin(angle)) + height*Math.abs(Math.cos(angle))) * 0.8;
		double boxHeight = (width*Math.abs(Math.cos(angle)) + height*Math.abs(Math.sin(angle))) * 0.8;
		double boxX = x+width/2 - boxWidth/2;
		double boxY = y+height/2 - boxHeight/2;

		cbSet = new CollisionBox[] {new CollisionBox(boxX, boxY, boxWidth, boxHeight)};
    }

	/**
		Draws the bullet onto the canvas.
		@param g2d The canvas's Graphics2D object
	**/
	public void draw(Graphics2D g2d) {
        g2d.rotate(Math.PI/2 + angle, x+width/2, y+height/2);
		Path2D.Double bullet = new Path2D.Double();
		bullet.moveTo(x+width*0.5, y+height);
		bullet.curveTo(x+width*0.75, y+height, x+width, y+height*0.9, x+width, y+height*0.8);
		bullet.curveTo(x+width, y+height*0.6, x+width, y+height*0.2, x+width*0.5, y);
		bullet.curveTo(x, y+height*0.2, x, y+height*0.6, x, y+height*0.8);
		bullet.curveTo(x, y+height*0.9, x+width*0.25, y+height, x+width*0.5, y+height);

		g2d.setColor(color);
		g2d.fill(bullet);
        g2d.rotate(-Math.PI/2 - angle, x+width/2, y+height/2);
	}

    /** Moves the bullet forward */
    public void move() {
		x += speed*Math.cos(angle);
		y += speed*Math.sin(angle);
		formCollisionBoxSet();
    }
}