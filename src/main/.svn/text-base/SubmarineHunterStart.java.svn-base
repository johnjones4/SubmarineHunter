package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import userinterface.graphics.DisplayController;

public class SubmarineHunterStart {
	private static String ipAddress = null;
	private static Properties properties;
	private static String pathMod = "";
	public static final String RESOURCES_DIRECTORY = "resources/";
	public static final String PROPERTIES_FILE = "client_preferences.xml";
	
	//Properties
	public static final String SERVER_IP_ADDRESS = "server_ip_address";
	public static final String DISPLAY_WIDTH = "display_width";
	public static final String DISPLAY_HEIGHT = "display_height";

	public static void main(String[] args) {
		
		if (args.length >= 1)
		{
			pathMod = args[0];
		}
		
		DisplayController displayController = null;
		Game game = null;
		try {
			properties = new Properties();
			properties.loadFromXML(new FileInputStream(new File(getResourcesPath()+PROPERTIES_FILE)));
			String newIP = JOptionPane.showInputDialog("Please enter the game server's IP address.",getProperty(SERVER_IP_ADDRESS));
			if (newIP != null) {
				setProperty(SERVER_IP_ADDRESS,newIP);
				game = new Game(getProperty(SERVER_IP_ADDRESS));
				displayController = new DisplayController();
				while (!game.isReady()) { }
				displayController.beginDoubleBufferedRendering(game);
			}
		} catch (Exception e) {
			if (game != null)
				game.stopAll();
			System.err.println(e.getClass());
			System.err.println(e.getMessage());
			if (displayController != null)
				displayController.close();
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	public static String getClientIPAddress()
	{
		if (ipAddress == null) {
			try {
				ipAddress = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				ipAddress = "UNKNOWN";
			}
		}
		return ipAddress;	
	}
	
	public static String getProperty(String property)
	{
		return properties.get(property).toString();
	}
	
	public static void setProperty(String property, String value)
	{
		properties.setProperty(property, value);
		try {
			properties.storeToXML(new FileOutputStream(new File(getResourcesPath()+PROPERTIES_FILE)), "Submarine Hunter Properties");
		} catch (Exception e) {}
	}
	
	public static String getResourcesPath()
	{
		return pathMod + RESOURCES_DIRECTORY;
	}
}
