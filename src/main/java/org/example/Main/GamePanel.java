package org.example.Main;
import org.example.Entity.*;
import org.example.GameState.GameStateManager;
import org.example.TileMap.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.sql.SQLOutput;


// CREATING A PANEL, STARTING THE GAME, IMAGES AND GSM
public class GamePanel extends JPanel implements Runnable, KeyListener {
    // width of the panel
    public static final int WIDTH = 384;

    // height of the panel
    public static final int HEIGHT = 288;

    // scale of the panel
    public static final int SCALE = 2;


    // Thread usage
    private static Player player;
    private static TileMap tileMap;
    private static Monster monster;
    private static Tomato tomato;
    private static Avocado avocado;

    private static Explosion explosion;
    private static FireBall fireball;
    private static HUD hud;
    private static Egg egg;

    // game thread
    private Thread mainThread;

    // the running of the thread
    private boolean threadIsRunning;

    // images
    private BufferedImage image;
    private Graphics2D g;

    // game state manager
    private GameStateManager gsm;
    private static Thread playerThread;
    public static Thread tileMapThread;
    public static Thread monsterThread;
    public static Thread tomatoThread;
    public static Thread avocadoThread;
    public static Thread fireballThread;
    public static Thread HUDThread;
    public static Thread explosionThread;
    public static Thread eggThread;


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
        if (mainThread == null) {
            mainThread = new Thread(this);
            addKeyListener(this);
            mainThread.start();
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

        startThreads();
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

    public static void startThreads() {
        startPlayerThread(player);
        startTileMapThread(tileMap);
        startMonstersThread(monster);
        startTomatoThread(tomato);
        startAvocadoThread(avocado);
        startFireBallThread(fireball);
        startHUDThread(hud);
        startExplosionThread(explosion);
        startEggThread(egg);
    }

    public static void stopThreads() {
        tileMapThread.stop();
        playerThread.stop();
        monsterThread.stop();
        tomatoThread.stop();
        avocadoThread.stop();
        fireballThread.stop();
        HUDThread.stop();
        explosionThread.stop();
        eggThread.stop();
        System.out.println("Threads stoped");
    }

    public static void startTileMapThread(TileMap tileMap) {
        System.out.println("TileMap thread started");
        tileMapThread = new Thread(tileMap);
        tileMapThread.start();
    }

    // Metoda startująca wątek gracza
    public static void startPlayerThread(Player player) {
        System.out.println("Player thread started");
        playerThread = new Thread(player);
        playerThread.start();
    }

    public static void startMonstersThread(Monster monster) {
        System.out.println("Monster thread started");
        monsterThread = new Thread(monster);
        monsterThread.start();
    }

    public static void startTomatoThread(Tomato tomato) {
        System.out.println("Tomato thread started");
        tomatoThread = new Thread(tomato);
        tomatoThread.start();
    }

    public static void startAvocadoThread(Avocado avocado) {
        System.out.println("Avocado thread started");
        avocadoThread = new Thread(avocado);
        avocadoThread.start();
    }

    public static void startFireBallThread(FireBall fireball) {
        System.out.println("Fireball thread started");
        fireballThread = new Thread(avocado);
        fireballThread.start();
    }

    public static void startHUDThread(HUD hud) {
        System.out.println("HUD thread started");
        HUDThread = new Thread(hud);
        HUDThread.start();
    }

    public static void startExplosionThread(Explosion explosion) {
        System.out.println("Explosion thread started");
        explosionThread = new Thread(explosion);
        explosionThread.start();
    }

    public static void startEggThread(Egg egg) {
        System.out.println("Explosion thread started");
        eggThread = new Thread(egg);
        eggThread.start();
    }

    // Metoda zatrzymująca wątek gracza
    public static void stopPlayerThread() {
        if (playerThread != null && playerThread.isAlive()) {
            playerThread.interrupt(); // Przerwanie wątku gracza
            try {
                playerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        // You can perform additional drawing operations here if needed
        g2d.dispose();
    }

    public void keyTyped(KeyEvent key) {
    }

    // managing the pressing of a key and delegating to GameStateManager
    public void keyPressed(KeyEvent key) {
        if (key.getKeyCode() == KeyEvent.VK_ENTER) {
            gsm.keyPressed(key.getKeyCode());
        } else {
            gsm.keyPressed(key.getKeyCode());
        }
    }

    // managing the releasing of a key and delegating to GameStateManager
    public void keyReleased(KeyEvent key) {
        gsm.keyReleased(key.getKeyCode());
    }
}
