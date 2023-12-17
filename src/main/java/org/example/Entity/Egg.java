package org.example.Entity;

import org.example.TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Egg extends MapObject implements Runnable{

    private BufferedImage image;
    private final boolean running = true;

    public Egg(TileMap tm) {
        super(tm);
        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        // Load the image for the money
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Egg/julek_the_egg.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

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

    public void draw(Graphics2D g) {
        setMapPosition();
        g.drawImage(image, (int) (x + xMap - width / 2), (int) (y + yMap - height / 2), null);
    }

}
