package presentation;

import domain.Flavor;
import domain.saveFlavor;
import java.awt.*;
import java.io.File;
import javax.swing.*;

/**
 * Selection of the flavors (Vainilla, Chocolate, Strawberry)
 * Juan Esteban Rodriguez- Sara Gonzalez
 */

public class CharacterSelection extends JFrame {

    private JButton btnChocolate;
    private JButton btnVainilla;
    private JButton btnStrawberry;
    private JButton btnPlay;
    private JButton btnBack;
    private JLabel background;

    public CharacterSelection() {
        setTitle("Seleccione su Personaje");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        prepareElements();
        prepareActions();

        setVisible(true);
    }

    private JButton createImageButton(String path, int x, int y, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        JButton button = new JButton(icon);
        button.setBounds(x, y, width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void prepareElements() {

        File file = new File("resources/backgr.png");
        ImageIcon bgIcon = new ImageIcon(file.getAbsolutePath());
        background = new JLabel(bgIcon) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = bgIcon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };

        btnChocolate = createImageButton("resources/Chocolate.jpg", 120, 160, 150, 150);
        btnStrawberry = createImageButton("resources/Fresa.jpg", 290, 150, 150, 150);
        btnVainilla = createImageButton("resources/Vainilla.jpg", 460, 170, 150, 150);
        btnPlay = createImageButton("resources/play.jpg", 200, 380, 100, 40);
        btnBack = createImageButton("resources/back.jpg", 200, 450, 200, 60);

        add(btnChocolate);
        add(btnStrawberry);
        add(btnVainilla);
        add(btnPlay);
        add(btnBack);

        background.setBounds(0, 0, getWidth(), getHeight());
        add(background);

    }
    private void prepareActions() {
        
        btnChocolate.addActionListener(e -> {
            saveFlavor.selectedFlavor = Flavor.CHOCOLATE;
            CampaignPanel.showInFrame();
            dispose();
        });
        
        btnStrawberry.addActionListener(e -> {
            saveFlavor.selectedFlavor = Flavor.STRAWBERRY;
            CampaignPanel.showInFrame();
            dispose();
        });
        
        btnVainilla.addActionListener(e -> {
            saveFlavor.selectedFlavor = Flavor.VANILLA;
            CampaignPanel.showInFrame();
            dispose();
        });
        
        btnPlay.addActionListener(e -> {
            CampaignPanel.showInFrame();
            dispose();
            });
            
        btnBack.addActionListener(e -> {
            new ModeSelection();
            dispose();
            });
    }
    
}
