package org.example.GameState;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

// HANDLING THE CURRENT STATE OF THE GAME
public class GameStateManager {

    // ArrayList of the states
    private final ArrayList<GameState> gameStates;

    // current state
    private int currentState;

    // available states
    public static final int MENU_STATE = 0;
    public static final int LEVEL_1_STATE = 1;
    public static final int HELP_STATE = 2;
    public static final int GAME_OVER_STATE = 3;
    public static final int CONGRATULATIONS_STATE = 4;

    // GameStateManager constructor
    public GameStateManager() {
        gameStates = new ArrayList<>();
        currentState = MENU_STATE;

        // adding available states to the ArrayList
        gameStates.add(new Menu(this));
        gameStates.add(new Level1(this));
        gameStates.add(new Help(this));
        gameStates.add(new GameOver(this));
        gameStates.add(new Congratulations(this));

    }

    // setting the state based on the choice of the user
    public void setState(int state) {
        currentState = state;
        gameStates.get(currentState).initialization();
    }


    // updating to the current state of the game
    public void update() {
        if (currentState >= 0 && currentState < gameStates.size()) {
            gameStates.get(currentState).update();
        }
    }

    // drawing the current state of the game
    public void draw(java.awt.Graphics2D g) { gameStates.get(currentState).draw(g); }

    // handling the pressed key for the current state
    public void keyPressed(int k) {
        gameStates.get(currentState).keyPressed(k);
    }

    // handling the released key for the current state
    public void keyReleased(int k) {
        gameStates.get(currentState).keyReleased(k);
    }

}