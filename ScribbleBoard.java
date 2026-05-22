/**
	This class creates and holds the pen's scribbles through an ArrayList.
    It also has the function of detecting collisions for the indiviudal blots.

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
import java.util.ArrayList;

public class ScribbleBoard {
    private ArrayList<Blot> blotList;
    private int erasures;
    private Color color;

    /** Constructs the blotList to take in multiple blots
     * @param color The color for the blots
     * @param erasures The number of erasures that the ScribbleBoard can permit
     */
    public ScribbleBoard(Color color, int erasures) {
        blotList = new ArrayList<Blot>();
        this.color = color;
        this.erasures = erasures;
    }

    /** Adds a new blot object to the list of blots
     * @param pen The pen object whose variables will be used
     */
    public void scribble(Pen pen) {
        double x = pen.getX() - pen.getLineWidth()/2;
        double y = pen.getY() - pen.getLineWidth()/2;
        blotList.add(new Blot(x, y, pen.getLineWidth()));
    }

    /** Adds in-between blots to fill out disconnected strokes
     * @param pen The pen object whose variables will be used
     */
    public void fillStroke(Pen pen) {
        if (blotList.size() > 1 && pen.getPenDown()) {
            double prevX = blotList.get(blotList.size() - 2).getX();
            double prevY = blotList.get(blotList.size() - 2).getY();
            double lastX = blotList.get(blotList.size() - 1).getX();
            double lastY = blotList.get(blotList.size() - 1).getY();

            double blotNum =  Math.max(Math.abs(lastX - prevX), Math.abs(lastY - prevY));
            for (int step = 1; step < blotNum; step++) {
                double betweenX = prevX + (lastX - prevX)/blotNum * step;
                double betweenY = prevY + (lastY - prevY)/blotNum * step;
                blotList.add(new Blot(betweenX, betweenY, pen.getLineWidth()));
                pen.useInk();
            }
        }
    }

    /** Draws all of the blots onto the canvas in succession
     * @param g2d The graphics object of the canvas
     */
    public void draw(Graphics2D g2d) {
        for (Blot blot : blotList) blot.draw(g2d);
    }

    /** Gets every blot's CollisionBox and returns it as an array
     * @return An array containing each blot's CollisionBox
     */
    public CollisionBox[] getCollisionBoxes() {
        CollisionBox[] cbSet = new CollisionBox[blotList.size()];
        for (int i = 0; i < blotList.size(); i++) {
            cbSet[i] = blotList.get(i).getCollisionBoxes();
        }
        return cbSet;
    }

    /** 
     * This class creates a circle with its own CollisionBox.
     * Multiple instances of this class are created in order to form scribbles.
     */
    private class Blot {
        private double x, y;
        private Ellipse2D.Double dot;
        private CollisionBox cb;
        
        /** Constructs the blot with the specified position and size
         * @param x The blot's x-position
         * @param y The blot's y-position
         * @param width The blot's width
        */
        public Blot(double x, double y, double width) {
            this.x = x;
            this.y = y;
            dot = new Ellipse2D.Double(x, y, width, width);
            cb = new CollisionBox(x+width*0.1, y+width*0.1, width*0.8, width*0.8);
        }

        /** Draws the blot onto the canvas
         * @param g2d The canvas's Graphics2D object
         */
        public void draw(Graphics2D g2d) {
            g2d.setColor(color);
            g2d.fill(dot);
        }

        /** Returns the blot's x-position
         * @return The blot's x-position
         */
        public double getX() {
            return x;
        }

        /** Returns the blot's y-position
         * @return The blot's y-position
         */
        public double getY() {
            return y;
        }

        /** Returns the blot's CollisionBox
         * @return The blot's CollisionBox
        */
        public CollisionBox getCollisionBoxes() {
            return cb;
        }
    }
}