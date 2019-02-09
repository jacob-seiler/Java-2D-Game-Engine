package me.jacob.zombiegame.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import me.jacob.zombiegame.engine.prop.PropManager;

public class DisplayManager extends Canvas {
	
	private static final long serialVersionUID = 4512280121799171194L; // Generated serial
	
	// Frame information
	private JFrame frame;
	public double width;
	public double height;
	
	// Scale calculations
	private double scaleX;
	private double scaleY;
	private double deadSpaceX;
	private double deadSpaceY;
	
	private boolean isScaled;
	private AffineTransform vanilla;
	
	private BufferStrategy strategy;
	
	// Full screen variables
	private boolean fullScreen;
	private Dimension oldSize;
	private Point oldLocation;
	
	public DisplayManager(String title, Dimension resolution, Dimension window) {
		width = resolution.getWidth();
		height = resolution.getHeight();
		
		// Default values
		scaleX = 1;
		scaleY = 1;
		deadSpaceX = 0;
		deadSpaceY = 0;
		
		isScaled = false;
		
		fullScreen = false;
		
		// Declare values
		frame = new JFrame(title);
		
		// Setup panel
		JPanel panel = (JPanel) frame.getContentPane();
		frame.setPreferredSize(window);
		frame.setMinimumSize(new Dimension(window.width / 2, window.height / 2));

		panel.add(this);
		fixSize(window);
		
		// Tell AWT that we will control when the canvas is repainted
		setIgnoreRepaint(true);
		
		// Buffer Strategy
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * Sets up all key and mouse listeners.
	 * 
	 * @param inputManager the InputManager for tracking key and mouse activity
	 */
	public void setupListeners(InputManager inputManager) {
		addKeyListener(inputManager);
		addMouseListener(inputManager);
		addMouseMotionListener(inputManager);
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
	private void reset(Graphics2D g2) {
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
	private void scale(Graphics2D g2) {
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
	private void drawBars(Graphics2D g2) {
		g2.setColor(Color.BLACK);

		if (scaleX < scaleY) {
			// Top / bottom bars
			g2.fill(new Rectangle2D.Double(0, 0, frame.getContentPane().getSize().getWidth(), deadSpaceY / 2)); // Top
			g2.fill(new Rectangle2D.Double(0, frame.getContentPane().getSize().getHeight() - (deadSpaceY / 2),
					frame.getContentPane().getSize().getWidth(), deadSpaceY / 2)); // Bottom
		} else if (scaleY < scaleX) {
			// Left / right bars
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
			pos -= deadSpaceX / 2; // Black bars
			
			// Scale
			pos *= width / (frame.getContentPane().getSize().getWidth() - deadSpaceX);
			
			return (int) pos;
		} else {
			// Border offset
			pos -= deadSpaceY / 2; // Black bars
			
			// Scale
			pos *= height / (frame.getContentPane().getSize().getHeight() - deadSpaceY);
			
			return (int) pos;
		}
	}
	
	/**
	 * Call all related render functions.
	 * 
	 * @param currentRoom the room to render
	 * @param propManager the prop manager to render props
	 */
	public void render(Room currentRoom, PropManager propManager) {
		Graphics2D g2 = (Graphics2D) strategy.getDrawGraphics();
		scale(g2); // Scale
		
		// Rendering Hints
		RenderingHints rh = g2.getRenderingHints();
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // TODO
		
		// Draw room & props
		if (currentRoom != null) {
			currentRoom.draw(g2);
			propManager.drawAll(g2);
		}
		
		// Reset rendering hints
		g2.setRenderingHints(rh);
		
		reset(g2);
		drawBars(g2); // Add black bars
		
		// Flip buffer
		g2.dispose();
		strategy.show();
	}

	/**
	 * Toggles anti aliasing.
	 * 
	 * @param g2 the graphics to apply the anti aliasing to
	 * @param toggle true to enable anti aliasing
	 */
	public void setAntiAliasing(Graphics2D g2, boolean toggle) {
		if (toggle)
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		else
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}
	
	/**
	 * Adjusts the frame to be either windowed mode or full screen mode.
	 * 
	 * @param fullScreen true to set the window to full screen mode
	 */
	public void setFullScreen(boolean fullScreen) {
		if (this.fullScreen == fullScreen)
			return;
		
		this.fullScreen = fullScreen;
		
		frame.setVisible(false);
		frame.dispose();
		frame.setUndecorated(fullScreen);
		
		if (fullScreen) {
			oldSize = frame.getSize();
			oldLocation = frame.getLocation();
			
			frame.setLocation(0, 0);
			frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		} else {
			frame.setSize(oldSize);
			frame.setLocation(oldLocation);
		}
		
		frame.setVisible(true);
		frame.toFront();
	}
	
	/**
	 * Checks if the screen is currently in full screen mode.
	 * 
	 * @return true, if in full screen mode
	 */
	public boolean isFullScreen() {
		return fullScreen;
	}
	
}
