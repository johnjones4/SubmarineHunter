package server.server.ui;

import java.util.List;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.rmi.Naming;
import java.util.Arrays;

import server.server.RemoteRepositoryServer;

public class ServerStart {
	public static final String RESOURCES_DIRECTORY = "resources/";
	public static final String PROPERTIES_FILE = "server_preferences.xml";
	
	public static void startServer(PrintStream logStream,Properties properties) throws Exception
	{
		java.rmi.registry.LocateRegistry.createRegistry(1099);
		if (logStream != null)
			logStream.println("RMI registry ready.");
		RemoteRepositoryServer service = new RemoteRepositoryServer(logStream,properties);
		Naming.rebind("SubmarineHunter",service);
		if (logStream != null)
			logStream.println("Server Running.");
	}
	
	public static void main(String[] args) throws IOException
	{
		List<String> args1 = Arrays.asList(args);
		try {
			Properties properties = new Properties();
			properties.loadFromXML(new FileInputStream(new File(RESOURCES_DIRECTORY+PROPERTIES_FILE)));
			if (args1.contains("-l"))
				startServer(System.out,properties);
			else
				startServer(null,properties);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		/*JFrame frame = new JFrame("Submarine Hunter Server");
		frame.getContentPane().add(new ServerPanel(service));
		frame.pack();
		frame.setVisible(true);*/
	}
}
