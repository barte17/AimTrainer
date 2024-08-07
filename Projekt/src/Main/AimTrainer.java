package Main;

import FirstPanel.FirstPanel;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class AimTrainer extends JFrame {
    ArrayList<Profil> profiles;

    FirstPanel firstPanel;


    public AimTrainer() {
        super("AimTrainer");

        profiles = new ArrayList<>();
        readProfiles();

        setSize ( 950, 600 );
        setLocationRelativeTo ( null ); //wyśrodkowanie frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        firstPanel = new FirstPanel(this);

        setResizable(false);
        setContentPane(firstPanel);
        setVisible(true);
    }


    //funkcja do zmiany panelu w frame
    public void showView(JPanel panel) {
        // tutaj zmienic wielkosc okna
        firstPanel.removeAll();
        setContentPane(panel);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameHeight =(int) (screenSize.height*0.7);
        int frameWidth = (int) (screenSize.width*0.65);

        setSize(frameWidth,frameHeight);
        setLocationRelativeTo(null);

        invalidate();
        validate();
    }

    public void readProfiles() {
            Path path = Paths.get("profiles.txt");
            File file = new File("profiles.txt");

            if(!(Files.exists(path))) {
                try {
                    System.out.println("Brak pliku, tworzenie nowego");
                    file.createNewFile();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        if(file.length()<10) {
            try {
                FileWriter myWriter = new FileWriter("profiles.txt");
                myWriter.write("1, Profile 1\n");
                myWriter.write("2, Profile 2\n");
                myWriter.write("3, Profile 3\n");
                myWriter.write("4, Profile 4\n");
                myWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Scanner scanner = new Scanner(path);
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String parts[] = line.split(", ");
                profiles.add(new Profil(Integer.parseInt(parts[0]), parts[1]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Profil> getProfiles() {
        return this.profiles;
    }

}
