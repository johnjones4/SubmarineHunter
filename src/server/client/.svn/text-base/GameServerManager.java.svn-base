package server.client;
import gameelements.GameElement;
import gameelements.client.ClientGameElement;
import gameelements.server.ServerGameElement;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import server.GameElementUpdate;
import server.RemoteRepository;
import main.Game;
import main.IterationActionable;
import main.Stoppable;
import main.SubmarineHunterStart;
import main.SynchronizedPoint;

public class GameServerManager implements Runnable,Stoppable {
    private RemoteRepository service;
    private AtomicBoolean stop;
    private Game game;
    
    public GameServerManager(String serverIP,Game game) throws MalformedURLException, RemoteException, NotBoundException
    {
    	this.service = (RemoteRepository)Naming.lookup("rmi://" + serverIP + "/SubmarineHunter");
    	this.service.registerPlayer(SubmarineHunterStart.getClientIPAddress());
    	this.game = game;
    	this.stop = new AtomicBoolean(false);
    }
    
	public void run() {
		try {
			this.game.setOceanDepth(service.getOceanDepth());
			this.game.gameIsReady();
		} catch (RemoteException e) {}
		
		while (!stop.get())
		{
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {}	
			this.sendUpdates();
			this.getUpdates();
			this.sendDestroyed();
			this.getDestroyed();
		}
	}

	private void sendUpdates()
	{
		final LinkedList<GameElementUpdate> list = new LinkedList<GameElementUpdate>();
		game.getManager().iterateClientsWithAction(new IterationActionable.Client() {
			public void performAction(ClientGameElement element) {
				list.add(element.generateUpdate());
			}
		});
		try {
			service.updateGameElements(list.toArray(new GameElementUpdate[0]),SubmarineHunterStart.getClientIPAddress());
		} catch (RemoteException e) {}
	}

	private void getUpdates()
	{
		try {
			GameElementUpdate[] updates = service.getUpdatesForGameElements(SubmarineHunterStart.getClientIPAddress());
			for(GameElementUpdate update: updates) {
				ServerGameElement element = game.getManager().getServerGameElement(update.uid);
				if (element != null) {
					element.update(update);
				} else /*if (!game.getManager().destroyed().contains(element)) */ {
					game.getManager().addGameElement(update.getNewInstance());
				}
			}
		} catch (RemoteException e) {}
	}
	
	private void sendDestroyed()
	{
		LinkedList<String> destroyed = new LinkedList<String>();
		GameElement el = null;
		while((el = game.getManager().nextDestroyed()) != null)
			destroyed.add(el.getUID());
		try {
			service.reportDestroyed(destroyed.toArray(new String[0]), SubmarineHunterStart.getClientIPAddress());
		} catch (RemoteException e) {}
	}
	
	private void getDestroyed()
	{
		try {
			String[] destroyed = service.getDestroyedGameElements(SubmarineHunterStart.getClientIPAddress());
			for(String uid: destroyed)
			{
				GameElement element = game.getManager().getServerGameElement(uid);
				System.out.println("Got " + element.getUID());
				game.getManager().destoryGameElement(element);
			}
		} catch (RemoteException e) {}
	}
	
	public SynchronizedPoint getFreeLocation()
	{
		try {
			return service.negotiateFreeLocation(SubmarineHunterStart.getClientIPAddress(), 0);
		} catch (RemoteException e) {
			return new SynchronizedPoint(0,0);
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
}
