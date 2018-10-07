package me.jacob.zombiegame.engine.sprite;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.jacob.zombiegame.engine.util.Logger;

public class SpriteImage {
	
	// Image
	private String path;
	private BufferedImage image;
	private Dimension size;
	
	// Sprite
	private SpriteOrigin origin;
	
	/**
	 * Initialization. Sets up and stores values.
	 * 
	 * @param path the image file path in the project res folder
	 */
	public SpriteImage(String path) {
		this.path = path;
		image = null;
		size = null;
		
		origin = SpriteOrigin.TOP_LEFT;
	}
	
	/**
	 * Initialization. Sets up and stores values.
	 * 
	 * @param path the image file path in the project res folder
	 * @param origin the sprite origin to be used
	 */
	public SpriteImage(String path, SpriteOrigin origin) {
		this.path = path;
		image = null;
		size = null;
		
		this.origin = origin;
	}
	
	/**
	 * Initialization. Sets up and stores values.
	 * 
	 * @param path the image file path in the project res folder
	 * @param width the size on the x-axis of the sprite
	 * @param height the size on the y-axis of the sprite
	 */
	public SpriteImage(String path, int width, int height) {
		this.path = path;
		image = null;
		this.size = new Dimension(width, height);
		
		origin = SpriteOrigin.TOP_LEFT;
	}
	
	/**
	 * Initialization. Stores values.
	 * 
	 * @param path the image file path in the project res folder
	 * @param origin the sprite origin to be used
	 * @param width the size on the x-axis of the sprite
	 * @param height the size on the y-axis of the sprite
	 */
	public SpriteImage(String path, SpriteOrigin origin, int width, int height) {
		this.path = path;
		image = null;
		this.size = new Dimension(width, height);
		
		this.origin = origin;
	}
	
	/**
	 * Gets the image being used by the sprite.
	 * 
	 * @return the image, null if not loaded
	 */
	public BufferedImage getImage() {
		return image;
	}
	
	/**
	 * Gets the sprite origin in use.
	 * 
	 * @return origin being used
	 */
	public SpriteOrigin getOrigin() {
		return origin;
	}
	
	/**
	 * Redefines the origin.
	 * 
	 * @param origin the new sprite origin
	 */
	public void setOrigin(SpriteOrigin origin) {
		this.origin = origin;
	}
	
	/**
	 * Loads the image.
	 */
	public void load() {
		try {
			image = ImageIO.read(getClass().getClassLoader().getResource(path));
		} catch (IOException e) {
			Logger.error("Error loading image... " + e.getMessage());
			return;
		}
	}
	
	/**
	 * Unload the image.
	 */
	public void unload() {
		image = null;
	}

	/**
	 * Check if the image is currently loaded.
	 * 
	 * @return if the image is set to null
	 */
	public boolean isLoaded() {
		return image != null;
	}
	
	/**
	 * The size of the sprite. If no defined size, attempt to define.
	 * 
	 * @return the size being used for sprite boundaries
	 */
	public Dimension getSize() {
		if (size == null && isLoaded())
			size = new Dimension(image.getWidth(), image.getHeight());

		return size;
	}
	
}
