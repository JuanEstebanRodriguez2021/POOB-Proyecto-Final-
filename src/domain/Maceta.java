package domain;

/**
 * sigue al jugador perono puede romper bloques
 * Sara González
 */
public class Maceta extends Enemy {
    
    public Maceta(Position position) {
        super(position);
    }
    
    @Override
    public void move(Map map, Position iceCreamPos) {
        int x = position.getX();
        int y = position.getY();
        int iceX = iceCreamPos.getX();
        int iceY = iceCreamPos.getY();
        
        if (iceX > x) {
            x++;
        } else if (iceX < x) {
            x--;
        }

        else if (iceY > y) {
            y++;
        } else if (iceY < y) {
            y--;
        }
        
        // Valida que no choque con obstáculo
        Position newPos = new Position(x, y);
        if (!map.isBlocked(newPos)) {
            position.setX(x);
            position.setY(y);
        }
    }
}

