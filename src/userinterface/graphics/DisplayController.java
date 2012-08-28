package userinterface.graphics;
import java.awt.*;
import java.awt.image.BufferStrategy;
import userinterface.graphics.game.GamePanel;
import userinterface.input.KeyboardController;

import main.Game;
import main.SubmarineHunterStart;

public class DisplayController {
	private Frame mainFrame;
	
	private static DisplayMode[] BEST_DISPLAY_MODES = new DisplayMode[] {
        new DisplayMode(Integer.parseInt(SubmarineHunterStart.getProperty(SubmarineHunterStart.DISPLAY_WIDTH))
        				,Integer.parseInt(SubmarineHunterStart.getProperty(SubmarineHunterStart.DISPLAY_HEIGHT)), 32, 0),
        new DisplayMode(Integer.parseInt(SubmarineHunterStart.getProperty(SubmarineHunterStart.DISPLAY_WIDTH))
						,Integer.parseInt(SubmarineHunterStart.getProperty(SubmarineHunterStart.DISPLAY_HEIGHT)), 16, 0),
        new DisplayMode(Integer.parseInt(SubmarineHunterStart.getProperty(SubmarineHunterStart.DISPLAY_WIDTH))
						,Integer.parseInt(SubmarineHunterStart.getProperty(SubmarineHunterStart.DISPLAY_HEIGHT)), 8, 0)
    };
	
	private static DisplayMode getBestDisplayMode(GraphicsDevice device) {
        for (int x = 0; x < BEST_DISPLAY_MODES.length; x++) {
            DisplayMode[] modes = device.getDisplayModes();
            for (int i = 0; i < modes.length; i++) {
                if (modes[i].getWidth() == BEST_DISPLAY_MODES[x].getWidth()
                   && modes[i].getHeight() == BEST_DISPLAY_MODES[x].getHeight()
                   && modes[i].getBitDepth() == BEST_DISPLAY_MODES[x].getBitDepth()
                   ) {
                    return BEST_DISPLAY_MODES[x];
                }
            }
        }
        return null;
    }
	
	public static void chooseBestDisplayMode(GraphicsDevice device) {
        DisplayMode best = getBestDisplayMode(device);
        if (best != null) {
            device.setDisplayMode(best);
        }
    }
	
	public void beginDoubleBufferedRendering(Game game)
	{
		this.close();
		GraphicsEnvironment env = GraphicsEnvironment.
        getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		
		try {
            GraphicsConfiguration gc = device.getDefaultConfiguration();
            GamePanel panel = new GamePanel(game);
            mainFrame = new Frame(gc);
            mainFrame.setUndecorated(true);
            mainFrame.setIgnoreRepaint(true);
            mainFrame.addKeyListener(new KeyboardController(game));
            device.setFullScreenWindow(mainFrame);
            if (device.isDisplayChangeSupported()) {
                chooseBestDisplayMode(device);
            }
            Rectangle bounds = mainFrame.getBounds();
            int numBuffers = 2;
            mainFrame.createBufferStrategy(numBuffers);
            BufferStrategy bufferStrategy = mainFrame.getBufferStrategy();
            int lag = 10;
            while(true) {
                for (int i = 0; i < numBuffers; i++) {
                    Graphics g = bufferStrategy.getDrawGraphics();
                    if (!bufferStrategy.contentsLost()) {
                    	g.setColor(Color.black);
                        g.fillRect(0,0,bounds.width, bounds.height);
                        panel.draw(g);
                        bufferStrategy.show();
                        g.dispose();
                    }
                    try {
                        Thread.sleep(lag);
                    } catch (InterruptedException e) {}
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            device.setFullScreenWindow(null);
        }
	}
	
	public void close() {
		if (mainFrame != null)
			mainFrame.dispose();
	}
	
	public static int getGamePixelWidth()
	{
		return Integer.parseInt(SubmarineHunterStart.getProperty(SubmarineHunterStart.DISPLAY_WIDTH));
	}
	
	public static int getGamePixelHeight()
	{
		return Integer.parseInt(SubmarineHunterStart.getProperty(SubmarineHunterStart.DISPLAY_HEIGHT));
	}
}
