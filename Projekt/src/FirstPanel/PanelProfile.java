package FirstPanel;

import Main.AimTrainer;
import MainMenuPanel.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import Main.Profil;

public class PanelProfile extends JPanel implements ActionListener {
    final private AimTrainer aimTrainer;
    ProfilButton profil4, profil3, profil2, profil1;

    ChangeProfileName changeProfileName;

    EditProfileButton editProfileButton1, editProfileButton2, editProfileButton3, editProfileButton4;
    ArrayList<Profil>profiles;
    JLabel lProfil1, lProfil2, lProfil3, lProfil4;

    public PanelProfile(AimTrainer aimTrainer) {
        this.aimTrainer = aimTrainer;
        setLayout(null);
        setSize(950,180);
        setBackground(Color.CYAN);

        profiles = aimTrainer.getProfiles();

        for (Profil profil: profiles) {
            System.out.println(profil);
        }

        // Profile buttons

        ImageIcon imageIconPlus = new ImageIcon(getClass().getClassLoader().getResource("Icons/userImage.png"));
        profil4 = new ProfilButton(imageIconPlus, Color.RED);
        profil4.setLocation(700,10);
        profil4.addActionListener(this);

        profil3 = new ProfilButton(imageIconPlus, Color.GREEN);
        profil3.setLocation(500,10);
        profil3.addActionListener(this);

        profil2 = new ProfilButton(imageIconPlus, Color.YELLOW);
        profil2.setLocation(300,10);
        profil2.addActionListener(this);

        profil1 = new ProfilButton(imageIconPlus, Color.BLUE);
        profil1.setLocation(100,10);
        profil1.addActionListener(this);


        // Profile label names

        setLabels();


        // przyciski edycji profilu
        ImageIcon imageIconEditProfile = new ImageIcon(getClass().getClassLoader().getResource("Icons/editprofile.png"));

        editProfileButton1 = new EditProfileButton(imageIconEditProfile);
        editProfileButton1.setLocation(218,143);
        editProfileButton1.addActionListener(this);

        editProfileButton2 = new EditProfileButton(imageIconEditProfile);
        editProfileButton2.setLocation(418,143);
        editProfileButton2.addActionListener(this);

        editProfileButton3 = new EditProfileButton(imageIconEditProfile);
        editProfileButton3.setLocation(618,143);
        editProfileButton3.addActionListener(this);

        editProfileButton4 = new EditProfileButton(imageIconEditProfile);
        editProfileButton4.setLocation(818,143);
        editProfileButton4.addActionListener(this);


        add(profil4);
        add(profil1);
        add(profil2);
        add(profil3);

        add(editProfileButton1);
        add(editProfileButton2);
        add(editProfileButton3);
        add(editProfileButton4);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o==profil1) {
            aimTrainer.showView(new MainMenu(profiles.get(0)));
        }
        if(o==profil2) {
            aimTrainer.showView(new MainMenu(profiles.get(1)));
        }
        if(o==profil3) {
            aimTrainer.showView(new MainMenu(profiles.get(2)));
        }
        if(o==profil4) {
            aimTrainer.showView(new MainMenu(profiles.get(3)));
        }
        if(o==editProfileButton1) {
            changeProfileName = new ChangeProfileName(aimTrainer, profiles.get(0));
            profiles.set(0,changeProfileName.getNewProfileName());
            saveProfiles();
            refreshLabels();
        }
        if(o==editProfileButton2) {
            changeProfileName = new ChangeProfileName(aimTrainer, profiles.get(1));
            profiles.set(1,changeProfileName.getNewProfileName());
            saveProfiles();
            refreshLabels();
        }
        if(o==editProfileButton3) {
            changeProfileName = new ChangeProfileName(aimTrainer, profiles.get(2));
            profiles.set(2,changeProfileName.getNewProfileName());
            saveProfiles();
            refreshLabels();
        }
        if(o==editProfileButton4) {
            changeProfileName = new ChangeProfileName(aimTrainer, profiles.get(3));
            profiles.set(3,changeProfileName.getNewProfileName());
            saveProfiles();
            refreshLabels();
        }
    }

    public void setLabels() {
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 14);
        lProfil1 = new JLabel(profiles.get(0).getName(), SwingConstants.CENTER);
        lProfil1.setSize(105,25);
        lProfil1.setLocation(109,145);
        lProfil1.setFont(f);

        lProfil2 = new JLabel(profiles.get(1).getName(), SwingConstants.CENTER);
        lProfil2.setSize(105,25);
        lProfil2.setLocation(309,145);
        lProfil2.setFont(f);

        lProfil3 = new JLabel(profiles.get(2).getName(), SwingConstants.CENTER);
        lProfil3.setSize(105,25);
        lProfil3.setLocation(509,145);
        lProfil3.setFont(f);

        lProfil4 = new JLabel(profiles.get(3).getName(), SwingConstants.CENTER);
        lProfil4.setSize(105,25);
        lProfil4.setLocation(709,145);
        lProfil4.setFont(f);

        add(lProfil1);
        add(lProfil2);
        add(lProfil3);
        add(lProfil4);
    }

    public void refreshLabels() {
        lProfil1.setText(profiles.get(0).getName());
        lProfil2.setText(profiles.get(1).getName());
        lProfil3.setText(profiles.get(2).getName());
        lProfil4.setText(profiles.get(3).getName());
    }

    public void saveProfiles() {
        Path path = Paths.get("profiles.txt");
        File file = new File("profiles.txt");

        if (!(Files.exists(path))) {
            try {
                System.out.println("Brak pliku, tworzenie nowego");
                file.createNewFile();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            FileWriter myWriter = new FileWriter("profiles.txt");
            myWriter.write("1, " + profiles.get(0).getName() + "\n");
            myWriter.write("2, " + profiles.get(1).getName() + "\n");
            myWriter.write("3, " + profiles.get(2).getName() + "\n");
            myWriter.write("4, " + profiles.get(3).getName() + "\n");
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
