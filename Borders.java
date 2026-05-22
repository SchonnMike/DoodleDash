/**
	This class creates the game's borders, used for containing the other objects within the screen.
    The borders can be scaled through the thickness variable.

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

public class Borders {
    private Color color;
    private Wall top, bottom, left, right;

    /** Constructs the game's borders by creating four walls, one in each direction
     * @param thickness The thickness of the walls
     */
    public Borders(int thickness) {
        color = new Color(0, 42, 73);
        top = new Wall(0, 0, GameFrame.WIDTH, thickness, color);
        bottom = new Wall(0, GameFrame.HEIGHT-thickness, GameFrame.WIDTH, thickness, color);
        left = new Wall(0, 0, thickness, GameFrame.HEIGHT, color);
        right = new Wall(GameFrame.WIDTH-thickness, 0, thickness, GameFrame.HEIGHT, color);
    }

    /** Draws the borders onto the canvas
     * @param g2d The canvas's Graphics2D object
     */
    public void draw(Graphics2D g2d) {
        top.draw(g2d);
        bottom.draw(g2d);
        left.draw(g2d);
        right.draw(g2d);
    }

    /** Returns the CollisionBoxes of the four walls as an array
     * @return An array containing the CollisionBoxes of the borders
     */
    public CollisionBox[] getCollisionBoxes() {
        return new CollisionBox[] {
            top.getCollisionBoxes(),
            bottom.getCollisionBoxes(),
            left.getCollisionBoxes(),
            right.getCollisionBoxes()
        };
    }

    /**
     * This inner class creates a rectangular wall, which is used to form the game's borders. 
     * It also contains a CollisionBox for detecting collisions with Sprites.
    */
    private class Wall {
        private double x, y, width, height;
        private Color color;
        private CollisionBox cb;

        /** Constructs the wall with the specified position, dimensions, and color
         * @param x The wall's x-position
         * @param y The wall's y-position
         * @param width The wall's width
         * @param height The wall's height
         * @param color The wall's Color
         */
        public Wall(double x, double y, double width, double height, Color color) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
            cb = new CollisionBox(x, y, width, height);
        }

        /** Draws the wall onto the canvas
         * @param g2d The canvas's g2d object
         */
        public void draw(Graphics2D g2d) {
            Rectangle2D.Double rect = new Rectangle2D.Double(x, y, width, height);
            g2d.setColor(color);
            g2d.fill(rect);
        }

        /** Returns the wall's CollisionBox
         * @return This wall's CollisionBox
         */
        public CollisionBox getCollisionBoxes() {
            return cb;
        }
    }
}
