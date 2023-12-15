package org.example.TileMap;
import org.example.Main.GamePanel;
import java.awt.*;
import java.awt.image.*;
import java.util.Objects;
import javax.imageio.ImageIO;

// HANDLING THE IMAGE, LOCATION AND MOVEMENT OF THE BACKGROUND
public class Background {
    // image
    private BufferedImage image;

    // location x, y and its change dx and dy
    private double x;
    private double y;
    private double dx;
    private double dy;

    // move scale
    private double moveScale;

    // Background constructor
    public Background(String s, double ms) {
        // getting the image from the file
        try {
            image = ImageIO.read(
                    Objects.requireNonNull(getClass().getResourceAsStream(s))
            );
            moveScale = ms;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    // setting the position of the background
    public void setPosition(double x, double y) {
        this.x = (x * moveScale) % GamePanel.WIDTH;
        this.y = (y * moveScale) % GamePanel.HEIGHT;
    }

    // setting the vector of the background
    public void setVector(double dx, double dy) {
        this.dx = dx * moveScale;
        this.dy = dy * moveScale;
    }

    // updating the background
    public void update() {
        x += dx;
        y += dy;

        // changing the x to be in the range from 0 to GamePanel.WIDTH
        x = (x + GamePanel.WIDTH) % GamePanel.WIDTH;
    }

    // drawing the background
    public void draw(Graphics2D g) {
        g.drawImage(image, (int)x, (int)y, null);

        // drawing the background based on the value of x
        if(x < 0) {
            g.drawImage(image, (int)x + GamePanel.WIDTH, (int)y, null);
        }
        if(x > 0) {
            g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);
        }
    }
}
