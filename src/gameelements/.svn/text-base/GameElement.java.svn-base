package gameelements;
import java.awt.Graphics;
import java.awt.Point;

import main.SynchronizedPoint;

import server.GameElementUpdate;

public interface GameElement {
	/**
	 * The UID stands for Unique Identification, and it is made up of the object owner's IP address, 
	 * the class name, and a unique-for-that-machine integer. 
	 * @return The UID
	 */
	public String getUID();
	
	/**
	 * Draw the Game Element to the Graphics context.
	 * @param g The Graphics context
	 * @param point The point to draw at
	 * @param targeted If true then draw a "selected" box.  If false then just draw the element.
	 */
	public void draw(Graphics g,Point point,boolean targeted);
	
	/**
	 * A string representation of the game element.
	 * @return The string.
	 */
	public String toString();
	
	/**
	 * Get the location of the Game Element.
	 * @return A Point describing the location
	 */
    public SynchronizedPoint getLocation();
    
    public double getCollisionRadius();
    
    /**
     * Get the depth of the Game Element.
     * @return The depth (d<0)
     */
    public double getDepth();
    
    /**
     * Set the depth of the Game Element
     * @param i The new depth
     */
    public void setDepth(double d);
    
    /**
     * Generate an update to transmit to another machine.
     * @return The GameElementUpdate
     */
    public GameElementUpdate generateUpdate();
    
    /**
     * Get the distance to another element.
     * @param element The other element
     * @return The distance (d>=0) 
     */
    public double distanceToGameElement(GameElement element);
    
    /**
     * Is this Game Element colliding with the other Game Element.
     * @param element The other Game Elemnt
     * @return True if colliding. False if not.
     */
    public boolean isColliding(GameElement element);
    
    public boolean equals(Object o);
}
