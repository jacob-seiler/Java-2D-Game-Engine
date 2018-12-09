package me.jacob.zombiegame.engine.prop;

import java.awt.Graphics2D;

import me.jacob.zombiegame.engine.Game;
import me.jacob.zombiegame.engine.Room;
import me.jacob.zombiegame.engine.sprite.Sprite;
import me.jacob.zombiegame.engine.sprite.SpriteImage;

public abstract class Prop {
	
	public boolean enabled;
	
	// Entity position
	public double x;
	public double y;
	public double depth;

	// Entity sprite
	public abstract Sprite getSprite();
	private PropMask mask;
	
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
		
		mask = null; // Declare mask
		
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
	 * Gets/configures the prop mask for this prop.
	 * 
	 * @return the prop mask for this prop
	 */
	public PropMask getMask() {
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
}
