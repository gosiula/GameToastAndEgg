package org.example.Entity;
import org.example.TileMap.TileMap;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

// GHOST - DRAWING, ANIMATION AND UPDATING
public class Ghost extends Enemy implements Runnable {
    // image
    private BufferedImage[] sprites;

    // running the thread in GamePanel
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            update();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Ghost constructor
    public Ghost(TileMap tm) {
        // calling the constructor of the parent class
        super(tm);

        // speed of the ghost
        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        // size of the ghost
        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        // health and damage to the ghost
        health = maxHealth = 2;
        damage = 1;

        // loading the image
        try {
            BufferedImage spriteSheet = ImageIO.read(
                    Objects.requireNonNull(getClass().getResourceAsStream(
                            "/Enemy/ghost.png"
                    ))
            );

            // initialize ghost sprites
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

        // initialization of Animation object
        animation = new Animation();

        // creating animation frames
        animation.setFrames(sprites);

        // setting the delay
        animation.setDelay(300);

        right = true;
        facingRight = true;
    }

    private void getNextPosition() {
        // movement of the ghost
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

        // falling of the ghost
        if(falling) {
            dy += fallSpeed;
        }
    }

    // updating the ghost
    public void update() {
        // updating position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xTemporary, yTemporary);

        // checking for flinching
        if(flinching) {
            long elapsed =
                    (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > 400) {
                flinching = false;
            }
        }

        // if the ghost hits a wall, go other direction
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

        // updating animation
        animation.update();

    }

    // drawing the ghost
    public void draw(Graphics2D g) {
        // setting the position of the ghost
        setMapPosition();

        // drawing the ghost
        super.draw(g);
    }
}
