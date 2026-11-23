package presentation;
import javax.swing.*;
import java.awt.*;
public class LevelSelection extends JFrame {
    public LevelSelection() {
        super("Bad DOPO Cream | Seleccionar nivel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(660,470); setLocationRelativeTo(null);
        JPanel root = new JPanel(new BorderLayout(16,16));
        root.setBackground(new Color(20,52,82)); root.setBorder(BorderFactory.createEmptyBorder(30,35,30,35));
        JLabel title = new JLabel("ELIGE TU AVENTURA",SwingConstants.CENTER);
        title.setForeground(Color.WHITE); title.setFont(new Font("SansSerif",Font.BOLD,30));
        root.add(title,BorderLayout.NORTH);
        JPanel levels = new JPanel(new GridLayout(2,2,16,16)); levels.setOpaque(false);
        for (int i=1;i<=4;i++) {
            final int level=i;
            JButton b=new JButton("NIVEL " + i + (i==4?"  ❄ NUEVO":""));
            b.setFont(new Font("SansSerif",Font.BOLD,19)); b.setBackground(new Color(126,215,235));
            b.setFocusPainted(false);
            b.addActionListener(e->{dispose();switch(level){case 1:LevelOne.showInFrame();break;case 2:LevelTwo.showInFrame();break;case 3:new LevelThree();break;default:LevelFour.showInFrame();}});
            levels.add(b);
        }
        root.add(levels,BorderLayout.CENTER);
        JButton back=new JButton("VOLVER A PERSONAJES");
        back.addActionListener(e->{dispose();new CharacterSelection();});
        root.add(back,BorderLayout.SOUTH);
        setContentPane(root);setVisible(true);
    }
}
