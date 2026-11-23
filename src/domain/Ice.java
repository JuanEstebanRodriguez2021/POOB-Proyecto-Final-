package domain;

import javax.swing.ImageIcon;

/**
 * Hielo de badDopoCream
 * Sara Gonzalez Juan R
 */
public class Ice {
    private boolean breakable;
    private Position position;
    private ImageIcon sprite;

    public Ice(boolean breakable, Position position) {
        this.breakable = breakable;
        this.position = position;
        this.sprite = new ImageIcon("resources/iceBlock.jpg");
    }

    public void breakIce() {
        if (breakable) {
            breakable = false;
            System.out.println("Hielo roto en (" + position.getX() + ", " + position.getY() + ")");
        }
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isBreakable() {
        return breakable;
    }

    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }

    public ImageIcon getSprite() {
        return sprite;
    }
}
