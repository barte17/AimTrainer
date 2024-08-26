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

public class GameRace extends GameWithCircles {


    Timer timer;
    JLabel timeLabel;
    int time;

    public GameRace(Profil profil, String difficulty, Color color, Image background) {
        super(profil,difficulty,color,background);


        if(difficulty.equals("TRUDNY")) {
            BALL_SIZE = 40;
            minBALL_SIZE = 35;
            maxBALL_SIZE = 55;
        }

        // czas
        timeLabel = new JLabel("60");
        timeLabel.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
        timeLabel.setBounds(width - 100, 5, 100, 30);
        panelGora.add(timeLabel,BorderLayout.EAST);

        startTimer();


        panelGra.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                checkCircleClicked(e.getX(), e.getY());
            }
        });


        spawnCircle();
    }

    //licznik 60 sekund
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

    //czy trafiono cel podczas kliknięcia
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

}









