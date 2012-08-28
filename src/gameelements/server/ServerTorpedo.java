package gameelements.server;

import main.SynchronizedPoint;
import server.GameElementUpdate;
import gameelements.DestructiveGameElement;
import gameelements.frameworks.AbstractTorpedo;

public class ServerTorpedo extends AbstractTorpedo implements ServerGameElement, DestructiveGameElement {

	public ServerTorpedo(SynchronizedPoint point) {
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
