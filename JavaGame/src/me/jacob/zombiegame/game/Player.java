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

	// Target
	private int targetSize;
	private double targetX;
	private double targetY;

	public Player(Game game, Room room) {
		super(game, room);

		speed = 20;
		dirX = 0;
		dirY = 0;

		targetSize = 500;
		targetX = 0;
		targetY = 0;

		im = game.getInputManager();
	}

	@Override
	public void update(double delta) {
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

		// Target
		double theta = Math.atan2(im.getMouseY() - (y - 180), im.getMouseX() - x);
		targetX = (x) + targetSize * Math.cos(theta);
		targetY = (y - 180) + targetSize * Math.sin(theta);

		depth = -y;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(Color.RED);
		g2.fillRect((int) x - 100, (int) y - 360, 200, 360);
		g2.setColor(Color.BLUE);
		g2.fillOval((int) x - 50, (int) y - 50, 100, 100);

		g2.setColor(Color.BLACK);
		g2.drawOval((int) x - targetSize, (int) y - (targetSize + 180), targetSize * 2, targetSize * 2);

		g2.setColor(Color.PINK);
		g2.fillOval((int) targetX - 50, (int) targetY - 50, 100, 100);
	}

}
