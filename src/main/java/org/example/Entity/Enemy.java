package org.example.Entity;
import org.example.TileMap.TileMap;

// THE ACTIONS OF THE ENEMY - BEING HURT BY PLAYER AND HURTING THE PLAYER
public class Enemy extends MapObject {
    // parameters of the enemy
    protected int health;
    protected int maxHealth;
    protected boolean dead;
    protected int damage;
    protected boolean flinching;
    protected long flinchTimer;

    // Enemy constructor
    public Enemy(TileMap tm) {
        super(tm);
    }

    // checking if the enemy is dead
    public boolean isDead() { return dead; }

    // checking if the enemy got damaged by the player
    public int getDamage() { return damage; }

    public void hit(int damage) {
        // checking if the enemy is dead or the player is flinching
        if(dead || flinching) return;

        health -= damage;

        // ensure the health is greater than 0
        if(health < 0) health = 0;

        // marking as dead if the health equals to 0
        if(health == 0) dead = true;

        // making the player flinch
        flinching = true;

        // recording the time of the flinching
        flinchTimer = System.nanoTime();
    }

    public void update() {}
}
