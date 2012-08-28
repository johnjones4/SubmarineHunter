package userinterface.graphics.game;

import gameelements.GameElement;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicBoolean;

import userinterface.graphics.game.ghosting.GhostCollection;
import userinterface.graphics.game.ghosting.GhostPoint;
import main.Game;
import main.IterationActionable;
import main.Stoppable;

public class Sonar implements Runnable,Stoppable{
	private static final int SONAR_DELAY = 4;
	private static final int SONAR_PAD = 10;
	private static final Color GUIDE_COLOR = new Color(0,50,0);
	private int range;
	private int max;
	private Rectangle bounds;
	private Game game;
	private Point center;
	private GhostCollection collection;
	private AtomicBoolean stop;
	
	public Sonar(Rectangle rect,Game game,GhostCollection collection)
	{
		this.bounds = rect;
		this.game = game;
		this.stop = new AtomicBoolean(false);
		this.range = 0;
		this.max = bounds.width/2;
		this.center = new Point(bounds.x + bounds.width/2,bounds.y + bounds.height/2);
		this.collection = collection;
	}
	
	private void findElementsInRange()
	{
		game.getManager().iterateWithAction(new IterationActionable() {
			public void performAction(GameElement element) {
				if (element != game.getPlayerSubmarine())
				{
					int distance = (int) game.getPlayerSubmarine().distanceToGameElement(element);
					if ((distance <= range && distance >= range-SONAR_PAD) || distance < SONAR_PAD) {
						collection.addOrUpdate(element);
					} else if (distance > max) {
						collection.remove(element);
					}
				}
			}
		});
	}

	public void run() {
		long start = System.currentTimeMillis();
		while(!stop.get()) {
			if (start + SONAR_DELAY < System.currentTimeMillis())
			{
				start = System.currentTimeMillis();
				range++;
				
				if (range*2 >= bounds.height-10)
				{
					range = 0;
				}
				findElementsInRange();
			}
		}
	}
	
	public void draw(final Graphics g)
	{
		g.setColor(GUIDE_COLOR);
		for(int i=10;i<bounds.height/2;i=i+(bounds.height/2/5)) {
			int x = bounds.x + ((bounds.width - (i*2)) / 2);
			int y = bounds.y + ((bounds.height - (i*2)) / 2);
			g.drawOval(x, y, i*2, i*2);
		}
		
		g.setColor(Color.RED);
		int y2 = ((int) ((int)(bounds.width/2)*Math.sin(Math.toRadians((double)game.getPlayerSubmarine().getCourse().getHelm()))));
		int x2 = (int)Math.sqrt(Math.pow((bounds.width/2), 2)-Math.pow(y2,2));
		if (game.getPlayerSubmarine().getCourse().getHelm() > 90 && game.getPlayerSubmarine().getCourse().getHelm() < 270)
			x2 = -x2;
		g.drawLine(center.x, center.y, center.x+x2, center.y+y2);

		g.setColor(GamePanel.GRAPHICS_COLOR);
		g.drawOval(bounds.x,bounds.y,bounds.width,bounds.height);
		int x = bounds.x + ((bounds.width - (range*2)) / 2);
		int y = bounds.y + ((bounds.height - (range*2)) / 2);
		g.drawOval(x, y, range*2, range*2);
		
		collection.iterateGhosts(new IterationActionable.Ghost() {
			public void performAction(GhostPoint ghostPoint) {
				ghostPoint.draw(g,center);
			}
		});
	}

	public void stop() {
		stop.set(true);
	}
	
	public boolean isStopped() {
		return stop.get();
	}
	
	public void launch() {
		new Thread(this).start();
	}
}