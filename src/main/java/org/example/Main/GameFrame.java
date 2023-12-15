package org.example.Main;
import javax.swing.JFrame;

// CREATING A FRAME
public class GameFrame {
    public static void main(String[] args) {
        // creating a new frame with the title
        JFrame frame = new JFrame("Toast & Egg");

        // setting the content of the window to an object of GamePanel class
        frame.setContentPane(new GamePanel());

        // setting the default close operation to exit on close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // setting the window not to be resizable by user
        frame.setResizable(false);

        // adjusting the window size to fit its content
        frame.pack();

        // centering the frame on the screen
        frame.setLocationRelativeTo(null);

        // setting visibility of the window to true in order to display it
        frame.setVisible(true);
    }
}
