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

public class GameDefault extends GameWithCircles {



    JLabel hpLabel;
    private Timer spawnTimer;
    private int SPAWN_INTERVAL = 700;
    private int BALL_DURATION = 2000;


    public GameDefault(Profil profil, String difficulty, Color color, Image background) {
        super(profil,difficulty,color,background);


        if(difficulty.equals("TRUDNY")) {
            SPAWN_INTERVAL = 500;
            BALL_DURATION = 1600;
            BALL_SIZE = 40;
            minBALL_SIZE = 35;
            maxBALL_SIZE = 55;
        }

        //label z życiami
        hpLabel = new JLabel("♥♥♥");
        hpLabel.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
        hpLabel.setBounds(width - 100, 5, 100, 30);
        panelGora.add(hpLabel,BorderLayout.EAST);


        panelGra.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                checkCircleClicked(e.getX(), e.getY());
            }
        });

        // generowanie celów co SPAWN_INTERVAL
        spawnTimer = new Timer(SPAWN_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spawnCircle();
            }
        });
        spawnTimer.start();

    }

    //tworzenie celu
    @Override
    protected void spawnCircle() {

        //różne miejsca pojawienia się piłki oraz jej wielkości
        Random random = new Random();
        BALL_SIZE = random.nextInt(minBALL_SIZE,maxBALL_SIZE);
        int x = random.nextInt(10, width - BALL_SIZE-25);
        int y = random.nextInt(10, height - BALL_SIZE-60);

        Circle circle = new Circle(x, y, BALL_SIZE, circleColor);
        circles.add(circle);
        Rectangle circleBounds = circle.getBounds();
        panelGra.repaint(circleBounds.x, circleBounds.y, circleBounds.width, circleBounds.height);

        //czas trwania celu przed zniknięciem
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

    //czy trafiono cel podczas kliknięcia
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

    //usuwanie jednego życia
    public String removeLastChar(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == '♥') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
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