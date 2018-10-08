package me.jacob.zombiegame;

import java.awt.Dimension;

import me.jacob.zombiegame.engine.Game;
import me.jacob.zombiegame.game.DebugRoom;

public class Main {
	
	private static Game game;
	
	public static void main(String[] args) {
		double aspectRatio = 16.0 / 9.0;
		int width = 1080;
		int height = (int) (width / aspectRatio);
		
		Dimension window = new Dimension(width, height);
		Dimension resolution = new Dimension(width * 5, height * 5);
		
		// Initialize and setup new game
		game = new Game("Zombie Game", resolution, window, 60);
		game.setCurrentRoom(new DebugRoom(game));
//		game.setFullScreen(true);
//		game.setFullScreen(false);
		game.start(); // Run game loop
	}
	
}
