package me.jacob.zombiegame.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.List;

import me.jacob.zombiegame.Main;
import me.jacob.zombiegame.engine.Room;
import me.jacob.zombiegame.engine.entity.Entity;

public class TestScreen extends Room {

	private List<Entity> entities;
	private TestEntity testEntity;

	public TestScreen() {
		testEntity = new TestEntity();
		entities = Arrays.asList(testEntity);
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, Main.getGame().getResolution().width, Main.getGame().getResolution().height);
	}

	@Override
	public void update(double delta) {
		
	}

	@Override
	public List<Entity> getEntities() {
		return entities;
	}

}
