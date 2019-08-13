package me.jacob.javagame.engine;

import java.awt.Graphics2D;

public abstract class Room {

	private Game game;
	
	public Room(Game game) {
		this.game = game;
	}

	/**
	 * Gets the game that the room is a part of.
	 * 
	 * @return the game associated with the room
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Called by game when room needs to be updated.
	 * 
	 * @param delta value used to adjust for change in time between frames
	 */
	public abstract void update(double delta);

	/**
	 * Called by game when room needs to be drawn.
	 * 
	 * @param g2 graphics used to draw with
	 */
	public abstract void draw(Graphics2D g2);

}
