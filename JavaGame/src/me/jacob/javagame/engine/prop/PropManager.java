package me.jacob.javagame.engine.prop;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import me.jacob.javagame.engine.Room;
import me.jacob.javagame.engine.util.Sorter;

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
	
	private void disableProp(Prop prop) {
		prop.enabled = false;
		
		if (prop.getSprite() != null)
			prop.getSprite().unload();
	}
	
	public void removeProp(Prop prop) {
		// Disable prop
		disableProp(prop);
		
		// Remove prop from list
		for (int i = 0; i < props.size(); i++)
			if (props.get(i) == prop)
				props.remove(i);
	}
	
	public List<Prop> getProps() {
		return props;
	}

    public void removeNonPersistent(Room newRoom) {
        List<Prop> delete = new ArrayList<>();

        for (int i = 0; i < props.size(); i++) {
            Prop p = props.get(i);

            if (p.isPersistent())
                continue;

            if (p.getRoom() != newRoom) {
                disableProp(p);
                delete.add(p);
            }
        }

        for (Prop p : delete)
            props.remove(p);
    }
	
	public void updateAll(double delta) {
		for (Prop p : props)
			if (p.enabled)
				p.update(delta);
	}

	public void drawAll(Graphics2D g2) {
		for (Prop p : props)
			if (p.enabled)
				p.draw(g2);
	}

	public void sort() {
		Sorter.sort(props);
	}
	
}
