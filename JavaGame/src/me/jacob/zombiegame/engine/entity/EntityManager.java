package me.jacob.zombiegame.engine.entity;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import me.jacob.zombiegame.engine.Room;

public class EntityManager {
	
	private List<Entity> entities;
	
	public EntityManager() {
		entities = new ArrayList<Entity>();
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
		e.enabled = true;
	}
	
	public void removeEntity(Entity e) {
		e.enabled = false;
		entities.remove(e);
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public void clean(Room newRoom) {
		List<Entity> remove = new ArrayList<Entity>();
		
		for (Entity e : getEntities())
			if (!e.isPersistent() && e.getRoom() != newRoom)
				remove.add(e);
		
		for (Entity e : remove) {
			if (e.getSprite() != null)
				e.getSprite().unload();
			
			removeEntity(e);
		}
	}
	
	public void updateEntities(double delta) {
		for (Entity e : getEntities())
			if (e.enabled)
				e.update(delta);
	}

	public void drawEntities(Graphics2D g2) {
		for (Entity e : getEntities())
			if (e.enabled)
				e.draw(g2);
	}
	
}
