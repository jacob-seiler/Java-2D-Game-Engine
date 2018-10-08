package me.jacob.zombiegame.test;

import java.awt.event.KeyEvent;

import me.jacob.zombiegame.engine.Game;
import me.jacob.zombiegame.engine.Room;
import me.jacob.zombiegame.engine.entity.Entity;
import me.jacob.zombiegame.engine.sprite.Sprite;
import me.jacob.zombiegame.engine.sprite.SpriteImage;

public class TestEntity extends Entity {

	private boolean paused = false;
	
	private double speedX = 1;
	private double speedY = 1;

	private int width = 100;
	private int height = 70;

	private Sprite sprite;

	public TestEntity(Game game, Room room) {
		super(game, room);

		sprite = new Sprite(new SpriteImage("test.png", width, height));
	}

	@Override
	public void update(double delta) {
		// Toggle pause
		if (getGame().getInputManager().keyIsPressed(KeyEvent.VK_SPACE))
			paused = true;
		
		if (getGame().getInputManager().keyIsReleased(KeyEvent.VK_SPACE))
			paused = false;
		
		if (paused)
			return;
		
		// Move
		x += speedX * delta;
		y += speedY * delta;
		
		// Left and right wall collisions, change direction
		if (x <= 0) {
			x = 0;
			speedX = Math.abs(speedX);
		} else if (x + width >= getGame().getResolution().width) {
			x = getGame().getResolution().width - width;
			speedX = -1 * Math.abs(speedX);
		}
		
		// Ceiling and floor collisions change direction
		if (y <= 0) {
			y = 0;
			speedY = Math.abs(speedY);
		} else if (y + height >= getGame().getResolution().height) {
			y = getGame().getResolution().height - height;
			speedY = -1 * Math.abs(speedY);
		}
		
		depth = -y;
	}
	
	@Override
	public Sprite getSprite() {
		return sprite;
	}

}
