package FirstPanel;

import javax.swing.*;
import java.awt.*;

public class LabelAimTrainer extends JLabel {

    public LabelAimTrainer() {
        super("Aim Trainer");
        setSize(450,80);
        setForeground(Color.BLACK);


        Font f1 = new Font(Font.SERIF, Font.BOLD, 80);

        setFont(f1);
    }

    public LabelAimTrainer(String name) {
        super(name);
        setSize(500,100);
        setForeground(Color.GREEN);


        Font f1 = new Font(Font.SERIF, Font.BOLD, 70);

        setFont(f1);
    }
}
