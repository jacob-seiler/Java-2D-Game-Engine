package me.jacob.zombiegame;

import java.awt.Dimension;

import me.jacob.zombiegame.engine.Game;
import me.jacob.zombiegame.game.DebugRoom;

public class Main {
	
	private static Game game;
	
	public static void main(String[] args) {
		double aspectRatio = 18.0 / 12.0;
		int width = 1080;
		int height = (int) (width / aspectRatio);
		
		Dimension window = new Dimension(width, height);
		Dimension resolution = new Dimension(width * 5, height * 5);
		
		// Initialize game
		game = new Game("Zombie Game", resolution, window, 60);
		
		// Create room
		game.setCurrentRoom(new DebugRoom(game));

		// Run game loop
		game.start();
	}
	
}
