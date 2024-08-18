package Games;
import Main.Profil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class GameWithCircles extends JFrame {
    protected Profil profil;
    protected String difficulty;
    protected JPanel panelGra, panelGora, panelGlowny;
    protected List<Circle> circles;
    protected int BALL_SIZE = 50;
    protected int minBALL_SIZE = 45;
    protected int maxBALL_SIZE = 65;
    protected int width, height;
    protected JLabel pointsLabel;
    protected int points = 0;
    protected Image backgroundImage;
    protected Color circleColor;

    public GameWithCircles(Profil profil, String difficulty, Color color, Image background) {
        super("Aim Trainer");
        this.profil = profil;
        this.difficulty = difficulty;
        this.backgroundImage = background;
        this.circleColor = color;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screenSize.width;
        this.height = screenSize.height;
        //powiększenie okna do maximum
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        setSize(width,height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);



        circles = new ArrayList<>();
    }

    protected void spawnCircle() {
        Random random = new Random();
        BALL_SIZE = random.nextInt(minBALL_SIZE,maxBALL_SIZE);
        int x = random.nextInt(10, width - BALL_SIZE-25);
        int y = random.nextInt(10, height - BALL_SIZE-60);

        Circle circle = new Circle(x, y, BALL_SIZE, circleColor);
        circles.add(circle);
        Rectangle circleBounds = circle.getBounds();
        panelGra.repaint(circleBounds.x, circleBounds.y, circleBounds.width, circleBounds.height);
    }
}
