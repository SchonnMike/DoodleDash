/**
	This abstract class contains a method for its subclasses to form an array of CollisionBoxes and draw themselves.
    In addition, it contains methods for interaction between that array and other arrays.

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

public abstract class Sprite {
    protected CollisionBox[] cbSet = null;

    /** Draws the sprite onto the canvas
     * @param g2d The canvas's Graphics2D object
     */
    abstract void draw(Graphics2D g2d);

    /** Creates an array of CollisionBoxes for the cbSet */
    abstract void formCollisionBoxSet();

    /** Checks if any CollisionBox from this sprite's array is colliding with a CollisionBox from another array 
     * @param cbSet2 The second array of CollisionBoxes to test
     * @return true if any two CollisionBoxes beteween the arrays collide, false if not
     */
    public boolean setCollided(CollisionBox[] cbSet2) {
        for (CollisionBox cb : cbSet) 
            if (cb.colliding(cbSet2)) return true;
        return false;
    }

    /** Checks if any CollisionBox from this sprite's array is colliding from its top part with a CollisionBox from another array 
     * @param cbSet2 The second array of CollisionBoxes to test
     * @return true if any of this sprite's CollisionBox collides with any from the other array's at its top part, false if not
     */
    public boolean setCollidedFromAbove(CollisionBox[] cbSet2) {
        for (CollisionBox cb : cbSet) {
            if (cb.collidingFromAbove(cbSet2)) return true;
        }
        return false;
    }

    /** Checks if any CollisionBox from this sprite's array is colliding from its bottom part with a CollisionBox from another array 
     * @param cbSet2 The second array of CollisionBoxes to test
     * @return true if any of this sprite's CollisionBox collides with any from the other array's at its bottom part, false if not
     */
    public boolean setCollidedFromBelow(CollisionBox[] cbSet2) {
        for (CollisionBox cb : cbSet) {
            if (cb.collidingFromBelow(cbSet2)) return true;
        }
        return false;
    }

    /** Checks if any CollisionBox from this sprite's array is colliding from its left part with a CollisionBox from another array 
     * @param cbSet2 The second array of CollisionBoxes to test
     * @return true if any of this sprite's CollisionBox collides with any from the other array's at its left part, false if not
     */
    public boolean setCollidedFromLeft(CollisionBox[] cbSet2) {
        for (CollisionBox cb : cbSet) {
            if (cb.collidingFromLeft(cbSet2)) return true;
        }
        return false;
    }

    /** Checks if any CollisionBox from this sprite's array is colliding from its right part with a CollisionBox from another array 
     * @param cbSet2 The second array of CollisionBoxes to test
     * @return true if any of this sprite's CollisionBox collides with any from the other array's at its right part, false if not
     */
    public boolean setCollidedFromRight(CollisionBox[] cbSet2) {
        for (CollisionBox cb : cbSet) {
            if (cb.collidingFromRight(cbSet2)) return true;
        }
        return false;
    }

    /** Returns this sprite's array of CollisionBoxes
     * @return cbSet
     */
    public CollisionBox[] getCollisionBoxes() {
        return cbSet;
    }
}
