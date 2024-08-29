package MainMenuPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

public class WynikiPanel extends JDialog {

    Image backgroundImage = null;

    JPanel panel, panelSrodek, panelDol;

    JPanel panelDomyslny, panelPrecyzja,panelRefleks, panelWyscig;

    JLabel najlepszeWyniki;


    ArrayList<Wynik> wyniki;

    JPanel blok1, blok2, blok3, blok4;


    public WynikiPanel(JFrame frameAncesor) {
        super(frameAncesor,"Wyniki",true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameHeight =(int) (screenSize.height*0.70);
        int frameWidth = (int) (screenSize.width*0.65);
        setSize(frameWidth,frameHeight);
        setLocationRelativeTo(null);

        panel = new JPanel(new BorderLayout()) {
            public void paintComponent(Graphics g) {
                Image newImage;
                try {
                    backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("Icons/wyniki.jpg"));
                    newImage = backgroundImage.getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_DEFAULT);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                g.drawImage(newImage,0,0,this);
                }
        };


        //Wczytanie wszystkich wyników
        wyniki = new ArrayList<>();
        wczytajWyniki();


        //Wczytanie najlepszych wyników
        ArrayList<Wynik> top5Domyslny = new ArrayList<>();
        top5Domyslny = najlepszeWyniki(wyniki,"Domyślny","TRUDNY");

        ArrayList<Wynik> top5Precyzja = new ArrayList<>();
        top5Precyzja = najlepszeWyniki(wyniki,"Precyzja","TRUDNY");

        ArrayList<Wynik> top5Refleks = new ArrayList<>();
        top5Refleks = najlepszeWyniki(wyniki,"Reflex","TRUDNY");

        ArrayList<Wynik> top5Wyscig = new ArrayList<>();
        top5Wyscig = najlepszeWyniki(wyniki,"Wyścig","TRUDNY");


        najlepszeWyniki = new JLabel("Najlepsze wyniki",SwingConstants.CENTER);
        najlepszeWyniki.setFont(new Font("Comic Sans MS",Font.BOLD,42));
        najlepszeWyniki.setForeground(Color.WHITE);

        panel.add(najlepszeWyniki,BorderLayout.NORTH);

        panelSrodek = new JPanel(new GridBagLayout());
        panelSrodek.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 40, 70, 40); // Marginesy pomiędzy przyciskami
        gbc.anchor = GridBagConstraints.CENTER; // Wyśrodkowanie komponentów
        gbc.gridx = 1;
        gbc.gridy = 0;

        Font trybFont = new Font("Comic Sans MS",Font.BOLD,26);

        Color color = new Color(170,255,225);

        blok1 = new JPanel(new GridBagLayout());
        blok1.setOpaque(false);
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(10, 5, 10, 5); // Marginesy pomiędzy przyciskami
        gbc2.anchor = GridBagConstraints.CENTER; // Wyśrodkowanie komponentów
        gbc2.gridx = 1;
        gbc2.gridy = 1;
        JLabel lTrybDomyslny = new JLabel("Tryb Domyślny",SwingConstants.CENTER);
        lTrybDomyslny.setForeground(Color.WHITE);
        lTrybDomyslny.setFont(trybFont);
        lTrybDomyslny.setOpaque(false);

        panelDomyslny = new JPanel(new GridBagLayout());
        panelDomyslny.setBackground(color);
        panelDomyslny.add(new JLabel("Poziom Trudny",SwingConstants.CENTER), gbc2);
        gbc2.gridy++;
        for (int i = 0; i < 5; i++) {
            if(top5Domyslny.size()>i) {
                panelDomyslny.add(new JLabel((i + 1) + ". " + String.valueOf(top5Domyslny.get(i)), SwingConstants.CENTER), gbc2);
            } else {
                panelDomyslny.add(new JLabel("",SwingConstants.CENTER),gbc2);
            }
            gbc2.gridy++;
        }
        gbc2.gridy=1;
        blok1.add(lTrybDomyslny,gbc2);
        gbc2.gridy=2;
        blok1.add(panelDomyslny,gbc2);

        panelDomyslny = new JPanel(new GridBagLayout());
        panelDomyslny.setBackground(color);
        gbc2.gridx = 1;
        gbc2.gridy = 1;
        panelDomyslny.add(new JLabel("Poziom Łatwy",SwingConstants.CENTER), gbc2);
        top5Domyslny = najlepszeWyniki(wyniki,"Domyślny","ŁATWY");
        gbc2.gridy++;
        for (int i = 0; i < 5; i++) {
            if(top5Domyslny.size()>i) {
                panelDomyslny.add(new JLabel((i + 1) + ". " + String.valueOf(top5Domyslny.get(i)), SwingConstants.CENTER), gbc2);
            } else {
                panelDomyslny.add(new JLabel("",SwingConstants.CENTER),gbc2);
            }
            gbc2.gridy++;
        }

        gbc2.gridy=3;
        blok1.add(panelDomyslny,gbc2);

        panelSrodek.add(blok1,gbc);

        gbc.gridx++;
        blok2 = new JPanel(new GridBagLayout());
        blok2.setOpaque(false);
        gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(10, 5, 10, 5); // Marginesy pomiędzy przyciskami
        gbc2.anchor = GridBagConstraints.CENTER; // Wyśrodkowanie komponentów
        gbc2.gridx = 1;
        gbc2.gridy = 1;
        JLabel lTrybPrecyzja = new JLabel("Tryb Precyzja",SwingConstants.CENTER);
        lTrybPrecyzja.setForeground(Color.WHITE);
        lTrybPrecyzja.setFont(trybFont);
        lTrybPrecyzja.setOpaque(false);

        panelPrecyzja = new JPanel(new GridBagLayout());
        panelPrecyzja.setBackground(color);
        panelPrecyzja.add(new JLabel("Poziom Trudny",SwingConstants.CENTER), gbc2);
        gbc2.gridy++;
        for (int i = 0; i < 5; i++) {
            if(top5Precyzja.size()>i) {
                panelPrecyzja.add(new JLabel((i + 1) + ". " + String.valueOf(top5Precyzja.get(i)), SwingConstants.CENTER), gbc2);
            } else {
                panelPrecyzja.add(new JLabel(" ",SwingConstants.CENTER),gbc2);
            }
            gbc2.gridy++;
        }
        gbc2.gridy=1;
        blok2.add(lTrybPrecyzja,gbc2);
        gbc2.gridy=2;
        blok2.add(panelPrecyzja,gbc2);

        panelPrecyzja = new JPanel(new GridBagLayout());
        panelPrecyzja.setBackground(color);
        gbc2.gridx = 1;
        gbc2.gridy = 1;
        panelPrecyzja.add(new JLabel("Poziom Łatwy",SwingConstants.CENTER), gbc2);
        top5Precyzja = najlepszeWyniki(wyniki,"Precyzja","ŁATWY");
        gbc2.gridy++;
        for (int i = 0; i < 5; i++) {
            if(top5Precyzja.size()>i) {
                panelPrecyzja.add(new JLabel((i + 1) + ". " + String.valueOf(top5Precyzja.get(i)), SwingConstants.CENTER), gbc2);
            } else {
                panelPrecyzja.add(new JLabel(" ",SwingConstants.CENTER),gbc2);
            }
            gbc2.gridy++;
        }

        gbc2.gridy=3;
        blok2.add(panelPrecyzja,gbc2);

        panelSrodek.add(blok2,gbc);

        gbc.gridx++;
        blok3 = new JPanel(new GridBagLayout());
        blok3.setOpaque(false);
        gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(10, 5, 10, 5); // Marginesy pomiędzy przyciskami
        gbc2.anchor = GridBagConstraints.CENTER; // Wyśrodkowanie komponentów
        gbc2.gridx = 1;
        gbc2.gridy = 1;
        JLabel lTrybRefleks = new JLabel("Tryb Refleks",SwingConstants.CENTER);
        lTrybRefleks.setForeground(Color.WHITE);
        lTrybRefleks.setFont(trybFont);
        lTrybRefleks.setOpaque(false);

        panelRefleks = new JPanel(new GridBagLayout());
        panelRefleks.setBackground(color);
        panelRefleks.add(new JLabel("Poziom Trudny",SwingConstants.CENTER), gbc2);
        gbc2.gridy++;
        for (int i = 0; i < 5; i++) {
            if(top5Refleks.size()>i) {
                panelRefleks.add(new JLabel((i + 1) + ". " + String.valueOf(top5Refleks.get(i)), SwingConstants.CENTER), gbc2);
            } else {
                panelRefleks.add(new JLabel(" ",SwingConstants.CENTER),gbc2);
            }
            gbc2.gridy++;
        }
        gbc2.gridy=1;
        blok3.add(lTrybRefleks,gbc2);
        gbc2.gridy=2;
        blok3.add(panelRefleks,gbc2);

        panelRefleks = new JPanel(new GridBagLayout());
        panelRefleks.setBackground(color);

        gbc2.gridx = 1;
        gbc2.gridy = 1;
        panelRefleks.add(new JLabel("Poziom Łatwy",SwingConstants.CENTER), gbc2);
        top5Refleks = najlepszeWyniki(wyniki,"Reflex","ŁATWY");
        gbc2.gridy++;
        for (int i = 0; i < 5; i++) {
            if(top5Refleks.size()>i) {
                panelRefleks.add(new JLabel((i + 1) + ". " + String.valueOf(top5Refleks.get(i)), SwingConstants.CENTER), gbc2);
            } else {
                panelRefleks.add(new JLabel(" ",SwingConstants.CENTER),gbc2);
            }
            gbc2.gridy++;
        }

        gbc2.gridy=3;
        blok3.add(panelRefleks,gbc2);

        panelSrodek.add(blok3,gbc);


        gbc.gridx++;
        blok4 = new JPanel(new GridBagLayout());
        blok4.setOpaque(false);
        gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(10, 5, 10, 5); // Marginesy pomiędzy przyciskami
        gbc2.anchor = GridBagConstraints.CENTER; // Wyśrodkowanie komponentów
        gbc2.gridx = 1;
        gbc2.gridy = 1;
        JLabel lTrybWyscig = new JLabel("Tryb Wyścig",SwingConstants.CENTER);
        lTrybWyscig.setForeground(Color.WHITE);
        lTrybWyscig.setFont(trybFont);
        lTrybWyscig.setOpaque(false);

        panelWyscig = new JPanel(new GridBagLayout());
        panelWyscig.setBackground(color);

        panelWyscig.add(new JLabel("Poziom Trudny",SwingConstants.CENTER), gbc2);
        gbc2.gridy++;
        for (int i = 0; i < 5; i++) {
            if(top5Wyscig.size()>i) {
                panelWyscig.add(new JLabel((i + 1) + ". " + String.valueOf(top5Wyscig.get(i)), SwingConstants.CENTER), gbc2);
            } else {
                panelWyscig.add(new JLabel(" ",SwingConstants.CENTER),gbc2);
            }
            gbc2.gridy++;
        }
        gbc2.gridy=1;
        blok4.add(lTrybWyscig,gbc2);
        gbc2.gridy=2;
        blok4.add(panelWyscig,gbc2);

        panelWyscig = new JPanel(new GridBagLayout());
        panelWyscig.setBackground(color);
        gbc2.gridx = 1;
        gbc2.gridy = 1;
        panelWyscig.add(new JLabel("Poziom Łatwy",SwingConstants.CENTER), gbc2);
        top5Refleks = najlepszeWyniki(wyniki,"Wyścig","ŁATWY");
        gbc2.gridy++;
        for (int i = 0; i < 5; i++) {
            if(top5Wyscig.size()>i) {
                panelWyscig.add(new JLabel((i + 1) + ". " + String.valueOf(top5Wyscig.get(i)), SwingConstants.CENTER), gbc2);
            } else {
                panelWyscig.add(new JLabel(" ",SwingConstants.CENTER),gbc2);
            }
            gbc2.gridy++;
        }

        gbc2.gridy=3;
        blok4.add(panelWyscig,gbc2);

        panelSrodek.add(blok4,gbc);


        panelDol = new JPanel(new GridBagLayout());
        JButton przyciskZamknij = new JButton("Zamknij");
        przyciskZamknij.setFont(new Font(Font.SANS_SERIF,Font.BOLD,24));
        przyciskZamknij.setBackground(new Color(4,231,255));
        przyciskZamknij.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        gbc.insets = new Insets(0,45,22,0);

        panelDol.add(przyciskZamknij,gbc);
        panelDol.setOpaque(false);



        panel.add(panelSrodek, BorderLayout.CENTER);
        panel.add(panelDol, BorderLayout.SOUTH);
        setContentPane(panel);
        setVisible(true);
    }

    private void wczytajWyniki() {
        try {
            Scanner scanner = new Scanner(new File("wyniki.txt"));
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String parts[] = line.split(", ");
                wyniki.add(new Wynik(Integer.parseInt(parts[0]),parts[1],parts[2],parts[3],Integer.parseInt(parts[4])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Brak pliku, tworzenie nowego");
            File file = new File("wyniki.txt");;
            try {
                file.createNewFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private ArrayList<Wynik> najlepszeWyniki(ArrayList<Wynik>wyniki,String tryb, String difficulty) {
        return wyniki.stream()
                .filter(w -> w.getTryb().equals(tryb) && w.getPoziom().equals(difficulty))
                .sorted(Comparator.comparingInt(Wynik::getPunkty).reversed())
                .limit(5)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
