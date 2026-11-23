package domain;

import java.awt.Point;
import java.util.*;

public class CampaignGame {

    public static final int COLS = 19;
    public static final int ROWS = 13;

    public enum EnemyType {
        RANDOM, HUNTER, PATROL
    }

    public static class Player {
        private final Point position;
        private final Flavor flavor;
        private final int number;
        private int lives = 3;
        private int invulnerable = 0;

        public Player(int number, Flavor flavor, Point position) {
            this.number = number;
            this.flavor = flavor;
            this.position = position;
        }

        public Point getPosition() {
            return new Point(position);
        }

        public Flavor getFlavor() {
            return flavor;
        }

        public int getNumber() {
            return number;
        }

        public int getLives() {
            return lives;
        }

        public boolean isAlive() {
            return lives > 0;
        }

        public int getInvulnerable() {
            return invulnerable;
        }
    }

    public static class GameEnemy {
        private final Point position;
        private final EnemyType type;
        private int direction;

        public GameEnemy(Point position, EnemyType type) {
            this.position = position;
            this.type = type;
        }

        public Point getPosition() {
            return new Point(position);
        }

        public EnemyType getType() {
            return type;
        }
    }

    private final Random random = new Random();
    private final Set<Point> walls = new HashSet<>();
    private final Set<Point> ice = new HashSet<>();
    private final java.util.Map<Point, Integer> fruits =
        new HashMap<>();

    private final List<Player> players = new ArrayList<>();
    private final List<GameEnemy> enemies = new ArrayList<>();

    private int level = 1;
    private int score = 0;
    private boolean finished = false;

    private static final int[][] DIRS = {
        {0, -1}, {0, 1}, {-1, 0}, {1, 0}
    };

    public CampaignGame(Flavor flavor1, Flavor flavor2) {
        players.add(new Player(
            1, flavor1, new Point(1, 1)
        ));

        if (flavor2 != null) {
            players.add(new Player(
                2, flavor2, new Point(2, 1)
            ));
        }

        generateLevel();
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isGameOver() {
        for (Player p : players) {
            if (p.isAlive()) {
                return false;
            }
        }
        return true;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public List<GameEnemy> getEnemies() {
        return Collections.unmodifiableList(enemies);
    }

    public Set<Point> getWalls() {
        return Collections.unmodifiableSet(walls);
    }

    public Set<Point> getIce() {
        return Collections.unmodifiableSet(ice);
    }

    public java.util.Map<Point, Integer> getFruits() {
        return Collections.unmodifiableMap(fruits);
    }

    private boolean inside(int x, int y) {
        return x > 0 && y > 0
            && x < COLS - 1 && y < ROWS - 1;
    }

    private boolean free(int x, int y) {
        Point p = new Point(x, y);
        return inside(x, y)
            && !walls.contains(p)
            && !ice.contains(p);
    }

    private Point spawn(Player p) {
        return new Point(p.number, 1);
    }

    private void generateLevel() {
        walls.clear();
        ice.clear();
        fruits.clear();
        enemies.clear();

        for (Player p : players) {
            p.position.setLocation(spawn(p));
            p.invulnerable = 5;
        }

        for (int x = 0; x < COLS; x++) {
            walls.add(new Point(x, 0));
            walls.add(new Point(x, ROWS - 1));
        }

        for (int y = 0; y < ROWS; y++) {
            walls.add(new Point(0, y));
            walls.add(new Point(COLS - 1, y));
        }

        if (level == 1) {
            for (int x = 5; x <= 13; x++) {
                if (x != 9) {
                    ice.add(new Point(x, 5));
                }
            }
        } else {
            generateRandomBoard();
        }

        for (int i = 0; i < 4 + level * 2; i++) {
            Point p = randomPosition();
            fruits.put(p, random.nextInt(4));
        }

        for (int i = 0; i < level; i++) {
            Point p = randomPosition();

            EnemyType type = EnemyType.values()[
                random.nextInt(EnemyType.values().length)
            ];

            enemies.add(new GameEnemy(p, type));
        }
    }

    private void generateRandomBoard() {
        int target = 12 + level * 7;
        int attempts = 0;

        while (ice.size() < target && attempts < 2000) {
            attempts++;

            int x = 2 + random.nextInt(COLS - 4);
            int y = 2 + random.nextInt(ROWS - 4);

            Point p = new Point(x, y);
            ice.add(p);

            if (!allReachable()) {
                ice.remove(p);
            }
        }
    }

    private boolean allReachable() {
        Set<Point> visited = new HashSet<>();
        Queue<Point> queue = new ArrayDeque<>();

        Point start = new Point(1, 1);
        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            for (int[] d : DIRS) {
                Point next = new Point(
                    current.x + d[0],
                    current.y + d[1]
                );

                if (free(next.x, next.y)
                        && visited.add(next)) {
                    queue.add(next);
                }
            }
        }

        for (int y = 1; y < ROWS - 1; y++) {
            for (int x = 1; x < COLS - 1; x++) {
                if (free(x, y)
                        && !visited.contains(new Point(x, y))) {
                    return false;
                }
            }
        }

        return true;
    }

    private Point randomPosition() {
        List<Point> options = new ArrayList<>();

        for (int y = 1; y < ROWS - 1; y++) {
            for (int x = 1; x < COLS - 1; x++) {
                Point p = new Point(x, y);

                if (!free(x, y) || fruits.containsKey(p)) {
                    continue;
                }

                boolean occupied = false;

                for (Player player : players) {
                    if (player.position.distance(p) < 4) {
                        occupied = true;
                    }
                }

                for (GameEnemy enemy : enemies) {
                    if (enemy.position.equals(p)) {
                        occupied = true;
                    }
                }

                if (!occupied) {
                    options.add(p);
                }
            }
        }

        if (options.isEmpty()) {
            throw new IllegalStateException(
                "No hay espacio en el tablero"
            );
        }

        return options.get(random.nextInt(options.size()));
    }

    public void movePlayer(int number, int dx, int dy) {
        if (finished || isGameOver()) {
            return;
        }

        if (Math.abs(dx) + Math.abs(dy) != 1) {
            return;
        }

        Player p = players.get(number - 1);

        if (!p.isAlive()) {
            return;
        }

        int nx = p.position.x + dx;
        int ny = p.position.y + dy;

        if (!free(nx, ny)) {
            return;
        }

        for (Player other : players) {
            if (other != p && other.isAlive()
                    && other.position.equals(new Point(nx, ny))) {
                return;
            }
        }

        p.position.setLocation(nx, ny);

        Integer fruit = fruits.remove(new Point(p.position));

        if (fruit != null) {
            score += 100;

            if (fruits.isEmpty()) {
                nextLevel();
                return;
            }
        }

        checkCollision();
    }

    public void toggleIce(int number, int dx, int dy) {
        if (finished || isGameOver()) {
            return;
        }

        Player p = players.get(number - 1);

        if (!p.isAlive()) {
            return;
        }

        Point target = new Point(
            p.position.x + dx,
            p.position.y + dy
        );

        if (!inside(target.x, target.y)
                || walls.contains(target)
                || fruits.containsKey(target)) {
            return;
        }

        for (Player other : players) {
            if (other.position.equals(target) && other.isAlive()) {
                return;
            }
        }

        for (GameEnemy enemy : enemies) {
            if (enemy.position.equals(target)) {
                return;
            }
        }

        if (!ice.remove(target)) {
            ice.add(target);

            if (!allReachable()) {
                ice.remove(target);
            }
        }
    }

    public void tick() {
        if (finished || isGameOver()) {
            return;
        }

        for (Player p : players) {
            if (p.invulnerable > 0) {
                p.invulnerable--;
            }
        }

        for (GameEnemy enemy : enemies) {
            moveEnemy(enemy);
        }

        checkCollision();
    }

    private void moveEnemy(GameEnemy enemy) {
        List<Point> options = new ArrayList<>();

        for (int[] d : DIRS) {
            Point p = new Point(
                enemy.position.x + d[0],
                enemy.position.y + d[1]
            );

            if (free(p.x, p.y) && !enemyOccupied(p, enemy)) {
                options.add(p);
            }
        }

        if (options.isEmpty()) {
            return;
        }

        Point chosen;

        if (enemy.type == EnemyType.HUNTER) {
            chosen = hunterMove(options);
        } else if (enemy.type == EnemyType.PATROL) {
            int[] d = DIRS[enemy.direction];

            Point forward = new Point(
                enemy.position.x + d[0],
                enemy.position.y + d[1]
            );

            if (options.contains(forward)) {
                chosen = forward;
            } else {
                enemy.direction = random.nextInt(4);
                chosen = options.get(random.nextInt(options.size()));
            }
        } else {
            chosen = options.get(random.nextInt(options.size()));
        }

        enemy.position.setLocation(chosen);
    }

    private boolean enemyOccupied(Point p, GameEnemy current) {
        for (GameEnemy e : enemies) {
            if (e != current && e.position.equals(p)) {
                return true;
            }
        }
        return false;
    }

    private Point hunterMove(List<Point> options) {
        java.util.Map<Point, Integer> distances =
            new HashMap<>();

        Queue<Point> queue = new ArrayDeque<>();

        for (Player p : players) {
            if (p.isAlive()) {
                Point start = new Point(p.position);
                distances.put(start, 0);
                queue.add(start);
            }
        }

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            int distance = distances.get(current);

            for (int[] d : DIRS) {
                Point next = new Point(
                    current.x + d[0],
                    current.y + d[1]
                );

                if (free(next.x, next.y)
                        && !distances.containsKey(next)) {
                    distances.put(next, distance + 1);
                    queue.add(next);
                }
            }
        }

        int best = Integer.MAX_VALUE;
        List<Point> candidates = new ArrayList<>();

        for (Point p : options) {
            int distance = distances.getOrDefault(
                p, Integer.MAX_VALUE
            );

            if (distance < best) {
                best = distance;
                candidates.clear();
                candidates.add(p);
            } else if (distance == best) {
                candidates.add(p);
            }
        }

        return candidates.get(random.nextInt(candidates.size()));
    }

    private void checkCollision() {
        for (Player p : players) {
            if (!p.isAlive() || p.invulnerable > 0) {
                continue;
            }

            for (GameEnemy e : enemies) {
                if (p.position.equals(e.position)) {
                    p.lives--;
                    p.invulnerable = 5;
                    p.position.setLocation(spawn(p));
                    break;
                }
            }
        }
    }

    private void nextLevel() {
        score += 500;

        if (level >= 4) {
            finished = true;
        } else {
            level++;
            generateLevel();
        }
    }

    public void restart() {
        level = 1;
        score = 0;
        finished = false;

        for (Player p : players) {
            p.lives = 3;
        }

        generateLevel();
    }
}


