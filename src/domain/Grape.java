package domain;

public class Grape extends Fruit {

    /**
     * Uva estática. No se mueve ni tiene lógica temporal.
     */
    public Grape(int row, int col) {
        super(row, col, "recursos/uva.png");
    }

    @Override
    public int getPoints() {
        return 50;
    }
}
