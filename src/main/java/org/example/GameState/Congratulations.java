package org.example.GameState;
import org.example.Main.GamePanel;
import org.example.TileMap.Background;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import static org.example.Music.Music.choosingSound;
import static org.example.Music.Music.stopChoosingSound;

// INITIALIZATION AND DRAWING OF CONGRATULATIONS STATE (BACKGROUND, TITLE, OPTIONS, SOUNDS)
public class Congratulations extends GameState {

    // adding back buffer
    private BufferedImage backBuffer;

    // background
    private Background bg;
    private Background bg2;

    // current choice
    private int currentChoice = 0;

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
    private Color frameColor;
    private Color textColor;

    // text font
    private Font textFont;

    // color of the pink rectangle on the background
    private Color pinkRectangleColor;

    // Help constructor
    public Congratulations(GameStateManager gsm) {
        this.gsm = gsm;

        try {
            // initialization of the background and the speed of its movement
            bg = new Background("/Backgrounds/background_sky.png", 8);
            bg2 = new Background("/Backgrounds/sandwich.png",0);

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
            this.frameColor = new Color(0, 0, 0);
            this.textColor = new Color(255, 255, 255);
            this.pinkRectangleColor = new Color(255, 113, 181, 105);
            textFont = new Font("Century Gothic", Font.PLAIN, 24);

            // initialization of the back buffer
            backBuffer = new BufferedImage(GamePanel.WIDTH, GamePanel.HEIGHT, BufferedImage.TYPE_INT_RGB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialization() { }

    public void update() { bg.update(); }

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

    private void drawPlayerPointsAndTime(Graphics2D g) {
        // calculating final points
        int points;
        if(gsm.getElapsedTime() > 0) {
            points = Level1.getFinalPoints() + 100 - (int) gsm.getElapsedTime();
        } else {
            points = Level1.getFinalPoints();
        }

        // draw the points with black outline
        g.setFont(textFont);
        g.setColor(frameColor);
        g.drawString("Points: " + points, 138, 171);
        g.drawString("Points: " + points, 136, 171);
        g.drawString("Points: " + points, 136, 169);
        g.drawString("Points: " + points, 138, 169);
        g.setColor(textColor);
        g.drawString("Points: " + points, 137, 170);

        // changing the format of the time to display in the format 00:00
        int minutes = (int) gsm.getElapsedTime() / 60;
        int seconds = (int) gsm.getElapsedTime() % 60;
        String formattedTime = String.format("%02d:%02d", minutes, seconds);

        // draw the time with black outline
        g.setColor(frameColor);
        g.drawString("Time: " + formattedTime, 132, 194);
        g.drawString("Time: " + formattedTime, 130, 194);
        g.drawString("Time: " + formattedTime, 130, 196);
        g.drawString("Time: " + formattedTime, 132, 196);
        g.setColor(textColor);
        g.drawString("Time: " + formattedTime, 131, 195);
    }

    private void drawGameOver(Graphics2D g) {
        // draw background
        bg.draw(g);

        // transparent pink rectangle
        g.setColor(pinkRectangleColor);
        int rectWidth = 384;
        int rectHeight = 288;
        int rectX = 0;
        int rectY = 0;
        g.fillRect(rectX, rectY, rectWidth, rectHeight);

        // draw second background
        bg2.draw(g);

        // draw the title with black outline
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("Congratulations !", 62, 98);
        g.drawString("Congratulations !", 60, 100);
        g.drawString("Congratulations !", 62, 100);
        g.drawString("Congratulations !", 60, 98);

        // draw "He" in brown
        g.setColor(LightBrownColor);
        g.drawString("Congratu", 61, 99);

        // draw "lp" in yellow
        g.setColor(yellowColor);
        g.drawString("lations !", 206, 99);

        // drawing player points and time
        drawPlayerPointsAndTime(g);

        g.setFont(optionsFont);

        // location y of the options to select
        int startY = 220;

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
            stopChoosingSound();
            choose();
        }
        if (k == KeyEvent.VK_UP) {
            choosingSound();
            currentChoice--;
            if (currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            choosingSound();
            currentChoice++;
            if (currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }

    public void keyReleased(int k) { }
}
