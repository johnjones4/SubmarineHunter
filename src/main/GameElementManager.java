package main;

import gameelements.GameElement;
import gameelements.client.ClientGameElement;
import gameelements.frameworks.AbstractMine;
import gameelements.server.ServerGameElement;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import userinterface.graphics.game.ghosting.GhostCollection;

public class GameElementManager {
    private Map<String,ServerGameElement> serverElements;
    private Collection<ClientGameElement> clientElements;
    private Queue<GameElement> locallyDestroyed;
    private GhostCollection collection;
    final private Lock serverLock;
    final private Lock clientLock;
    final private Lock destroyedLock;
    
    public GameElementManager(GhostCollection collection)
    {
    	serverElements = new HashMap<String,ServerGameElement>();
    	clientElements = new LinkedList<ClientGameElement>();
    	locallyDestroyed = new LinkedList<GameElement>();
    	serverLock = new ReentrantLock();
    	clientLock = new ReentrantLock();
    	destroyedLock = new ReentrantLock();
    	this.collection = collection;
    }
    
    public int size()
    {
    	clientLock.lock();
    	serverLock.lock();
    	try {
    		return serverElements.size() + clientElements.size();
    	} finally {
    		clientLock.unlock();
    		serverLock.unlock();
    	}
    }
    
    public ServerGameElement getServerGameElement(String UID) {
    	serverLock.lock();
    	ServerGameElement e = null;
    	try {
    		e = serverElements.get(UID);
    	} finally {
    		serverLock.unlock();
    	}
    	return e;
    }

    public void destoryGameElement(GameElement element) {
    	collection.remove(element);
    	if (element instanceof ServerGameElement) {
    		serverLock.lock();
    		try {
    			serverElements.remove(element.getUID());
    		} finally {
    			serverLock.unlock();
    		}
        } else if (element instanceof ClientGameElement ){
        	((Stoppable)element).stop();
        	clientLock.lock();
        	try {
    			clientElements.remove(element);
        	} finally {
        		clientLock.unlock();
        	}
    	}
        	
        if (element instanceof ClientGameElement || element instanceof AbstractMine) {
    		destroyedLock.lock();
    		try {
    			locallyDestroyed.add(element);
    		} finally {
    			destroyedLock.unlock();
    		}
       	}
    }
    
    public void destoryAllClientElements()
    {
    	clientLock.lock();
    	try {
			for(ClientGameElement element: this.clientElements)
			{
				element.stop();
			}
			clientElements.clear();
    	} finally {
    		clientLock.unlock();
    	}
    }
    
    public void addGameElement(GameElement element){
    	if (element instanceof ServerGameElement) {
    		serverLock.lock();
    		try {
    			serverElements.put(element.getUID(),(ServerGameElement)element);
    		} finally {
    			serverLock.unlock();
    		}
    	} else if  (element instanceof ClientGameElement) {
    		clientLock.lock();
    		try {
				clientElements.add((ClientGameElement) element);
    		} finally {
    			clientLock.unlock();
    		}
    	}
    }

    public void stopAll()
    {
    	clientLock.lock();
    	try {
	    	for(ClientGameElement el: this.clientElements)
	    		el.stop();
    	} finally {
			clientLock.unlock();
		}
    }
    
    public GameElement nextDestroyed()
    {
    	destroyedLock.lock();
    	GameElement e = null;
    	try {
    		e = locallyDestroyed.poll();
    	} finally {
    		destroyedLock.unlock();
    	}
    	return e;
    }
	
	public void iterateWithAction(IterationActionable action)
	{
		serverLock.lock();
		try {
			for(ServerGameElement element: this.serverElements.values())
				action.performAction(element);
		} finally {
			serverLock.unlock();
		}
		
		clientLock.lock();
		try {
			for(ClientGameElement element: this.clientElements)
				action.performAction(element);
		} finally {
			clientLock.unlock();
		}
	}
	
	public void iterateClientsWithAction(IterationActionable.Client action)
	{
		clientLock.lock();
		try {
			for(ClientGameElement element: this.clientElements)
				action.performAction(element);
		} finally {
			clientLock.unlock();
		}
	}
	
	public void iterateServersWithAction(IterationActionable.Server action)
	{
		serverLock.lock();
		try {
			for(ServerGameElement element: this.serverElements.values())
				action.performAction(element);
		} finally {
			serverLock.unlock();
		}
	}
}
