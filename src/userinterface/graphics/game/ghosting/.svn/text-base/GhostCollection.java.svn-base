package userinterface.graphics.game.ghosting;

import gameelements.GameElement;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import main.Game;
import main.IterationActionable;

public class GhostCollection {
	private List<GhostPoint> visible;
	private Game game;
	private int target;
	Lock lock = new ReentrantLock();
	
	public GhostCollection(Game game)
	{
		this.game = game;
		this.visible = new LinkedList<GhostPoint>();
		this.target = 0;
	}
	
	public void addOrUpdate(GameElement element)
	{
		lock.lock();
		try {
			boolean updated = false;
			for(GhostPoint p: visible)
			{
				if (p.getElement() == element) 
				{
					p.update();
					updated = true;
				}
			}
			if (!updated)
				visible.add(new GhostPoint(element,game));
		} finally {
			lock.unlock();
		}
	}
	
	public boolean remove(GameElement element)
	{
		lock.lock();
		boolean removed = false;
		try {
			for(GhostPoint p: visible)
			{
				if (p.getElement() == element) 
				{
					visible.remove(p);
					removed = true;
					break;
				}
			}
		} finally {
			lock.unlock();
		}
		return removed;
	}
	
	public GameElement getTarget()
	{
		GameElement e = null;
		lock.lock();
		try {
			e = visible.get(target).getElement();
		} finally {
			lock.unlock();
		}
		return e;
	}
	
	public void setTargeting(boolean t)
	{
	/*	lock.lock();
		try {
			if (target<visible.size() && visible.size() > 0)
				visible.get(target).target(t);
		} finally {
			lock.unlock();
		}*/
	}
	
	public void setNextTarget()
	{
		lock.lock();
		try {
			if (target < visible.size()-1 && visible.size() > 0)
			{
				visible.get(target).target(false);
				target++;
				visible.get(target).target(true);
			}
		} finally {
			lock.unlock();
		}
	}
	
	public void setPreviousTarget()
	{
		lock.lock();
		try {
			if (target > 0 && visible.size() > 0)
			{
				visible.get(target).target(false);
				target--;
				visible.get(target).target(true);
			}
		} finally {
			lock.unlock();
		}
	}
	
	public void iterateGhosts(IterationActionable.Ghost action)
	{
		lock.lock();
		try {
			for (GhostPoint point : visible)
				action.performAction(point);
		} finally {
			lock.unlock();
		}
	}
}
