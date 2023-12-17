package org.example.Entity;
import org.example.TileMap.TileMap;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

// AVOCADO
public class Avocado extends MapObject implements Runnable{
    // image
    private BufferedImage image;

    // Avocado constructor
    public Avocado(TileMap tm) {
        // calling the constructor of the parent class
        super(tm);

        // avocado size
        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        // loading the image
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Prizes/avocado.png")));
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
            }
        }
    }

    // drawing the avocado
    public void draw(Graphics2D g) {
        setMapPosition();
        g.drawImage(image, (int) (x + xMap - width / 2), (int) (y + yMap - height / 2), null);
    }


}
