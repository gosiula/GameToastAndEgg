package org.example.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Explosion {
    private final int x;
    private final int y;
    private int xMap;
    private int yMap;
    private final int width;
    private final int height;
    private final Animation animation;
    private BufferedImage[] sprites;
    private boolean remove;

    private final Thread explosionLogicThread;
    private Thread explosionGraphicsThread;
    private Thread explosionMainThread;
    private volatile boolean running = true;

    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;
        width = 30;
        height = 30;

        try {
            BufferedImage spriteSheet = ImageIO.read(
                    Objects.requireNonNull(getClass().getResourceAsStream(
                            "/Enemy/explosion.png"
                    ))
            );

            sprites = new BufferedImage[6];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spriteSheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(70);

        // Separate thread for logic
        explosionLogicThread = new Thread(this::explosionLogic);
        explosionLogicThread.start();

        // Separate thread for graphics
        explosionGraphicsThread = new Thread(this::explosionGraphics);
        explosionGraphicsThread.start();

        // Separate main thread (for any additional main logic)
        explosionMainThread = new Thread(this::explosionMain);
        explosionMainThread.start();
    }

    public void update() {
        animation.update();
        if (animation.hasPlayedOnce()) {
            remove = true;
        }
    }

    public boolean shouldRemove() {
        return remove;
    }

    public void setMapPosition(int x, int y) {
        xMap = x;
        yMap = y;
    }

    public void draw(Graphics2D g) {
        g.drawImage(
                animation.getImage(),
                x + xMap - width / 2,
                y + yMap - height / 2,
                null
        );
    }

    public void stop() {
        running = false;
        try {
            explosionLogicThread.join();
            explosionGraphicsThread.join();
            explosionMainThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void explosionLogic() {
        while (running) {
            update();
            // Dodatkowa logika dla wątka logiki explosion
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void explosionGraphics() {
        while (running) {
            // Aktualizacja grafiki explosion
            // (możesz dodać swoją logikę renderowania)
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void explosionMain() {
        while (running) {
            // Dodatkowa logika dla wątka głównego explosion
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
