package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Level
 * Representa un nivel del juego, con su mapa, enemigos y frutas.
 * 
 * Sara Gonzalez
 */
public class Level {

    private Map map;
    private List<Enemy> enemies;
    private List<Fruit> fruits;
    private int number;
    private int timeLimit;

    /**
     * Constructor
     */
    public Level(Map map, int number, int timeLimit) {
        this.map = map;
        this.number = number;
        this.timeLimit = timeLimit;

        this.enemies = new ArrayList<>();
        this.fruits = new ArrayList<>();
    }

    public void reset() {
        enemies.clear();
        fruits.clear();
    }

    public boolean completed() {
        return enemies.isEmpty();
    }

    public Map getMap() {
        return map;
    }

    public int getNumber() {
        return number;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Fruit> getFruits() {
        return fruits;
    }

    public void load() {
    }
}
