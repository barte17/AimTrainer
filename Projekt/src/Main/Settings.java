package Main;

import MainMenuPanel.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class Settings {
    Color circleColor;
    String backgroundPath;
    JFrame frame;

    public Settings(Profil profil) {
        String idSettings = "id"+String.valueOf(profil.getId())+"settings";
        readSettings(idSettings);
    }

    public void readSettings(String idSettings) {
        Path path = Paths.get(idSettings+".txt");
        File file = new File(idSettings+".txt");

        if (!(Files.exists(path))) {
            try {
                System.out.println("Brak pliku, tworzenie nowego");
                file.createNewFile();
                FileWriter myWriter = new FileWriter(idSettings+".txt");

                myWriter.write("Circle_Color: "+Color.GREEN.getRed()+","+Color.GREEN.getGreen()+","+Color.GREEN.getBlue()+"\n");
                Path backgroundPath = Paths.get("Icons/strzelec2.jpg");
                myWriter.write("Background: "+backgroundPath);
                myWriter.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            Scanner scanner = new Scanner(path);
            String line = scanner.nextLine();
            String parts[] = line.split(": ");

            if(parts[0].equals("Circle_Color")) {
                String rgb[] = parts[1].split(",");
                int r = Integer.parseInt(rgb[0]);
                int g = Integer.parseInt(rgb[1]);
                int b = Integer.parseInt(rgb[2]);
                this.circleColor = new Color(r,g,b);
            } else {
                this.circleColor = new Color(0,255,0);
            }

            line = scanner.nextLine();
            parts = line.split(": ");

            if(parts[0].equals("Background")) {
                this.backgroundPath = parts[1];
            } else {
                this.backgroundPath = "Icons/sniper2.png";
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveSettings(Color color, String path,String idSettings) {
        try {
            FileWriter myWriter = new FileWriter(idSettings+".txt");
            myWriter.write("Circle_Color: "+color.getRed()+","+color.getGreen()+","+color.getBlue()+"\n");
            myWriter.write("Background: "+path);
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Color getCircleColor() {
        return circleColor;
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }
}
