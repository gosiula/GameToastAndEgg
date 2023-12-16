package org.example.GameState;
import org.example.Main.GamePanel;
import org.example.TileMap.Background;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
public class Help extends GameState {

    // adding back buffer
    private BufferedImage backBuffer;

    // background
    private Background bg;

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

    // font of the instruction
    private Font instructionFont;

    // Help constructor
    public Help(GameStateManager gsm) {
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
            instructionFont = new Font("Century Gothic", Font.PLAIN, 9);

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
        drawHelp(backBufferGraphics);

        // changing the buffers
        g.drawImage(backBuffer, 0, 0, null);
    }

    private void drawHelp(Graphics2D g) {
        // draw background
        bg.draw(g);

        // draw the title with black outline
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("Help", 156, 60);
        g.drawString("Help", 158, 62);
        g.drawString("Help", 156, 62);
        g.drawString("Help", 158, 60);

        // draw "He" in brown
        g.setColor(LightBrownColor);
        g.setFont(titleFont);
        g.drawString("He", 157, 61);

        // draw "lp" in yellow
        g.setColor(yellowColor);
        g.drawString("lp", 199, 61);

        // draw menu options
        g.setFont(optionsFont);
        int startY = 215;

        // draw the instruction text background
        g.setColor(new Color(22, 34, 89, 100));  // Transparent navy blue color
        int rectWidth = 340;  // Adjust the rectangle width as needed
        int rectHeight = 120;  // Adjust the rectangle height as needed
        int rectX = (GamePanel.WIDTH - rectWidth) / 2;
        int rectY = 80;
        int arcWidth = 10;  // Adjust the arc width as needed
        int arcHeight = 10;  // Adjust the arc height as needed
        g.fillRoundRect(rectX, rectY, rectWidth, rectHeight, arcWidth, arcHeight);

        // draw the instruction text
        g.setFont(instructionFont);
        g.setColor(Color.WHITE);

        // Set the maximum width for text wrapping
        int maxWidth = 320;  // Adjust the maximum width as needed

        // Your help text
        String helpText = "Welcome to Toast & Egg! " +
                "In this adventure you will " +
                "take on the role of Henio The Bread, " +
                "who must safe his beloved friend Julek The Egg. " +
                "Along the way, he fights monsters " +
                "and collects points - tomatoes and avocados. " +
                "For each defeated monster he gets 10 points, " +
                "for each avocado 4, and for each tomato 2. " +
                "" +
                "Henio The Bread uses the following buttons: " +
                "left and right arrow to move, " +
                "up arrow to jump, " +
                "up arrow + space to fly, " +
                "F to throw fireballs, " +
                "S to scratching. " +
                "Good luck!";

        // Split the help text into lines with text wrapping
        ArrayList<String> lines = new ArrayList<>();
        String[] words = helpText.split("\\s+");
        StringBuilder currentLine = new StringBuilder();
        for (String word : words) {
            if (g.getFontMetrics().stringWidth(currentLine + " " + word) < maxWidth) {
                currentLine.append(word).append(" ");
            } else {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word + " ");
            }
        }
        lines.add(currentLine.toString());

        // Draw each line of the text
        int yy = 100;  // Adjust the starting y-coordinate as needed
        for (String line : lines) {
            g.drawString(line, rectX + 10, yy);
            yy += 15;  // Adjust the vertical spacing between lines as needed
        }

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
            choose();
        }
        if (k == KeyEvent.VK_UP) {
            currentChoice--;
            if (currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if (currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }

    public void keyReleased(int k) {

    }
}
