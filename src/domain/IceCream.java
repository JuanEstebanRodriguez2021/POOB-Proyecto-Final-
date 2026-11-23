package domain;

import java.awt.event.KeyEvent;

/**
 * Especifica atributos y métodos que usa el jugador (ice cream)
 * Sara Gonzalez
 */
public class IceCream {

    private Flavor flavor;
    private int lives;
    private Position position;
    private long finCongelado;
    private boolean congelado;

    public IceCream(Flavor flavor, int lives, Position position) {
        this.flavor = flavor;
        this.lives = lives;
        this.position = position;
    }

    public void moveIceCream(int tecla) {
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

    public Ice createIce() {
        Position icePos = new Position(position.getX(), position.getY());
        return new Ice(true, icePos);
    }

    public Flavor getFlavor() {
        return flavor;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void congelar(long duracionMs) {
        congelado = true;
        finCongelado = System.currentTimeMillis() + duracionMs;
    }

    public boolean estaCongelado() {
        return congelado;
    }
}
