package userinterface.graphics;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import main.SubmarineHunterStart;

public class SplashScreen extends JPanel {
	private static final long serialVersionUID = -6549822160676774605L;
	private Image image;
	
	public SplashScreen() {
		this.setBackground(Color.green);
		this.repaint();
		File imageFile = new File(SubmarineHunterStart.getResourcesPath()+"splash.png");
		try { 
			image = ImageIO.read(imageFile);
			this.setPreferredSize(new Dimension(image.getWidth(null),image.getHeight(null)));
		} catch (IOException e) {
		}
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawImage(image, 0, 0, null);
	}
}
