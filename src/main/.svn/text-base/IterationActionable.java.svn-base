package main;

import userinterface.graphics.game.ghosting.GhostPoint;
import gameelements.GameElement;
import gameelements.client.ClientGameElement;
import gameelements.server.ServerGameElement;

public interface IterationActionable {
	public void performAction(GameElement element);
	
	public interface Client {
		public void performAction(ClientGameElement element);
	}
	
	public interface Server {
		public void performAction(ServerGameElement element);
	}
	
	public interface Ghost {
		public void performAction(GhostPoint ghostPoint);
	}
}
