package Games;

import javax.swing.*;
import java.awt.*;

public class Target {

    private int x;
    private int y;

    private int BALL_SIZE;

    boolean isHit = false;

    ImageIcon icon = new ImageIcon(this.getClass().getResource("target.png"));

    Image image = icon.getImage();


    public Target(int x, int y, int BALL_SIZE) {
        this.x = x;
        this.y = y;
        this.BALL_SIZE = BALL_SIZE;
    }

    public boolean contains(int x, int y) {
        // Obliczamy odległość między punktem (x, y) a środkiem koła
        double distance = Math.sqrt(Math.pow(x - (this.x + BALL_SIZE / 2), 2) + Math.pow(y - (this.y + BALL_SIZE / 2), 2));
        // Jeśli odległość jest mniejsza niż promień koła, to punkt jest wewnątrz koła
        return distance <= BALL_SIZE / 2;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, 48, 48, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.BALL_SIZE, this.BALL_SIZE);
    }
}


