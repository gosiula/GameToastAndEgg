package org.example.Entity;
import org.example.Main.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Explosion implements Runnable{
    private final int x;
    private final int y;
    private int xMap;
    private int yMap;
    private final int width;
    private final int height;
    private final Animation animation;
    private BufferedImage[] sprites;
    private boolean remove;
    private GamePanel gamePanel;

    private volatile boolean running = true;

    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;
        this.gamePanel = gamePanel;
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
    }

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
}
