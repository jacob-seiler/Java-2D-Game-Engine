package me.jacob.zombiegame.game;

import me.jacob.zombiegame.engine.entity.Entity;
import me.jacob.zombiegame.engine.sprite.Sprite;

public abstract class Human extends Entity {
	
	private Sprite sprite;
	
	public Human() {
		sprite = new Sprite();
		// TODO make sprite
	}
	
	@Override
	public Sprite getSprite() {
		return sprite;
	}
	
	public abstract void update(double delta);
	
}
