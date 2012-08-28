package server;

import gameelements.GameElement;
import gameelements.frameworks.AbstractMine;
import gameelements.frameworks.AbstractSubmarine;
import gameelements.frameworks.AbstractTorpedo;
import gameelements.server.ServerGameElement;
import gameelements.server.ServerMine;
import gameelements.server.ServerSubmarine;
import gameelements.server.ServerTorpedo;
import java.io.Serializable;

import main.SynchronizedPoint;

public class GameElementUpdate implements Serializable {
	public static final int UNKNOWN = -1;
	public static final int SUBMARINE = 0;
	public static final int TORPEDO = 1;
	public static final int MINE = 2;
	
	private static final long serialVersionUID = 1L;
	
	public final String owner;
    public final SynchronizedPoint location;
    public final double depth;
    public final String uid;
    public final int elementTypeID;
    
    public GameElementUpdate(String owner, SynchronizedPoint location, double depth, String uid, int elementTypeID)
    {
    	super();
		this.owner = owner;
		this.location = location;
		this.depth = depth;
		this.uid = uid;
		this.elementTypeID = elementTypeID;
    }

    public GameElementUpdate(String owner, SynchronizedPoint location, double depth, String uid, GameElement elementType) {
		super();
		this.owner = owner;
		this.location = location;
		this.depth = depth;
		this.uid = uid;
		
		if (elementType instanceof AbstractSubmarine)
			this.elementTypeID = SUBMARINE;
		else if (elementType instanceof AbstractTorpedo)
			this.elementTypeID = TORPEDO;
		else if (elementType instanceof AbstractMine)
			this.elementTypeID = MINE;
		else
			this.elementTypeID = UNKNOWN;
	}

	public boolean equals(Object o)
    {
    	if (o instanceof GameElementUpdate) {
    		GameElementUpdate u = (GameElementUpdate)o;
    		return uid.equals(u.uid);
    	} else
    		return false;
    }
	
	public String toString()
	{
		return this.owner + "." + this.uid + ": " + this.location.getX() + "," + this.location.getY() + ", " + this.depth + ", " + this.elementTypeID;
	}
	
	public ServerGameElement getNewInstance()
	{
		ServerGameElement element = null;
		if (this.elementTypeID == SUBMARINE) {
			element = new ServerSubmarine(location);
		} else if (this.elementTypeID == TORPEDO) {
			element = new ServerTorpedo(location);
		} else if (this.elementTypeID == MINE) {
			element = new ServerMine(location,false);
		}
		if (element != null)
			element.reset(this);
		return element;
	}
}
