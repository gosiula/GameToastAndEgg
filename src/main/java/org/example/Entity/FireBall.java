package org.example.Entity;

import org.example.TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class FireBall extends MapObject {
    private boolean hit;
    private boolean remove;
    private BufferedImage[] hitSprites;
    private Thread fireBallLogicThread;
    private Thread fireBallGraphicsThread;
    private Thread fireBallMainThread;
    private volatile boolean running = true;

    public FireBall(TileMap tm, boolean right) {
        super(tm);
        facingRight = right;

        moveSpeed = 3.8;

        if (right) dx = moveSpeed;
        else dx = -moveSpeed;

        width = 30;
        height = 30;
        cWidth = 14;
        cHeight = 14;

        try {
            BufferedImage spriteSheets = ImageIO.read(
                    Objects.requireNonNull(getClass().getResourceAsStream(
                            "/Player/fireball.png"
                    ))
            );

            BufferedImage[] sprites = new BufferedImage[4];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spriteSheets.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }

            hitSprites = new BufferedImage[3];
            for (int i = 0; i < hitSprites.length; i++) {
                hitSprites[i] = spriteSheets.getSubimage(
                        i * width,
                        height,
                        width,
                        height
                );
            }

            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(70);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHit() {
        if (hit) return;
        hit = true;
        animation.setFrames(hitSprites);
        animation.setDelay(70);
        dx = 0;
    }

    public boolean shouldRemove() {
        return remove;
    }

    public void update() {
        checkTileMapCollision();
        setPosition(xTemporary, yTemporary);

        if (dx == 0 && !hit) {
            setHit();
        }

        animation.update();
        if (hit && animation.hasPlayedOnce()) {
            remove = true;
        }
    }

    public void draw(Graphics2D g) {
        setMapPosition();
        super.draw(g);
    }

    public void start() {
        fireBallLogicThread = new Thread(this::fireBallLogic);
        fireBallGraphicsThread = new Thread(this::fireBallGraphics);
        fireBallMainThread = new Thread(this::fireBallMain);

        fireBallLogicThread.start();
        fireBallGraphicsThread.start();
        fireBallMainThread.start();
    }

    public void stop() {
        running = false;
        try {
            fireBallLogicThread.join();
            fireBallGraphicsThread.join();
            fireBallMainThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void fireBallLogic() {
        while (running) {
            update();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void fireBallGraphics() {
        while (running) {
            // Aktualizacja grafiki fireballa
            // (możesz dodać swoją logikę renderowania)
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void fireBallMain() {
        while (running) {
            // Dodatkowa logika dla wątka głównego fireballa
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
