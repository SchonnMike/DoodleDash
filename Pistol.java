/**
	This class is a gray variant of the Turret. It is designed to occasionally fire a bullet of moderate speed.
	
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

public class Pistol extends Turret {
    /** Constructs the pistol with a set of values 
	 * @param seed An integer to determine the randomization
	*/
    public Pistol(int seed) {
        super(Color.GRAY, 0.5, 15, 5, new Sound("fire.wav", -15), seed);
    }
}
