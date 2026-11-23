package domain;

import javax.swing.ImageIcon;

public class Banana extends Fruit {

    public Banana(int row, int col) {
        super(row, col, "recursos/platano.png"); 
    }

    /**
     * Devuelve los puntos que otorga la banana al recolectarse.
     */
    @Override
    public int getPoints() {
        return 100;
    }
}
