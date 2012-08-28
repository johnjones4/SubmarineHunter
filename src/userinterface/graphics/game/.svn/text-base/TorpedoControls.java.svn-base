package userinterface.graphics.game;

import gameelements.client.ClientSubmarine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import userinterface.graphics.DisplayController;

import main.Game;

public class TorpedoControls {
	private Rectangle bounds;
	private static final int PADDING = 10;
	private Game game;
	
	public TorpedoControls(Rectangle rect,Game game)
	{
		this.bounds = rect;
		this.game = game;
	}
	
	public TorpedoControls(Game game)
	{
		this(new Rectangle(0,0,(DisplayController.getGamePixelWidth()-DisplayController.getGamePixelHeight())/2,DisplayController.getGamePixelHeight()),game);
	}
	
	public void draw(Graphics g)
	{
		g.setColor(GamePanel.WARNING_COLOR);
		int x = bounds.x+(PADDING*2);
		int y = bounds.y+(PADDING*2);
		if (game.getActivePanel().getCurrentlyModifying() == GamePanel.TORPEDOS)
			g.setFont(GamePanel.NORMAL_FONT_BOLD);
		else
			g.setFont(GamePanel.NORMAL_FONT);
		g.drawString("[T]orpedos", x, y+=g.getFont().getSize());
		g.setFont(GamePanel.LARGE_FONT);
		g.drawString(""+game.getPlayerSubmarine().getTorpedosRemaining(), x, y+=g.getFont().getSize());
		
		g.setColor(GamePanel.GRAPHICS_COLOR);
		y += PADDING;
		g.setFont(GamePanel.NORMAL_FONT);
		g.drawString("Status Level", x, y+=g.getFont().getSize());
		
		if (game.getPlayerSubmarine().getLife() < ClientSubmarine.MAX_LIFE / 3)
			g.setColor(GamePanel.WARNING_COLOR);
		g.setFont(GamePanel.NORMAL_FONT);
		y += PADDING;
		int maxW = (bounds.width-(PADDING*4));
		double percentOK = (double)game.getPlayerSubmarine().getLife() / (double)ClientSubmarine.MAX_LIFE;
		int w = (int)(maxW * percentOK);
		g.drawRect(x, y, maxW, g.getFont().getSize()+6);
		g.fillRect(x, y, w, g.getFont().getSize()+6);
		g.setColor(Color.black);
		g.drawString(((int)(percentOK*100))+"%", x + PADDING, y+=g.getFont().getSize()+3);
		
		g.setColor(GamePanel.GRAPHICS_COLOR);
		y += PADDING;
		g.drawRect(bounds.x+PADDING, bounds.y+PADDING, bounds.width - (PADDING*2), y + PADDING);
	}
}

// Torpedo Launching
