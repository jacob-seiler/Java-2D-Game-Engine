package me.jacob.javagame.engine;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

import me.jacob.javagame.engine.prop.PropManager;
import me.jacob.javagame.engine.util.Logger;

public class Game {

	private boolean running;
	private double amountOfTicks = 0.0;
	private double ns; // Nano seconds
	
	private Room currentRoom;
	
	private DisplayManager displayManager;
	private InputManager inputManager;
	private PropManager propManager;
	
	public Game(String title, Dimension resolution, Dimension window, int fps) {
		// TODO load saved settings ?
		
		// Declare values
		running = true;

		setFPSLock(fps);

		// Setup managers
		displayManager = new DisplayManager(title, resolution, window);
		inputManager = new InputManager(displayManager);
		propManager = new PropManager();
		
		// Setup listeners
		displayManager.setupListeners(inputManager);
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
		if (getInputManager().keyIsPressed(KeyEvent.VK_F11))
			displayManager.setFullScreen(!displayManager.isFullScreen());
		
		if (currentRoom != null) {
			currentRoom.update(delta); // Update current room
			getPropManager().updateAll(delta);
		}
		
		inputManager.tick(); // Update input manager
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
		
		getPropManager().removeNonPersistent(currentRoom);
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

			displayManager.render(currentRoom, propManager); // Call render
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
	 * Gets the display manager.
	 * 
	 * @return the display manager
	 */
	public DisplayManager getDisplayManager() {
		return displayManager;
	}
	
	/**
	 * Gets the input manager.
	 * 
	 * @return the input manager
	 */
	public InputManager getInputManager() {
		return inputManager;
	}
	
	/**
	 * Gets the prop manager.
	 * 
	 * @return the prop manager
	 */
	public PropManager getPropManager() {
		return propManager;
	}

}
