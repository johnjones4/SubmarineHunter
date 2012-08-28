package gameelements.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import userinterface.graphics.game.GamePanel;
import main.Game;
import main.SynchronizedPoint;
import gameelements.GameElement;
import gameelements.client.utils.CollisionDetection;
import gameelements.client.utils.Course;
import gameelements.frameworks.AbstractTorpedo;

public class ClientTorpedo extends AbstractTorpedo implements ClientGameElement {
	private static final int MAX_LIFE = 3;
	public static final int MAX_SPEED = (int)(ClientSubmarine.MAX_SPEED * 1.5);
	public static final int MAX_CHANGE = 5;
	public static final int LIFETIME = 40;
	
	private AtomicBoolean stop;
	private AtomicInteger life;
	private Course course;
	private ClientSubmarine creator;
    private GameElement target;
    private Game game;
    private AtomicInteger time;
	
    public ClientTorpedo(ClientSubmarine creator,GameElement target,Game game) {
		super((SynchronizedPoint)creator.getLocation().clone());
		this.life = new AtomicInteger(MAX_LIFE);
		this.stop = new AtomicBoolean(false);
		this.depth.set(creator.getDepth());
		this.course = new Course(MAX_SPEED);
		this.course.setHelm(creator.getCourse().getHelm());
		this.course.setPlane(creator.getCourse().getPlane());
		this.course.setSpeed(ClientTorpedo.MAX_SPEED);
		this.game = game;
		this.target = target;
		this.creator = creator;
		this.time = new AtomicInteger(LIFETIME*1000);
	}
    
    public ClientSubmarine getCreator() {
    	return this.creator;
    }
    
    private void adjustCourse()
    {
    	double xLeg = this.getLocation().getX() - target.getLocation().getX();
    	double yLeg = this.getLocation().getX() - target.getLocation().getX();
    	double zLeg = this.getDepth() - target.getDepth();
    	double hypot = Math.sqrt(Math.pow(xLeg, 2) + Math.pow(yLeg, 2));
    	
    	if (xLeg == 0)
    		xLeg = 1;
    	if (yLeg == 0)
    		yLeg = 1;
    	
    	int helm = 0;
    	if (xLeg > 0) {
    		helm = (int) (Math.toDegrees(Math.atan2(yLeg,xLeg))+180);
    	} else {
    		xLeg = - xLeg;
    		helm = (int) (-Math.toDegrees(Math.atan2(yLeg,xLeg)));
    	}
    	
    	int inc = 1;
    	if (Math.abs(this.course.getHelm() - helm) > 10)
    		inc = 10;
    	System.out.println(helm);
    	if (this.course.getHelm() < helm)
    		this.course.incrementHelm(inc);
    	else if (this.course.getHelm() > helm)
    		this.course.incrementHelm(-inc);

    	int plane = (int) (Math.toDegrees(Math.atan2(zLeg,hypot)));
    	if (Math.abs(this.course.getPlane() - plane) > 10)
    		inc = 10;
    	else
    		inc = 1;
    	if (this.course.getPlane() < plane)
    		this.course.incrementPlane(-inc);
    	else if (this.course.getPlane() > plane)
    		this.course.incrementPlane(inc);
    }
	
	public void run() {
		long last = System.currentTimeMillis();
		while(!stop.get()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long d = System.currentTimeMillis()-last;
			if (time.addAndGet(((int)d)*-1) <= 0) {
				game.getManager().destoryGameElement(this);
				continue;
			} else {
				course.affectCourse(this,d);
				CollisionDetection.detectCollisions(this, game, life);
				last = System.currentTimeMillis();
			//	this.adjustCourse();
			}
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
	
	public final void draw(Graphics g,Point point,boolean targeted)
    {
    	g.setColor(Color.red);
    	g.fillOval(point.x-(SIZE/2),point.y-(SIZE/2),SIZE,SIZE);
    	if (targeted)
    		this.drawSelected(g, point);
		point.x += SIZE + 5;
    	this.drawStats(g, point);
    }
	
	public void drawStats(Graphics g, Point point)
    {
    	point.y+=g.getFont().getSize();
    	g.setColor(GamePanel.GRAPHICS_COLOR);
    	g.setFont(GamePanel.SMALL_FONT);
    	g.drawString(uid, point.x, point.y);
    	point.y+=g.getFont().getSize();
    	g.drawString("Z: " + depth, point.x, point.y);
    	
    //	point.y+=g.getFont().getSize();
    //	g.drawString("Heading: " + (course.getHelm()-Course.HELM_OFFSET), point.x, point.y);
    //	point.y+=g.getFont().getSize();
    //	g.drawString("Plane: " + course.getPlane(), point.x, point.y);
    	point.y+=g.getFont().getSize();
        g.drawString("Life: " + this.getLife(), point.x, point.y);
    	point.y+=g.getFont().getSize();
    	g.drawString("Time Left: " + (time.get()/1000), point.x, point.y);
    }

	public int getLife() {
		return life.get();
	}
}
