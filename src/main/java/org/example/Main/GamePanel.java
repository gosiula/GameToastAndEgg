package org.example.Main;
import org.example.Entity.*;
import org.example.GameState.GameStateManager;
import org.example.TileMap.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

// CREATING A PANEL, STARTING THE GAME, IMAGES, GSM AND ALL THREADS
public class GamePanel extends JPanel implements Runnable, KeyListener {
    // width of the panel
    public static final int WIDTH = 384;

    // height of the panel
    public static final int HEIGHT = 288;

    // scale of the panel
    public static final int SCALE = 2;


    // thread usage
    private static Player player;
    private static TileMap tileMap;
    private static Ghost monster;
    private static Tomato tomato;
    private static Avocado avocado;
    private static Explosion explosion;
    private static Fireball fireball;
    private static HUD hud;
    private static Egg egg;


    // main game thread
    private Thread mainThread;


    // the running of the thread
    private boolean threadIsRunning;

    // images and graphics
    private BufferedImage image;
    private Graphics2D g;


    // game state manager
    private GameStateManager gsm;

    // game threads
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
        // calling the constructor of the parent class
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

        // starting threads
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
    private void update() { gsm.update(); }

    // drawing the game
    private void draw() { gsm.draw(g); }

    // drawing the image on the screen
    private void drawToScreen() {
        // getting the graphics
        Graphics g2 = getGraphics();

        // drawing the image and scaling it
        g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

        // disposing the resources of the Graphics
        g2.dispose();
    }

    // starting threads
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

    // stopping threads
    public static void stopThreads() {
        stopPlayerThread();
        stopTileMapThread();
        stopMonsterThread();
        stopTomatoThread();
        stopAvocadoThread();
        stopFireBallThread();
        stopHUDThread();
        stopExplosionThread();
        stopEggThread();
    }

    public static void startTileMapThread(TileMap tileMap) {
        tileMapThread = new Thread(tileMap);
        tileMapThread.start();
    }

    // Metoda startująca wątek gracza
    public static void startPlayerThread(Player player) {
        playerThread = new Thread(player);
        playerThread.start();
    }

    public static void startMonstersThread(Ghost monster) {
        monsterThread = new Thread(monster);
        monsterThread.start();
    }

    public static void startTomatoThread(Tomato tomato) {
        tomatoThread = new Thread(tomato);
        tomatoThread.start();
    }

    public static void startAvocadoThread(Avocado avocado) {
        avocadoThread = new Thread(avocado);
        avocadoThread.start();
    }

    public static void startFireBallThread(Fireball fireball) {
        fireballThread = new Thread(fireball);
        fireballThread.start();
    }

    public static void startHUDThread(HUD hud) {
        HUDThread = new Thread(hud);
        HUDThread.start();
    }

    public static void startExplosionThread(Explosion explosion) {
        explosionThread = new Thread(explosion);
        explosionThread.start();
    }

    public static void startEggThread(Egg egg) {
        eggThread = new Thread(egg);
        eggThread.start();
    }

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

    public static void stopTileMapThread() {
        if (tileMapThread != null && tileMapThread.isAlive()) {
            tileMapThread.interrupt();
            try {
                tileMapThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stopMonsterThread() {
        if (monsterThread != null && monsterThread.isAlive()) {
            monsterThread.interrupt();
            try {
                monsterThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stopTomatoThread() {
        if (tomatoThread != null && tomatoThread.isAlive()) {
            tomatoThread.interrupt();
            try {
                tomatoThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stopAvocadoThread() {
        if (avocadoThread != null && avocadoThread.isAlive()) {
            avocadoThread.interrupt();
            try {
                avocadoThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stopFireBallThread() {
        if (fireballThread != null && fireballThread.isAlive()) {
            fireballThread.interrupt();
            try {
                fireballThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stopHUDThread() {
        if (HUDThread != null && HUDThread.isAlive()) {
            HUDThread.interrupt();
            try {
                HUDThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stopExplosionThread() {
        if (explosionThread != null && explosionThread.isAlive()) {
            explosionThread.interrupt();
            try {
                explosionThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stopEggThread() {
        if (eggThread != null && eggThread.isAlive()) {
            eggThread.interrupt();
            try {
                eggThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
