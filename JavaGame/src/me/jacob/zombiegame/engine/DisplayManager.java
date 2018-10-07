package me.jacob.zombiegame.engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

public class DisplayManager {
	
	// Values from game
	private JFrame frame;
	private double width;
	private double height;
	
	// Scale calculations
	private double scaleX;
	private double scaleY;
	private double deadSpaceX;
	private double deadSpaceY;
	
	private boolean isScaled;
	private AffineTransform vanilla;
	
	public DisplayManager(JFrame frame, Dimension resolution) {
		this.frame = frame;
		width = resolution.getWidth();
		height = resolution.getHeight();
		
		// Default values
		scaleX = 1;
		scaleY = 1;
		deadSpaceX = 0;
		deadSpaceY = 0;
		
		isScaled = false;
	}
	
	/**
	 * Saves the current transformations.
	 * 
	 * @param g2 the graphics to get the transformations from
	 */
	private void save(Graphics2D g2) {
		vanilla = g2.getTransform();
	}
	
	/**
	 * Apply the saved transformations.
	 * 
	 * @param g2 the graphics to apply the transformations to
	 */
	public void reset(Graphics2D g2) {
		g2.setTransform(vanilla); // Reset
		isScaled = false;
	}
	
	/**
	 * Checks if the graphics are currently scaled.
	 * 
	 * @return if the graphics are currently scaled
	 */
	private boolean isScaled() {
		return isScaled;
	}
	
	/**
	 * Determine the correct transformation values.
	 * 
	 * @param g2 the graphics to determine the values for
	 */
	private void calculate(Graphics2D g2) {
		scaleX = frame.getContentPane().getSize().getWidth() / width;
		scaleY = frame.getContentPane().getSize().getHeight() / height;

		deadSpaceX = 0;
		deadSpaceY = 0;

		if (scaleX < scaleY) {
			// Top / bottom margins
			deadSpaceY = frame.getContentPane().getSize().getHeight() - (height * scaleX);
		} else if (scaleY < scaleX) {
			// Left / right margins
			deadSpaceX = frame.getContentPane().getSize().getWidth() - (width * scaleY);
		}
	}
	
	/**
	 * Scales graphics to fix the correct aspect ratio.
	 * 
	 * @param g2 the graphics to apply transformation to
	 */
	public void scale(Graphics2D g2) {
		if (isScaled())
			reset(g2);
		
		save(g2);
		
		calculate(g2);

		double scale = scaleX;
		if (scaleY < scaleX)
			scale = scaleY;
		
		// g2.translate(deadSpaceX / 2, deadSpaceY / 2);
		g2.scale(scale, scale); // Scale from the origin
		g2.translate(deadSpaceX / (2 * scale), deadSpaceY / (2 * scale));
		
		isScaled = true;
	}
	
	/**
	 * Draws black bars to hide the part of the screen that does not fit the aspect ratio.
	 * 
	 * @param g2 the graphics to use to draw the bars
	 */
	public void drawBars(Graphics2D g2) {
		if (scaleX < scaleY) {
			// Top / bottom bars
			g2.setColor(Color.BLACK);
			g2.fill(new Rectangle2D.Double(0, 0, frame.getContentPane().getSize().getWidth(), deadSpaceY / 2)); // Top
			g2.fill(new Rectangle2D.Double(0, frame.getContentPane().getSize().getHeight() - (deadSpaceY / 2),
					frame.getContentPane().getSize().getWidth(), deadSpaceY / 2)); // Bottom
		} else if (scaleY < scaleX) {
			// Left / right bars
			g2.setColor(Color.BLACK);
			g2.fill(new Rectangle2D.Double(0, 0, deadSpaceX / 2, frame.getContentPane().getSize().getHeight())); // Left
			g2.fill(new Rectangle2D.Double(frame.getContentPane().getSize().getWidth() - (deadSpaceX / 2), 0,
					deadSpaceX / 2, frame.getContentPane().getSize().getHeight())); // Right
		}
	}
	
	/**
	 * Corrects the size of the frame.
	 * 
	 * @param window the dimension to make the frame
	 */
	public void fixSize(Dimension window) {
		double width = window.getWidth();
		double height = window.getHeight();
		
		frame.pack();

		double borderWidth = width - frame.getContentPane().getSize().getWidth();
		double borderHeight = height - frame.getContentPane().getSize().getHeight();

		frame.setPreferredSize(new Dimension((int) (width + borderWidth), (int) (height + borderHeight)));
		frame.pack();
	}
	
	/**
	 * Corrects the mouse position to adjust for screen scaling.
	 * 
	 * @param pos the mouse coordinate
	 * @param xAxis true if pos is on the x-axis
	 * @return the new position for the mouse coordinate
	 */
	public int fixMouse(double pos, boolean xAxis) {
		if (xAxis) {
			// Border offset
			pos -= frame.getInsets().left; // Border
			pos -= deadSpaceX / 2; // Black bars
			
			// Scale
			pos *= width / (frame.getContentPane().getSize().getWidth() - deadSpaceX);
			
			return (int) pos;
		} else {
			// Border offset
			pos -= frame.getInsets().top; // Border
			pos -= deadSpaceY / 2; // Black bars
			
			// Scale
			pos *= height / (frame.getContentPane().getSize().getHeight() - deadSpaceY);
			
			return (int) pos;
		}
	}
	
}
