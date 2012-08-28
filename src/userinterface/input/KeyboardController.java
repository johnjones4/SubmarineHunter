package userinterface.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import userinterface.graphics.game.GamePanel;
import userinterface.graphics.game.ghosting.GhostCollection;
import main.Game;

public class KeyboardController implements KeyListener {
	private String currentlyModifying;
	private static final String HELM_PLAIN = "h";
	private static final String SPEED = "s";
	private static final String TORPEDOS = "t";
	private Game game;
	private GhostCollection collection;
	
	public KeyboardController(Game game)
	{
		this.currentlyModifying = "";
		this.game = game;
		this.collection = game.getGhostCollection();
	}
	
	private void left()
	{
		if (this.currentlyModifying.equals(HELM_PLAIN) || this.currentlyModifying.equals(TORPEDOS)) {
			game.getPlayerSubmarine().getCourse().incrementHelm(-1);
		} else if (this.currentlyModifying.equals(SPEED)) {
			game.getPlayerSubmarine().getCourse().incrementSpeed(-1);
		}
	}
	
	private void right()
	{
		if (this.currentlyModifying.equals(HELM_PLAIN) || this.currentlyModifying.equals(TORPEDOS)) {
			game.getPlayerSubmarine().getCourse().incrementHelm(1);
		} else if (this.currentlyModifying.equals(SPEED)) {
			game.getPlayerSubmarine().getCourse().incrementSpeed(1);
		}
	}
	
	private void up()
	{
		if (this.currentlyModifying.equals(HELM_PLAIN)) {
			game.getPlayerSubmarine().getCourse().incrementPlane(1);
		}
	}
	
	private void down()
	{
		if (this.currentlyModifying.equals(HELM_PLAIN)) {
			game.getPlayerSubmarine().getCourse().incrementPlane(-1);
		}
	}

	public void keyPressed(KeyEvent e) {
		if (!game.isGameOver()) {
			this.collection.setTargeting(this.currentlyModifying.equals(TORPEDOS));
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_LEFT:
				this.left();
				break;
			case KeyEvent.VK_RIGHT:
				this.right();
				break;
			case KeyEvent.VK_UP:
				this.up();
				break;
			case KeyEvent.VK_DOWN:
				this.down();
				break;
			case KeyEvent.VK_ENTER:
				if (this.currentlyModifying.equals(TORPEDOS))
					this.game.getPlayerSubmarine().fireTorpedo(this.collection.getTarget());
				break;
			default:
				this.currentlyModifying = e.getKeyChar()+"";
				if (this.currentlyModifying.equals(HELM_PLAIN))
					this.game.getActivePanel().setModifying(GamePanel.HELM_PLAIN);
				else if (this.currentlyModifying.equals(SPEED))
					this.game.getActivePanel().setModifying(GamePanel.SPEED);
				else if (this.currentlyModifying.equals(TORPEDOS))
					this.game.getActivePanel().setModifying(GamePanel.TORPEDOS);
				break;
			}
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

}
