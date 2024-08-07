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

public class GameRace extends JFrame {
    Profil profil;
    String difficulty;
    JPanel panelGra, panelGora, panelGlowny;

    private final List<Circle> circles;

    private int BALL_SIZE = 50;

    private int minBALL_SIZE = 45;
    private int maxBALL_SIZE = 65;

    int width, height;

    JLabel pointsLabel;
    JLabel timeLabel;

    int points = 0;

    Timer timer;

    int time;

    Image backgroundImage;

    Color circleColor;

    public GameRace(Profil profil, String difficulty, Color color, Image background) {
        super("Aim Trainer");
        this.profil = profil;
        this.difficulty = difficulty;
        this.backgroundImage = background;
        this.circleColor = color;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screenSize.width;
        this.height = screenSize.height;
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);


        setSize(width,height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        if(difficulty.equals("TRUDNY")) {
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
        panelGora.setBackground(color);
        panelGora.setLayout(new BorderLayout());
        panelGora.setBackground(new Color(240,240,240));

        // Punkty i czas

        pointsLabel = new JLabel("Punkty: "+points);
        pointsLabel.setBounds(10, 5, 100, 30);
        panelGora.add(pointsLabel,BorderLayout.WEST);


        timeLabel = new JLabel("60");
        timeLabel.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
        timeLabel.setBounds(width - 100, 5, 100, 30);
        panelGora.add(timeLabel,BorderLayout.EAST);

        startTimer();

        circles = new ArrayList<>();

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

        spawnCircle();


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

    }

    private void startTimer() {
        time = 60;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time--;
                timeLabel.setText(String.valueOf(time));
                if(time==0) {
                    timer.stop();
                    endScreen();
                }
            }
        });
        timer.start();
    }

    private void checkCircleClicked(int x, int y) {
        for (Circle circle : circles) {
            if (circle.contains(x, y)) {
                circle.isHit = true;
                points++;
                Rectangle circleBounds = circle.getBounds();
                panelGra.repaint(circleBounds.x, circleBounds.y, circleBounds.width, circleBounds.height);
                circles.remove(circle);
                updatePoints();
                spawnCircle();
                return;
            } else {
                points--;
                updatePoints();
            }
        }


    }

    public void updatePoints() {
        pointsLabel.setText("Punkty: "+points);
    }

    public void endScreen() {
        circles.clear();
        panelGra.removeAll();
        panelGora.removeAll();
        setContentPane(new KoniecGryPanel(this.profil, this.points,"Wyścig", this.difficulty));
        revalidate();
    }

    }

