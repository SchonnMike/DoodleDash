/**
	This class contains the game's JFrame object.
	
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

import javax.swing.*;
import java.awt.*;

public class GameFrame {
    public static final int WIDTH = 900, HEIGHT = 600;
    private JFrame frame;
    private GameCanvas gc;

    /** Constructs the GameFrame with the dimension constants */
    public GameFrame() {
        gc = new GameCanvas();
        frame = new JFrame();        
    }

    /** Sets up the game frame with the canvas inside it */
    public void setUpFrame() {
        frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Doodle Dash");
        frame.setResizable(false);
        frame.add(gc);
        frame.pack();
        frame.setVisible(true);
    }
}