package domain;

import javax.swing.ImageIcon;

public class Calamar extends Enemy {
    private ImageIcon sprite;

    public Calamar(Position position) {
        super(position);
        this.sprite = new ImageIcon("resources/calamar.png");
    }

    @Override
    public void move(Map map, Position iceCreampos) {
        int x = position.getX();
        int y = position.getY();
        int iceX = iceCreampos.getX();
        int iceY = iceCreampos.getY();

        int newX = x;
        int newY = y;

        if (iceX > x) {
            newX++;

        } else if (iceX < x) {
            newX--;
        }

        else if (iceY > y) {
            newY++;

        } else if (iceY < y) {
            newY--;
        }

        Position next = new Position(newX, newY);

        if (map.isBreakableIce(next)) {
            map.removeIce(next);
            return;
        }
        if (map.isBlocked(next)) {
            return;
        }

        position.setX(newX);
        position.setY(newY);
    }
}
