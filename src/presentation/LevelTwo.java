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
import domain.Calamar;
import domain.Cherry;
import domain.Pineapple;
import domain.saveFlavor;

public class LevelTwo extends JPanel {

    private final int TILE = 40;
    private final int ROWS = 15;
    private final int COLS = 20;

    private Image floorImg;
    private Image iceBlockImg;
    private Image playerImg;
    private Image calamarImg;
    private Image pineappleImg;
    private Image cherryImg;

    private IceCream player;
    private Calamar calamar;

    private List<Cherry> cherries;
    private List<Pineapple> pineapples;
    private List<Position> iceBlocks;

    public LevelTwo(JFrame parent) {
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
        calamarImg = new ImageIcon("resources/calamar.png").getImage();
        cherryImg = new ImageIcon("resources/cherry.png").getImage();
        pineappleImg = new ImageIcon("resources/pineapple.png").getImage();

        Flavor f = saveFlavor.selectedFlavor;
        if (f == Flavor.CHOCOLATE) {
            playerImg = new ImageIcon("resources/Chocolate.png").getImage();
        } else if (f == Flavor.VANILLA) {
            playerImg = new ImageIcon("resources/Vainilla.png").getImage();
        } else  {
            playerImg = new ImageIcon("resources/Fresa.png").getImage(); //Fresa
        }
    }

    private void initLevel() {
        Flavor f = saveFlavor.selectedFlavor;
        player = new IceCream(f, 1, new Position(9, 11));
        calamar = new Calamar(new Position(15, 11));

        cherries = new ArrayList<>();
        int[][] cherryPositions = {
                { 8, 5 }, { 9, 5 }, { 10, 5 }, { 11, 5 },
                { 8, 6 }, { 11, 6 },
                { 8, 7 }, { 11, 7 },
                { 8, 8 }, { 9, 8 }, { 10, 8 }, { 11, 8 }
        };
        for (int[] p : cherryPositions) {
            cherries.add(new Cherry(p[1], p[0]));
        }

        pineapples = new ArrayList<>();
        int[][] pineapplePositions = {
                { 6, 10 }, { 7, 10 }, { 8, 10 }, { 9, 10 },
                { 6, 11 }, { 9, 11 },
                { 6, 12 }, { 9, 12 },
                { 6, 13 }, { 7, 13 }, { 8, 13 }, { 9, 13 }
        };
        for (int[] p : pineapplePositions) {
            pineapples.add(new Pineapple(p[1], p[0]));
        }

        iceBlocks = new ArrayList<>();
        for (int c = 3; c < COLS - 3; c++) {
            iceBlocks.add(new Position(c, 3));
            iceBlocks.add(new Position(c, 9));
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

        player.setPosition(next);

        for (int i = 0; i < cherries.size(); i++) {
            Cherry c = cherries.get(i);
            if (c.getCol() == x && c.getRow() == y) {
                cherries.remove(i);
                break;
            }
        }

        for (int i = 0; i < pineapples.size(); i++) {
            Pineapple p = pineapples.get(i);
            if (p.getCol() == x && p.getRow() == y) {
                pineapples.remove(i);
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

        for (Position p : iceBlocks) {
            g.drawImage(iceBlockImg, p.getX() * TILE, p.getY() * TILE, TILE, TILE, this);
        }

        for (Cherry c : cherries) {
            g.drawImage(cherryImg, c.getCol() * TILE, c.getRow() * TILE, TILE, TILE, this);
        }

        for (Pineapple p : pineapples) {
            g.drawImage(pineappleImg, p.getCol() * TILE, p.getRow() * TILE, TILE, TILE, this);
        }

        g.drawImage(calamarImg,
                calamar.getPosition().getX() * TILE,
                calamar.getPosition().getY() * TILE,
                TILE, TILE, this);

        g.drawImage(playerImg,
                player.getPosition().getX() * TILE,
                player.getPosition().getY() * TILE,
                TILE, TILE, this);
    }

    public static void showInFrame() {
        JFrame frame = new JFrame("Level 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LevelTwo panel = new LevelTwo(frame);
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        panel.requestFocusInWindow();
    }
}
