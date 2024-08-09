package FirstPanel;

import Main.AimTrainer;
import javax.swing.*;
import java.awt.*;

public class FirstPanel extends JPanel {

    JLabel lCrosshair;

    PanelProfile panelProfile;

    LabelAimTrainer lAimTrainer = new LabelAimTrainer();

    LabelAimTrainer lWybierzProfil = new LabelAimTrainer("Wybierz profil");


    public FirstPanel(AimTrainer aimTrainer) {
        setLayout(null);
        setBackground(new Color(3,211,252));
        setSize(950,600);

        panelProfile = new PanelProfile(aimTrainer);
        panelProfile.setLocation(0,200);

        ImageIcon imageIconCrosshair = new ImageIcon(getClass().getClassLoader().getResource("Icons/celownik.png"));
        lCrosshair = new JLabel(imageIconCrosshair);
        lCrosshair.setSize(130,130);
        lCrosshair.setLocation(625,22);

        lAimTrainer.setLocation(175,50);

        lWybierzProfil.setLocation(225,380);



        add(panelProfile);
        add(lCrosshair);
        add(lAimTrainer);
        add(lWybierzProfil);


    }
}
