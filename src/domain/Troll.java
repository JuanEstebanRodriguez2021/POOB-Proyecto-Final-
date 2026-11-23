package domain;

import javax.swing.ImageIcon;
import java.util.Random;

public class Troll extends Enemy {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private ImageIcon sprite;
    private Direction direction;
    private Random random;

    public Troll(Position position) {
        super(position);
        this.sprite = new ImageIcon("resources/troll.jpg");
        this.random = new Random();
        this.direction = Direction.values()[random.nextInt(Direction.values().length)];
    }

    public ImageIcon getSprite() {
        return sprite;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void move(Map map, Position iceCreamPos) {
        int x = position.getX();
        int y = position.getY();

        int newX = x;
        int newY = y;

        switch (direction) {
            case UP:
                newY--;
                break;
            case DOWN:
                newY++;
                break;
            case LEFT:
                newX--;
                break;
            case RIGHT:
                newX++;
                break;
        }

        Position next = new Position(newX, newY);

        if (newX < 0 || newY < 0 || newX >= map.getCols() || newY >= map.getRows() || map.isBlocked(next)) {
            Direction newDir;
            do {
                newDir = Direction.values()[random.nextInt(Direction.values().length)];
            } while (newDir == direction);
            direction = newDir;
        } else {
            position.setX(newX);
            position.setY(newY);
        }
    }
}
