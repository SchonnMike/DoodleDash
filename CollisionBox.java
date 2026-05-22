/**
	This class sets up an imaginary rectangle that will cover Sprites.
    It contains methods to detect collisions with other CollisionBoxes from different directions.
	
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

public class CollisionBox {
    private double x, y, width, height;

    /** Constructs the CollisionBox with the specified position and dimensions
     * @param x The CollisionBox's x-position
     * @param y The CollisionBox's y-position
     * @param width The CollisionBox's width
     * @param height The CollisionBox's height
     */
    public CollisionBox(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /** Checks if the CollisionBoxes collide with each other
     * @param cb The second CollisionBox to be tested with
     * @return true if the boxes collide, false if not
     */
    public boolean colliding(CollisionBox cb) {
        return x < cb.getX() + cb.getWidth() && x + width > cb.getX() &&
                y < cb.getY() + cb.getHeight() && y + height > cb.getY();
    }

    /** Checks if the CollisionBoxes collide with each other on this CollisionBox's left side
     * @param cb The second CollisionBox to be tested with
     * @return true if the boxes collide from this box's left side, false if not
     */
    public boolean collidingFromLeft(CollisionBox cb) {
        return x > cb.getX() && x+width > cb.getX()+cb.getWidth() && colliding(cb);
    }

    /** Checks if the CollisionBoxes collide with each other on this CollisionBox's right side
     * @param cb The second CollisionBox to be tested with
     * @return true if the boxes collide from this box's right side, false if not
     */
    public boolean collidingFromRight(CollisionBox cb) {
        return x < cb.getX() && x+width < cb.getX()+cb.getWidth() && colliding(cb);
    }

    /** Checks if the CollisionBoxes collide with each other on this CollisionBox's top side
     * @param cb The second CollisionBox to be tested with
     * @return true if the boxes collide from this box's top side, false if not
     */
    public boolean collidingFromAbove(CollisionBox cb) {
        return y > cb.getY() && y+height > cb.getY()+cb.getHeight() && colliding(cb);
    }

    /** Checks if the CollisionBoxes collide with each other on this CollisionBox's bottom side
     * @param cb The second CollisionBox to be tested with
     * @return true if the boxes collide from this box's bottom side, false if not
     */
    public boolean collidingFromBelow(CollisionBox cb) {
        return y < cb.getY() && y+height < cb.getY()+cb.getHeight() && colliding(cb);
    }


    // FOR COLLISION WITH ARRAYS
    /** Checks for collisions between this CollisionBox and an array of CollisionBoxes
     * @param cbSet The array of CollisionBoxes to be tested with
     * @return true if this box collides with any of the boxes in the array, false if not 
    */
    public boolean colliding(CollisionBox[] cbSet) {
        for (CollisionBox cb2 : cbSet)
            if (colliding(cb2)) return true;
        return false;
    }

    /** Checks for collisions between this CollisionBox and an array of CollisionBoxes for its left side
     * @param cbSet The array of CollisionBoxes to be tested with
     * @return true if this box collides with any of the boxes in the array from the left side, false if not 
    */
    public boolean collidingFromLeft(CollisionBox[] cbSet) {
        for (CollisionBox cb2 : cbSet)
            if (collidingFromLeft(cb2)) return true;
        return false;
    }

    /** Checks for collisions between this CollisionBox and an array of CollisionBoxes for its right side
     * @param cbSet The array of CollisionBoxes to be tested with
     * @return true if this box collides with any of the boxes in the array from the right side, false if not 
    */
    public boolean collidingFromRight(CollisionBox[] cbSet) {
        for (CollisionBox cb2 : cbSet)
            if (collidingFromRight(cb2)) return true;
        return false;
    }

    /** Checks for collisions between this CollisionBox and an array of CollisionBoxes for its top side
     * @param cbSet The array of CollisionBoxes to be tested with
     * @return true if this box collides with any of the boxes in the array from the top side, false if not 
    */
    public boolean collidingFromAbove(CollisionBox[] cbSet) {
        for (CollisionBox cb2 : cbSet)
            if (collidingFromAbove(cb2)) return true;
        return false;
    }

    /** Checks for collisions between this CollisionBox and an array of CollisionBoxes for its bottom side
     * @param cbSet The array of CollisionBoxes to be tested with
     * @return true if this box collides with any of the boxes in the array from the bottom side, false if not 
    */
    public boolean collidingFromBelow(CollisionBox[] cbSet) {
        for (CollisionBox cb2 : cbSet)
            if (collidingFromBelow(cb2)) return true;
        return false;
    }


    // ACCESSOR METHODS
    /** Returns this CollisionBox's x-position
     * @return This CollisionBox's x-position
     */
    public double getX() {
        return x;
    }

    /** Returns this CollisionBox's y-position
     * @return This CollisionBox's y-position
     */
    public double getY() {
        return y;
    }

    /** Returns this CollisionBox's width
     * @return This CollisionBox's width
     */
    public double getWidth() {
        return width;
    }

    /** Returns this CollisionBox's height
     * @return This CollisionBox's height
     */
    public double getHeight() {
        return height;
    }
}
