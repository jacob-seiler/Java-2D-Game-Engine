package me.jacob.zombiegame.test;

import java.awt.Color;
import java.awt.Graphics2D;

import me.jacob.zombiegame.engine.Game;
import me.jacob.zombiegame.engine.Room;

public class TestRoom extends Room {

	public TestRoom(Game game) {
		super(game);
		
		new TestProp(game, this);
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, (int) getGame().getDisplayManager().width, (int) getGame().getDisplayManager().height);
	}

	@Override
	public void update(double delta) {
		
	}

}
