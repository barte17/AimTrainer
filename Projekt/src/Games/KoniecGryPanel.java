package Games;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import Main.*;
import MainMenuPanel.MainMenu;


public class KoniecGryPanel extends JPanel {

    int points;

    Image backgroundImage;

    public KoniecGryPanel(Profil profil, int points, String tryb, String difficulty) {
        this.points = points;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);


        JLabel koniecGryLabel = new JLabel("Koniec gry", SwingConstants.CENTER);
        koniecGryLabel.setFont(new Font("Bahnschrift", Font.BOLD, 120));
        koniecGryLabel.setForeground(Color.WHITE);
        add(koniecGryLabel, gbc);

        // Dodanie etykiet profilu i punktów w jednej linii
        JLabel infoLabel = new JLabel("Profil: " + profil.getName() + "    Zdobyte punkty: " + this.points);
        infoLabel.setFont(new Font("Bahnschrift",Font.PLAIN,30));
        infoLabel.setForeground(Color.WHITE);
        add(infoLabel, gbc);

        JLabel trybLabel = new JLabel("Tryb gry: "+tryb);
        trybLabel.setFont(new Font("Bahnschrift",Font.PLAIN,30));
        trybLabel.setForeground(Color.WHITE);
        add(trybLabel, gbc);

        gbc.insets = new Insets(20,10,10,10);

        JButton menuButton = new JButton("Powrót do menu");
        menuButton.setFont(new Font("Arial",Font.PLAIN,25));
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(KoniecGryPanel.this);
                frame.dispose();
            }
        });
        add(menuButton, gbc);

        JButton wylaczButton = new JButton("Wyłącz grę");
        wylaczButton.setFont(new Font("Arial",Font.PLAIN,25));

        wylaczButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(wylaczButton, gbc);


        //Zapisywanie punktów w pliku tekstowym

        Path path = Paths.get("wyniki.txt");
        File file = new File("wyniki.txt");

        if (!(Files.exists(path))) {
            try {
                System.out.println("Brak pliku, tworzenie nowego");
                file.createNewFile();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            BufferedWriter myWriter = new BufferedWriter(new FileWriter("wyniki.txt", true));
            myWriter.newLine();
            myWriter.write(profil.getId()+", "+profil.getName()+", "+tryb+", "+difficulty+", "+points);

            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void paintComponent(Graphics g) {
        Image newImage;
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(KoniecGryPanel.this);
        super.paintComponent(g);
        try {
            backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("Icons/endgame.jpg"));
            newImage = backgroundImage.getScaledInstance(frame.getWidth(),frame.getHeight(),Image.SCALE_DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(newImage,0,0,this);
    }
    }


