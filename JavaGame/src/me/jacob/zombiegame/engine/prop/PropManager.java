package me.jacob.zombiegame.engine.prop;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import me.jacob.zombiegame.engine.Room;
import me.jacob.zombiegame.engine.util.Logger;

public class PropManager {
	
	private List<Prop> props;
	
	public PropManager() {
		props = new ArrayList<Prop>();
	}
	
	public void addProp(Prop p) {
		// Add prop to list
		props.add(p);
		
		// Enable prop
		p.enabled = true;
	}
	
	private void disableProp(Prop p) {
		p.enabled = false;
		
		if (p.getSprite() != null)
			p.getSprite().unload();
	}
	
	public void removeProp(Prop p) {
		// Disable prop
		disableProp(p);
		
		// Remove prop from list
		for (int i = 0; i < props.size(); i++)
			if (props.get(i) == p)
				props.remove(i);
	}
	
	public List<Prop> getProps() {
		return props;
	}
	
	public void removeNonPersistent(Room newRoom) {
		for (int i = 0; i < props.size(); i++) {
			Prop p = props.get(i);
			
			if (!p.isPersistent() && p.getRoom() != newRoom) {
				disableProp(p);
				props.remove(i);
			}
		}
	}
	
	public void updateAll(double delta) {
		for (Prop p : props)
			if (p.enabled)
				p.update(delta);
		
		// TODO sort all props by depth
	}

	public void drawAll(Graphics2D g2) {
		for (Prop p : props)
			if (p.enabled)
				p.draw(g2);
	}
	
}
