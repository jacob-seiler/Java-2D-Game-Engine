package me.jacob.zombiegame.engine.entity;

import java.awt.Graphics2D;

import me.jacob.zombiegame.engine.Game;
import me.jacob.zombiegame.engine.Room;
import me.jacob.zombiegame.engine.sprite.Sprite;
import me.jacob.zombiegame.engine.sprite.SpriteImage;
import me.jacob.zombiegame.engine.util.Sortable;

public abstract class Entity implements Sortable {
	
	public boolean enabled;
	
	// Entity position
	public double x;
	public double y;
	public double depth;

	// Entity sprite
	public abstract Sprite getSprite();
	private EntityMask mask;
	
	// Presence
	private Game game;
	private Room room;
	
	/**
	 * Initialization. Declare and setup variables.
	 */
	public Entity(Game game, Room room) {
		enabled = true;
		
		x = 0.0;
		y = 0.0;
		depth = 0.0;

		this.game = game;
		this.room = room;
		
		mask = null; // Declare mask
		
		game.getEntityManager().addEntity(this);
	}
	
	/**
	 * Called by game when entity needs to be drawn. Calls sprite draw method.
	 * 
	 * @param g2 graphics used to draw with
	 */
	public void draw(Graphics2D g2) {
		getSprite().draw(g2, x, y);
	}

	/**
	 * Called by game when entity needs to be updated.
	 * 
	 * @param delta value used to adjust for change in time between frames
	 */
	public abstract void update(double delta);
	
	/**
	 * Gets/configures the entity mask for this entity.
	 * 
	 * @return the entity mask for this entity
	 */
	public EntityMask getMask() {
		if (getSprite() == null)
			return null;
		
		SpriteImage sImg = getSprite().getCurrentFrame();
		
		// Get sprite size
		int width = 0;
		int height = 0;
		
		if (sImg.getSize() != null) {
			width = sImg.getSize().width;
			height = sImg.getSize().height;
		}
		
		// Offset mask based on origin
		int posX = (int) (x - (width * sImg.getOrigin().getOffsetX()));
		int posY = (int) (y - (height * sImg.getOrigin().getOffsetY()));
		
		mask.setPos(posX, posY);
		return mask;
	}
	
	/**
	 * Gets the game that the entity is a part of.
	 * 
	 * @return the game associated with the entity
	 */
	public Game getGame() {
		return game;
	}
	
	/**
	 * Gets the room that the entity is bound to.
	 * 
	 * @return the room that the entity is bound to
	 */
	public Room getRoom() {
		return room;
	}
	
	
	/**
	 * Sets the room that the entity is bound to.
	 * 
	 * @param room the room that the entity is bound to
	 */
	public void setRoom(Room room) {
		this.room = room;
	}
	
	/**
	 * Checks if the entity is persistent (not room bound).
	 * 
	 * @return true, if the entity is persistent
	 */
	public boolean isPersistent() {
		return room == null;
	}
	
	/**
	 * Gets the depth value of the entity for sorting purposes.
	 * 
	 * @return the entity depth value
	 */
	public double getDepth() {
		return depth;
	}
	
}
