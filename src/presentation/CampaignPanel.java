package presentation;

import domain.CampaignGame;
import domain.Flavor;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import javax.swing.*;

public class CampaignPanel extends JPanel {

    private static final int TILE = 40;
    private static final int HUD = 100;

    private final CampaignGame game;
    private final Timer timer;

    private final int[][] directions = {
        {0, 1},
        {0, 1}
    };

    private JFrame frame;
    private boolean messageShown = false;
    private int previousLevel;

    public CampaignPanel(Flavor flavor1, Flavor flavor2) {
        game = new CampaignGame(flavor1, flavor2);
        previousLevel = game.getLevel();

        setPreferredSize(new Dimension(
            CampaignGame.COLS * TILE,
            CampaignGame.ROWS * TILE + HUD
        ));

        setBackground(new Color(15, 40, 70));
        setFocusable(true);
        setDoubleBuffered(true);

        configureControls();

        timer = new Timer(420, e -> {
            game.tick();
            updateGame();
            repaint();
        });

        timer.start();
    }

    private void configureControls() {
        bindMove("W", 1, 0, -1);
        bindMove("S", 1, 0, 1);
        bindMove("A", 1, -1, 0);
        bindMove("D", 1, 1, 0);

        bindMove("UP", 2, 0, -1);
        bindMove("DOWN", 2, 0, 1);
        bindMove("LEFT", 2, -1, 0);
        bindMove("RIGHT", 2, 1, 0);

        bind("SPACE", "ice1", () -> {
            game.toggleIce(
                1,
                directions[0][0],
                directions[0][1]
            );
        });

        bind("ENTER", "ice2", () -> {
            if (game.getPlayers().size() == 2) {
                game.toggleIce(
                    2,
                    directions[1][0],
                    directions[1][1]
                );
            }
        });

        bind("R", "restart", () -> {
            game.restart();
            previousLevel = 1;
            messageShown = false;
            frame.setTitle("Bad DOPO Cream - Nivel 1");
            timer.start();
        });

        bind("ESCAPE", "menu", () -> {
            timer.stop();

            if (frame != null) {
                frame.dispose();
            }

            new GameSetupFrame();
        });
    }

    private void bindMove(
            String key, int player, int dx, int dy) {

        bind(key, "move_" + key, () -> {
            if (player > game.getPlayers().size()) {
                return;
            }

            directions[player - 1][0] = dx;
            directions[player - 1][1] = dy;

            game.movePlayer(player, dx, dy);
            updateGame();
        });
    }

    private void bind(
            String key, String name, Runnable action) {

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(key), name
        );

        getActionMap().put(name, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
                repaint();
            }
        });
    }

    private void updateGame() {
        if (game.getLevel() != previousLevel) {
            previousLevel = game.getLevel();

            if (frame != null) {
                frame.setTitle(
                    "Bad DOPO Cream - Nivel " + previousLevel
                );
            }
        }

        if ((game.isFinished() || game.isGameOver())
                && !messageShown) {

            messageShown = true;
            timer.stop();

            SwingUtilities.invokeLater(() -> {
                String message = game.isFinished()
                    ? "¡GANASTE LOS CUATRO NIVELES!"
                    : "GAME OVER";

                int option = JOptionPane.showConfirmDialog(
                    this,
                    message
                        + "\nPuntuación: " + game.getScore()
                        + "\n¿Jugar nuevamente?",
                    "Bad DOPO Cream",
                    JOptionPane.YES_NO_OPTION
                );

                if (option == JOptionPane.YES_OPTION) {
                    game.restart();
                    previousLevel = 1;
                    messageShown = false;

                    if (frame != null) {
                        frame.setTitle(
                            "Bad DOPO Cream - Nivel 1"
                        );
                    }

                    timer.start();
                } else {
                    if (frame != null) {
                        frame.dispose();
                    }

                    new GameSetupFrame();
                }

                repaint();
            });
        }
    }

    @Override
    protected void paintComponent(Graphics original) {
        super.paintComponent(original);

        Graphics2D g = (Graphics2D) original.create();

        g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        g.setRenderingHint(
            RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
        );

        drawBoard(g);
        drawIce(g);
        drawFruits(g);
        drawEnemies(g);
        drawPlayers(g);
        drawHUD(g);

        g.dispose();
    }

    private void drawBoard(Graphics2D g) {
        for (int y = 0; y < CampaignGame.ROWS; y++) {
            for (int x = 0; x < CampaignGame.COLS; x++) {
                SpriteManager.draw(
                    g,
                    "floor.jpg",
                    x * TILE,
                    HUD + y * TILE,
                    TILE,
                    TILE
                );
            }
        }

        for (Point p : game.getWalls()) {
            SpriteManager.draw(
                g,
                "iceBlock.jpg",
                p.x * TILE,
                HUD + p.y * TILE,
                TILE,
                TILE
            );
        }
    }

    private void drawIce(Graphics2D g) {
        for (Point p : game.getIce()) {
            SpriteManager.draw(
                g,
                "iceBlock.jpg",
                p.x * TILE,
                HUD + p.y * TILE,
                TILE,
                TILE
            );
        }
    }

    private void drawFruits(Graphics2D g) {
        for (Map.Entry<Point, Integer> entry
                : game.getFruits().entrySet()) {

            Point p = entry.getKey();

            SpriteManager.draw(
                g,
                SpriteManager.fruitSprite(entry.getValue()),
                p.x * TILE + 4,
                HUD + p.y * TILE + 4,
                TILE - 8,
                TILE - 8
            );
        }
    }

    private void drawEnemies(Graphics2D g) {
        for (CampaignGame.GameEnemy enemy
                : game.getEnemies()) {

            Point p = enemy.getPosition();

            SpriteManager.draw(
                g,
                SpriteManager.enemySprite(enemy.getType()),
                p.x * TILE + 2,
                HUD + p.y * TILE + 2,
                TILE - 4,
                TILE - 4
            );
        }
    }

    private void drawPlayers(Graphics2D g) {
        for (CampaignGame.Player player
                : game.getPlayers()) {

            if (!player.isAlive()) {
                continue;
            }

            if (player.getInvulnerable() > 0
                    && player.getInvulnerable() % 2 == 0) {
                continue;
            }

            Point p = player.getPosition();

            SpriteManager.draw(
                g,
                SpriteManager.playerSprite(
                    player.getFlavor()
                ),
                p.x * TILE + 1,
                HUD + p.y * TILE + 1,
                TILE - 2,
                TILE - 2
            );

            g.setColor(Color.WHITE);
            g.setFont(new Font(
                "SansSerif", Font.BOLD, 12
            ));

            g.drawString(
                "P" + player.getNumber(),
                p.x * TILE + 12,
                HUD + p.y * TILE + 10
            );
        }
    }

    private void drawHUD(Graphics2D g) {
        g.setColor(new Color(16, 40, 69));
        g.fillRect(0, 0, getWidth(), HUD);

        g.setColor(Color.WHITE);
        g.setFont(new Font(
            "SansSerif", Font.BOLD, 24
        ));

        g.drawString(
            "BAD DOPO CREAM | NIVEL " + game.getLevel(),
            18, 30
        );

        g.setFont(new Font(
            "SansSerif", Font.PLAIN, 15
        ));

        StringBuilder status = new StringBuilder();

        status.append("Puntos: ")
              .append(game.getScore());

        for (CampaignGame.Player player
                : game.getPlayers()) {

            status.append("    P")
                  .append(player.getNumber())
                  .append(" Vidas: ")
                  .append(player.getLives());
        }

        status.append("    Frutas: ")
              .append(game.getFruits().size());

        status.append("    Enemigos: ")
              .append(game.getEnemies().size());

        g.drawString(status.toString(), 18, 55);

        g.setFont(new Font(
            "SansSerif", Font.PLAIN, 13
        ));

        g.drawString(
            "P1: WASD + Espacio    "
                + "P2: Flechas + Enter",
            18, 75
        );

        g.drawString(
            "R: Reiniciar     ESC: Menu",
            18, 92
        );
    }

    public static void showInFrame(
            Flavor flavor1, Flavor flavor2) {

        JFrame frame = new JFrame(
            "Bad DOPO Cream - Nivel 1"
        );

        CampaignPanel panel =
            new CampaignPanel(flavor1, flavor2);

        panel.frame = frame;

        frame.setContentPane(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(
            JFrame.DISPOSE_ON_CLOSE
        );

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                panel.timer.stop();
            }
        });

        frame.setVisible(true);
        panel.requestFocusInWindow();
    }

    public static void showInFrame() {
        showInFrame(Flavor.CHOCOLATE, null);
    }
}
