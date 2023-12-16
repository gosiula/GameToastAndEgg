package org.example.Entity;

import org.example.TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Monster extends Enemy implements Runnable {
    private BufferedImage[] sprites;
    private final boolean running = true;


    @Override
    public void run() {
        while (running && !Thread.interrupted()) {
            update(); // Aktualizacja logiki gracza

            try {
                Thread.sleep(10); // Dodatkowy delay dla wątku gracza
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Przerwanie wątku po przechwyceniu InterruptedException
            }
        }
    }

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
        animation.setDelay(300);

        right = true;
        facingRight = true;
    }

    private void getNextPosition() {
        if (left) {
            dx -= moveSpeed;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else if (right) {
            dx += moveSpeed;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        }

        if (falling) {
            dy += fallSpeed;
        }
    }

    public void update() {
        getNextPosition();
        checkTileMapCollision();
        setPosition(xTemporary, yTemporary);

        if (flinching) {
            long elapsed =
                    (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed > 400) {
                flinching = false;
            }
        }

        if (right && dx == 0) {
            right = false;
            left = true;
            facingRight = false;
        } else if (left && dx == 0) {
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

}
