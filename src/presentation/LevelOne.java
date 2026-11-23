package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import domain.IceCream;
import domain.Flavor;
import domain.Position;
import domain.Troll;
import domain.Banana;
import domain.saveFlavor;
import domain.Grape;

/**
 * Panel del Level 1 con jugador, frutas y enemigo básico
 */
public class LevelOne extends JPanel {

    private final int TILE = 40;
    private final int ROWS = 15;
    private final int COLS = 20;

    private Image floorImg;
    private Image iceBlockImg;
    private Image iglooImg;
    private Image playerImg;
    private Image trollImg;
    private Image bananaImg;
    private Image grapeImg;

    private IceCream player;
    private Troll troll;
    private List<Banana> bananas;
    private List<Grape> grapes;
    private List<Position> igloo;
    private List<Position> iceBlocks;


    public LevelOne(JFrame parent) {
        setPreferredSize(new Dimension(COLS * TILE, ROWS * TILE));
        setFocusable(true);

        loadImages();
        initLevel();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                movePlayer(e.getKeyCode()); 
            }
        });
    }

    private void loadImages() {
        floorImg = new ImageIcon("resources/floor.jpg").getImage();
        iceBlockImg = new ImageIcon("resources/iceBlock.jpg").getImage();
        iglooImg = new ImageIcon("resources/igloo.jpg").getImage();
        trollImg = new ImageIcon("resources/troll.jpg").getImage();
        bananaImg = new ImageIcon("resources/platano.png").getImage();
        grapeImg = new ImageIcon("resources/uva.png").getImage();

        Flavor f = saveFlavor.selectedFlavor;
        if (f == Flavor.CHOCOLATE) {
            playerImg = new ImageIcon("resources/Chocolate.png").getImage();
        } else if (f == Flavor.VANILLA) {
            playerImg = new ImageIcon("resources/Vainilla.png").getImage();
        } else { 
            playerImg = new ImageIcon("resources/Fresa.png").getImage(); //Fresa
        }
    }

    private void initLevel() {
        Flavor f = saveFlavor.selectedFlavor;
        player = new IceCream(f, 1, new Position(9, 11));

        troll = new Troll(new Position(15, 11));

        bananas = new ArrayList<>();
        int[][] bananaPositions = {
                { 8, 1}, { 9, 1}, { 10, 1 }, { 11, 1 },
                { 7, 5 }, { 12, 5 },
                { 7, 6 }, { 12, 6 },
                { 8, 8 }, { 9, 8 }, { 10, 8 }, { 11, 8 }
        };
        for (int[] p : bananaPositions) {
            bananas.add(new Banana(p[1], p[0]));
        }

        grapes = new ArrayList<>();
        int[][] grapePositions = {
                { 6, 10 }, { 7, 10 }, { 8, 10 }, { 9, 10 },
                { 6, 11 }, { 9, 11 },
                { 6, 12 }, { 9, 12 },
                { 6, 13 }, { 7, 13 }, { 8, 13 }, { 9, 13 }
        };

        for (int[] p : grapePositions) {
            grapes.add(new Grape(p[1], p[0]));
        }

        iceBlocks = new ArrayList<>();
        for (int c = 3; c < COLS - 3; c++) {
            iceBlocks.add(new Position(c, 3));
            iceBlocks.add(new Position(c, 9));
            }

        igloo = new ArrayList<>();
        for (int r = 4; r < 8; r++) {
            for (int c = 8; c < 12; c++) {
                igloo.add(new Position(c, r));
                }
            }
        }

    private void movePlayer(int keyCode) {
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();

        if (keyCode == KeyEvent.VK_UP)
            y = y - 1;

        if (keyCode == KeyEvent.VK_DOWN)
            y = y + 1;

        if (keyCode == KeyEvent.VK_LEFT)
            x = x - 1;

        if (keyCode == KeyEvent.VK_RIGHT)
            x = x + 1;

        // límites del mapa
        if (x < 0 || x >= COLS || y < 0 || y >= ROWS)
            return;

        Position next = new Position(x, y);
        for (Position p : iceBlocks) {
            if (p.getX() == next.getX() && p.getY() == next.getY()) {
                return;
            }
        }
        for (Position q : igloo) {
            if (q.getX() == next.getX() && q.getY() == next.getY()) {
                return;
            }
        }
        player.setPosition(next);

        // recolección de bananos
        for (int i = 0; i < bananas.size(); i++) {
            Banana b = bananas.get(i);
            if (b.getCol() == x && b.getRow() == y) {
                bananas.remove(i);
                break; 
            }
        }
        repaint();

        //recoleccion de uvas
        for (int i = 0; i < grapes.size(); i++) {
            Grape g = grapes.get(i);
            if (g.getCol() == x && g.getRow() == y) {
                grapes.remove(i);
                break; 
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                g.drawImage(floorImg, c * TILE, r * TILE, TILE, TILE, this);
            }
        }

        for (int c = 3; c < COLS - 3; c++) {
            g.drawImage(iceBlockImg, c * TILE, 3 * TILE, TILE, TILE, this);
            g.drawImage(iceBlockImg, c * TILE, 9 * TILE, TILE, TILE, this);
        }

        g.drawImage(iglooImg, 8 * TILE, 4 * TILE, TILE * 4, TILE * 4, this);

        for (Banana b : bananas) {
            g.drawImage(bananaImg, b.getCol() * TILE, b.getRow() * TILE, TILE, TILE, this);
        }
        for (Grape gr : grapes) {
            g.drawImage(grapeImg, gr.getCol() * TILE, gr.getRow() * TILE, TILE, TILE, this);
        }

        g.drawImage(trollImg, troll.getPosition().getX() * TILE, troll.getPosition().getY() * TILE, TILE, TILE, this);

        g.drawImage(playerImg, player.getPosition().getX() * TILE, player.getPosition().getY() * TILE, TILE, TILE,
                this);
    }

    public static void showInFrame() {
        JFrame frame = new JFrame("Level 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LevelOne panel = new LevelOne(frame);
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        panel.requestFocusInWindow();
    }
}
