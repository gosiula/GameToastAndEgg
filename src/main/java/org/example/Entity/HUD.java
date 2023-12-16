package org.example.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

// DISPLAYING THE HUD OF THE PLAYER - HEADS-UP DISPLAY
public class HUD implements Runnable{
    private final Player player;
    private BufferedImage image;
    private Font font;
    private volatile boolean running = true;

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

    @Override
    public void run() {
        while (running && !Thread.interrupted()) {
            try {
                HUD hud = new HUD(player);
                Thread.sleep(10); // Dodatkowy delay dla wątku gracza
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Przerwanie wątku po przechwyceniu InterruptedException
            }
        }
    }

    public void draw(Graphics2D g) {
        // Drawing code for the HUD
        g.drawImage(image, 0, 20, null);
        g.setColor(new Color(255, 255, 255));
        g.setFont(font);
        g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 30, 35);
        g.drawString(player.getFire() / 100 + "/" + player.getMaxFire() / 100, 30, 55);
        g.drawString(String.valueOf(player.getPoints()),  38, 76);
    }
}
