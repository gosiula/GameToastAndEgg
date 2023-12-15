package org.example.Entity;

import org.example.TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Monster extends Enemy {
    private BufferedImage[] sprites;
    private Thread monsterLogicThread1;
    private Thread monsterGraphicsThread;
    private Thread monsterMainThread;
    private volatile boolean running = true;

    public Monster(TileMap tm) {
        super(tm);

        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        health = maxHealth = 2;
        damage = 1;

        try {
            BufferedImage spriteSheet = ImageIO.read(
                    Objects.requireNonNull(getClass().getResourceAsStream(
                            "/Enemy/ghost.png"
                    ))
            );

            sprites = new BufferedImage[3];
            for(int i = 0; i < sprites.length; i++) {
                sprites[i] = spriteSheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        right = true;
        facingRight = true;
    }

    private void getNextPosition() {
        if(left) {
            dx -= moveSpeed;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        }
        else if(right) {
            dx += moveSpeed;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        }

        if(falling) {
            dy += fallSpeed;
        }
    }

    public void update() {
        getNextPosition();
        checkTileMapCollision();
        setPosition(xTemporary, yTemporary);

        if(flinching) {
            long elapsed =
                    (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > 400) {
                flinching = false;
            }
        }

        if(right && dx == 0) {
            right = false;
            left = true;
            facingRight = false;
        }
        else if(left && dx == 0) {
            right = true;
            left = false;
            facingRight = true;
        }

        animation.update();
    }

    public void draw(Graphics2D g) {
        setMapPosition();
        super.draw(g);
    }

    public void start() {
        monsterLogicThread = new Thread(this::monsterLogic);
        monsterGraphicsThread = new Thread(this::monsterGraphics);
        monsterMainThread = new Thread(this::monsterMain);

        monsterLogicThread.start();
        monsterGraphicsThread.start();
        monsterMainThread.start();
    }

    public void stop() {
        running = false;
        try {
            monsterLogicThread.join();
            monsterGraphicsThread.join();
            monsterMainThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void monsterLogic() {
        while (running) {
            update();
            // Dodatkowa logika dla wątka logiki potwora
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void monsterGraphics() {
        while (running) {
            // Aktualizacja grafiki potwora
            // (możesz dodać swoją logikę renderowania)
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void monsterMain() {
        while (running) {
            // Dodatkowa logika dla wątka głównego potwora
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
