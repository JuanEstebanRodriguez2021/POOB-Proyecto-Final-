package presentation;

import domain.Flavor;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GameSetupFrame extends JFrame {

    private int playerCount = 1;

    private Flavor flavor1 = Flavor.CHOCOLATE;
    private Flavor flavor2 = Flavor.STRAWBERRY;

    private JPanel cards;
    private JButton onePlayer;
    private JButton twoPlayers;

    private Font pixelFont;
    private Font pixelTitle;

    private final Color darkText = new Color(34, 29, 53);

    public GameSetupFrame() {
        setTitle("Bad DOPO Cream - Seleccion de personajes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        loadFonts();
        createInterface();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadFonts() {
        Font loaded = null;

        String[] paths = {
            "resources/font.ttf",
            "resources/fonts/font.ttf",
            "resources/fonts/PressStart2P-Regular.ttf",
            "resources/PressStart2P-Regular.ttf"
        };

        for (String path : paths) {
            File file = new File(path);

            if (!file.isFile()) {
                continue;
            }

            try {
                loaded = Font.createFont(
                    Font.TRUETYPE_FONT, file
                );
                break;
            } catch (Exception e) {
                System.err.println(
                    "No se pudo cargar fuente: " + path
                );
            }
        }

        if (loaded == null) {
            loaded = new Font(
                Font.MONOSPACED, Font.BOLD, 16
            );
        }

        pixelFont = loaded.deriveFont(Font.BOLD, 13f);
        pixelTitle = loaded.deriveFont(Font.BOLD, 23f);
    }

    private void createInterface() {

        JPanel background = new JPanel(new BorderLayout(16, 16)) {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);

                Graphics2D g = (Graphics2D) graphics.create();

                BufferedImage image =
                    SpriteManager.loadBackground("backgr");

                if (image != null) {
                    g.drawImage(
                        image,
                        0, 0,
                        getWidth(), getHeight(),
                        null
                    );
                } else {
                    g.setColor(new Color(226, 246, 255));
                    g.fillRect(
                        0, 0, getWidth(), getHeight()
                    );
                }

                g.dispose();
            }
        };

        background.setPreferredSize(new Dimension(950, 720));
        background.setBorder(
            new EmptyBorder(30, 45, 28, 45)
        );

        setContentPane(background);

        JPanel header = new JPanel(new BorderLayout(12, 16));
        header.setOpaque(false);

        JLabel title = new JLabel(
            "SELECCIONA TU PERSONAJE",
            SwingConstants.CENTER
        );

        title.setFont(pixelTitle);
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(12, 64, 117));

        title.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(54, 178, 244), 4
                ),
                new EmptyBorder(14, 8, 14, 8)
            )
        );

        header.add(title, BorderLayout.NORTH);

        JPanel modes = new JPanel(new GridLayout(1, 2, 30, 0));
        modes.setOpaque(false);

        onePlayer = SpriteManager.imageButton(
            "1 Player.png", "1 JUGADOR", 245, 58
        );

        twoPlayers = SpriteManager.imageButton(
            "2 Player.png", "2 JUGADORES", 245, 58
        );

        onePlayer.addActionListener(e -> {
            playerCount = 1;
            refresh();
        });

        twoPlayers.addActionListener(e -> {
            playerCount = 2;
            refresh();
        });

        modes.add(onePlayer);
        modes.add(twoPlayers);

        header.add(modes, BorderLayout.SOUTH);
        background.add(header, BorderLayout.NORTH);

        cards = new JPanel(new GridLayout(1, 2, 25, 0));
        cards.setOpaque(false);

        background.add(cards, BorderLayout.CENTER);

        JPanel footer = new JPanel(new GridLayout(1, 2, 30, 0));
        footer.setOpaque(false);

        JButton back = SpriteManager.imageButton(
            "Back.png", "VOLVER", 240, 65
        );

        JButton start = SpriteManager.imageButton(
            "Start Game.png", "JUGAR", 240, 65
        );

        back.addActionListener(e -> {
            dispose();
            new BadDOPOCreamGUI();
        });

        start.addActionListener(e -> {
            CampaignPanel.showInFrame(
                flavor1,
                playerCount == 2 ? flavor2 : null
            );
            dispose();
        });

        footer.add(back);
        footer.add(start);

        background.add(footer, BorderLayout.SOUTH);

        refresh();
    }

    private JPanel iceCard() {
        JPanel panel = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);

                Graphics2D g = (Graphics2D) graphics.create();

                g.setColor(new Color(218, 245, 255, 225));
                g.fillRoundRect(
                    4, 4,
                    getWidth() - 8,
                    getHeight() - 8,
                    20, 20
                );

                g.setColor(new Color(36, 151, 218));
                g.setStroke(new BasicStroke(4));

                g.drawRoundRect(
                    4, 4,
                    getWidth() - 9,
                    getHeight() - 9,
                    20, 20
                );

                g.setColor(new Color(255, 255, 255, 160));
                g.fillRoundRect(
                    14, 14,
                    getWidth() - 28,
                    12,
                    10, 10
                );

                g.dispose();
            }
        };

        panel.setOpaque(false);
        panel.setBorder(
            new EmptyBorder(22, 20, 22, 20)
        );

        return panel;
    }

    private JPanel playerCard(int number) {
        JPanel card = iceCard();

        JLabel label = new JLabel(
            "JUGADOR " + number,
            SwingConstants.CENTER
        );

        label.setFont(pixelFont.deriveFont(17f));
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(new Color(13, 83, 145));

        label.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(58, 178, 240), 3
                ),
                new EmptyBorder(12, 5, 12, 5)
            )
        );

        card.add(label, BorderLayout.NORTH);

        Flavor current = number == 1 ? flavor1 : flavor2;

        JPanel preview = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);

                Graphics2D g = (Graphics2D) graphics.create();

                g.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
                );

                int size = Math.min(
                    185,
                    Math.min(getWidth(), getHeight()) - 10
                );

                if (size > 0) {
                    SpriteManager.draw(
                        g,
                        SpriteManager.playerSprite(current),
                        (getWidth() - size) / 2,
                        (getHeight() - size) / 2,
                        size,
                        size
                    );
                }

                g.dispose();
            }
        };

        preview.setOpaque(false);
        card.add(preview, BorderLayout.CENTER);

        JPanel flavors = new JPanel(new GridLayout(3, 1, 4, 4));
        flavors.setOpaque(false);
        flavors.setPreferredSize(new Dimension(300, 190));

        addFlavorButton(
            flavors, "CHOCOLATE",
            Flavor.CHOCOLATE, number
        );

        addFlavorButton(
            flavors, "VAINILLA",
            Flavor.VANILLA, number
        );

        addFlavorButton(
            flavors, "FRESA",
            Flavor.STRAWBERRY, number
        );

        card.add(flavors, BorderLayout.SOUTH);

        return card;
    }

    private void addFlavorButton(
            JPanel container,
            String text,
            Flavor flavor,
            int number) {

        String filename;
        
        if (flavor == Flavor.CHOCOLATE) {
            filename = "btnChocolate.png";
        } 
        else if (flavor == Flavor.VANILLA) {
            filename = "btnVainilla.png";
        } 
        else if (flavor == Flavor.STRAWBERRY) {
            filename = "btnFresa.png";
        } 
        else {
            throw new IllegalArgumentException(
                "Sabor no soportado: " + flavor
            );
        }

        JButton button = new JButton() {

            @Override
            protected void paintComponent(Graphics graphics) {
                Graphics2D g = (Graphics2D) graphics.create();

                g.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR
                );

                BufferedImage image = SpriteManager.load(filename);

                if (image != null) {
                    int margin = 8;

                    int availableWidth = getWidth() - margin * 2;
                    int availableHeight = getHeight() - margin * 2;

                    double scale = Math.min(
                        (double) availableWidth / image.getWidth(),
                        (double) availableHeight / image.getHeight()
                    );

                    int width = (int) (image.getWidth() * scale);
                    int height = (int) (image.getHeight() * scale);

                    int x = (getWidth() - width) / 2;
                    int y = (getHeight() - height) / 2;

                    g.drawImage(
                        image, x, y, width, height, null
                    );

                    Flavor selected =
                        number == 1 ? flavor1 : flavor2;

                    if (selected == flavor) {
                        g.setColor(new Color(255, 210, 40));
                        g.setStroke(new BasicStroke(3));

                        g.drawRoundRect(
                            x - 3, y - 3,
                            width + 6, height + 6,
                            10, 10
                        );
                    }
                } else {
                    g.setColor(darkText);
                    g.setFont(pixelFont);

                    FontMetrics metrics = g.getFontMetrics();

                    g.drawString(
                        text,
                        (getWidth() - metrics.stringWidth(text)) / 2,
                        getHeight() / 2
                    );
                }

                g.dispose();
            }
        };

        button.setPreferredSize(new Dimension(270, 62));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        button.setCursor(
            Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        button.addActionListener(e -> {
            if (number == 1) {
                flavor1 = flavor;
            } else {
                flavor2 = flavor;
            }

            refresh();
        });

        container.add(button);
    }

    private JPanel individualCard() {
        JPanel card = iceCard();
        card.setLayout(new GridBagLayout());

        JLabel info = new JLabel(
            "<html><center>"
            + "MODO INDIVIDUAL"
            + "<br><br>"
            + "4 NIVELES"
            + "<br><br>"
            + "ENEMIGOS AUTOMATICOS"
            + "<br><br>"
            + "MAPAS ALEATORIOS"
            + "</center></html>",
            SwingConstants.CENTER
        );

        info.setFont(pixelFont);
        info.setForeground(darkText);

        card.add(info);
        return card;
    }

    private void refresh() {
        cards.removeAll();

        cards.add(playerCard(1));

        if (playerCount == 2) {
            cards.add(playerCard(2));
        } else {
            cards.add(individualCard());
        }

        cards.revalidate();
        cards.repaint();
    }
}


