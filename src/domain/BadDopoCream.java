package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase de Bad DOPO Cream
 * Sara Gonzalez
 */
public class BadDopoCream implements Serializable {

    // Identificador para guardar o cargar partidas
    private static final long serialVersionUID = 1L;

    private int score;
    private int time;
    private boolean running;
    private boolean paused;

    private static final int MAX_LEVEL_TIME_SECONDS = 180;

    private Level actualLevel;
    private GameMode mode;

    private List<Level> levels;
    private List<IceCream> players;

    private List<Ice> iceBlocks;
    private Map<IceCream, AIProfile> aiProfiles;
    private long levelStartTimeMillis;

    public BadDopoCream(Level initialLevel) {
        this.score = 0;
        this.time = 0;
        this.running = false;
        this.paused = false;
        this.levels = new ArrayList<>();
        this.players = new ArrayList<>();
        this.iceBlocks = new ArrayList<>();
        this.aiProfiles = new HashMap<>();
        this.levelStartTimeMillis = 0L;

        if (initialLevel != null) {
            this.levels.add(initialLevel);
            this.actualLevel = initialLevel;
        }
    }

    // Inicia y resetea los contadores.

    public void startGame() throws GameException {
        if (running)
            return;

        try {
            if (levels.isEmpty()) {
                throw new GameException("No hay niveles configurados.");
            }

            this.score = 0;
            this.time = 0;
            this.running = true;
            this.paused = false;
            this.actualLevel = levels.get(0);
            this.levelStartTimeMillis = System.currentTimeMillis();
            actualLevel.load();
        } catch (Exception e) {
            throw new GameException("No se pudo iniciar el juego", e);
        }
    }

    // Pausa el juego y congela el tiempo.

    public void pauseGame() {
        if (!running || paused)
            return;

        paused = true;
        long now = System.currentTimeMillis();
        // Acumula el tiempo transcurrido hasta ahora
        time += (int) ((now - levelStartTimeMillis) / 1000L);
    }

    public void resumeGame() {
        if (!running || !paused)
            return;

        paused = false;
        levelStartTimeMillis = System.currentTimeMillis();
    }

    public void stopGame() {
        if (!running)
            return;

        running = false;
        paused = false;
    }

    public void resetLevel() {
        actualLevel.reset();
        iceBlocks.clear();
        this.time = 0;
        this.levelStartTimeMillis = System.currentTimeMillis();
    }

    public void nextLevel() {
        int currentIndex = levels.indexOf(actualLevel);

        if (currentIndex + 1 < levels.size()) {
            this.actualLevel = levels.get(currentIndex + 1);
            iceBlocks.clear();
            actualLevel.load();
            this.time = 0;
            this.levelStartTimeMillis = System.currentTimeMillis();
        } else {
            stopGame();
        }
    }

    public void touchedByEnemy(IceCream player) {
        this.actualLevel = levels.get(0);
        this.score = 0;
        this.time = 0;
        iceBlocks.clear();
        resetAllPlayersPositions();
        actualLevel.load();
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public GameMode getMode() {
        return mode;
    }

    public void addHumanPlayer(IceCream player) {
        if (player == null)
            return;
        players.add(player);
    }

    public void addMachinePlayer(IceCream player, AIProfile profile) {
        if (player == null || profile == null)
            return;
        players.add(player);
        aiProfiles.put(player, profile);
    }

    public void removePlayer(IceCream player) {
        if (player == null)
            return;
        players.remove(player);
        aiProfiles.remove(player);
    }

    public List<IceCream> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public GameStatus getStatus() {
        int timeLeft = MAX_LEVEL_TIME_SECONDS - getElapsedLevelSeconds();
        if (timeLeft < 0)
            timeLeft = 0;
        return new GameStatus(score, timeLeft,
                actualLevel.getFruits().size(),
                actualLevel.getEnemies().size(),
                running, paused);
    }

    // Guarda la partida
    public void saveGame(File file) throws GameException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(this); 
        } catch (IOException e) {
            throw new GameException("Error al guardar partida", e);
        }
    }

    public static BadDopoCream loadGame(File file) throws GameException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = in.readObject();
            if (!(obj instanceof BadDopoCream)) {
                throw new GameException("Archivo inválido");
            }
            return (BadDopoCream) obj; // Devuelve la partida cargada
        } catch (Exception e) {
            throw new GameException("Error al cargar partida", e);
        }
    }

    public Level getActualLevel() {
        return actualLevel;
    }

    public int getScore() {
        return score;
    }

    private void checkLevelCompleted() {
        if (actualLevel.getFruits().isEmpty()) {
            score += 500 * actualLevel.getNumber();
            nextLevel();
        }
    }

    private int getElapsedLevelSeconds() {
        if (!running)
            return time;
        if (paused)
            return time;
        long now = System.currentTimeMillis();
        return time + (int) ((now - levelStartTimeMillis) / 1000L);
    }

    private void resetAllPlayersPositions() {
        for (IceCream p : players) {
            p.getPosition().setX(0);
            p.getPosition().setY(0);
        }
    }

    private void checkAllCollisions(IceCream player) {
        // Colisión con frutas
        List<Fruit> fruits = actualLevel.getFruits();
        for (int i = 0; i < fruits.size(); i++) {
            Fruit fruit = fruits.get(i);
            if (!fruit.isCollected() && player.getPosition().equals(fruit.getPosition())) {
                fruit.collect();
                score += fruit.getPoints();
                fruits.remove(i);
                break;
            }
        }

        for (Enemy enemy : actualLevel.getEnemies()) {
            if (player.getPosition().equals(enemy.getPosition())) {
                touchedByEnemy(player);
                return;
            }
        }
    }

    public static class GameStatus {
        public final int score;
        public final int secondsLeft;
        public final int fruitsRemaining;
        public final int enemiesCount;
        public final boolean running;
        public final boolean paused;

        public GameStatus(int score, int secondsLeft, int fruits, int enemies, boolean running, boolean paused) {
            this.score = score;
            this.secondsLeft = secondsLeft;
            this.fruitsRemaining = fruits;
            this.enemiesCount = enemies;
            this.running = running;
            this.paused = paused;
        }
    }

    public enum GameMode {
        PLAYER,
        PVS_PLAYER,
        PVS_MACHINE,
        MACHINE_VS_MACHINE
    }

    public enum AIProfile {
        HUNGRY,
        FEARFUL,
        EXPERT
    }

    public static class GameException extends Exception {
        public GameException(String message) {
            super(message);
        }

        public GameException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
