package test;
import domain.FrozenLabyrinth;
public final class FrozenLabyrinthSmokeTest {
    public static void main(String[] args){
        FrozenLabyrinth g=new FrozenLabyrinth();
        if(g.lives()!=3||g.fruits().isEmpty())throw new AssertionError("Estado inicial incorrecto");
        if(g.move(-1,0))throw new AssertionError("El muro debe bloquear");
        g.shoot(1,0);g.tick();g.reset();
        if(g.score()!=0||g.lives()!=3||g.won()||g.lost())throw new AssertionError("Reinicio incorrecto");
        System.out.println("Pruebas de dominio OK");
    }
}
