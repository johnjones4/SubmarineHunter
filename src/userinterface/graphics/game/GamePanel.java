package userinterface.graphics.game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.Game;
import main.SubmarineHunterStart;
import userinterface.graphics.DisplayController;

public class GamePanel {
	
	public static final Color GRAPHICS_COLOR = new Color(0,150,0);
	public static final Color WARNING_COLOR = Color.red;
	public static final Font LARGE_FONT = new Font("Monaco",Font.PLAIN,100);
	public static final Font MEDIUM_FONT = new Font("Monaco",Font.PLAIN,50);
	public static final Font NORMAL_FONT = new Font("Monaco",Font.PLAIN,15);
	public static final Font NORMAL_FONT_BOLD = new Font("Monaco",Font.ITALIC,15);
	public static final Font SMALL_FONT = new Font("Monaco",Font.PLAIN,10);
	private Sonar sonar;
	private TorpedoControls torpedos; 
	private HelmControls helm;
	private Game game;
	
	public static final int HELM_PLAIN = 0;
	public static final int SPEED = 1;
	public static final int TORPEDOS = 2;
	private int currentlyModifying;
	
	public GamePanel(Game game) {
		this.game = game;
		this.game.setActivePanel(this);
		this.currentlyModifying = -1;
		sonar = new Sonar(new Rectangle((DisplayController.getGamePixelWidth()-DisplayController.getGamePixelHeight())/2,0,DisplayController.getGamePixelHeight(),DisplayController.getGamePixelHeight()),game,game.getGhostCollection());
		sonar.launch();
		torpedos = new TorpedoControls(game);
		helm = new HelmControls(game);
	//	new AudioClip(new File(SubmarineHunterStart.getResourcesPath() + "/background.aifc"),true);
	}
	
	public void draw(Graphics g)
	{
		sonar.draw(g);
		torpedos.draw(g);
		helm.draw(g);
		if (game.isGameOver())
		{
			try {
				Image gameOver = ImageIO.read(new File(SubmarineHunterStart.getResourcesPath() + "gameOver.png"));
				int x = (DisplayController.getGamePixelWidth()/2) - (gameOver.getWidth(null)/2);
				int y = (DisplayController.getGamePixelHeight()/2) - (gameOver.getHeight(null)/2);
				g.drawImage(gameOver, x, y, null);
			} catch (IOException e) {
				
			}
		}
	}

	public void setModifying(int m)
	{
		this.currentlyModifying = m;
	}
	
	public int getCurrentlyModifying()
	{
		return this.currentlyModifying;
	}

	public void stop() {
		sonar.stop();
	}
}
