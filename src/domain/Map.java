package domain;

/**
 * Clase Map
 * Representa el mapa del nivel como una matriz de enteros (grid).
 * Cada valor del grid indica qué hay en la celda:
 * 0 = suelo, 1 = muro, 2 = bloque de hielo,
 * 3 = fogata, 5 = baldosa caliente.
 */
public class Map {

    // Constantes para los tipos de casilla
    public static final int SUELO             = 0;
    public static final int MURO              = 1;
    public static final int HIELO             = 2;
    public static final int FOGATA            = 3;
    public static final int BALDOSA_CALIENTE  = 5;

    private int[][] grid;   // mapa lógico
    private Ice[][] ices;   // referencia a los hielos en cada celda
    private int height;
    private int width;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new int[height][width];
        this.ices = new Ice[height][width];
    }

    public int getRows() {
        return height;
    }

    public int getCols() {
        return width;
    }

    /**
     * Indica si una posición está bloqueada para movimiento normal.
     * Ahora solo se considera MURO (1) como bloqueo "duro".
     * El hielo (2) se maneja aparte para permitir que algunos enemigos lo rompan.
     */
    public boolean isBlocked(Position position) {
        int x = position.getX();
        int y = position.getY();

        if (x < 0 || x >= width || y < 0 || y >= height) {
            return true;
        }
        return grid[y][x] == MURO;
    }

    /**
     * Coloca un bloque de hielo en el mapa:
     * - Marca la celda del grid como HIELO (2).
     * - Guarda la referencia al objeto Ice en la matriz ices.
     */
    public void placeIce(Ice block) {
        Position p = block.getPosition();
        int x = p.getX();
        int y = p.getY();

        if (x >= 0 && x < width && y >= 0 && y < height) {
            grid[y][x] = HIELO;
            ices[y][x] = block;
        }
    }

    /**
     * Limpia una celda dejándola como SUELO (0).
     * No toca la matriz de hielos.
     */
    public void clearCell(Position p) {
        int x = p.getX();
        int y = p.getY();
        if (x >= 0 && x < width && y >= 0 && y < height) {
            grid[y][x] = SUELO;
        }
    }

    /**
     * Elimina el hielo en una posición:
     * - Pone la celda a SUELO.
     * - Quita la referencia al Ice.
     */
    public void removeIce(Position p) {
        int x = p.getX();
        int y = p.getY();

        if (x >= 0 && x < width && y >= 0 && y < height) {
            grid[y][x] = SUELO;
            ices[y][x] = null;
        }
    }

    /**
     * Indica si en la posición hay un bloque de hielo rompible.
     * Se usa por ejemplo por Calamar o Narval para decidir si rompen hielo.
     */
    public boolean isBreakableIce(Position p) {
        int x = p.getX();
        int y = p.getY();

        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }

        return grid[y][x] == HIELO && ices[y][x] != null && ices[y][x].isBreakable();
    }

    /**
     * Indica si la celda es una fogata.
     * El helado que pise esta casilla debe morir / reiniciar nivel.
     */
    public boolean esFogata(Position p) {
        int x = p.getX();
        int y = p.getY();
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }
        return grid[y][x] == FOGATA;
    }

    /**
     * Indica si la celda es una baldosa caliente.
     * El hielo creado encima se derrite inmediatamente.
     */
    public boolean esBaldosaCaliente(Position p) {
        int x = p.getX();
        int y = p.getY();
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }
        return grid[y][x] == BALDOSA_CALIENTE;
    }

    /**
     * Permite consultar qué tipo de celda hay en una posición,
     * útil para dibujar el mapa en la capa de presentación.
     */
    public int getTipoCelda(Position p) {
        int x = p.getX();
        int y = p.getY();
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return MURO; // fuera del mapa se considera muro
        }
        return grid[y][x];
    }

    /**
     * Permite fijar manualmente el tipo de celda, por ejemplo
     * al construir un nivel (poner fogatas o baldosas calientes).
     */
    public void setTipoCelda(Position p, int tipo) {
        int x = p.getX();
        int y = p.getY();
        if (x >= 0 && x < width && y >= 0 && y < height) {
            grid[y][x] = tipo;
        }
    }
}
