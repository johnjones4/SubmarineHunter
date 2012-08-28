package gameelements.client.utils;

import java.util.concurrent.atomic.AtomicInteger;
import gameelements.DestructiveGameElement;
import gameelements.GameElement;
import gameelements.client.ClientTorpedo;
import main.Game;
import main.IterationActionable;

public class CollisionDetection {
	public static final int NORMAL_COLLISION_AFFECT = 1;
	
	public static final void detectCollisions(final GameElement self, final Game game, final AtomicInteger life)
	{
		if (self.getDepth() <= game.getOceanDepth())
			affectLife(self,game,life,life.get());
		else {
			game.getManager().iterateWithAction(new IterationActionable() {
				public void performAction(GameElement element) {
					if (
							!(
									element instanceof ClientTorpedo 
									&& ((ClientTorpedo)element).getCreator() == self
							)
							&&
							!(
									self instanceof ClientTorpedo 
									&& ((ClientTorpedo)self).getCreator() == element
							)
							&& 
							(
									element != self 
									&& self.isColliding(element)
							)
						) {
						String uid1 = self.getUID();
						String uid2 = element.getUID();
						System.out.println("Collision between: " + uid1 + " and " + uid2);
						if (element instanceof DestructiveGameElement)
							CollisionDetection.affectLife(self, game, life, ((DestructiveGameElement) element).getYield());
						else
							CollisionDetection.affectLife(self, game, life, NORMAL_COLLISION_AFFECT);
					}
				}
			});
		}
	}
	
	private static void affectLife(GameElement self, Game game, AtomicInteger life, int dLife)
	{
		life.addAndGet(-dLife);
		if (life.get() <= 0) {
			game.getManager().destoryGameElement(self);
			if (self == game.getPlayerSubmarine())
				game.gameOver();
		}
	}
}
