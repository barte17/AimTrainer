package Games;
import Main.Profil;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        //pobranie rozmiarów ekranu użytkownika
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screenSize.width;
        this.height = screenSize.height;
        //powiększenie okna do maximum
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        setSize(width,height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);


        panelGlowny = new JPanel(new BorderLayout());
        //rysowanie celów oraz tła
        panelGra = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage,0,0,this);
                for (Circle circle : circles) {
                    circle.draw(g);
                }
            }


        };

        panelGra.setBackground(color);
        panelGra.setLayout(null);

        panelGora = new JPanel();
        panelGora.setBackground(color);
        panelGora.setBackground(new Color(240,240,240));
        panelGora.setLayout(new BorderLayout());

        pointsLabel = new JLabel("Punkty: "+points);
        pointsLabel.setBounds(10, 5, 100, 30);
        panelGora.add(pointsLabel,BorderLayout.WEST);

        circles = new ArrayList<>();

        panelGlowny.add(panelGra,BorderLayout.CENTER);
        panelGlowny.add(panelGora,BorderLayout.NORTH);

        setContentPane(panelGlowny);
        setVisible(true);
    }

    protected void spawnCircle() {
        Random random = new Random();
        BALL_SIZE = random.nextInt(minBALL_SIZE,maxBALL_SIZE);
        int x = random.nextInt(50, (int)(width*0.80) - BALL_SIZE-25);
        int y = random.nextInt(50, (int)(height*0.85) - BALL_SIZE-50);

        Circle circle = new Circle(x, y, BALL_SIZE, circleColor);
        circles.add(circle);
        Rectangle circleBounds = circle.getBounds();
        panelGra.repaint(circleBounds.x, circleBounds.y, circleBounds.width, circleBounds.height);
    }

    protected void updatePoints() {
        pointsLabel.setText("Punkty: "+points);
    }

    protected void endScreen() {
        setContentPane(new KoniecGryPanel(this.profil, this.points,"Domyślny",this.difficulty));
        revalidate();
    }






}
