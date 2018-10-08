package me.jacob.zombiegame.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import me.jacob.zombiegame.engine.Game;
import me.jacob.zombiegame.engine.InputManager;
import me.jacob.zombiegame.engine.Room;

public class Player extends Human {
	
	// TODO appearance ?

	private int speed;
	private int dirX, dirY;
	
	private InputManager im;
	
	private int targetX, targetY;
	
	public Player(Game game, Room room) {
		super(game, room); // TODO
		
		speed = 20;
		dirX = 0;
		dirY = 0;
		
		targetX = 0;
		targetY = 0;
		
		im = game.getInputManager();
	}
	
	@Override
	public void update(double delta) {
		targetX = im.getMouseX();
		targetY = im.getMouseY();
		
		if (im.keyIsHeld(KeyEvent.VK_D))
			dirX = 1;
		else if (im.keyIsHeld(KeyEvent.VK_A))
			dirX = -1;
		else
			dirX = 0;
		
		if (im.keyIsHeld(KeyEvent.VK_S))
			dirY = 1;
		else if (im.keyIsHeld(KeyEvent.VK_W))
			dirY = -1;
		else
			dirY = 0;
		
		x += speed * dirX * delta;
		y += speed * dirY * delta;
	}
	
	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(Color.RED);
		g2.fillRect((int) x, (int) y, 200, 360);
		
		g2.setColor(Color.GREEN);
		g2.fillOval(targetX - 50, targetY - 50, 100, 100);
	}

}
