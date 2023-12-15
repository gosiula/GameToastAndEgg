package org.example.GameState;
import java.awt.Graphics2D;

// HANDLING THE STATES OF THE GAME
public abstract class GameState {
    // reference to GameStateManager available to the GameState package
    protected GameStateManager gsm;

    // initializing the state of the game
    public abstract void initialization();


    // updating the state of the game
    public abstract void update();

    // drawing the state of the game
    public abstract void draw(Graphics2D g);

    // handling the pressing of the key
    public abstract void keyPressed(int k);

    // handling the releasing of the key
    public abstract void keyReleased(int k);

}
