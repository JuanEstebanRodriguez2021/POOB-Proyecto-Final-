
package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BadDOPOCreamGUI extends JFrame {

    private JButton playButton;

    public BadDOPOCreamGUI() {
        setTitle("Bad DOPO Cream");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        prepareElements();
        prepareActions();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void prepareElements() {

        JPanel background = new JPanel(null) {

            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);

                Graphics2D g = (Graphics2D) graphics.create();

                g.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
                );

                BufferedImage image =
                    SpriteManager.load("inicio.png");

                if (image != null) {
                    g.drawImage(
                        image,
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        null
                    );
                } else {
                    g.setColor(new Color(40, 120, 190));
                    g.fillRect(
                        0, 0,
                        getWidth(),
                        getHeight()
                    );

                    g.setColor(Color.WHITE);
                    g.setFont(new Font(
                        "SansSerif", Font.BOLD, 36
                    ));

                    g.drawString(
                        "BAD DOPO CREAM",
                        100, 240
                    );
                }

                g.dispose();
            }
        };

        background.setPreferredSize(
            new Dimension(650, 650)
        );

        setContentPane(background);

        playButton = SpriteManager.imageButton(
            "Start.png",
            "JUGAR",
            220,
            75
        );

        playButton.setBounds(
            215,
            535,
            220,
            75
        );

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

