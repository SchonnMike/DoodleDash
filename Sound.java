/**
	This class facilitates the creation of a Clip object from a wav file.
    It also contains methods for playing and stopping the clip.

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

import java.io.File;
import javax.sound.sampled.*;

public class Sound {
    private Clip clip;

    /** Constructs the sound
	 * @param fileName File name of the sound
     * @param volumeIncrease Volume of the sound
	*/
    public Sound(String fileName, float volumeIncrease) {
        try {
            File file = new File(fileName);
            
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            AudioFormat format = ais.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(ais);

            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(volumeIncrease);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Plays the sound */
    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    /** Stops the sound */
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}

