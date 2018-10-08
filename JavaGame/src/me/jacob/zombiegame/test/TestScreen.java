package me.jacob.zombiegame.test;

import java.awt.Color;
import java.awt.Graphics2D;

import me.jacob.zombiegame.engine.Game;
import me.jacob.zombiegame.engine.Room;

public class TestScreen extends Room {

	public TestScreen(Game game) {
		super(game);
		
		new TestEntity(game, this);
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, getGame().getResolution().width, getGame().getResolution().height);
	}

	@Override
	public void update(double delta) {
		
	}

}
