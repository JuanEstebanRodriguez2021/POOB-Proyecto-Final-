package domain;

import java.awt.event.KeyEvent;

/**
 * Clase Enemy
 * Representa un enemigo con una posición en el mapa.
 * Sara González
 */
public abstract class Enemy {

    // Posición del enemigo 
    protected Position position;

    public Enemy(Position position) {
        this.position = position;
    }

    public abstract void move(Map map, Position iceCreamPos);

    /**
     * Movimiento  con teclas 
     */
    public void moveIce(int tecla) {
        int x = position.getX();
        int y = position.getY();

        if (tecla == KeyEvent.VK_UP) {
            y = y - 1;
        } else if (tecla == KeyEvent.VK_DOWN) {
            y = y + 1;
        } else if (tecla == KeyEvent.VK_RIGHT) {
            x = x + 1;
        } else if (tecla == KeyEvent.VK_LEFT) {
            x = x - 1;
        }

        position.setX(x);
        position.setY(y);
    }

    public void followIceCream(Position icePos) {
        int x = position.getX();
        int y = position.getY();

        int iceX = icePos.getX();
        int iceY = icePos.getY();

        if (iceX > x) {
            x = x + 1;
        } else if (iceX < x) {
            x = x - 1;
        } else if (iceY > y) {
            y = y + 1;
        } else if (iceY < y) {
            y = y - 1;
        }

        position.setX(x);
        position.setY(y);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
