package me.jacob.javagame.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputManager implements KeyListener, MouseListener, MouseMotionListener {

	private DisplayManager displayManager;
	
	private int maxValue = 65535;
	
	private boolean[] keys;
	private boolean[] keysPressed;
	private boolean[] keysReleased;
	
	private boolean[] mouse;
	private boolean[] mousePressed;
	private boolean[] mouseReleased;
	
	private int mouseX;
	private int mouseY;

	public InputManager(DisplayManager displayManager) {
		this.displayManager = displayManager;
		
		keys = new boolean[maxValue];
		keysPressed = new boolean[maxValue];
		keysReleased = new boolean[maxValue];
		
		mouse = new boolean[maxValue];
		mousePressed = new boolean[maxValue];
		mouseReleased = new boolean[maxValue];

		mouseX = 0;
		mouseY = 0;
	}
	
	public void tick() {
		keysPressed = new boolean[maxValue];
		keysReleased = new boolean[maxValue];
	}
	
	/** Key check methods */
	
	public boolean keyIsHeld(int keyCode) {
		return keys[keyCode];
	}
	
	public boolean keyIsPressed(int keyCode) {
		return keysPressed[keyCode];
	}
	
	public boolean keyIsReleased(int keyCode) {
		return keysReleased[keyCode];
	}
	
	/** Mouse press check methods */
	
	public boolean mouseIsHeld(int button) {
		return mouse[button];
	}
	
	public boolean mouseIsPressed(int button) {
		return mousePressed[button];
	}
	
	public boolean mouseIsReleased(int button) {
		return mouseReleased[button];
	}
	
	/** Mouse motion check methods */
	
	public int getMouseX() {
		return displayManager.fixMouse(mouseX, true);
	}
	
	public int getMouseY() {
		return displayManager.fixMouse(mouseY, false);
	}
	
	/** Key listener methods */

	@Override
	public void keyPressed(KeyEvent e) {
		if (keys[e.getKeyCode()])
			return;
		
		keysPressed[e.getKeyCode()] = true;
		keysReleased[e.getKeyCode()] = false;
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (!keys[e.getKeyCode()])
			return;
		
		keysPressed[e.getKeyCode()] = false;
		keysReleased[e.getKeyCode()] = true;
		keys[e.getKeyCode()] = false;
	}
	
	/** Mouse press listener methods */

	@Override
	public void mousePressed(MouseEvent e) {
		if (mouse[e.getButton()])
			return;
		
		mousePressed[e.getButton()] = true;
		mouseReleased[e.getButton()] = false;
		mouse[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!mouse[e.getButton()])
			return;
		
		mousePressed[e.getButton()] = false;
		mouseReleased[e.getButton()] = true;
		mouse[e.getButton()] = false;
	}
	
	/** Mouse motion listener methods */
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	/** Unused methods */
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
