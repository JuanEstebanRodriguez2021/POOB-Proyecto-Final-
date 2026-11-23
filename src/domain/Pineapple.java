package domain;

import java.util.Random;

public class Pineapple extends Fruit {

    private Random random;

    /**
     * Piña: se mueve únicamente cuando el helado se mueve.
     */
    public Pineapple(int row, int col) {
        super(row, col, "recursos/pineapple.png");
        this.random = new Random();
    }

    @Override
    public int getPoints() {
        return 200;
    }

    /**
     * Se llama cuando el helado realiza un movimiento.
     */
    @Override
    public void onIceCreamMove() {
        moveRandomly();
    }

    /**
     * Desplaza la piña a una casilla vecina aleatoria.
     */
    private void moveRandomly() {
        int direction = random.nextInt(4);

        switch (direction) {
            case 0: row--; break;
            case 1: row++; break;
            case 2: col--; break;
            case 3: col++; break;
        }
    }
}
