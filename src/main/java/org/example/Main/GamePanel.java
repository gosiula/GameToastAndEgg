package org.example.Main;

import org.example.GameState.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

// CREATING A PANEL, STARTING THE GAME, IMAGES AND GSM
public class GamePanel extends JPanel implements Runnable, KeyListener {
    // width of the panel
    public static final int WIDTH = 384;

    // height of the panel
    public static final int HEIGHT = 288;

    // scale of the panel
    public static final int SCALE = 2;

    // game thread
    private Thread thread;

    // the running of the thread
    private boolean threadIsRunning;

    // images
    private BufferedImage image;
    private Graphics2D g;

    // game state manager
    private GameStateManager gsm;

    // GamePanel constructor
    public GamePanel() {
        super();

        // setting preferable size of the panel
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        // the ability to focus
        setFocusable(true);
        requestFocus();
    }

    // a method to start the thread
    public void addNotify() {
        super.addNotify();

        // initialization and starting the game thread if it wasn't made yet,
        if (thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    // initialization of the game
    private void initialization() {
        // creating an BuggeredImage object to create the image
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        // the thread is running
        threadIsRunning = true;

        // creating a GameStateManager
        gsm = new GameStateManager();
    }

    // running of the game
    public void run() {
        initialization();

        long start;
        long elapsed;
        long wait;

        // game loop
        while (threadIsRunning) {
            start = System.nanoTime();

            // updating the state of the game
            update();

            // drawing the objects of the game
            draw();

            // drawing the images on the screen
            drawToScreen();

            // desired frames per second
            int FRAMES_PER_SECOND = 60;

            // controlling the frame rate
            elapsed = System.nanoTime() - start;
            long targetTime = 1000 / FRAMES_PER_SECOND;
            wait = targetTime - elapsed / 1000000;

            // ensure that wait is not negative before sleeping
            if (wait > 0) {
                try {
                    Thread.sleep(wait);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // updating the game
    private void update() {
        gsm.update();
    }

    // drawing the game
    private void draw() {
        gsm.draw(g);
    }

    // drawing the image on the screen
    private void drawToScreen() {
        // getting the graphics
        Graphics g2 = getGraphics();

        // drawing the image and scaling it
        g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

        // disposing the resources of the Graphics
        g2.dispose();
    }

    public void keyTyped(KeyEvent key) {
    }

    // managing the pressing of a key and delegating to GameStateManager
    public void keyPressed(KeyEvent key) {
        gsm.keyPressed(key.getKeyCode());
    }

    // managing the releasing of a key and delegating to GameStateManager
    public void keyReleased(KeyEvent key) {
        gsm.keyReleased(key.getKeyCode());
    }
}
