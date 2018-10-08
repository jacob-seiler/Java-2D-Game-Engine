package me.jacob.zombiegame.engine.prop;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.jacob.zombiegame.engine.Room;
import me.jacob.zombiegame.engine.util.Sorter;

public class PropManager {
	
	private Prop[] props;
	
	public PropManager() {
		props = new Prop[0];
	}
	
	public void addProp(Prop p) {
		// Convert array to list and add
		List<Prop> list = new ArrayList<Prop>(Arrays.asList(props));
		list.add(p);
		
		// Convert list back to array and replace
		props = list.toArray(new Prop[list.size()]);
		p.enabled = true;
	}
	
	public void removeProp(Prop p) {
		p.enabled = false;
		
		for (int i = 0; i < props.length; i++)
			if (props[i] == p)
				props[i] = null;

		clean();
	}
	
	public Prop[] getProps() {
		return props;
	}
	
	/**
	 * Removes null items from the array.
	 */
	public void clean() {
		List<Prop> list = new ArrayList<Prop>();
		
		for (Prop p : props)
			if (p != null)
				list.add(p);
		
		props = list.toArray(new Prop[list.size()]);
	}
	
	public void clean(Room newRoom) {
		clean();
			
		List<Prop> remove = new ArrayList<Prop>();
		
		for (Prop p : props)
			if (!p.isPersistent() && p.getRoom() != newRoom)
				remove.add(p);
		
		for (Prop p : remove) {
			if (p.getSprite() != null)
				p.getSprite().unload();
			
			removeProp(p);
		}
	}
	
	private void sortByDepth() {
		Sorter.quicksort(props);
	}
	
	public void updateAll(double delta) {
		sortByDepth();
		
		for (Prop p : props)
			if (p.enabled)
				p.update(delta);
	}

	public void drawAll(Graphics2D g2) {
		sortByDepth();
		
		for (Prop p : props)
			if (p.enabled)
				p.draw(g2);
	}
	
}
