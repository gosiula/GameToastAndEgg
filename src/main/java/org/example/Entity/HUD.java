package org.example.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

// DISPLAYING THE HUD OF THE PLAYER - HEADS-UP DISPLAY
public class HUD {
    private final Player player;
    private BufferedImage image;
    private Font font;

    public HUD(Player p) {
        player = p;
        try {
            image = ImageIO.read(
                    Objects.requireNonNull(getClass().getResourceAsStream(
                            "/Player/hud.png"
                    ))
            );
            font = new Font("Century Gothic", Font.PLAIN, 14);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateInBackground() {
        // Add any background logic here, if needed
    }

    private void updateOnEDT() {
        // Update the UI on the Event Dispatch Thread
        player.getHealth(); // Example: Accessing a player's health
        player.getMaxHealth(); // Example: Accessing a player's max health
        player.getFire(); // Example: Accessing a player's fire
        player.getMaxFire(); // Example: Accessing a player's max fire
        // You can use these values to update the HUD accordingly
    }

    public void draw(Graphics2D g) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                updateInBackground();
                return null;
            }

            @Override
            protected void done() {
                updateOnEDT();
            }
        };

        worker.execute();

        // Drawing code for the HUD
        g.drawImage(image, 0, 20, null);
        g.setColor(new Color(255, 255, 255));
        g.setFont(font);
        g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 30, 35);
        g.drawString(player.getFire() / 100 + "/" + player.getMaxFire() / 100, 30, 55);
        g.drawString(String.valueOf(player.getPoints()),  38, 76);
    }
}
