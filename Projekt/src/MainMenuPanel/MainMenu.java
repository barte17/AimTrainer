package MainMenuPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


import Games.GameDefault;
import Games.GamePrecision;
import Games.GameRace;
import Games.GameReflex;
import Main.*;

public class MainMenu extends JPanel {

    Settings settings;
    final private JButton grajButton, trybButton, ustawieniaButton, wynikiButton;
    final private PoziomTrudnosciButton poziomTrudnosciButton;

    final private WyjdzButton wyjdzButton;


    private final JLabel zalogowanoLabel, poziomTrudnosciLabel, trybLabel;

    private String difficulty = "ŁATWY";


    String TRYB_GRY = "DOMYŚLNY";

    Image backgroundImage = null;

    Image gameBackgroundImage;

    JFrame frame;

    double screenWidth;
    double screenHeight;
    BufferedImage buttonIcon;

    String pathGameBackground;

    Color color;


    public MainMenu(Profil profil) {
        setLayout(new BorderLayout());
        //pobranie okna aplikacji
        frame = (JFrame) SwingUtilities.getWindowAncestor(MainMenu.this);
        //pobranie wymiarów ekranu
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        screenWidth = screenSize.getWidth();
        screenHeight = screenSize.getHeight();


        settings = new Settings(profil);
        this.color = settings.getCircleColor();
        this.pathGameBackground = settings.getBackgroundPath();



        // Inicjalizacja przycisków
        try {
            buttonIcon = ImageIO.read(getClass().getClassLoader().getResource("Icons/GrajButton.png"));
            grajButton = new JButton(new ImageIcon(buttonIcon));
            grajButton.setBackground(Color.green);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Color buttonColor = new Color(143,236,152);



        trybButton = new JButton("Zmień tryb");
        trybButton.setFont(new Font("Comic Sans MS",Font.BOLD,19));
        trybButton.setBackground(buttonColor);
        trybButton.setForeground(Color.WHITE);

        poziomTrudnosciButton = new PoziomTrudnosciButton();
        poziomTrudnosciButton.setFont(new Font("Comis Sans MS",Font.BOLD,18));
        poziomTrudnosciButton.setBackground(buttonColor);
        poziomTrudnosciButton.setForeground(Color.WHITE);

        ustawieniaButton = new JButton("Ustawienia");
        ustawieniaButton.setFont(new Font("Comis Sans MS",Font.BOLD,19));
        ustawieniaButton.setBackground(buttonColor);
        ustawieniaButton.setForeground(Color.WHITE);

        wynikiButton = new JButton("Wyniki");
        wynikiButton.setFont(new Font("Comis Sans MS",Font.BOLD,19));
        wynikiButton.setBackground(buttonColor);
        wynikiButton.setForeground(Color.WHITE);

        wyjdzButton = new WyjdzButton();
        wyjdzButton.setFont(new Font("Comis Sans MS",Font.BOLD,19));
        wyjdzButton.setBackground(buttonColor);
        wyjdzButton.setForeground(Color.WHITE);


        poziomTrudnosciButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeDifficulty();
                poziomTrudnosciLabel.setText(" Poziom trudności: "+difficulty);
            }
        });



        grajButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                // Tło dla gry

                try {
                    gameBackgroundImage = ImageIO.read(getClass().getClassLoader().getResource(pathGameBackground));
                    gameBackgroundImage = gameBackgroundImage.getScaledInstance((int)screenWidth,(int)screenHeight,Image.SCALE_DEFAULT);
                } catch (IOException ee) {
                    throw new RuntimeException(ee);
                } catch (IllegalArgumentException ex ) {
                    JOptionPane.showMessageDialog(frame, "Brakuje pliku "+pathGameBackground);
                    System.exit(0);
                }

                //włączenie okna gry w zależności od wybranego trybu
                if(TRYB_GRY.equals("DOMYŚLNY")) {
                    new GameDefault(profil, difficulty, color, gameBackgroundImage);
                } else if(TRYB_GRY.equals("PRECYZJA")) {
                    new GamePrecision(profil, difficulty, color,gameBackgroundImage);
                } else if(TRYB_GRY.equals("REFLEKS")) {
                    new GameReflex(profil,difficulty,color,gameBackgroundImage);
                } else if(TRYB_GRY.equals("WYŚCIG")) {
                    new GameRace(profil,difficulty,color,gameBackgroundImage);
                }

            }
        });

        trybButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(MainMenu.this);
                WybierzTryb wybierzTryb =new WybierzTryb(frame);
                String tryb = wybierzTryb.getTRYB();
                if(!(tryb.equals("null"))) {
                    TRYB_GRY = tryb;
                }
                trybLabel.setText("Tryb: "+TRYB_GRY+"   ");
            }
        });

        ustawieniaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UstawieniaFrame ustawienia = new UstawieniaFrame(frame, pathGameBackground, color);
                pathGameBackground = ustawienia.getPath();
                color = ustawienia.getCircleColor();
                settings.saveSettings(color, pathGameBackground, new String("id"+profil.getId()+"settings"));
            }
        });

        wynikiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WynikiPanel(frame);
            }
        });

        // Rozmiar przycisków (proporcje)

        Dimension buttonSize = new Dimension((int)(screenWidth*0.14), (int)(screenHeight*0.065));
        grajButton.setPreferredSize(buttonSize);
        trybButton.setPreferredSize(buttonSize);
        poziomTrudnosciButton.setPreferredSize(buttonSize);
        ustawieniaButton.setPreferredSize(buttonSize);
        wynikiButton.setPreferredSize(buttonSize);
        wyjdzButton.setPreferredSize(buttonSize);


        // Panel srodek

        JPanel panelSrodek = new JPanel(new GridBagLayout()) {
            public void paintComponent(Graphics g) {
                Image newImage;
                frame = (JFrame) SwingUtilities.getWindowAncestor(MainMenu.this);
                super.paintComponent(g);
                try {
                    backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("Icons/sniper1.png"));
                    newImage = backgroundImage.getScaledInstance(frame.getWidth(),frame.getHeight(),Image.SCALE_DEFAULT);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                g.drawImage(newImage,0,0,this);
            }
        };
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 10); // Marginesy pomiędzy przyciskami
        gbc.anchor = GridBagConstraints.CENTER; // Wyśrodkowanie komponentów

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelSrodek.add(grajButton, gbc);

        gbc.gridy = 1;
        panelSrodek.add(trybButton, gbc);

        gbc.gridy = 2;
        panelSrodek.add(poziomTrudnosciButton, gbc);

        gbc.gridy = 3;
        panelSrodek.add(ustawieniaButton, gbc);

        gbc.gridy = 4;
        panelSrodek.add(wynikiButton, gbc);

        gbc.gridy = 5;
        panelSrodek.add(wyjdzButton, gbc);

        //Panel gorny
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setBackground(Color.WHITE);

        zalogowanoLabel = new JLabel("Zalogowano jako: "+profil.getName()+"  ");
        zalogowanoLabel.setHorizontalAlignment(JLabel.RIGHT);
        zalogowanoLabel.setForeground(Color.BLACK);
        zalogowanoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        poziomTrudnosciLabel = new JLabel(" Poziom trudności: "+difficulty);
        poziomTrudnosciLabel.setForeground(Color.BLACK);
        poziomTrudnosciLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        poziomTrudnosciLabel.setHorizontalAlignment(JLabel.LEFT);

        trybLabel = new JLabel("Tryb: "+TRYB_GRY+"   ");
        trybLabel.setForeground(Color.BLACK);
        trybLabel.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
        trybLabel.setHorizontalAlignment(JLabel.CENTER);

        labelPanel.add(trybLabel,BorderLayout.CENTER);
        labelPanel.add(zalogowanoLabel, BorderLayout.EAST);
        labelPanel.add(poziomTrudnosciLabel, BorderLayout.WEST);





        add(panelSrodek,BorderLayout.CENTER);
        add(labelPanel, BorderLayout.NORTH);


    }


    public void changeDifficulty() {
        if(difficulty.equals("ŁATWY")) {
            this.difficulty = "TRUDNY";
        } else {
            this.difficulty = "ŁATWY";
        }
    }



}
