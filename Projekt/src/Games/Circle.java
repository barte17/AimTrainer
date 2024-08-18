package Games;

import java.awt.*;


public class Circle {
    private int x;
    private int y;

    private int BALL_SIZE;

    boolean isHit = false;

    Color color;


    public Circle(int x, int y, int BALL_SIZE, Color color) {
        this.x = x;
        this.y = y;
        this.BALL_SIZE = BALL_SIZE;
        this.color = color;
    }

    //sprawdzenie, czy kliknięcie znajduje się w celu, zwraca odległość od srodka
    public boolean contains(int x, int y) {
        // Obliczamy odległość między punktem (x, y) a środkiem koła
        double distance = Math.sqrt(Math.pow(x - (this.x + BALL_SIZE / 2), 2) + Math.pow(y - (this.y + BALL_SIZE / 2), 2));
        // Jeśli odległość jest mniejsza niż promień koła, to punkt jest wewnątrz koła
        return distance <= BALL_SIZE / 2;
    }

    //rysowanie  obiektu
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, BALL_SIZE, BALL_SIZE);
    }

    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.BALL_SIZE, this.BALL_SIZE);
    }
}

