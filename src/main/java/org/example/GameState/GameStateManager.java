package org.example.GameState;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

import org.example.Entity.*;

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

    private int playerPoints;
    private Timer congratulationsTimer;

    // GameStateManager constructor
    public GameStateManager() {
        gameStates = new ArrayList<GameState>();
        currentState = MENU_STATE;

        // adding available states to the ArrayList
        gameStates.add(new Menu(this));
        gameStates.add(new Level1(this));
        gameStates.add(new Help(this));
        gameStates.add(new GameOver(this));
        gameStates.add(new Congratulations(this));

        congratulationsTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Po upływie 7 sekund przejdź do stanu Congratulations
                setState(GameStateManager.CONGRATULATIONS_STATE);
                congratulationsTimer.stop(); // Zatrzymaj timer po przejściu do Congratulations
            }
        });
    }

    public void goToCongratulationsState(Player player) {
        // Ustawienie obiektu gracza
        Congratulations congratulationsState = new Congratulations(this);
        congratulationsState.setPlayer(player);

        // Uruchomienie timera oczekiwania 7 sekund
        congratulationsTimer.start();
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