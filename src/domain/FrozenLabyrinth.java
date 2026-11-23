package domain;
import java.awt.Point;
import java.util.*;

/** Modelo independiente de Swing: reglas, colisiones, progreso y enemigos. */
public final class FrozenLabyrinth {
    public static final int COLS=20, ROWS=15;
    private final Set<Point> walls=new HashSet<>(), ice=new HashSet<>(), fruits=new HashSet<>();
    private final List<Point> enemies=new ArrayList<>();
    private final Random random=new Random(42);
    private Point player=new Point(1,1);
    private int score, lives=3, ticks;
    private boolean won, lost;
    public FrozenLabyrinth(){reset();}
    public void reset(){walls.clear();ice.clear();fruits.clear();enemies.clear();score=0;lives=3;ticks=0;won=false;lost=false;player=new Point(1,1);
        for(int x=0;x<COLS;x++){walls.add(new Point(x,0));walls.add(new Point(x,ROWS-1));}
        for(int y=0;y<ROWS;y++){walls.add(new Point(0,y));walls.add(new Point(COLS-1,y));}
        for(int y=2;y<=12;y++) if(y!=4&&y!=10){walls.add(new Point(6,y));walls.add(new Point(13,y));}
        for(int x=3;x<=16;x++) if(x!=9&&x!=10){walls.add(new Point(x,7));}
        for(int x=2;x<18;x++)for(int y=2;y<13;y++)if((x*7+y*11)%13==0&&!walls.contains(new Point(x,y)))ice.add(new Point(x,y));
        for(int x=2;x<18;x+=3)for(int y=2;y<13;y+=3){Point p=new Point(x,y);if(!walls.contains(p)){ice.remove(p);fruits.add(p);}}
        for(int x=1;x<=3;x++)for(int y=1;y<=3;y++)ice.remove(new Point(x,y));
        enemies.add(new Point(17,12));enemies.add(new Point(16,2));
        fruits.remove(player);fruits.removeAll(enemies);
    }
    public boolean move(int dx,int dy){if(won||lost)return false;Point next=new Point(player.x+dx,player.y+dy);
        if(walls.contains(next)||ice.contains(next)||outside(next))return false;
        player=next;if(fruits.remove(next))score+=100;
        collide();if(fruits.isEmpty())won=true;return true;
    }
    public void shoot(int dx,int dy){if(won||lost)return;Point p=new Point(player);for(int i=0;i<4;i++){
        p.translate(dx,dy);if(outside(p)||walls.contains(p))break;
        if(ice.remove(p))continue;
        if(fruits.contains(p)||enemies.contains(p))break;
        ice.add(new Point(p));break;
    }}
    public void tick(){if(won||lost)return;ticks++;
        if(ticks%2!=0)return;
        for(int i=0;i<enemies.size();i++){
            Point e=enemies.get(i);int dx=Integer.compare(player.x,e.x),dy=Integer.compare(player.y,e.y);
            int[][] steps=random.nextBoolean()?new int[][]{{dx,0},{0,dy}}:new int[][]{{0,dy},{dx,0}};
            for(int[] s:steps){Point n=new Point(e.x+s[0],e.y+s[1]);if(!n.equals(e)&&!outside(n)&&!walls.contains(n)&&!ice.contains(n)&&!enemies.contains(n)){enemies.set(i,n);break;}}
        }collide();
    }
    private void collide(){if(enemies.contains(player)){lives--;player=new Point(1,1);if(lives<=0)lost=true;}}
    private boolean outside(Point p){return p.x<0||p.y<0||p.x>=COLS||p.y>=ROWS;}
    public Point player(){return new Point(player);}public Set<Point> walls(){return Collections.unmodifiableSet(walls);}
    public Set<Point> ice(){return Collections.unmodifiableSet(ice);}public Set<Point> fruits(){return Collections.unmodifiableSet(fruits);}
    public List<Point> enemies(){return Collections.unmodifiableList(enemies);}
    public int score(){return score;}public int lives(){return lives;}public boolean won(){return won;}public boolean lost(){return lost;}
}
