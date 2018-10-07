package me.jacob.zombiegame.engine.entity;

import java.awt.Graphics2D;

import me.jacob.zombiegame.engine.sprite.Sprite;
import me.jacob.zombiegame.engine.sprite.SpriteImage;

public abstract class Entity {
	
	// TODO depth?
	
	// Entity position
	public double x;
	public double y;

	// Entity sprite
	public abstract Sprite getSprite();
	private EntityMask mask;
	
	/**
	 * Initialization. Sets up variables.
	 */
	public Entity() {
		x = 0.0;
		y = 0.0;
		
		mask = null; // Declare mask
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
	
}
