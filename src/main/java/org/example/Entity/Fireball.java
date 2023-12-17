package org.example.Entity;
import org.example.TileMap.TileMap;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

// CREATING THE ANIMATION OF THE FIREBALL
public class Fireball extends MapObject implements Runnable {
    // fireball parameters
    private boolean hit;
    private boolean remove;
    private BufferedImage[] hitSprites;

    // Fireball constructor
    public Fireball(TileMap tm, boolean right) {
        // calling the constructor of the parent class
        super(tm);

        facingRight = right;
        moveSpeed = 3.8;

        // horizontal speed of the fireball
        if (right) dx = moveSpeed;
        else dx = -moveSpeed;

        // size of the fireball
        width = 30;
        height = 30;
        cWidth = 14;
        cHeight = 14;

        // loading the image
        try {
            BufferedImage spriteSheets = ImageIO.read(
                    Objects.requireNonNull(getClass().getResourceAsStream(
                            "/Player/fireball.png"
                    ))
            );

            // initialize hit sprites
            BufferedImage[] sprites = new BufferedImage[4];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spriteSheets.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }

            // initialize animation
            hitSprites = new BufferedImage[3];
            for (int i = 0; i < hitSprites.length; i++) {
                hitSprites[i] = spriteSheets.getSubimage(
                        i * width,
                        height,
                        width,
                        height
                );
            }

            // initialization of Animation object
            animation = new Animation();

            // creating animation frames
            animation.setFrames(sprites);

            // setting the delay
            animation.setDelay(70);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public void setHit() {
        // checking if the fireball was already hit on the wall
        if (hit) return;

        // switch to hit sprites and stop movement
        hit = true;
        animation.setFrames(hitSprites);
        animation.setDelay(70);
        dx = 0;
    }

    // checking if the fireball should be removed
    public boolean shouldRemove() {
        return remove;
    }

    // updating the fireball
    public void update() {
        checkTileMapCollision();
        setPosition(xTemporary, yTemporary);

        // if the fireball hits an obstacle, set it as hit
        if (dx == 0 && !hit) {
            setHit();
        }

        // if hit and animation has played once, flag for removal
        animation.update();
        if (hit && animation.hasPlayedOnce()) {
            remove = true;
        }
    }

    // drawing the fireball directly on the game panel
    public void draw(Graphics2D g) {
        setMapPosition();
        super.draw(g);
    }
}