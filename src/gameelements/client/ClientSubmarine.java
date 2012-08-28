package gameelements.client;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import main.Game;
import main.SynchronizedPoint;
import gameelements.GameElement;
import gameelements.client.utils.CollisionDetection;
import gameelements.client.utils.Course;
import gameelements.frameworks.AbstractSubmarine;

public class ClientSubmarine extends AbstractSubmarine implements ClientGameElement {
	public static final int MAX_LIFE = 15;
	public static final int MAX_SPEED = 10;	
	public static final int MAX_TORPEDOS = 40;
	
    private Course course;
    private AtomicInteger life;
    private AtomicBoolean stop;
    private Game game;
    private AtomicInteger torpedosRemaining;
    
    public ClientSubmarine(SynchronizedPoint p,Game game)
    {
    	super(p);
    	this.game = game;
    	this.course = new Course(MAX_SPEED);
    	this.stop = new AtomicBoolean(false);
    	this.life = new AtomicInteger(MAX_LIFE);
    	this.torpedosRemaining = new AtomicInteger(MAX_TORPEDOS);
    	this.depth.set(game.getOceanDepth()/2.0);
    }
    
	public Course getCourse() {
		return course;
	}

	public int getLife() {
		return life.get();
	}

	public void run() {
		long last = System.currentTimeMillis();
		while(!stop.get()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			course.affectCourse(this,System.currentTimeMillis()-last);
			CollisionDetection.detectCollisions(this, game, life);
			last = System.currentTimeMillis();
		}
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
	
	public int getTorpedosRemaining() {
		return this.torpedosRemaining.get();
	}
	
	public void fireTorpedo(GameElement target)
	{
		if (this.torpedosRemaining.decrementAndGet() >= 0) {
			ClientTorpedo tp = new ClientTorpedo(this,target,game);
			tp.launch();
			this.game.getManager().addGameElement(tp);
		}
	}
}
