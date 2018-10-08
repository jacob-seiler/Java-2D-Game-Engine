package me.jacob.zombiegame.engine.entity;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.jacob.zombiegame.engine.Room;
import me.jacob.zombiegame.engine.util.Sorter;

public class EntityManager {
	
	private Entity[] entities;
	
	public EntityManager() {
		entities = new Entity[0];
	}
	
	public void addEntity(Entity e) {
		// Convert array to list and add
		List<Entity> list = new ArrayList<Entity>(Arrays.asList(entities));
		list.add(e);
		
		// Convert list back to array and replace
		entities = list.toArray(new Entity[list.size()]);
		e.enabled = true;
	}
	
	public void removeEntity(Entity e) {
		e.enabled = false;
		
		for (int i = 0; i < entities.length; i++)
			if (entities[i] == e)
				entities[i] = null;

		clean();
	}
	
	public Entity[] getEntities() {
		return entities;
	}
	
	/**
	 * Removes null items from the entities array.
	 */
	public void clean() {
		List<Entity> list = new ArrayList<Entity>();
		
		for (Entity e : entities)
			if (e != null)
				list.add(e);
		
		entities = list.toArray(new Entity[list.size()]);
	}
	
	public void clean(Room newRoom) {
		clean();
			
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
	
	private void sortEntityDepth() {
		Sorter.quicksort(entities);
	}
	
	public void updateEntities(double delta) {
		sortEntityDepth();
		
		for (Entity e : getEntities())
			if (e.enabled)
				e.update(delta);
	}

	public void drawEntities(Graphics2D g2) {
		sortEntityDepth();
		
		for (Entity e : getEntities())
			if (e.enabled)
				e.draw(g2);
	}
	
}
