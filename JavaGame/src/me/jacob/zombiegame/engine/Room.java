package me.jacob.zombiegame.engine;

import java.awt.Graphics2D;
import java.util.List;

import me.jacob.zombiegame.engine.entity.Entity;

public abstract class Room {

	public abstract List<Entity> getEntities();
	
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
