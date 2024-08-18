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

public class GamePrecision extends JFrame {
    Profil profil;
    String difficulty;
    JPanel panelGra, panelGora, panelGlowny;

    private final List<Target> targets;
    private Timer spawnTimer;
    ImageIcon icon = new ImageIcon(this.getClass().getResource("target.png"));
    private int BALL_SIZE = icon.getIconWidth();;
    private int SPAWN_INTERVAL = 750;
    private int BALL_DURATION = 2000;

    int width, height;

    JLabel pointsLabel;
    JLabel hpLabel;

    int points = 0;

    Image backgroundImage;


    public GamePrecision(Profil profil, String difficulty, Color color, Image background) {
        super("Aim Trainer");
        this.profil = profil;
        this.difficulty = difficulty;
        this.backgroundImage = background;


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screenSize.width;
        this.height = screenSize.height;
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        setSize(width,height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        if(difficulty.equals("TRUDNY")) {
            SPAWN_INTERVAL = 600;
            BALL_DURATION = 1500;
            BALL_SIZE = icon.getIconWidth();
        }

        panelGlowny = new JPanel(new BorderLayout());

        panelGra = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage,0,0,this);
                for(Target target : targets) {
                    target.draw(g);
                }
            }

        };

        panelGra.setLayout(null);

        panelGora = new JPanel();
        panelGora.setLayout(new BorderLayout());
        panelGora.setBackground(new Color(240,240,240));

        // Punkty i czas

        pointsLabel = new JLabel("Punkty: "+points);
        pointsLabel.setBounds(10, 5, 100, 30);
        panelGora.add(pointsLabel,BorderLayout.WEST);



        hpLabel = new JLabel("♥♥♥");
        hpLabel.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
        hpLabel.setBounds(width - 100, 5, 100, 30);
        panelGora.add(hpLabel,BorderLayout.EAST);



        targets = new ArrayList<>();


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
        int x = random.nextInt(10, width - BALL_SIZE-25);
        int y = random.nextInt(10, height - BALL_SIZE-60);

        Target target = new Target(x, y, BALL_SIZE);
        targets.add(target);
        Rectangle circleBounds = target.getBounds();
        panelGra.repaint(circleBounds.x, circleBounds.y, circleBounds.width, circleBounds.height);

        Timer timer = new Timer(BALL_DURATION, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                targets.remove(target);
                panelGra.repaint(circleBounds.x, circleBounds.y, circleBounds.width, circleBounds.height);
                if(target.isHit==false) {
                    losePoints();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void checkCircleClicked(int x, int y) {
        boolean circleHit = false;
        for (Target target : targets) {
            if (target.contains(x, y)) {
                circleHit = true;
                target.isHit = true;
                points++;
                Rectangle circleBounds = target.getBounds();
                double distance = Math.sqrt(Math.pow(x - (circleBounds.x + BALL_SIZE / 2), 2) + Math.pow(y - (circleBounds.y + BALL_SIZE / 2), 2));
                points += 24-distance;
                panelGra.repaint(circleBounds.x, circleBounds.y, circleBounds.width, circleBounds.height);
                targets.remove(target);
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
        setContentPane(new KoniecGryPanel(this.profil, this.points,"Precyzja",this.difficulty));
        revalidate();
    }

    public void losePoints() {
        points -= 48;
        updatePoints();
        hpLabel.setText(removeLastChar(hpLabel.getText()));
        if(hpLabel.getText().isEmpty()) {
            for(Target target : targets) {
                target.isHit = true;
            }
            targets.clear();
            spawnTimer.stop();
            panelGra.removeAll();
            panelGora.removeAll();
            endScreen();
        }
    }

}