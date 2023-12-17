package org.example.GameState;
import javax.swing.Timer;
import static org.example.Music.Music.*;

// HANDLING THE CURRENT STATE OF THE GAME + GAME TIME
public class GameStateManager {
    // timer
    private long startTime;
    private long stopTime;

    // table of the states
    private final GameState[] gameStates;

    // current state
    private static int currentState;

    // number of states
    public static final int NUMBER_OF_STATES = 5;

    // available states
    public static final int MENU_STATE = 0;
    public static final int LEVEL_1_STATE = 1;
    public static final int HELP_STATE = 2;
    public static final int GAME_OVER_STATE = 3;
    public static final int CONGRATULATIONS_STATE = 4;

    // elapsed time
    long elapsedTime;

    // GameStateManager constructor
    public GameStateManager() {
        gameStates = new GameState[NUMBER_OF_STATES];
        currentState = MENU_STATE;

        // adding available states to the ArrayList
        loadState(currentState);

        // initializing the timer to update the elapsed time every second
        Timer timer = new Timer(1000, e -> updateElapsedTime());
        timer.start();
    }

    // loading the states
    private void loadState(int state) {
        if (state == MENU_STATE) {
            // resetting the elapsed time
            elapsedTime = 0;

            menuSound();
            gameStates[state] = new Menu(this);
        }
        if (state == LEVEL_1_STATE) {
            backgroundMusic();

            // starting the game timer
            startTime = System.currentTimeMillis();

            gameStates[state] = new Level1(this);
        }
        if (state == HELP_STATE) gameStates[state] = new Help(this);
        if (state == GAME_OVER_STATE) {
            stopBgMusic();

            // stopping the game timer
            stopTime = System.currentTimeMillis();

            // calculating the elapsed time
            elapsedTime = (stopTime - startTime) / 1000;

            gameStates[state] = new GameOver(this);
        }
        if (state == CONGRATULATIONS_STATE) {
            // stopping the game timer
            stopTime = System.currentTimeMillis();

            // calculating the elapsed time
            elapsedTime = (stopTime - startTime) / 1000;

            gameStates[state] = new Congratulations(this);
        }
    }

    // getting the elapsed time
    public long getElapsedTime() { return elapsedTime; }

    // unloading the game state
    private void unloadState(int state) { gameStates[state] = null; }

    // setting the state based on the choice of the user
    public void setState(int state) {
        unloadState(currentState);
        currentState = state;
        loadState(currentState);

        // resetting the shouldLoop flag when changing states
        resetShouldLoop();
    }

    // updating to the current state of the game
    public void update() {
        try {
            gameStates[currentState].update();
        } catch (Exception e){
        }
    }

    // drawing the current state of the game
    public void draw(java.awt.Graphics2D g) {
        try {
            gameStates[currentState].draw(g);
        } catch (Exception e) {

        }
    }

    // handling the pressed key for the current state
    public void keyPressed(int k) { gameStates[currentState].keyPressed(k);}

    // handling the released key for the current state
    public void keyReleased(int k) { gameStates[currentState].keyReleased(k); }

    // updating the elapsed time every second
    private void updateElapsedTime() {
        if (currentState == LEVEL_1_STATE) {
            stopTime = System.currentTimeMillis();
            elapsedTime = (stopTime - startTime) / 1000;
        }
    }
}
