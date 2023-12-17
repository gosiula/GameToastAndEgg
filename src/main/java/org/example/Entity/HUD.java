package org.example.Entity;
import org.example.GameState.GameStateManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.imageio.ImageIO;

// DISPLAYING THE HUD OF THE PLAYER - HEADS-UP DISPLAY
public class HUD implements Runnable {
    // player
    private final Player player;

    // image
    private BufferedImage image;

    // font of the hud
    private Font font;

    private final GameStateManager gsm;

    // HUD constructor
    public HUD(Player p, GameStateManager gsm) {
        player = p;
        this.gsm = gsm;
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

    // running the thread in GamePanel
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // drawing the hud
    public void draw(Graphics2D g) {
        // image
        g.drawImage(image, 0 ,20, null);

        // color of the hud
        g.setColor(new Color(255, 255, 255));

        // font of the hud
        g.setFont(font);

        // drawing the health of the player
        g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 30, 34);

        // drawing the fireball supply of the player
        g.drawString(player.getFire() / 100 + "/" + player.getMaxFire() / 100, 30, 55);

        // drawing the points of the player
        g.drawString(String.valueOf(player.getPoints()),  38, 76);

        // changing the format of the time to display in the format 00:00
        int minutes = (int) gsm.getElapsedTime() / 60;
        int seconds = (int) gsm.getElapsedTime() % 60;
        String formattedTime = String.format("%02d:%02d", minutes, seconds);

        // drawing the timer
        g.drawString(formattedTime, 340, 34);
    }
}
