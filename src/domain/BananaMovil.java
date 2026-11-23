package domain;
/** Fruta que se desplaza horizontalmente y rebota en obstáculos. */
public class BananaMovil extends Fruit {
    private int direction=1;
    public BananaMovil(Position position){super(position.getY(),position.getX(),"resources/platano.png");}
    @Override public int getPoints(){return 150;}
    public void move(Map map){Position next=new Position(col+direction,row);
        if(map.isBlocked(next)){direction=-direction;return;}col+=direction;}
}
