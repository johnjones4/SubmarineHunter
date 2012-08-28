package gameelements.frameworks;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.SubmarineHunterStart;
import main.SynchronizedPoint;

public abstract class AbstractSubmarine extends AbstractGameElement {
	private Image subImage;
	
    public AbstractSubmarine(SynchronizedPoint point) {
		super(point);
		try {
			subImage = ImageIO.read(new File(SubmarineHunterStart.getResourcesPath() + "sub.png"));
			this.collisionRadius = subImage.getHeight(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    protected Image getImage()
    {
    	return subImage;
    }

    @Override
	public final void draw(Graphics g,Point point,boolean targeted)
	{
    	g.drawImage(getImage(),point.x-(getImage().getWidth(null)/2),point.y-(getImage().getHeight(null)/2),null);
		if (targeted)
			this.drawSelected(g, point);
		point.x += getImage().getWidth(null)+5;
    	this.drawStats(g, point);
	}
    
    protected void drawSelected(Graphics g,Point point)
    {
    	g.setColor(Color.red);
    	g.drawRect(point.x-5-(getImage().getWidth(null)/2),
    			   point.y-5-(getImage().getHeight(null)/2),
    			   10+getImage().getWidth(null),
    			   10+getImage().getHeight(null));
    }
}
