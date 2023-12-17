package org.example.GameState;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

import static org.example.Music.Music.*;

// HANDLING THE CURRENT STATE OF THE GAME
public class GameStateManager {
    private long startTime;
    private long stopTime;

    // ArrayList of the states
    private final GameState[] gameStates;

    // current state
    private static int currentState;
    public static final int NUMSTATES = 5;

    // available states
    public static final int MENU_STATE = 0;
    public static final int LEVEL_1_STATE = 1;
    public static final int HELP_STATE = 2;
    public static final int GAME_OVER_STATE = 3;
    public static final int CONGRATULATIONS_STATE = 4;

    // GameStateManager constructor
    public GameStateManager() {
        gameStates = new GameState[NUMSTATES];
        currentState = MENU_STATE;

        // adding available states to the ArrayList
        loadState(currentState);

    }

    private void loadState(int state) {
        if(state == MENU_STATE) {
            menuMusic();
            gameStates[state] = new Menu(this);
        }
        if(state == LEVEL_1_STATE) {
            backgroundMusic();
            startTime = System.currentTimeMillis();
            gameStates[state] = new Level1(this);
        }
        if(state == HELP_STATE) gameStates[state] = new Help(this);
        if(state == GAME_OVER_STATE) {
            stopBgMusic();
            stopTime = System.currentTimeMillis();
            long elapsedTime = (stopTime - startTime) / 1000;
            System.out.println("Czas działania klasy: " + elapsedTime + " sekund.");
            gameStates[state] = new GameOver(this);
        }
        if(state == CONGRATULATIONS_STATE) {
            stopTime = System.currentTimeMillis();
            long elapsedTime = (stopTime - startTime) / 1000;
            System.out.println("Czas działania klasy: " + elapsedTime + " sekund.");
            gameStates[state] = new Congratulations(this);
        }
    }

    private void unloadState(int state) {
        gameStates[state] = null;
    }

    // setting the state based on the choice of the user
    public void setState(int state) {
        unloadState(currentState);
        currentState = state;
        loadState(currentState);

        // Reset the shouldLoop flag when changing states
        resetShouldLoop();

        //gameStates[currentState].initialization();
    }

    public static int getCurrentState() {
        return currentState;
    }


    // updating to the current state of the game
    public void update() {
        try {
            gameStates[currentState].update();
        } catch(Exception e) {}
    }

    // drawing the current state of the game
    public void draw(java.awt.Graphics2D g) {
        try {
            gameStates[currentState].draw(g);
        } catch(Exception e) {}
    }

    // handling the pressed key for the current state
    public void keyPressed(int k) {
        gameStates[currentState].keyPressed(k);
    }

    // handling the released key for the current state
    public void keyReleased(int k) {
        gameStates[currentState].keyReleased(k);
    }

}