package org.example.Entity;
import java.awt.image.BufferedImage;

// CONTROLLING ANIMATION FRAMES
public class Animation {
    // table with animation frames
    private BufferedImage[] frames;

    // information about animation frame
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    // Animation constructor
    public Animation() {
        playedOnce = false;
    }

    // setting animation frames
    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        playedOnce = false;
    }

    // setting delay
    public void setDelay(long d) {
        delay = d;
    }

    // updating the animation
    public void update() {
        // no animation
        if(delay == -1) return;

        // time passed from last animation frame
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if(elapsed > delay) {
            // going to the next frame and starting the time again
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame == frames.length) {
            // going to the first animation frame
            currentFrame = 0;
            playedOnce = true;
        }
    }

    // getting animation image
    public BufferedImage getImage() {
        return frames[currentFrame];
    }

    // checking if the animation was played once
    public boolean hasPlayedOnce() {
        return playedOnce;
    }
}
