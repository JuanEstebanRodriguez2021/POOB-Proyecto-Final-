
package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BadDOPOCreamGUI extends JFrame {

    private JButton playButton;

    public BadDOPOCreamGUI() {
        setTitle("Bad DOPO Cream");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        prepareElements();
        prepareActions();

        setVisible(true);
    }

    private void prepareElements() {
        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                BufferedImage image =
                    SpriteManager.load("inicio.jpg");

                if (image != null) {
                    g.drawImage(
                        image,
                        0, 0,
                        getWidth(), getHeight(),
                        this
                    );
                } else {
                    g.setColor(new Color(20, 48, 78));
                    g.fillRect(
                        0, 0, getWidth(), getHeight()
                    );
                }
            }
        };

        background.setLayout(null);
        setContentPane(background);

        playButton = new JButton("JUGAR");
        playButton.setBounds(210, 365, 180, 55);

        playButton.setFont(new Font(
            "SansSerif", Font.BOLD, 22
        ));

        playButton.setBackground(
            new Color(42, 161, 198)
        );

        playButton.setForeground(Color.WHITE);
        playButton.setFocusPainted(false);

        playButton.setCursor(
            Cursor.getPredefinedCursor(
                Cursor.HAND_CURSOR
            )
        );

        background.add(playButton);
    }

    private void prepareActions() {
        playButton.addActionListener(e -> {
            new GameSetupFrame();
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
            BadDOPOCreamGUI::new
        );
    }
}

