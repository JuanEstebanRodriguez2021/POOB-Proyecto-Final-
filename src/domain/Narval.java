package domain;

import javax.swing.ImageIcon;

/**
 * Enemigo Narval.
 * - Normalmente patrulla (se mueve tranquilo).
 * - Si el helado queda alineado en la misma fila o columna,
 *   el Narval pasa a modo EMBESTIDA y se lanza en línea recta
 *   rompiendo bloques de hielo que encuentre.
 */
public class Narval extends Enemy {

    private ImageIcon sprite;

    // Modo de comportamiento actual del Narval
    private enum Mode { PATROL, CHARGE }
    private Mode mode = Mode.PATROL;      // Arranca patrullando

    // Dirección en la que se mueve
    private enum Direction { UP, DOWN, LEFT, RIGHT }
    private Direction direction = Direction.LEFT;   // Dirección inicial

    /**
     * Constructor: crea un Narval en una posición dada.
     */
    public Narval(Position position) {
        super(position);
        this.sprite = new ImageIcon("resources/narval.png");
    }

    /**
     * Devuelve la imagen (sprite) para dibujar el Narval.
     */
    public ImageIcon getSprite() {
        return sprite;
    }

    /**
     * Método principal de movimiento del enemigo.
     * - Si está en modo PATROL llama a patrol().
     * - Si está en modo CHARGE llama a charge().
     *
     * @param map Mapa del nivel, usado para saber obstáculos y hielo.
     * @param iceCreamPos Posición actual del helado (jugador).
     */
    @Override
    public void move(Map map, Position iceCreamPos) {
        if (mode == Mode.PATROL) {
            patrol(map, iceCreamPos);
        } else {
            charge(map, iceCreamPos);
        }
    }

    /**
     * Modo de patrulla:
     * - Se mueve lentamente (ejemplo: de izquierda a derecha).
     * - Rebota cuando encuentra una pared (isBlocked).
     * - Si el helado está alineado en X o en Y con el Narval,
     *   cambia a modo CHARGE y fija la dirección de la embestida.
     */
    private void patrol(Map map, Position iceCreamPos) {
        int x = position.getX();
        int y = position.getY();

        int newX = x;
        int newY = y;

        // Movimiento horizontal simple
        if (direction == Direction.LEFT) {
            newX--;
        } else if (direction == Direction.RIGHT) {
            newX++;
        }

        Position next = new Position(newX, newY);

        // Si la siguiente celda es pared u obstáculo, el Narval “rebota”
        if (map.isBlocked(next)) {
            // Cambiar dirección izquierda ↔ derecha
            direction = (direction == Direction.LEFT) ? Direction.RIGHT : Direction.LEFT;
            return; // no avanza este turno
        }

        // Celda libre: avanza
        position.setX(newX);
        position.setY(newY);

        // Comprobamos si el helado está alineado con el Narval
        int iceX = iceCreamPos.getX();
        int iceY = iceCreamPos.getY();

        // Misma columna (X igual): embestida vertical
        if (iceX == position.getX()) {
            mode = Mode.CHARGE;                 // cambia a modo embestida
            direction = (iceY < y) ? Direction.UP : Direction.DOWN;
        }
        // Misma fila (Y igual): embestida horizontal
        else if (iceY == position.getY()) {
            mode = Mode.CHARGE;
            direction = (iceX < x) ? Direction.LEFT : Direction.RIGHT;
        }
    }

    /**
     * Modo de embestida:
     * - Se mueve en línea recta en la dirección fijada.
     * - Si hay hielo rompible delante (isBreakableIce), lo rompe y se queda
     *   quieto ese turno (solo rompe un bloque a la vez).
     * - Si hay pared u obstáculo fijo delante (isBlocked), termina la embestida
     *   y vuelve a modo PATROL.
     * - Si la celda está libre, avanza.
     * - Opcional: puedes volver a PATROL cuando deja de estar alineado
     *   con el jugador.
     */
    private void charge(Map map, Position iceCreamPos) {
        int x = position.getX();
        int y = position.getY();

        int newX = x;
        int newY = y;

        // Avanzar una casilla en la dirección actual de embestida
        switch (direction) {
            case UP:    newY--; break;
            case DOWN:  newY++; break;
            case LEFT:  newX--; break;
            case RIGHT: newX++; break;
        }

        Position next = new Position(newX, newY);

        // 1) Si hay hielo rompible delante, el Narval lo rompe
        if (map.isBreakableIce(next)) {
            // Internamente Map quitará ese hielo de la matriz
            map.removeIce(next);
            // Este turno solo rompe el hielo; no avanza
            return;
        }

        // 2) Si hay pared u otro obstáculo fijo delante, se detiene
        if (map.isBlocked(next)) {
            // Termina la embestida, vuelve a patrullar
            mode = Mode.PATROL;
            return;
        }

        // 3) Celda libre: avanza en línea recta
        position.setX(newX);
        position.setY(newY);

        // (Opcional) Si ya no está alineado con el jugador, volver a patrullar.
        // Esto evita que siga embistiendo eternamente.
        int iceX = iceCreamPos.getX();
        int iceY = iceCreamPos.getY();
        if (iceX != position.getX() && iceY != position.getY()) {
            mode = Mode.PATROL;
        }
    }
}



//si no quiero usar enum uso private boolean charging; // true = embistiendo, false = patrullando
// y luego if (charging) { ... } else { ... }. Funciona igual, solo es menos expresivo.


