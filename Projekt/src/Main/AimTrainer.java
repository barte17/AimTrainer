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


    //funkcja do zmiany panelu w frame po wybraniu profilu
    public void showView(JPanel panel) {
        firstPanel.removeAll();
        setContentPane(panel);

        //ustawienie proporcji okna wedle wielkości ekranu użytkownika
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameHeight =(int) (screenSize.height*0.7);
        int frameWidth = (int) (screenSize.width*0.65);

        setSize(frameWidth,frameHeight);
        setLocationRelativeTo(null);

        revalidate();
    }

    public void readProfiles() {
            Path path = Paths.get("profiles.txt");
            File file = new File("profiles.txt");
            ArrayList<String> lines = new ArrayList<>();

            //tworzenie nowego pliku z profilami jeśli go brakuje
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
                myWriter.write("1, Profil 1\n");
                myWriter.write("2, Profil 2\n");
                myWriter.write("3, Profil 3\n");
                myWriter.write("4, Profil 4\n");
                myWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //pobieranie profili ze sprawdzaniem poprawności formatu (<ID>, <NAZWA PROFILU>)
        try {
            Scanner scanner = new Scanner(path);
            for (int i = 1; i <= 4; i++) {
                if(scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    if (!line.contains(", ")) {
                        line = i+", Profil "+i;
                    }

                    String parts[] = line.split(", ");
                    if(parts.length != 2) {
                        line = i+", Profil "+i;
                        parts = line.split(", ");
                    }

                    try {
                        profiles.add(new Profil(Integer.parseInt(parts[0]), parts[1]));
                        lines.add(line);
                    } catch (NumberFormatException e) {
                        profiles.add(new Profil(i, parts[1]));
                        lines.add(i+", "+parts[1]);
                    }

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

            try {
                FileWriter myWriter = new FileWriter("profiles.txt");
                for (String l : lines) {
                    myWriter.write(l+"\n");
                }
                myWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }





    public ArrayList<Profil> getProfiles() {
        return this.profiles;
    }

}
