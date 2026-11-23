package presentation;

import domain.Flavor;
import domain.CampaignGame.EnemyType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class SpriteManager {

    private static final Map<String, BufferedImage> CACHE =
        new HashMap<>();

    private SpriteManager() {}

    public static BufferedImage load(String name) {
        if (CACHE.containsKey(name)) {
            return CACHE.get(name);
        }

        File file = new File("resources", name);

        try {
            BufferedImage image = ImageIO.read(file);
            CACHE.put(name, image);
            return image;
        } catch (IOException | IllegalArgumentException e) {
            System.err.println(
                "No se pudo cargar: " + file.getAbsolutePath()
            );
            return null;
        }
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
            g.setColor(new Color(180, 90, 180));
            g.fillRoundRect(
                x + 4, y + 4,
                width - 8, height - 8, 8, 8
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
}

