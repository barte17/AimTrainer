package FirstPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Main.Profil;

public class ChangeProfileName extends JDialog implements ActionListener {

    JTextField textField;
    JLabel label;
    JButton cancelButton, saveButton, wyczyscButton;

    Profil profil;

    public ChangeProfileName(JFrame frame, Profil profil) {
        super(frame,"Zmiana nazwy profilu", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(320,185);
        setBackground(Color.lightGray);
        this.profil = profil;

        JPanel panel = new JPanel(null);


        label = new JLabel("Wpisz nową nazwę (max 8 znaków):");
        label.setSize(2000,20);
        label.setLocation(45,20);

        textField = new JTextField();
        textField.setSize(100,20);
        textField.setLocation(95,50);
        textField.addActionListener(this);

        saveButton = new JButton("Zapisz");
        saveButton.setSize(80,40);
        saveButton.setLocation(55,83);
        saveButton.addActionListener(this);

        cancelButton = new JButton("Anuluj");
        cancelButton.setSize(80,40);
        cancelButton.setLocation(155,83);
        cancelButton.addActionListener(this);

        ImageIcon erase = new ImageIcon(getClass().getClassLoader().getResource("Icons/erase.png"));
        wyczyscButton = new JButton(erase);
        wyczyscButton.setSize(25,25);
        wyczyscButton.setLocation(205,47);
        wyczyscButton.addActionListener(this);

        panel.add(label);
        panel.add(textField);
        panel.add(saveButton);
        panel.add(cancelButton);
        panel.add(wyczyscButton);

        setContentPane(panel);
        setResizable(false);
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o==saveButton) {
            save();
        }
        if(o==cancelButton) {
            dispose();
        }
        if(o==wyczyscButton) {
            textField.setText("");
        }
        if(o==textField) {
            save();
        }
    }

    public Profil getNewProfileName() {
        return this.profil;
    }

    public void save() {
        String text = textField.getText();
        if(text.length()>8) {
            JOptionPane.showMessageDialog(this, "Za duża liczba znaków (max 8)");
        } else if(text.length()>=3){
            this.profil.setName(text);
            System.out.println(profil.getName());
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Za mała liczba znaków (min 3)");
        }
    }
}
