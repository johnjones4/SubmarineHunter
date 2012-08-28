package userinterface.graphics.game;

import gameelements.client.utils.Course;

import java.awt.Graphics;
import java.awt.Rectangle;

import userinterface.graphics.DisplayController;

import main.Game;

public class HelmControls {
	private static final int DEPTH_WARNING = 100;
	private Rectangle bounds;
	private static final int PADDING = 10;
	private Game game;
	
	
	public HelmControls(Rectangle rect,Game game)
	{
		this.bounds = rect;
		this.game = game;
	}
	
	public HelmControls(Game game)
	{
		this(new Rectangle(DisplayController.getGamePixelWidth()-((DisplayController.getGamePixelWidth()-DisplayController.getGamePixelHeight())/2),0,(DisplayController.getGamePixelWidth()-DisplayController.getGamePixelHeight())/2,DisplayController.getGamePixelHeight()),game);
	}
	
	public void draw(Graphics g)
	{
		g.setColor(GamePanel.GRAPHICS_COLOR);
		
		int x = bounds.x+(PADDING*2);
		int y = bounds.y+(PADDING*2);
		if (game.getActivePanel().getCurrentlyModifying() == GamePanel.SPEED)
			g.setFont(GamePanel.NORMAL_FONT_BOLD);
		else
			g.setFont(GamePanel.NORMAL_FONT);
		g.drawString("[S]peed", x, y+=g.getFont().getSize());
		g.setFont(GamePanel.LARGE_FONT);
		g.drawString(""+game.getPlayerSubmarine().getCourse().getSpeed(), x, y+=g.getFont().getSize());
		
		y += PADDING;
		
		if (game.getActivePanel().getCurrentlyModifying() == GamePanel.HELM_PLAIN)
			g.setFont(GamePanel.NORMAL_FONT_BOLD);
		else
			g.setFont(GamePanel.NORMAL_FONT);
		g.drawString("[H]elm/Plane", x, y+=g.getFont().getSize());
		g.setFont(GamePanel.MEDIUM_FONT);
		g.drawString((game.getPlayerSubmarine().getCourse().getHelm()-Course.HELM_OFFSET)+"/"+game.getPlayerSubmarine().getCourse().getPlane(), x, y+=g.getFont().getSize());
		
		y += PADDING;
		g.setFont(GamePanel.NORMAL_FONT);
		g.drawString("Depth", x, y+=g.getFont().getSize());
		g.setFont(GamePanel.MEDIUM_FONT);
		if (game.getPlayerSubmarine().getDepth() < game.getOceanDepth()+DEPTH_WARNING)
			g.setColor(GamePanel.WARNING_COLOR);
		g.drawString((int)game.getPlayerSubmarine().getDepth()+"", x, y+=g.getFont().getSize());
		
		g.setColor(GamePanel.GRAPHICS_COLOR);
		g.drawRect(bounds.x+PADDING, bounds.y+PADDING, bounds.width - (PADDING*2), y + PADDING);
	}
}
