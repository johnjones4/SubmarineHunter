package userinterface.sound;

import java.io.File;
import javax.sound.sampled.*;

public class AudioClip implements LineListener {
	private Clip clip;
	
	public AudioClip(File clipFile, boolean repeat)
	{
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(clipFile);
			
			clip = AudioSystem.getClip();
			clip.addLineListener(this);
			clip.open(audioInputStream);
			
			if (repeat)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			else
				clip.loop(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(LineEvent event) {
		if (event.getType().equals(LineEvent.Type.STOP))
		{
			clip.close();
		}
		else if (event.getType().equals(LineEvent.Type.CLOSE))
		{
			System.exit(0);
		}
	}
}
