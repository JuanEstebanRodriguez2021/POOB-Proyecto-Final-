package presentation;
import domain.FrozenLabyrinth;
import domain.Flavor;
import domain.saveFlavor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/** Vista y controlador de teclado; el modelo conserva las reglas. */
public final class LevelFour extends JPanel {
    private static final int TILE=40, HUD=66;
    private final FrozenLabyrinth game=new FrozenLabyrinth();
    private Timer timer;
    private int directionX=0,directionY=1;
    public LevelFour(){setPreferredSize(new Dimension(FrozenLabyrinth.COLS*TILE,FrozenLabyrinth.ROWS*TILE+HUD));
        setBackground(new Color(13,40,66));setFocusable(true);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"),"up");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"),"down");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"),"left");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"),"right");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('W'),"up");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('S'),"down");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('A'),"left");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('D'),"right");
        for(String dir:new String[]{"up","down","left","right"})getActionMap().put(dir,new AbstractAction(){public void actionPerformed(ActionEvent e){
            int dx=dir.equals("left")?-1:dir.equals("right")?1:0;
            int dy=dir.equals("up")?-1:dir.equals("down")?1:0;
            directionX=dx;directionY=dy;game.move(dx,dy);repaint();}});
        bind("SPACE","shoot",()->game.shoot(directionX,directionY));
        bind("R","restart",game::reset);
        bind("ESCAPE","menu",()->{timer.stop();Window w=SwingUtilities.getWindowAncestor(this);if(w!=null)w.dispose();new LevelSelection();});
        timer=new Timer(480,e->{game.tick();repaint();});timer.start();
    }
    private void bind(String key,String name,Runnable action){getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key),name);
        getActionMap().put(name,new AbstractAction(){public void actionPerformed(ActionEvent e){action.run();repaint();}});}
    @Override protected void paintComponent(Graphics original){super.paintComponent(original);Graphics2D g=(Graphics2D)original.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(17,48,76));g.fillRect(0,0,getWidth(),HUD);
        g.setColor(Color.WHITE);g.setFont(new Font("SansSerif",Font.BOLD,20));g.drawString("❄ LABERINTO CONGELADO · NIVEL 4",17,27);
        g.setFont(new Font("SansSerif",Font.PLAIN,14));g.drawString("Puntos: "+game.score()+"   Vidas: "+game.lives()+"   Frutas: "+game.fruits().size()+"     WASD/Flechas: mover  ·  Espacio: hielo  ·  R: reiniciar  ·  Esc: menú",17,52);
        for(int y=0;y<FrozenLabyrinth.ROWS;y++)for(int x=0;x<FrozenLabyrinth.COLS;x++){
            g.setColor((x+y)%2==0?new Color(191,233,242):new Color(178,225,239));g.fillRect(x*TILE,HUD+y*TILE,TILE,TILE);
            g.setColor(new Color(220,246,250));g.drawRect(x*TILE,HUD+y*TILE,TILE,TILE);
        }
        for(Point p:game.walls()){g.setColor(new Color(43,103,143));g.fillRoundRect(p.x*TILE+2,HUD+p.y*TILE+2,36,36,7,7);
            g.setColor(new Color(99,180,211));g.drawLine(p.x*TILE+5,HUD+p.y*TILE+7,p.x*TILE+32,HUD+p.y*TILE+7);}
        for(Point p:game.ice()){g.setColor(new Color(77,179,218,210));g.fillRoundRect(p.x*TILE+4,HUD+p.y*TILE+4,32,32,8,8);
            g.setColor(Color.WHITE);g.drawLine(p.x*TILE+10,HUD+p.y*TILE+11,p.x*TILE+29,HUD+p.y*TILE+11);}
        for(Point p:game.fruits()){int cx=p.x*TILE+20,cy=HUD+p.y*TILE+20;
            g.setColor(new Color(230,70,97));g.fillOval(cx-12,cy-10,24,24);g.setColor(new Color(49,137,84));g.fillOval(cx+1,cy-16,10,7);}
        for(Point p:game.enemies()){int x=p.x*TILE,y=HUD+p.y*TILE;
            g.setColor(new Color(113,71,170));g.fillRoundRect(x+5,y+5,30,30,13,13);
            g.setColor(Color.WHITE);g.fillOval(x+10,y+13,8,9);g.fillOval(x+23,y+13,8,9);
            g.setColor(Color.BLACK);g.fillOval(x+13,y+16,3,4);g.fillOval(x+26,y+16,3,4);}
        Point p=game.player();int x=p.x*TILE,y=HUD+p.y*TILE;
        Flavor flavor=saveFlavor.selectedFlavor;
        g.setColor(flavor==Flavor.CHOCOLATE?new Color(123,74,48):flavor==Flavor.VANILLA?new Color(250,235,174):new Color(246,119,157));
        g.fillOval(x+5,y+3,30,29);g.setColor(new Color(193,139,77));g.fillPolygon(new int[]{x+11,x+29,x+20},new int[]{y+27,y+27,y+39},3);
        g.setColor(Color.BLACK);g.fillOval(x+13,y+14,4,4);g.fillOval(x+23,y+14,4,4);
        if(game.won()||game.lost()){
            g.setColor(new Color(12,31,53,230));g.fillRoundRect(130,230,540,170,24,24);
            g.setColor(Color.WHITE);g.setFont(new Font("SansSerif",Font.BOLD,30));
            g.drawString(game.won()?"¡NIVEL COMPLETADO!":"FIN DE LA PARTIDA",190,294);
            g.setFont(new Font("SansSerif",Font.PLAIN,19));g.drawString("Puntuación: "+game.score()+"  ·  Pulsa R para jugar otra vez",190,341);
        }g.dispose();}
    public static void showInFrame(){JFrame f=new JFrame("Bad DOPO Cream | Nivel 4");f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        LevelFour panel=new LevelFour();f.setContentPane(panel);f.pack();f.setResizable(false);f.setLocationRelativeTo(null);
        f.addWindowListener(new WindowAdapter(){public void windowClosed(WindowEvent e){panel.timer.stop();}});f.setVisible(true);panel.requestFocusInWindow();}
}
