package org.example.GameState;
import org.example.Main.GamePanel;
import org.example.TileMap.Background;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import static org.example.Music.Music.choosingMusic;
import static org.example.Music.Music.stopChoosingMusic;

public class GameOver extends GameState {
    // background
    private Background bg;

    // current choice
    private int currentChoice = 0;

    private BufferedImage backBuffer;

    // table of options on the menu
    private final String[] options = { "Back To Menu", "Quit" };

    // title font
    private Font titleFont;

    // colors of the title
    private Color titleColor;
    private Color LightBrownColor;
    private Color yellowColor;

    // font of the options
    private Font optionsFont;

    // color of the options
    private Color pinkColor;
    private Color purpleColor;
    private Color darkBrownColor;

    // Help constructor
    public GameOver(GameStateManager gsm) {
        this.gsm = gsm;

        try {
            // initialization of the background and the speed of its movement
            bg = new Background("/Backgrounds/background_sky.png", 8);

            // vector of the movement of the background
            bg.setVector(-0.1, 0);

            // initialization of fonts and colors
            titleFont = new Font("Century Gothic", Font.BOLD, 32);
            this.titleColor = new Color(28, 28, 28);
            this.LightBrownColor = new Color(155, 117, 78);
            this.yellowColor = new Color(255, 210, 26);
            optionsFont = new Font("Century Gothic", Font.PLAIN, 12);
            this.pinkColor = new Color(231, 120, 231);
            this.purpleColor = new Color(131, 20, 131);
            this.darkBrownColor = new Color(51, 30, 10);

            // initialization of the back buffer
            backBuffer = new BufferedImage(GamePanel.WIDTH, GamePanel.HEIGHT, BufferedImage.TYPE_INT_RGB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void initialization() {

    }

    public void update() {
        bg.update();
    }

    public void draw(Graphics2D g) {
        // drawing on the back buffer
        Graphics2D backBufferGraphics = (Graphics2D) backBuffer.getGraphics();
        backBufferGraphics.setColor(Color.BLACK);
        backBufferGraphics.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        // drawing help with the original method
        drawGameOver(backBufferGraphics);

        // changing the buffers
        g.drawImage(backBuffer, 0, 0, null);
    }

    private void drawPlayerPoints(Graphics2D g) {
        // Ustawienia czcionki i koloru
        Font font = new Font("Century Gothic", Font.PLAIN, 24);
        Color textColor = new Color(255, 255, 255);

        // Przygotowanie do rysowania napisu z cieniem
        g.setFont(font);
        g.setColor(new Color(0, 0, 0));
        g.drawString("Points: " + Level1.getFinalPoints(), 138, 151);
        g.drawString("Points: " + Level1.getFinalPoints(), 136, 151);
        g.drawString("Points: " + Level1.getFinalPoints(), 136, 149);
        g.drawString("Points: " + Level1.getFinalPoints(), 138, 149);

        // Rysowanie właściwego napisu
        g.setColor(textColor);
        g.drawString("Points: " + Level1.getFinalPoints(), 137, 150);
    }

    private void drawGameOver(Graphics2D g) {
        // draw background
        bg.draw(g);

        g.setColor(new Color(0, 0, 0, 80));  // Transparent navy blue color
        int rectWidth = 384;  // Adjust the rectangle width as needed
        int rectHeight = 288;  // Adjust the rectangle height as needed
        int rectX = 0;
        int rectY = 0;
        g.fillRect(rectX, rectY, rectWidth, rectHeight);

        // draw the title with black outline
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("Game Over", 102, 98);
        g.drawString("Game Over", 100, 100);
        g.drawString("Game Over", 102, 100);
        g.drawString("Game Over", 100, 98);

        // draw "He" in brown
        g.setColor(LightBrownColor);
        g.setFont(titleFont);
        g.drawString("Game", 101, 99);

        // draw "lp" in yellow
        g.setColor(yellowColor);
        g.drawString("Over", 208, 99);

        drawPlayerPoints(g);

        // draw menu options
        g.setFont(optionsFont);
        int startY = 195;

        g.setFont(optionsFont);
        // options to select
        for (int i = 0; i < options.length; i++) {
            int textWidth = (int) g.getFontMetrics().getStringBounds(options[i], g).getWidth();
            int textHeight = (int) g.getFontMetrics().getStringBounds(options[i], g).getHeight();

            // calculating the center position for x and y
            int x = (GamePanel.WIDTH - textWidth - 20) / 2;
            int y = startY + i * 30 + (15 - textHeight) / 2;

            if (i == currentChoice) {
                // the option that is chosen (pink and purple color in a border)
                g.setColor(pinkColor);
                g.fillRoundRect(x, y, textWidth + 20, 15, 5, 5);
                g.setColor(purpleColor);
            } else {
                // the option that is not chosen (yellow and dark brown color in a border)
                g.setColor(yellowColor);
                g.fillRoundRect(x, y, textWidth + 20, 15, 5, 5);
                g.setColor(darkBrownColor);
            }
            // drawing the border with text
            g.drawRoundRect(x, y, textWidth + 20, 15, 5, 5);
            g.drawString(options[i], x + 10, y + textHeight - 2);
        }
    }

    // choosing the option
    private void choose() {
        if (currentChoice == 0) {
            // starting the level 1 state
            gsm.setState(GameStateManager.MENU_STATE);
        }
        if (currentChoice == 1) {
            // quit
            System.exit(0);
        }
    }

    // handling the pressed key in the help state
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            stopChoosingMusic();
            choose();
        }
        if (k == KeyEvent.VK_UP) {
            choosingMusic();
            currentChoice--;
            if (currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            choosingMusic();
            currentChoice++;
            if (currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }

    public void keyReleased(int k) {

    }
}
