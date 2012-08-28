package server.server;

import java.io.PrintStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import main.SynchronizedPoint;

import server.GameElementUpdate;
import server.RemoteRepository;

public class RemoteRepositoryServer extends UnicastRemoteObject implements RemoteRepository {
	public static final String SERVER_REF = "SERVER";
	public static final String OCEAN_DEPTH = "ocean_depth";
	public static final String MAX_MINES = "max_mines";
	public static final String MINE_SCATTER = "mine_scatter";
	public static final String RANDOM_STEP = "random_placement_step";
	public static final String RANDOM_TRIES = "random_placement_tries";
	private Map<String,GameElementUpdate> elements;
	private Map<String,Client> clients;
	private PrintStream log;
	private Properties properties;
	
	private Lock clientsLock;
	private Lock elementsLock; 
	
	private static final long serialVersionUID = 1L;

	public RemoteRepositoryServer(PrintStream log,Properties properties) throws RemoteException {
		super();
		this.elements = new HashMap<String,GameElementUpdate>();
		this.clients = new HashMap<String,Client>();
		this.log = log;
		this.properties = properties;
		this.clientsLock = new ReentrantLock();
		this.elementsLock = new ReentrantLock();
		this.buildRandomMines();
	}

	public String[] getAllCurrentGameElementUIDs(String sourceIP) throws RemoteException {
		String[] list = null;
		this.elementsLock.lock();
		try {
			list = new String[elements.size()];
			int i=0;
			for(String uid : elements.keySet()) {
				list[i] = uid;
				i++;
			}
		} finally {
			this.elementsLock.unlock();
		}
		log(sourceIP + " called for all current element UIDs.");
		return list;
	}

	public void reportDestroyed(String[] uids,String sourceIP) throws RemoteException {
		this.elementsLock.lock();
		try {
			for (String uid: uids)
				this.elements.remove(uid);
		} finally {
			this.elementsLock.unlock();
		}
		this.clientsLock.lock();
		try {
			for(Client c: clients.values())
				if (!c.getIPAddress().equals(sourceIP))
					for (String uid: uids)
						c.addUIDtoKill(uid);
		} finally {
			this.clientsLock.unlock();
		}
		log(sourceIP + " reported " + uids.length + " destroyed elements.");
	}

	public void updateGameElements(GameElementUpdate[] updates,String sourceIP) throws RemoteException {
		for (GameElementUpdate update: updates)
		{
			if (update != null) {
				this.elementsLock.lock();
				try {
					elements.put(update.uid, update);
					log(update.toString());
				} finally {
					this.elementsLock.unlock();
				}
			}
		}
		log(sourceIP + " updated " + updates.length + " elements.");
	}
	
	public void registerPlayer(String ip) throws RemoteException
	{
		this.clientsLock.lock();
		try {
			clients.put(ip,new Client(ip));
		} finally {
			this.clientsLock.unlock();
		}
		log("Client " + ip + " registered.");
	}

	public String[] getDestroyedGameElements(String sourceIP) throws RemoteException {
		try {
			log(sourceIP + " got destroyed list.");
			LinkedList<String> destroyed = new LinkedList<String>();
			String uid = null;
			this.clientsLock.lock();
			try {
				while ((uid = clients.get(sourceIP).getNextDestoryedUID()) != null) {
					destroyed.add(uid);
				}
			} finally {
				this.clientsLock.unlock();
			}
			return destroyed.toArray(new String[0]);
		} catch (Exception e) {
			return new String[0];
		}
	}

	public GameElementUpdate[] getUpdatesForGameElements(String sourceIP) throws RemoteException {
		LinkedList<GameElementUpdate> updates = new LinkedList<GameElementUpdate>();
		this.elementsLock.lock();
		try {
			for(GameElementUpdate update : elements.values())
				if (!update.owner.equals(sourceIP))
					updates.add(update);
		} finally {
			this.elementsLock.unlock();
		}
		log(sourceIP + " got " + updates.size() + " updates");
		return updates.toArray(new GameElementUpdate[0]);
	}
	
	public SynchronizedPoint negotiateFreeLocation(String sourceIP,int collisionRadius) throws RemoteException {
		SynchronizedPoint finalPoint = new SynchronizedPoint(0,0);
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		while(true)
		{
			if (elements.size() > 0) {
				boolean clear = true;
				int tries = 0;
				int level = Integer.parseInt(properties.get(RANDOM_STEP).toString());
				
				this.elementsLock.lock();
				try {
					for(GameElementUpdate update : elements.values()) {
						if (update.location.equals(finalPoint)) {
							clear = false;
							break;
						}
					}
				} finally {
					this.elementsLock.unlock();
				}
				
				log("Try #" + tries + " Passed: " + clear + " with point: " + finalPoint);
				
				if (clear)
					return finalPoint;
				else {
					tries++;
					if (tries >= Integer.parseInt(properties.get(RANDOM_TRIES).toString()))
					{
						level += Integer.parseInt(properties.get(RANDOM_STEP).toString());
						tries = 0;
					}
					int x = rand.nextInt(level*2)-level;
					int y = rand.nextInt(level*2)-level;
					finalPoint = new SynchronizedPoint(x,y);
				}
			} else {
				return finalPoint;
			}
		}
	}
   
    public int getOceanDepth() throws RemoteException {
    	return -1 * Integer.parseInt(properties.get(OCEAN_DEPTH).toString());
    }

	private void log(final String message)
	{
		if (log != null)
			log.println(new Date().toString() + ": " + message);
	}
	
	private void buildRandomMines()
	{
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		final int maxMines = Integer.parseInt(properties.get(MAX_MINES).toString());
		final int mineScatter = Integer.parseInt(properties.get(MINE_SCATTER).toString());
		final int depth = Integer.parseInt(properties.get(OCEAN_DEPTH).toString());
		for(int i=0;i<(maxMines/2)+rand.nextInt(maxMines/2);i++)
		{
			int x = rand.nextInt(mineScatter*2)-mineScatter;
			int y = rand.nextInt(mineScatter*2)-mineScatter;
			int z = -rand.nextInt(depth);
			GameElementUpdate update = new GameElementUpdate(SERVER_REF, new SynchronizedPoint(x,y), z, SERVER_REF+"-"+i, GameElementUpdate.MINE);
			log("Created mine at " + x + "," + y);
			elements.put(update.uid, update);
		}
	}
}
