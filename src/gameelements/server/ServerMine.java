package gameelements.server;

import main.SubmarineHunterStart;
import main.SynchronizedPoint;

import server.GameElementUpdate;
import gameelements.DestructiveGameElement;
import gameelements.frameworks.AbstractMine;

public class ServerMine extends AbstractMine implements ServerGameElement, DestructiveGameElement {
	private boolean server;
	public static final String SERVER_ID_PREFIX = "SERVER_MINE";
	
	public ServerMine(SynchronizedPoint point,boolean server) {
		super(point);
		this.server = server;
	}
	
	public GameElementUpdate generateUpdate() {
		if (server)
			return new GameElementUpdate(SERVER_ID_PREFIX+SubmarineHunterStart.getClientIPAddress(),location, depth.get(), uid, this);
		else
			return new GameElementUpdate(SubmarineHunterStart.getClientIPAddress(),location, depth.get(), uid, this);
    }

	public void update(GameElementUpdate update) {
		if (update != null && update.uid != null && update.uid.equals(this.getUID())) {
			this.location = update.location;
			this.depth.set(update.depth);
		}
	}
	
	public void reset(GameElementUpdate update) {
		if (update != null) {
			this.location = update.location;
			this.depth.set(update.depth);
			this.uid = update.uid;
		}
	}
}
