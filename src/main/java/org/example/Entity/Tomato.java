package org.example.Entity;
import org.example.TileMap.TileMap;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

// TOMATO
public class Tomato extends MapObject implements Runnable{
    // image
    private BufferedImage image;

    // Tomato constructor
    public Tomato(TileMap tm) {
        // calling the constructor of the parent class
        super(tm);

        // tomato size
        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        // loading the image
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Prizes/tomato.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() { }

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

    // drawing the tomato
    public void draw(Graphics2D g) {
        setMapPosition();
        g.drawImage(image, (int) (x + xMap - width / 2), (int) (y + yMap - height / 2), null);
    }
}
