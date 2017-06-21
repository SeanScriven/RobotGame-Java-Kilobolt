package kiloboltgame.framework;

import java.awt.Image;
import java.util.ArrayList;

public class Animation {

	//an arrayList to contain AnimFrame objects(an image and time displayed)
	private ArrayList<AnimFrame> frames;
	//the index of current frame in frames
	private int currentFrame;
	//how much time has elapsed since current image displayed
	private long animTime;
	//amount of time each frame will be displayed for
	private long totalDuration;
	   
	public Animation() {
		frames = new ArrayList<>();
		totalDuration = 0;

		//animTime and currentFrame called sequentially
		synchronized (this) {
			animTime = 0;
			currentFrame = 0;
		}
	}
	
	/*
	 * Adds AnimFrame object to the ArrayList frames
	 */
	public synchronized void addFrame(Image image, long duration) {
		totalDuration += duration;
		frames.add(new AnimFrame(image, totalDuration));
	}
	
	/*
	 * Called repeatedly and switches frames where necessary
	 * If the ArrayList frames > 1
	 * Add elapsed time time to animTime
	 * If the animTime is over the total time, 
	 * @param elapsedTime the time passed since last update
	 */
	public synchronized void update(long elapsedTime) {
		if (frames.size() > 1) {
			animTime += elapsedTime;
			if (animTime >= totalDuration) {
				animTime = animTime % totalDuration;
				currentFrame = 0;
			}

			while (animTime > getFrame(currentFrame).endTime) {
				currentFrame++;
			}
		}
	}
	
	/*
	 * Returns the image that belongs to the currentFrame
	 */
	public synchronized Image getImage() {
		if (frames.size() == 0) {
			return null;
		} else {
			return getFrame(currentFrame).image;
		}
	}
	
	/*
	 * Returns the current AnimFrame object of animation sequence
	 */
	private AnimFrame getFrame(int i) {
		   return (AnimFrame) frames.get(i);
		}
	
	
	private class AnimFrame {
		   Image image;
		   long endTime;

		   public AnimFrame(Image image, long endTime) {
		      this.image = image;
		      this.endTime = endTime;
		   }
		}
}
