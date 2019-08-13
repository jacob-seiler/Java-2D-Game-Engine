package me.jacob.javagame.engine.sprite;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sprite {
	
	// TODO public void set frameRate
	
	private List<SpriteImage> frames;
	
	// Animation
	private int frameIndex = 0;
	
	public Sprite() {
		frames = new ArrayList<SpriteImage>();
	}
	
	public Sprite(ArrayList<SpriteImage> spriteImages) {
		frames = spriteImages;
		
		load();
	}
	
	public Sprite(SpriteImage... spriteImages) {
		frames = Arrays.asList(spriteImages);
		
		load();
	}
	
	public Sprite(List<String> paths) {
		frames = new ArrayList<SpriteImage>();
		
		for (String p : paths) {
			frames.add(new SpriteImage(p));
		}
		
		load();
	}
	
	public Sprite(String... paths) {
		frames = new ArrayList<SpriteImage>();
		
		for (String p : paths) {
			frames.add(new SpriteImage(p));
		}
		
		load();
	}
	
	public void load() {
		if (frames == null)
			return;
		
		for (SpriteImage i : frames) {
			i.load();
		}
	}
	
	public void unload() {
		if (frames == null)
			return;
		
		for (SpriteImage i : frames) {
			i.unload();
		}
	}
	
	public void draw(Graphics2D g2, double x, double y) {
		if (frames == null || frames.isEmpty())
			return;
		
		// Setup image
		SpriteImage sImg = getCurrentFrame();

		if (!sImg.isLoaded())
			sImg.load();
		
		// Get image size
		int width = 0;
		int height = 0;
		
		if (sImg.getSize() != null) {
			width = sImg.getSize().width;
			height = sImg.getSize().height;
		} else {
			width = sImg.getImage().getWidth();
			height = sImg.getImage().getHeight();
		}

		// Offset image based on origin
		int posX = (int) (x - (width * sImg.getOrigin().getOffsetX()));
		int posY = (int) (y - (height * sImg.getOrigin().getOffsetY()));
		
		if (sImg.getSize() != null)
			g2.drawImage(sImg.getImage(), posX, posY, width, height, null);
		else
			g2.drawImage(sImg.getImage(), posX, posY, null);
	}
	
	public void update(double delta) {
		// TODO animation speed?
		nextFrame();
	}
	
	public List<SpriteImage> getFrames() {
		return frames;
	}
	
	public SpriteImage getCurrentFrame() {
		return frames.get(frameIndex);
	}
	
	private void nextFrame() {
		if (frames.size() <= 1 || (frameIndex + 1) >= frames.size())
			frameIndex = 0;
		else
			frameIndex++;
	}
	
}
