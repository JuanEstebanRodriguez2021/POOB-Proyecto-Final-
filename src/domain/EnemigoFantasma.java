package domain;

import javax.swing.ImageIcon;

/**
 * EnemigoFantasma:
 * - Persigue al helado.
 * - Puede atravesar bloques de hielo (no usa isBreakableIce).
 * - Solo se detiene ante muros u obstáculos duros (isBlocked).
 */
public class EnemigoFantasma extends Enemy {

    private ImageIcon sprite;

    public EnemigoFantasma(Position position) {
        super(position);
        this.sprite = new ImageIcon("resources/fantasma.png");
    }

    public ImageIcon getSprite() { return sprite; }

    @Override
    public void move(Map map, Position iceCreamPos) {
        int x = position.getX();
        int y = position.getY();

        int iceX = iceCreamPos.getX();
        int iceY = iceCreamPos.getY();

        int newX = x;
        int newY = y;

        // Perseguir al jugador con prioridad en X
        if (iceX > x)      newX++;
        else if (iceX < x) newX--;
        else if (iceY > y) newY++;
        else if (iceY < y) newY--;

        Position next = new Position(newX, newY);

        // Solo se detiene con muros (no le importa el hielo)
        if (map.isBlocked(next)) {
            return;
        }

        position.setX(newX);
        position.setY(newY);
    }
}
