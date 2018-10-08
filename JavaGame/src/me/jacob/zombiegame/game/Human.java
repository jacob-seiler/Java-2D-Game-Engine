package me.jacob.zombiegame.game;

import me.jacob.zombiegame.engine.Game;
import me.jacob.zombiegame.engine.Room;
import me.jacob.zombiegame.engine.entity.Entity;
import me.jacob.zombiegame.engine.sprite.Sprite;

public abstract class Human extends Entity {
	
	public Human(Game game, Room room) {
		super(game, room);
		
		sprite = new Sprite();
		// TODO make sprite
	}

	private Sprite sprite;
	
	
	
	@Override
	public Sprite getSprite() {
		return sprite;
	}
	
	public abstract void update(double delta);
	
}
