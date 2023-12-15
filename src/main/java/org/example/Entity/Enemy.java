package org.example.Entity;

import org.example.TileMap.TileMap;
import javax.swing.*;

// THE ACTIONS OF THE ENEMY - BEING HURT BY PLAYER AND HURTING THE PLAYER
public class Enemy extends MapObject {
    protected int health;
    protected int maxHealth;
    protected boolean dead;
    protected int damage;
    protected boolean flinching;
    protected long flinchTimer;

    public Enemy(TileMap tm) {
        super(tm);
    }

    public boolean isDead() {
        return dead;
    }

    public int getDamage() {
        return damage;
    }

    public void hit(int damage) {
        if (dead || flinching) return;

        health -= damage;

        if (health < 0) health = 0;

        if (health == 0) dead = true;

        flinching = true;
        flinchTimer = System.nanoTime();
    }

    private void updateInBackground() {
        // Update the enemy's logic in the background
        // This could include things like movement, AI, etc.
    }

    private void updateOnEDT() {
        // Perform any UI-related updates on the Event Dispatch Thread
    }

    public void update() {
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
}
