/**
	This class is a brown variant of the Turret. It is designed to infrequently fire a fast bullet.
	
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

public class Sniper extends Turret {
    /** Constructs the sniper with a set of values 
	 * @param seed An integer to determine the randomization
	*/
    public Sniper(int seed) {
        super(new Color(119, 66, 25), 0.05, 30, 12, new Sound("snipe.wav", -10), seed);
    }
}
