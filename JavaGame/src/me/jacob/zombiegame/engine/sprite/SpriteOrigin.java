package me.jacob.zombiegame.engine.sprite;

public enum SpriteOrigin {
	TOP_LEFT(0, 0), TOP_MIDDLE(0.5, 0), TOP_RIGHT(1, 0),
	CENTER_LEFT(0, 0.5), CENTER_MIDDLE(0.5, 0.5), CENTER_RIGHT(1, 0.5),
	BOTTOM_LEFT(0, 1), BOTTOM_MIDDLE(0.5, 1), BOTTOM_RIGHT(1, 1);

	// Multiple of sprite size to offset by
	private double offsetX;
	private double offsetY;
	
	/**
	 * The base position of the sprite relative to everything else.
	 * 
	 * @param offsetX amount to multiply the width by while translating
	 * @param offsetY amount to multiply the height by while translating
	 */
	SpriteOrigin(double offsetX, double offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	/**
	 * The amount to multiply the width by while translating
	 * 
	 * @return the stored offset value for the x-axis
	 */
	public double getOffsetX() {
		return offsetX;
	}
	
	/**
	 * The amount to multiply the height by while translating
	 * 
	 * @return the stored offset value for the y-axis
	 */
	public double getOffsetY() {
		return offsetY;
	}
}
