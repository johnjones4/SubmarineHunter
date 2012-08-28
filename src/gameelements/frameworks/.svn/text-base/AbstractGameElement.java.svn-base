package gameelements.frameworks;

import gameelements.GameElement;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.concurrent.atomic.AtomicReference;

import main.SubmarineHunterStart;
import main.SynchronizedPoint;

import server.GameElementUpdate;
import userinterface.graphics.game.GamePanel;

public abstract class AbstractGameElement implements GameElement {
	private static int counter = 0;
	
    volatile protected SynchronizedPoint location;
    protected AtomicReference<Double> depth;
    protected double collisionRadius;
    protected String uid;
    
    public AbstractGameElement()
    {
    	this(new SynchronizedPoint(0,0));
    }
    
    public AbstractGameElement(SynchronizedPoint point)
    {
    	this.location = point;
    	this.depth = new AtomicReference<Double>(new Double(0));
    	this.uid = AbstractGameElement.generateUIDForObject(this);
    }

    public String getUID() {
    	return uid;
    }
    
    public void draw(Graphics g,Point point,boolean targeted)
    {
    	g.setColor(Color.white);
    	g.drawOval(point.x,point.y,10,10);
    	if (targeted)
    		this.drawSelected(g, point);
    	point.x += 15;
    	this.drawStats(g, point);
    }
    
    protected void drawSelected(Graphics g,Point point)
    {
    	g.setColor(Color.white);
    	g.drawRect(point.x-10,point.y-10,30,30);
    }
    
    public void drawStats(Graphics g, Point point)
    {
    	point.y+=g.getFont().getSize();
    	g.setColor(GamePanel.GRAPHICS_COLOR);
    	g.setFont(GamePanel.SMALL_FONT);
    	g.drawString(uid, point.x, point.y);
    	point.y+=g.getFont().getSize();
    	g.drawString("Z: " + depth, point.x, point.y);
    }

    public String toString() {
        return null;
    }
    
    public SynchronizedPoint getLocation()
    {
    	return location;
    }

    public GameElementUpdate generateUpdate() {
        return new GameElementUpdate(SubmarineHunterStart.getClientIPAddress(),getLocation(),getDepth(),getUID(),this);
    }
    
    public double distanceToGameElement(GameElement element)
    {
    	double d1 = Point.distance(this.getLocation().getX(), this.getLocation().getY(), element.getLocation().getX(), element.getLocation().getY());
    	double zDif = Math.abs(this.getDepth() - element.getDepth());
    	return Math.sqrt(Math.pow(d1, 2)+Math.pow(zDif, 2));
    }

    public double getDepth()
    {
    	return this.depth.get();
    }
    
    public void setDepth(double d)
    {
    	if (d <= 0)
    		this.depth.set(d);
    }
    
    public double getCollisionRadius()
    {
    	return this.collisionRadius;
    }
    
    public boolean isColliding(GameElement element)
    {
    	return this.distanceToGameElement(element) < this.getCollisionRadius() + element.getCollisionRadius();
    }
    
    public static String generateUIDForObject(Object o)
    {
    	return SubmarineHunterStart.getClientIPAddress() + "_" + o.getClass().getName().substring(o.getClass().getName().lastIndexOf('.')+1) + "_" + (counter++);
    }
    
    public boolean equals(Object o) {
    	if (o instanceof GameElement)
    		return this.uid.equals(((GameElement)o).getUID());
    	else
    		return false;
	}
}
