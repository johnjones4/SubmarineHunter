package main;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.atomic.AtomicBoolean;

import server.client.GameServerManager;
import userinterface.graphics.game.GamePanel;
import userinterface.graphics.game.ghosting.GhostCollection;
import gameelements.client.ClientSubmarine;

public class Game {
	private GameElementManager manager;
    private ClientSubmarine playerSubmarine;
    private GamePanel panel;
    private GameServerManager serverManager;
    private double oceanDepth;
    private AtomicBoolean ready;
    private AtomicBoolean gameOver;
    private GhostCollection collection;
    
    public Game(String serverIP) throws MalformedURLException, RemoteException, NotBoundException
    {
    	ready = new AtomicBoolean(false);
    	gameOver = new AtomicBoolean(false);
    	collection = new GhostCollection(this);
	   	manager = new GameElementManager(collection);
	   	serverManager = new GameServerManager(serverIP,this);
	   	serverManager.launch();
    }
    
    public Game() throws MalformedURLException, RemoteException, NotBoundException
    {
    	this("127.0.0.1");
    }
    
    public GhostCollection getGhostCollection()
	{
		return collection;
	}
    
    public void stopAll()
    {
    	manager.stopAll();
    	serverManager.stop();
    }

	public GameElementManager getManager() {
		return manager;
	}

	public ClientSubmarine getPlayerSubmarine() {
		return playerSubmarine;
	}
	
	public void setActivePanel(GamePanel p)
	{
		this.panel = p;
	}
	
	public GamePanel getActivePanel()
	{
		return this.panel;
	}

    public double getOceanDepth()
    {
    	return this.oceanDepth;
    }
    
    public void setOceanDepth(int d)
    {
    	this.oceanDepth = d;
    }
    
    public void gameIsReady()
    {
    	playerSubmarine = new ClientSubmarine(serverManager.getFreeLocation(),this);
    	playerSubmarine.launch();
    	manager.addGameElement(playerSubmarine);
    	ready.set(true);
    }
    
    public boolean isReady()
    {
    	return ready.get();
    }
    
    public void gameOver() {
    	System.out.println("Game over");
    	this.gameOver.set(true);
    	this.manager.destoryAllClientElements();
    }
    
    public boolean isGameOver() {
    	return this.gameOver.get();
    }
}
