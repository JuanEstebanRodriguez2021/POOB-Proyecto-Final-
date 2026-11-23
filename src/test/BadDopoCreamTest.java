package test;

import domain.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit Test of BadDopoCream
 * Juan Esteban Rodriguez - Sara Gonzalez
 */
public class BadDopoCreamTest {

    private BadDopoCream game;

    @Before
    public void setUp() {
        game = new BadDopoCream(null);
    }

    @Test
    public void shouldStartScoreZero() {
        assertEquals(0, game.getScore());
    }

    @Test
    public void shouldAddHumanPlayer() {
        IceCream player = new IceCream(Flavor.VANILLA, 3, new Position(0, 0));
        game.addHumanPlayer(player);
        assertEquals(1, game.getPlayers().size());
    }

    @Test
    public void shouldNotAddNullHumanPlayer() {
        game.addHumanPlayer(null);
        assertEquals(0, game.getPlayers().size());
    }

    @Test
    public void shouldRemoveHumanPlayer() {
        IceCream player = new IceCream(Flavor.CHOCOLATE, 3, new Position(1, 1));
        game.addHumanPlayer(player);
        game.removePlayer(player);
        assertEquals(0, game.getPlayers().size());
    }
}



