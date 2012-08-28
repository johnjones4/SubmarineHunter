package server.server;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client {
	private Queue<String> uidsToKill;
	private String ipAddress;
	private Lock uidsToKillLock;
	
	public Client(String ip)
	{
		this.ipAddress = ip;
		this.uidsToKill = new LinkedList<String>();
		uidsToKillLock = new ReentrantLock();
	}
	
	public String getIPAddress()
	{
		return this.ipAddress;
	}
	
	public void addUIDtoKill(String uid)
	{
		this.uidsToKillLock.lock();
		this.uidsToKill.add(uid);
		this.uidsToKillLock.unlock();
	}
	
	public String getNextDestoryedUID()
	{
		this.uidsToKillLock.lock();
		String uid = uidsToKill.poll();
		this.uidsToKillLock.unlock();
		return uid;
	}
}
