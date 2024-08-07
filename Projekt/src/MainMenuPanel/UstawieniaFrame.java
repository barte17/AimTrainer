package MainMenuPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UstawieniaFrame extends JDialog {

    double screenWidth;
    double screenHeight;

    Image backgroundImage;

    JFrame frame;

    JLabel zmienTlo, zmienKolo;

    JButton background1, background2, background3, background4;

    JButton paleta, redButton, blueButton, greenButton;

    JButton saveButton, zamknijButton;

    Color circleColor;

    Color tempColor;

    String tempPath;
    private String path;


    public UstawieniaFrame(JFrame frameAncesor, String currentPath, Color color) {
        super(frameAncesor,"Ustawienia",true);
        path = currentPath;
        tempPath = currentPath;

        circleColor = color;
        tempColor = color;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.getWidth();
        screenHeight = screenSize.getHeight();
        setSize((int)screenWidth/2,(int)(screenHeight+200)/2);

        JPanel panel = new JPanel(new BorderLayout()) {
            public void paintComponent(Graphics g) {
                Image newImage;
                frame = (JFrame) SwingUtilities.getWindowAncestor(UstawieniaFrame.this);
                super.paintComponent(g);
                try {
                    backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("Icons/SnipeSettings.png"));
                    newImage = backgroundImage.getScaledInstance(((int)screenWidth/2),(int)(screenHeight+200)/2,Image.SCALE_DEFAULT);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                g.drawImage(newImage,0,0,this);
            }
        };

        //Przyciski i labele


        zmienTlo = new JLabel("Zmień tło gry");
        zmienTlo.setForeground(Color.WHITE);
        zmienTlo.setFont(new Font("Comic Sans MS",Font.BOLD,28));

        zmienKolo = new JLabel("Zmień kolor kółka");
        zmienKolo.setForeground(Color.WHITE);
        zmienKolo.setFont(new Font("Comic Sans MS",Font.BOLD,28));



        background1 = new JButton("Strzelnica 1");
        background1.setBackground(new Color(255,255,102));
        background1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempPath = "Icons/strzelnica1.jpg";
                saveButton.setBackground(null);
            }
        });

        background2 = new JButton("Strzelnica 2");
        background2.setBackground(new Color(255,255,102));
        background2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempPath = "Icons/strzelnica2.jpg";
                saveButton.setBackground(null);
            }
        });

        background3 = new JButton("Strzelec");
        background3.setBackground(new Color(255,255,102));
        background3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempPath = "Icons/sniper2.png";
                saveButton.setBackground(null);
            }
        });

        background4 = new JButton("Strzelec 2");
        background4.setBackground(new Color(255,255,102));
        background4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempPath = "Icons/strzelec2.jpg";
                saveButton.setBackground(null);
            }
        });



        paleta = new JButton("Paleta");
        paleta.setBackground(Color.MAGENTA);
        paleta.setForeground(Color.WHITE);
        paleta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(UstawieniaFrame.this, "Wybierz kolor", Color.MAGENTA);
                if(color!=null) {
                    tempColor = color;
                }
                saveButton.setBackground(null);
            }
        });

        redButton = new JButton("Czerwony");
        redButton.setBackground(Color.RED);
        redButton.setForeground(Color.WHITE);
        redButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempColor = Color.RED;
                saveButton.setBackground(null);
            }
        });

        blueButton = new JButton("Niebieski");
        blueButton.setBackground(Color.blue);
        blueButton.setForeground(Color.WHITE);
        blueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempColor = Color.BLUE;
                saveButton.setBackground(null);
            }
        });

        greenButton = new JButton("Zielony");
        greenButton.setBackground(Color.green);
        greenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempColor = Color.GREEN;
                saveButton.setBackground(null);
            }
        });

        saveButton = new JButton("Zapisz");
        saveButton.setBackground(new Color(203,203,203));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveButton.setBackground(new Color(102,255,102));
                path = tempPath;
                circleColor = tempColor;
            }
        });

        zamknijButton = new JButton("Zamknij");
        zamknijButton.setBackground(new Color(203,203,203));
        zamknijButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });



        //ustawienia podzielone na 3 panele (tło gry, kolor piłki, sens i dzwiek?)
        JPanel panelLewo = new JPanel(new GridBagLayout());
        panelLewo.setOpaque(false);

        JPanel panelPrawo = new JPanel(new GridBagLayout());
        panelPrawo.setOpaque(false);

        JPanel panelDol = new JPanel(new GridBagLayout());
        panelDol.setOpaque(false);

        //Panel Lewo

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 50, 35, 50); // Marginesy pomiędzy przyciskami
        gbc.anchor = GridBagConstraints.WEST; // Wyśrodkowanie komponentów

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        panelLewo.add(zmienTlo,gbc);

        gbc.insets = new Insets(15, 50, 20, 50);

        gbc.gridy = 1;
        panelLewo.add(background1,gbc);

        gbc.gridy = 2;
        panelLewo.add(background2,gbc);

        gbc.gridy = 3;
        panelLewo.add(background3,gbc);

        gbc.gridy = 4;
        panelLewo.add(background4,gbc);

        gbc.insets = new Insets(40, 50, 40, 50);



        gbc.gridy = 5;
        gbc.ipady = 20;
        panelLewo.add(zamknijButton, gbc);

        //Panel prawo

        gbc.ipady = 0;

        gbc.insets = new Insets(15, 50, 35, 50);
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelPrawo.add(zmienKolo,gbc);

        gbc.insets = new Insets(15, 50, 20, 50);

        gbc.gridy = 1;
        panelPrawo.add(paleta,gbc);

        gbc.gridy = 2;
        panelPrawo.add(greenButton,gbc);

        gbc.gridy = 3;
        panelPrawo.add(blueButton,gbc);


        gbc.gridy = 4;
        panelPrawo.add(redButton,gbc);

        gbc.insets = new Insets(40, 50, 40, 50);


        gbc.gridy = 5;
        gbc.ipady = 20;
        panelPrawo.add(saveButton,gbc);






        panel.add(panelLewo,BorderLayout.WEST);
        panel.add(panelPrawo,BorderLayout.EAST);
        panel.add(panelDol,BorderLayout.SOUTH);
        setContentPane(panel);
        setResizable(false);
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    public String getPath() {
        return path;
    }

    public Color getCircleColor() {
        return circleColor;
    }
}
