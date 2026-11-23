package domain;

import javax.swing.ImageIcon;

public abstract class Fruit {

    protected int row;
    protected int col;
    protected boolean collected;
    protected ImageIcon sprite;

    public Fruit(int row, int col, String spritePath) {
        this.row = row;
        this.col = col;
        this.collected = false;
        this.sprite = new ImageIcon(spritePath);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public ImageIcon getSprite() {
        return sprite;
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        this.collected = true;
    }
    
    public Position getPosition() {
        return new Position(col, row);
    }
    
    /**
     * define cuántos puntos da.
     */
    public abstract int getPoints();

    /**
     * Método opcional que las frutas pueden sobrescribir 
     * si tienen comportamiento periódico.
     */
    public void update(long currentTime) {
    }

    /**
     * Método opcional que se ejecuta cuando el helado se mueve.
     * Piña, por ejemplo, lo usa.
     */
    public void onIceCreamMove() {
        // Por defecto no hace nada
    }
}


