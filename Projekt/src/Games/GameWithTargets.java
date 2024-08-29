package Games;
import Main.Profil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWithTargets extends JFrame {
    protected Profil profil;
    protected String difficulty;
    protected JPanel panelGra, panelGora, panelGlowny;
    protected List<Target> targets;
    protected Timer spawnTimer;
    protected ImageIcon icon = new ImageIcon(this.getClass().getResource("target.png"));
    protected int BALL_SIZE = icon.getIconWidth();
    protected int width, height;
    protected JLabel pointsLabel;
    protected JLabel hpLabel;
    protected int points = 0;
    protected Image backgroundImage;

    public GameWithTargets(Profil profil, String difficulty, Color color, Image background) {
        super("AimTrainer");
        this.profil = profil;
        this.difficulty = difficulty;
        this.backgroundImage = background;

        //pobranie proporcji ekranu uzytkownika
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screenSize.width;
        this.height = screenSize.height;
        //pelny ekran dla okna
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        setSize(width,height);
        //wysrodkowanie
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

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

        panelGra.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                checkCircleClicked(e.getX(), e.getY());
            }
        });

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

        hpLabel = new JLabel("♥♥♥");
        hpLabel.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
        hpLabel.setBounds(width - 100, 5, 100, 30);
        panelGora.add(hpLabel,BorderLayout.EAST);

        targets = new ArrayList<>();




        panelGlowny.add(panelGra,BorderLayout.CENTER);
        panelGlowny.add(panelGora,BorderLayout.NORTH);

        setContentPane(panelGlowny);
        setVisible(true);
    }

    protected void spawnCircle(int BALL_DURATION) {
        Random random = new Random();
        int x = random.nextInt(50, (int)(width*0.80) - BALL_SIZE-25);
        int y = random.nextInt(50, (int)(height*0.85) - BALL_SIZE-50);

        Target target = new Target(x, y, BALL_SIZE);
        targets.add(target);
        Rectangle circleBounds = target.getBounds();
        panelGra.repaint(circleBounds.x, circleBounds.y, circleBounds.width, circleBounds.height);

        Timer timer = new Timer(BALL_DURATION, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                targets.remove(target);
                panelGra.repaint(circleBounds.x, circleBounds.y, circleBounds.width, circleBounds.height);
                if(!target.isHit) {
                    losePoints();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    //sprawdza, czy punkt klikniecia znajduje sie w obiekcie
    protected void checkCircleClicked(int x, int y) {
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

    protected void startSpawnTimer(int SPAWN_INTERVAL, int BALL_DURATION) {
        spawnTimer = new Timer(SPAWN_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spawnCircle(BALL_DURATION);
            }
        });
        spawnTimer.start();
    }
}










