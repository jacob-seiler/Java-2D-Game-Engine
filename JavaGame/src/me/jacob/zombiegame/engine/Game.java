package me.jacob.zombiegame.engine;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import me.jacob.zombiegame.engine.entity.EntityManager;
import me.jacob.zombiegame.engine.util.Logger;

public class Game extends Canvas {

	private static final long serialVersionUID = -6500528472638126288L; // Generated serial

	private boolean running;
	private Dimension resolution;
	
	// Full screen variables
	private boolean fullScreen;
	private Dimension oldSize;
	private Point oldLocation;
	
	private JFrame frame;

	private double amountOfTicks = 0.0;
	private double ns; // Nano seconds
	
	private Room currentRoom;
	
	private DisplayManager displayManager;
	private InputManager inputManager;
	private EntityManager entityManager;
	
	private BufferStrategy strategy;
	
	public Game(String title, Dimension resolution, Dimension window, int fps) {
		// TODO load saved settings ?
		
		// Declare values
		running = true;
		this.resolution = resolution;
		fullScreen = false;

		setFPSLock(fps);
		
		// Declare frame
		frame = new JFrame(title);
		
		// Setup panel
		JPanel panel = (JPanel) frame.getContentPane();
		frame.setPreferredSize(window);
		frame.setMinimumSize(new Dimension(window.width / 2, window.height / 2));

		// Setup managers
		displayManager = new DisplayManager(frame, resolution);
		inputManager = new InputManager(displayManager);
		entityManager = new EntityManager();
		
		// Setup listeners
		addKeyListener(inputManager);
		addMouseListener(inputManager);
		addMouseMotionListener(inputManager);
		
		panel.add(this);
		displayManager.fixSize(window);
		
		// Tell AWT that we will control when the canvas is repainted
		setIgnoreRepaint(true);
		
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		// Buffer Strategy
		createBufferStrategy(2);
		strategy = getBufferStrategy();
	}
	
	/**
	 * Adjusts the frame to be either windowed mode or full screen mode.
	 * 
	 * @param fullScreen true to set the window to full screen mode
	 */
	public void setFullScreen(boolean fullScreen) {
		if (this.fullScreen == fullScreen)
			return;
		
		this.fullScreen = fullScreen;
		
		frame.setVisible(false);
		frame.dispose();
		frame.setUndecorated(fullScreen);
		
		if (fullScreen) {
			oldSize = frame.getSize();
			oldLocation = frame.getLocation();
			
			frame.setLocation(0, 0);
			frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		} else {
			frame.setSize(oldSize);
			frame.setLocation(oldLocation);
		}
		
		frame.pack();
		frame.setVisible(true);
		render();
		frame.toFront();
	}

	/**
	 * Set the frame rate for the game to run at.
	 * 
	 * @param frameRate the desired amount of frames per second
	 */
	public void setFPSLock(double frameRate) {
		this.amountOfTicks = frameRate;
		this.ns = 1000000000 / amountOfTicks;
	}

	/**
	 * Call all related update functions.
	 * 
	 * @param delta value used to adjust for change in time between frames
	 */
	private void tick(double delta) {
		if (currentRoom != null) {
			currentRoom.update(delta); // Update current room
			getEntityManager().updateEntities(delta);
		}
		
		inputManager.tick(); // Update key manager
	}

	/**
	 * Call all related render functions.
	 */
	private void render() {
		Graphics2D g2 = (Graphics2D) strategy.getDrawGraphics();
		displayManager.scale(g2); // Scale
		
		// Draw room
		if (currentRoom != null) {
			currentRoom.draw(g2);
			getEntityManager().drawEntities(g2);
		}
		
		displayManager.reset(g2);
		displayManager.drawBars(g2); // Add black bars
		
		// Flip buffer
		g2.dispose();
		strategy.show();
	}

	/**
	 * Gets the current room being rendered and drawn.
	 * 
	 * @return the room in use
	 */
	public Room getCurrentRoom() {
		return currentRoom;
	}

	/**
	 * Sets the current room being rendered and drawn.
	 * 
	 * @param room the room to use
	 */
	public void setCurrentRoom(Room room) {
		if (this.currentRoom == room)
			return;
		
		currentRoom = room;
		
		getEntityManager().clean(currentRoom);
	}

	/**
	 * Starts the game loop that renders and updates the game.
	 */
	public void start() {
		// Used for calculations
		long lastTime = System.nanoTime(); // Store time reference
		double delta = 0;

		// Used for measuring performance
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;

		while (running) {
			long now = System.nanoTime(); // Get current time

			delta += (now - lastTime) / ns; // Calculate difference in time
			lastTime = now; // Update time reference

			// While it has been more than a frame
			while (delta >= 1) {
				tick(delta * (60 / amountOfTicks)); // Call update
				updates++;
				delta--;
			}

			render(); // Call render
			frames++;

			// Print performance statistics every second
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000; // Add another second delay

				Logger.info("FPS: " + frames + ", TICKS: " + updates);

				// Reset
				frames = 0;
				updates = 0;
			}
		}
	}
	
	/**
	 * Gets the input manager
	 * 
	 * @return the input manager
	 */
	public InputManager getInputManager() {
		return inputManager;
	}
	
	/**
	 * Gets the entity manager
	 * 
	 * @return the entity manager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Gets the size for drawing to the frame.
	 * 
	 * @return the correct size
	 */
	public Dimension getResolution() {
		return resolution;
	}

}
