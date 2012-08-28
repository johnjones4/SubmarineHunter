package gameelements.frameworks;

import gameelements.DestructiveGameElement;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import main.SynchronizedPoint;

public class AbstractTorpedo extends AbstractGameElement implements DestructiveGameElement {
	private static final int YIELD = 8;
	protected static final int SIZE = 8;
	
    public AbstractTorpedo(SynchronizedPoint point) {
		super(point);
		this.collisionRadius = SIZE;
	}

	public int getYield() {
		return YIELD;
	}
	
	public void draw(Graphics g,Point point,boolean targeted)
    {
    	g.setColor(Color.red);
    	g.fillOval(point.x-(SIZE/2),point.y-(SIZE/2),SIZE,SIZE);
    	if (targeted)
    		this.drawSelected(g, point);
    	point.x += SIZE + 5;
    	this.drawStats(g, point);
    }
	
	protected void drawSelected(Graphics g,Point point)
    {
    	g.setColor(Color.red);
    	g.drawRect(point.x-10,point.y-10,20+SIZE,20+SIZE);
    }
}
