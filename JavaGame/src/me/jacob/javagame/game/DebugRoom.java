package me.jacob.javagame.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import me.jacob.javagame.engine.Game;
import me.jacob.javagame.engine.Room;
import me.jacob.javagame.test.TestProp;
import me.jacob.javagame.test.TestRoom;

public class DebugRoom extends Room {

	private Game game;

	public DebugRoom(Game game) {
		super(game);

		this.game = game;
		
		new Player(game, this);
		new TestProp(game, this);
	}
	
	@Override
	public void update(double delta) {
	    game.getPropManager().sort();

		if (getGame().getInputManager().keyIsPressed(KeyEvent.VK_ENTER))
			getGame().setCurrentRoom(new TestRoom(getGame()));
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, (int) getGame().getDisplayManager().width, (int) getGame().getDisplayManager().height);
	}

}
