package domain;

public class Cactus extends Fruit {

    private boolean spikesOut;
    private long lastSpikeTime;

    /**
     * Cactus: cada 30 segundos extiende sus púas.
     */
    public Cactus(int row, int col) {
        super(row, col, "recursos/cactus.png");
        this.spikesOut = false;
        this.lastSpikeTime = System.currentTimeMillis();
    }

    @Override
    public int getPoints() {
        return 250; 
    }

    @Override
    public void update(long currentTime) {
        long elapsed = currentTime - lastSpikeTime;

        if (elapsed >= 30000) { 
            spikesOut = !spikesOut;  // alterna entre púas dentro y fuera
            lastSpikeTime = currentTime;
        }
    }

    /**
     * Devuelve si el cactus está peligroso.
     */
    public boolean hasSpikesOut() {
        return spikesOut;
    }
}
