package me.jacob.zombiegame.engine.entity;

public class EntityMask {

	// Mask parameters
	private double x;
	private double y;
	private double width;
	private double height;
	
	/**
	 * Initialization. Stores given values.
	 * 
	 * @param x position on the x-axis
	 * @param y position on the y-axis
	 * @param width size on the x-axis
	 * @param height size on the y-axis
	 */
	public EntityMask(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Gets the stored x value.
	 * 
	 * @return position on the x-axis
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Gets the stored y value.
	 * 
	 * @return position on the y-axis
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Gets the stored width value.
	 * 
	 * @return size on the x-axis
	 */
	public double getWidth() {
		return width;
	}
	
	/**
	 * Gets the stored height value.
	 * 
	 * @return size on the y-axis
	 */
	public double getHeight() {
		return height;
	}
	
	/**
	 * Redefines the position.
	 * 
	 * @param x new position on the x-axis
	 * @param y new position on the y-axis
	 */
	public void setPos(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
}
