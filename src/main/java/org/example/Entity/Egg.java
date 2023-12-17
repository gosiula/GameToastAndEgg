package org.example.Entity;
import org.example.TileMap.TileMap;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

// EGG
public class Egg extends MapObject implements Runnable{
    // image
    private BufferedImage image;

    public Egg(TileMap tm) {
        // calling the constructor of the parent class
        super(tm);

        // egg size
        width = 30;
        height = 30;


        // loading the image
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Egg/julek_the_egg.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() { }

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

    public void draw(Graphics2D g) {
        setMapPosition();
        g.drawImage(image, (int) (x + xMap - width / 2), (int) (y + yMap - height / 2), null);
    }

}
