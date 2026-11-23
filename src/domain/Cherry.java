package domain;

import javax.swing.*;
import java.util.Random;

public class Cherry extends Fruit {

    private Random random;

    // Posiciones libres del nivel (ejemplo nivel 2)
    private int[][] freePositions = {
        {2, 3}, {2, 10}, {4, 6}, {5, 12}, {7, 2},
        {7, 14}, {9, 5}, {11, 8}, {12, 3}, {13, 13}
    };

    // Cada cuánto teletransporta (ms)
    private final long TELEPORT_INTERVAL = 20000;
    private long lastTeleportTime = 0;

    public Cherry(int row, int col) {
        super(row, col, "recursos/cherry.png");
        random = new Random();
    }

    @Override
    public int getPoints() {
        return 150;
    }

    @Override
    public void update(long currentTime) {

        if (currentTime - lastTeleportTime >= TELEPORT_INTERVAL) {

            int index = random.nextInt(freePositions.length);

            // Nuevas coordenadas
            row = freePositions[index][0];
            col = freePositions[index][1];

            lastTeleportTime = currentTime;
        }
    }
}

