package userinterface.graphics.game.ghosting;

import gameelements.GameElement;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;

import main.Game;

public class GhostPoint {
	private Game game;
	private GameElement element;
	private Point2D.Double location;
	private Dimension offset;
	private boolean isTarget;
	
	public GhostPoint(GameElement element,Game game)
	{
		this.element = element;
		this.game = game;
		this.location = new Point2D.Double(element.getLocation().getX(),element.getLocation().getY());
		this.offset = new Dimension((int)game.getPlayerSubmarine().getLocation().getX(),(int)game.getPlayerSubmarine().getLocation().getY());
		this.isTarget = false;
	}
	
	public void update()
	{
		location = new Point2D.Double(element.getLocation().getX(),element.getLocation().getY());
		offset.width = (int)game.getPlayerSubmarine().getLocation().getX();
		offset.height = (int)game.getPlayerSubmarine().getLocation().getY();
	}

	public void draw(Graphics g,Point center)
	{
		element.draw(g,new Point((int)(center.x+location.x-offset.width),(int)(center.y+location.y-offset.height)),isTarget);
	}
	
	public void target(boolean t)
	{
		this.isTarget = t;
	}
	
	public GameElement getElement()
	{
		return element;
	}
}
