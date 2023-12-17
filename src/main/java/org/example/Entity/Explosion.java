package org.example.Entity;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

// CREATING THE ANIMATION OF THE EXPLOSION
public class Explosion implements Runnable {
    // explosion parameters
    private final int x;
    private final int y;
    private int xMap;
    private int yMap;
    private final int width;
    private final int height;
    private final Animation animation;
    private BufferedImage[] sprites;
    private boolean remove;

    // Explosion constructor
    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;
        width = 30;
        height = 30;

        try {
            // loading the explosion image
            BufferedImage spriteSheet = ImageIO.read(
                    Objects.requireNonNull(getClass().getResourceAsStream(
                            "/Enemy/explosion.png"
                    ))
            );

            // creating a table with sub images from the image
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

        // initialization of Animation object
        animation = new Animation();

        // creating animation frames
        animation.setFrames(sprites);

        // setting the delay
        animation.setDelay(70);
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

    // updating the animation
    public void update() {
        animation.update();
        if(animation.hasPlayedOnce()) {
            remove = true;
        }
    }

    // checking if the explosion should be removed
    public boolean shouldRemove() { return remove; }

    // setting map position of the explosion
    public void setMapPosition(int x, int y) {
        xMap = x;
        yMap = y;
    }

    // drawing the explosion
    public void draw(Graphics2D g) {
        g.drawImage(
                animation.getImage(),
                x + xMap - width / 2,
                y + yMap - height / 2,
                null
        );
    }
}
