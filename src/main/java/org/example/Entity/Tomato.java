package org.example.Entity;

import org.example.TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Tomato extends MapObject {

    private BufferedImage image;

    public Tomato(TileMap tm) {
        super(tm);
        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        // Load the image for the money
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Prizes/tomato.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // Money doesn't need to be updated actively
    }

    public void draw(Graphics2D g) {
        setMapPosition();
        g.drawImage(image, (int) (x + xMap - width / 2), (int) (y + yMap - height / 2), null);
    }
}
