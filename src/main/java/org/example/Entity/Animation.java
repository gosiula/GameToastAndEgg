package org.example.Entity;

import java.awt.image.BufferedImage;
import javax.swing.*;

// CONTROLLING ANIMATION FRAMES
public class Animation {
    private BufferedImage[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    public Animation() {
        playedOnce = false;
    }

    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        playedOnce = false;
    }

    public void setDelay(long d) {
        delay = d;
    }

    // This method will be called asynchronously in the background
    private void updateInBackground() {
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if (currentFrame == frames.length) {
            currentFrame = 0;
            playedOnce = true;
        }
    }

    // This method will be called on the Event Dispatch Thread
    private void updateOnEDT() {
        // Update any UI components or perform other tasks
        // related to the animation on the Event Dispatch Thread
    }

    public void update() {
        if (delay == -1) return;

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                updateInBackground();
                return null;
            }

            @Override
            protected void done() {
                updateOnEDT();
            }
        };

        worker.execute();
    }

    public BufferedImage getImage() {
        return frames[currentFrame];
    }

    public boolean hasPlayedOnce() {
        return playedOnce;
    }
}
