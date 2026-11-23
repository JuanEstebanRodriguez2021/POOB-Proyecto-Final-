
package presentation;

import domain.CampaignGame.EnemyType;
import domain.Flavor;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

public final class SpriteManager {

    private static final Map<String, BufferedImage> CACHE =
        new HashMap<>();

    private SpriteManager() {
    }

    public static BufferedImage load(String name) {
        if (CACHE.containsKey(name)) {
            return CACHE.get(name);
        }

        File file = new File("resources", name);

        if (!file.isFile()) {
            System.err.println(
                "Imagen no encontrada: "
                + file.getAbsolutePath()
            );
            return null;
        }

        try {
            BufferedImage image = ImageIO.read(file);

            if (image != null) {
                CACHE.put(name, image);
            }

            return image;
        } catch (IOException e) {
            System.err.println(
                "Error cargando " + name
                + ": " + e.getMessage()
            );
            return null;
        }
    }

    public static BufferedImage loadBackground(String name) {
        String[] extensions = {
            ".png", ".jpg", ".jpeg"
        };

        for (String extension : extensions) {
            BufferedImage image = load(name + extension);

            if (image != null) {
                return image;
            }
        }

        return null;
    }

    public static void draw(
            Graphics2D g, String name,
            int x, int y, int width, int height) {

        BufferedImage image = load(name);

        if (image != null) {
            g.drawImage(
                image, x, y, width, height, null
            );
        } else {
            g.setColor(new Color(80, 170, 220));
            g.fillRoundRect(
                x + 2, y + 2,
                Math.max(1, width - 4),
                Math.max(1, height - 4),
                8, 8
            );
        }
    }

    public static String playerSprite(Flavor flavor) {
        if (flavor == Flavor.CHOCOLATE) {
            return "Chocolate.png";
        }

        if (flavor == Flavor.VANILLA) {
            return "Vainilla.png";
        }

        return "Fresa.png";
    }

    public static String enemySprite(EnemyType type) {
        switch (type) {
            case HUNTER:
                return "troll.jpg";

            case PATROL:
                return "calamar.png";

            default:
                return "cactus.png";
        }
    }

    public static String fruitSprite(int type) {
        String[] names = {
            "platano.png",
            "cherry.png",
            "uva.png",
            "pineapple.png"
        };

        return names[Math.floorMod(type, names.length)];
    }

    public static JButton imageButton(
            String filename,
            String fallback,
            int width,
            int height) {

        JButton button = new JButton();

        button.setPreferredSize(
            new Dimension(width, height)
        );

        BufferedImage image = load(
            "Buttons/" + filename
        );

        if (image != null) {
            button.setIcon(
                new ImageIcon(
                    image.getScaledInstance(
                        width,
                        height,
                        Image.SCALE_SMOOTH
                    )
                )
            );

            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
        } else {
            button.setText(fallback);
            button.setBackground(
                new Color(35, 120, 190)
            );
            button.setForeground(Color.WHITE);
        }

        button.setFocusPainted(false);

        button.setCursor(
            Cursor.getPredefinedCursor(
                Cursor.HAND_CURSOR
            )
        );

        return button;
    }
}




