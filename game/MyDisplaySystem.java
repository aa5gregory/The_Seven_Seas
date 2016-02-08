package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import sage.display.*;
import sage.renderer.*;

public class MyDisplaySystem implements IDisplaySystem {

	private JFrame myFrame;
	private GraphicsDevice device;
	private IRenderer myRenderer;
	private int width, height, bitDepth, refreshRate;
	private Canvas rendererCanvas;
	private boolean isCreated = false;
	public MyDisplaySystem(String rName){
	
		//get a renderer from the RendererFactory
		myRenderer = RendererFactory.createRenderer(rName);
		if (myRenderer == null){
			throw new RuntimeException("Unable to find renderer");
		}
		rendererCanvas = myRenderer.getCanvas();
		myFrame = new JFrame("Default Title");
		myFrame.add(rendererCanvas);
		
		//initialize the screen with the specified parameters
		initScreen();
		
		//save DisplaySystem, show frame and indicate DisplaySystem is created
		DisplaySystem.setCurrentDisplaySystem(this);
		myFrame.setVisible(true);
		isCreated = true;
	}
	private void initScreen(){
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
		
		DisplaySettingsDialog dialog = new DisplaySettingsDialog(device);
		dialog.showIt();
		DisplayMode dispMode = dialog.getSelectedDisplayMode();
		
		if (device.isFullScreenSupported() && dialog.isFullScreenModeSelected()){
			myFrame.setUndecorated(true);
			// removes title and borders 
			myFrame.setResizable(false);
			// full-screen + not resizeable
			myFrame.setIgnoreRepaint(true);
			device.setFullScreenWindow(myFrame);
			if (dispMode != null && device.isDisplayChangeSupported()){
				try{
					device.setDisplayMode(dispMode);
					myFrame.setSize(dispMode.getWidth(), dispMode.getHeight());
				} catch (Exception ex){
					System.err.println("Exception setting DisplayMode: " + ex );
				}
			} else{
				System.err.println ("Cannot set display mode"); }
		} else{
			//use windowed mode
			myFrame.setSize(dispMode.getWidth(),dispMode.getHeight());
			myFrame.setLocationRelativeTo(null);
		}
	}	

	@Override
	public void addKeyListener(KeyListener arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addMouseListener(MouseListener arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addMouseMotionListener(MouseMotionListener arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void close() {
		if (device != null){
			Window window = device.getFullScreenWindow();
			if (window != null){
				window.dispose();
			}	
			device.setFullScreenWindow(null);
		} 
	}
	
	@Override
	public void convertPointToScreen(Point arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public int getBitDepth() {
		return this.bitDepth;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getRefreshRate() {
		return this.refreshRate;
	}

	public IRenderer getRenderer() {
		return myRenderer;
	}

	public int getWidth() {
		return this.width;
	}

	public boolean isCreated() {
		return this.isCreated;
	}
	
	public boolean isShowing() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setBitDepth(int bit) {
		this.bitDepth = bit;
	}
	@Override
	public void setCustomCursor(String arg0) {
		// TODO Auto-generated method stub
	}
	
	public void setHeight(int height) {
		this.height = height;
		
	}
	@Override
	public void setPredefinedCursor(int arg0) {
		// TODO Auto-generated method stub
	}

	public void setRefreshRate(int refresh) {
		this.refreshRate = refresh;
	}
	@Override
	public void setTitle(String title) {
		myFrame.setTitle(title);
		
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	@Override
	public boolean isFullScreen() {
		// TODO Auto-generated method stub
		return false;
	}
}