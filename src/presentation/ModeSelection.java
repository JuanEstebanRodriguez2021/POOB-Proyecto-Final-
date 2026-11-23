package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * Selection of the players and CPU
 * Juan Esteban Rodriguez- Sara Gonzalez
 */

public class ModeSelection extends JFrame {

    private JButton btnOnePlayer;
    private JButton btnTwoPlayers;
    private JButton btnBack;

    private JLabel background;
    private JLabel title;

    /**
     * Constructor
     */
    public ModeSelection() {
        setTitle("Play Menu");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        prepareElements();
        prepareActions();

        setVisible(true);
    }

    /**
     * Method to convert a picture as a button
     */
    private JButton createImageButton(String path) {
        ImageIcon icon = new ImageIcon(path);
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * Method for prepare all elements necessary to the mode selection
     */
    private void prepareElements() {
        ImageIcon titleIcon = new ImageIcon("resources/scoops.jpg");
        title = new JLabel(titleIcon);
        title.setBounds(150, 40, 300, 80);

        File file = new File("resources/backgr.png");
        ImageIcon bgIcon = new ImageIcon(file.getAbsolutePath());
        background = new JLabel(bgIcon) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = bgIcon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        background.setBounds(0, 0, getWidth(), getHeight());

        btnOnePlayer = createImageButton("resources/option1player.jpg");
        btnOnePlayer.setBounds(110, 200, 150, 150);

        btnTwoPlayers = createImageButton("resources/option2player.jpg");
        btnTwoPlayers.setBounds(330, 200, 150, 150);

        btnBack = createImageButton("resources/back.jpg");
        btnBack.setBounds(200, 390, 200, 60);

        add(title);
        add(btnOnePlayer);
        add(btnTwoPlayers);
        add(btnBack);
        add(background);
    }

    /**
     * Method for prepare the actions of those elements before mentioned and pass to
     * the other screen
     */
    private void prepareActions() {
        btnOnePlayer.addActionListener(e -> {
            new CharacterSelection();
            dispose();
        });

        btnTwoPlayers.addActionListener(e -> JOptionPane.showMessageDialog(null, "2 Players: En construcción"));

        btnBack.addActionListener(e -> {
            new BadDOPOCreamGUI();
            dispose();
        });
    }
}
