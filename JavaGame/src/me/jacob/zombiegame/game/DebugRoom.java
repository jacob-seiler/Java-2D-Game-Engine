package me.jacob.zombiegame.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.List;

import me.jacob.zombiegame.Main;
import me.jacob.zombiegame.engine.Room;
import me.jacob.zombiegame.engine.entity.Entity;

public class DebugRoom extends Room {

	private List<Entity> entities;
	private Player player;
	
	public DebugRoom() {
		player = new Player();
		entities = Arrays.asList(player);
	}
	
	@Override
	public void update(double delta) {
		
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, Main.getGame().getResolution().width, Main.getGame().getResolution().height);
	}
	
	@Override
	public List<Entity> getEntities() {
		return entities;
	}

}
