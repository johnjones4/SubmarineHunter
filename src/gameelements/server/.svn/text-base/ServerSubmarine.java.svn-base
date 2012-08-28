package gameelements.server;

import main.SynchronizedPoint;
import server.GameElementUpdate;
import gameelements.frameworks.AbstractSubmarine;

public class ServerSubmarine extends AbstractSubmarine implements ServerGameElement {

	public ServerSubmarine(SynchronizedPoint point) {
		super(point);
	}

	public void update(GameElementUpdate update) {
		if (update != null && update.uid != null && update.uid.equals(this.getUID())) {
			this.location = update.location;
			this.depth.set(update.depth);
			this.uid = update.uid;
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
