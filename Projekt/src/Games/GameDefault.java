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

public class GameDefault extends JFrame {
    Profil profil;
    String difficulty;
    JPanel panelGra, panelGora, panelGlowny;

    private final List<Circle> circles;
    private Timer spawnTimer;
    private int BALL_SIZE = 50;

    private int minBALL_SIZE = 45;
    private int maxBALL_SIZE = 65;
    private int SPAWN_INTERVAL = 700;
    private int BALL_DURATION = 2000;

    int width, height;

    JLabel pointsLabel;
    JLabel hpLabel;

    int points = 0;

    Image backgroundImage;

    Color circleColor;


    public GameDefault(Profil profil, String difficulty, Color color, Image background) {
        super("Aim Trainer");
        this.profil = profil;
        this.difficulty = difficulty;
        this.backgroundImage = background;
        circleColor = color;


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screenSize.width;
        this.height = screenSize.height;
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        setSize(width,height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        if(difficulty.equals("TRUDNY")) {
            SPAWN_INTERVAL = 500;
            BALL_DURATION = 1600;
            BALL_SIZE = 40;
            minBALL_SIZE = 35;
            maxBALL_SIZE = 55;
        }

        panelGlowny = new JPanel(new BorderLayout());


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
        panelGora.setBackground(new Color(240,240,240));
        panelGora.setLayout(new BorderLayout());

        // Punkty i czas

        pointsLabel = new JLabel("Punkty: "+points);
        pointsLabel.setBounds(10, 5, 100, 30);
        panelGora.add(pointsLabel,BorderLayout.WEST);



        hpLabel = new JLabel("♥♥♥");
        hpLabel.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
        hpLabel.setBounds(width - 100, 5, 100, 30);
        panelGora.add(hpLabel,BorderLayout.EAST);



        circles = new ArrayList<>();


        spawnTimer = new Timer(SPAWN_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spawnCircle();
            }
        });
        spawnTimer.start();


        panelGra.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                checkCircleClicked(e.getX(), e.getY());
            }
        });


        //
        panelGlowny.add(panelGra,BorderLayout.CENTER);
        panelGlowny.add(panelGora,BorderLayout.NORTH);

        setContentPane(panelGlowny);
        setVisible(true);
    }
    private void spawnCircle() {
        Random random = new Random();
        BALL_SIZE = random.nextInt(minBALL_SIZE,maxBALL_SIZE);
        int x = random.nextInt(10, width - BALL_SIZE-25);
        int y = random.nextInt(10, height - BALL_SIZE-60);

        Circle circle = new Circle(x, y, BALL_SIZE, circleColor);
        circles.add(circle);
        Rectangle circleBounds = circle.getBounds();
        panelGra.repaint(circleBounds.x, circleBounds.y, circleBounds.width, circleBounds.height);

        Timer timer = new Timer(BALL_DURATION, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                circles.remove(circle);
                panelGra.repaint(circleBounds.x, circleBounds.y, circleBounds.width, circleBounds.height);
                if(circle.isHit==false) {
                    losePoints();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void checkCircleClicked(int x, int y) {
        boolean circleHit = false;
        for (Circle circle : circles) {
            if (circle.contains(x, y)) {
                circleHit = true;
                circle.isHit = true;
                points++;
                Rectangle circleBounds = circle.getBounds();
                panelGra.repaint(circleBounds.x, circleBounds.y, circleBounds.width, circleBounds.height);
                circles.remove(circle);
                updatePoints();
                return;
            }
        }
        if(!circleHit) {
            losePoints();
        }


    }

    public void updatePoints() {
        pointsLabel.setText("Punkty: "+points);
    }

    public String removeLastChar(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == '♥') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public void endScreen() {
        setContentPane(new KoniecGryPanel(this.profil, this.points,"Domyślny",this.difficulty));
        revalidate();
    }

    public void losePoints() {
        points--;
        updatePoints();
        hpLabel.setText(removeLastChar(hpLabel.getText()));
        if(hpLabel.getText().isEmpty()) {
            for(Circle circle : circles) {
                circle.isHit = true;
            }
            circles.clear();
            spawnTimer.stop();
            panelGra.removeAll();
            panelGora.removeAll();
            endScreen();
        }
    }

}