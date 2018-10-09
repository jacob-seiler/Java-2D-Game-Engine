package me.jacob.zombiegame.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import me.jacob.zombiegame.engine.Game;
import me.jacob.zombiegame.engine.Room;
import me.jacob.zombiegame.test.TestRoom;

public class DebugRoom extends Room {

	public DebugRoom(Game game) {
		super(game);
		
		new Player(game, this);
	}
	
	@Override
	public void update(double delta) {
		if (getGame().getInputManager().keyIsPressed(KeyEvent.VK_ENTER))
			getGame().setCurrentRoom(new TestRoom(getGame()));
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, (int) getGame().getDisplayManager().width, (int) getGame().getDisplayManager().height);
	}

}
