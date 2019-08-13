package me.jacob.javagame.engine.prop;

import java.awt.Graphics2D;

import me.jacob.javagame.engine.Game;
import me.jacob.javagame.engine.Room;
import me.jacob.javagame.engine.sprite.Sprite;
import me.jacob.javagame.engine.util.Sortable;

public abstract class Prop implements Sortable {
	
	public boolean enabled;
	
	// Entity position
	public double x;
	public double y;
	public double depth;

	// Entity sprite
	public abstract Sprite getSprite();
	
	// Presence
	private Game game;
	private Room room;
	
	/**
	 * Initialization. Declare and setup variables.
	 */
	public Prop(Game game, Room room) {
		enabled = true;
		
		x = 0.0;
		y = 0.0;
		depth = 0.0;

		this.game = game;
		this.room = room;
		
		game.getPropManager().addProp(this);
	}
	
	/**
	 * Called by game when prop needs to be drawn. Calls sprite draw method.
	 * 
	 * @param g2 graphics used to draw with
	 */
	public void draw(Graphics2D g2) {
		getSprite().draw(g2, x, y);
	}

	/**
	 * Called by game when prop needs to be updated.
	 * 
	 * @param delta value used to adjust for change in time between frames
	 */
	public abstract void update(double delta);
	
	/**
	 * Gets the game that the prop is a part of.
	 * 
	 * @return the game associated with the prop
	 */
	public Game getGame() {
		return game;
	}
	
	/**
	 * Gets the room that the prop is bound to.
	 * 
	 * @return the room that the prop is bound to
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * Sets the room that the prop is bound to.
	 * 
	 * @param room the room that the prop is bound to
	 */
	public void setRoom(Room room) {
		this.room = room;
	}
	
	/**
	 * Checks if the prop is persistent (not room bound).
	 * 
	 * @return true, if the prop is persistent
	 */
	public boolean isPersistent() {
		return room == null;
	}

	/**
	 * Gets the dpeth of the prop (used mostly for sorting purposes).
	 *
	 * @return the depth of the prop
	 */
	public double getDepth() {
		return depth;
	}
}
