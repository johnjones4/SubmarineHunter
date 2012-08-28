package server;


import java.rmi.Remote;
import java.rmi.RemoteException;

import main.SynchronizedPoint;


public interface RemoteRepository extends Remote {

    public String[] getAllCurrentGameElementUIDs(String sourceIP) throws RemoteException;

    public GameElementUpdate[] getUpdatesForGameElements(String sourceIP) throws RemoteException;
    
    public String[] getDestroyedGameElements(String sourceIP) throws RemoteException;

    public void reportDestroyed(String[] uids,String sourceIP) throws RemoteException;

    public void updateGameElements(GameElementUpdate[] updates,String sourceIP) throws RemoteException;

    public void registerPlayer(String ip) throws RemoteException;
    
    public SynchronizedPoint negotiateFreeLocation(String sourceIP,int collisionRadius) throws RemoteException;
    
    public int getOceanDepth() throws RemoteException;
}