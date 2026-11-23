package presentation;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;

public class LevelThree extends JFrame {

    private JLabel background;
    private Image floorImg;
    private Image iceBlockImg;

    private static final int BLOCK_SIZE = 40;
    private static final int NUM_ICE_BLOCKS = 20;

    private int[][] iceBlockPositions;

    public LevelThree() {
        setTitle("Level 3");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loadImages();
        generateRandomIceBlocks();
        prepareElements();

        setVisible(true);
    }

    private void loadImages() {
        floorImg = new ImageIcon(getClass().getResource("/resources/floor.jpg")).getImage();
        iceBlockImg = new ImageIcon(getClass().getResource("/resources/iceBlock.jpg")).getImage();
    }

    private void generateRandomIceBlocks() {
        iceBlockPositions = new int[NUM_ICE_BLOCKS][2];
        Random random = new Random();

        for (int i = 0; i < NUM_ICE_BLOCKS; i++) {
            iceBlockPositions[i][0] = random.nextInt(20) * BLOCK_SIZE;
            iceBlockPositions[i][1] = random.nextInt(15) * BLOCK_SIZE;
        }
    }

    private void prepareElements() {
        File file = new File("resources/board.jpeg");
        ImageIcon bgIcon = new ImageIcon(file.getAbsolutePath());
        background = new JLabel(bgIcon);
        background.setBounds(0, 0, 800, 600);
        add(background);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawFloor(g);
        drawIceBlocks(g);
    }

    private void drawFloor(Graphics g) {
        for (int y = 0; y < getHeight(); y += BLOCK_SIZE) {
            for (int x = 0; x < getWidth(); x += BLOCK_SIZE) {
                g.drawImage(floorImg, x, y, BLOCK_SIZE, BLOCK_SIZE, this);
            }
        }
    }

    private void drawIceBlocks(Graphics g) {
        for (int i = 0; i < NUM_ICE_BLOCKS; i++) {g.drawImage(iceBlockImg,iceBlockPositions[i][0],iceBlockPositions[i][1], BLOCK_SIZE, BLOCK_SIZE,this);
        }
    }
}



