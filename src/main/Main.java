package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D adv");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel); // adding a sub-component GamePanel

        window.pack(); // this sizes the original window to fit the preferred size and layouts of its
        // sub-components i.e. GamePanel

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.setUpGame();
        gamePanel.startGameThread();
    }
}
