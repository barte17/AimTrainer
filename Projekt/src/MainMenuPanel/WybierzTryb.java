package MainMenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WybierzTryb extends JDialog {

    JPanel trybyPanel;
    JButton tryb1, tryb2,tryb3,tryb4;

    String TRYB = "null";
    public WybierzTryb(Frame frame) {
        super(frame,"Wybierz tryb",true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(320,190);
        setBackground(Color.lightGray);

        trybyPanel = new TrybyPanel();

        Color buttonColor = new Color(255,229,204);

        tryb1 = new JButton("Domyślny");
        tryb1.setBackground(buttonColor);
        tryb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TRYB = "DOMYŚLNY";
                dispose();
            }
        });

        tryb2 = new JButton("Precyzja");
        tryb2.setBackground(buttonColor);
        tryb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TRYB = "PRECYZJA";
                dispose();
            }
        });

        tryb3 = new JButton("Refleks");
        tryb3.setBackground(buttonColor);
        tryb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TRYB = "REFLEKS";
                dispose();
            }
        });
        tryb4 = new JButton("Wyścig");
        tryb4.setBackground(buttonColor);
        tryb4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TRYB = "WYŚCIG";
                dispose();
            }
        });




        trybyPanel.add(tryb1);
        trybyPanel.add(tryb2);
        trybyPanel.add(tryb3);
        trybyPanel.add(tryb4);

        setContentPane(trybyPanel);
        setResizable(false);
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    public String getTRYB() {
        return TRYB;
    }
}
