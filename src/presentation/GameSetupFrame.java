package presentation;

import domain.Flavor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GameSetupFrame extends JFrame {

    private int playerCount = 1;

    private Flavor flavor1 = Flavor.CHOCOLATE;
    private Flavor flavor2 = Flavor.STRAWBERRY;

    private final JPanel selection = new JPanel();
    private final JButton onePlayer = new JButton("1 JUGADOR");
    private final JButton twoPlayers = new JButton("2 JUGADORES");

    private final Color background = new Color(19, 42, 72);
    private final Color accent = new Color(50, 145, 195);

    public GameSetupFrame() {
        setTitle("Bad DOPO Cream - Configurar partida");
        setSize(800, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(15, 15));
        root.setBackground(background);
        root.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel(
            "BAD DOPO CREAM", SwingConstants.CENTER
        );
        title.setFont(new Font(
            "SansSerif", Font.BOLD, 36
        ));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
            "ELIGE TUS JUGADORES",
            SwingConstants.CENTER
        );
        subtitle.setForeground(new Color(170, 225, 255));

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setOpaque(false);
        header.add(title);
        header.add(subtitle);

        root.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(12, 12));
        center.setOpaque(false);

        JPanel modes = new JPanel(new GridLayout(1, 2, 12, 0));
        modes.setOpaque(false);

        style(onePlayer);
        style(twoPlayers);

        modes.add(onePlayer);
        modes.add(twoPlayers);

        onePlayer.addActionListener(e -> {
            playerCount = 1;
            refresh();
        });

        twoPlayers.addActionListener(e -> {
            playerCount = 2;
            refresh();
        });

        center.add(modes, BorderLayout.NORTH);

        selection.setLayout(new GridLayout(1, 2, 12, 0));
        selection.setOpaque(false);
        center.add(selection, BorderLayout.CENTER);

        root.add(center, BorderLayout.CENTER);

        JPanel footer = new JPanel(new GridLayout(1, 2, 12, 0));
        footer.setOpaque(false);

        JButton back = new JButton("VOLVER");
        JButton start = new JButton("COMENZAR PARTIDA");

        style(back);
        style(start);

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
        root.add(footer, BorderLayout.SOUTH);

        setContentPane(root);
        refresh();
        setVisible(true);
    }

    private void style(JButton button) {
        button.setFont(new Font(
            "SansSerif", Font.BOLD, 15
        ));
        button.setBackground(accent);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(
            Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        button.setBorder(new EmptyBorder(12, 8, 12, 8));
    }

    private JPanel playerCard(int number) {
        JPanel card = new JPanel(new BorderLayout(8, 8));
        card.setBackground(new Color(32, 70, 109));
        card.setBorder(new EmptyBorder(12, 12, 12, 12));

        JLabel label = new JLabel(
            "JUGADOR " + number, SwingConstants.CENTER
        );
        label.setForeground(Color.WHITE);
        label.setFont(new Font(
            "SansSerif", Font.BOLD, 20
        ));

        card.add(label, BorderLayout.NORTH);

        Flavor current = number == 1 ? flavor1 : flavor2;

        JPanel preview = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);

                Graphics2D g = (Graphics2D) graphics.create();

                int size = 145;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;

                SpriteManager.draw(
                    g,
                    SpriteManager.playerSprite(current),
                    x, y, size, size
                );

                g.dispose();
            }
        };

        preview.setOpaque(false);
        card.add(preview, BorderLayout.CENTER);

        JPanel flavors = new JPanel(new GridLayout(3, 1, 6, 6));
        flavors.setOpaque(false);

        addFlavor(flavors, "CHOCOLATE", Flavor.CHOCOLATE, number);
        addFlavor(flavors, "VAINILLA", Flavor.VANILLA, number);
        addFlavor(flavors, "FRESA", Flavor.STRAWBERRY, number);

        card.add(flavors, BorderLayout.SOUTH);

        return card;
    }

    private void addFlavor(
            JPanel container, String label,
            Flavor flavor, int player) {

        JButton button = new JButton(label);
        style(button);

        Flavor selected = player == 1 ? flavor1 : flavor2;

        if (selected == flavor) {
            button.setBackground(new Color(39, 172, 126));
        }

        button.addActionListener(e -> {
            if (player == 1) {
                flavor1 = flavor;
            } else {
                flavor2 = flavor;
            }
            refresh();
        });

        container.add(button);
    }

    private void refresh() {
        selection.removeAll();
        selection.add(playerCard(1));

        if (playerCount == 2) {
            selection.add(playerCard(2));
        } else {
            JPanel info = new JPanel(new GridBagLayout());
            info.setBackground(new Color(32, 70, 109));

            JLabel text = new JLabel(
                "<html><center>AVENTURA INDIVIDUAL"
                + "<br><br>4 niveles"
                + "<br>Enemigos automáticos"
                + "<br>Mapas aleatorios"
                + "<br>3 vidas</center></html>"
            );

            text.setFont(new Font(
                "SansSerif", Font.BOLD, 18
            ));
            text.setForeground(Color.WHITE);

            info.add(text);
            selection.add(info);
        }

        onePlayer.setBackground(
            playerCount == 1
                ? new Color(39, 172, 126) : accent
        );

        twoPlayers.setBackground(
            playerCount == 2
                ? new Color(39, 172, 126) : accent
        );

        selection.revalidate();
        selection.repaint();
    }
}
