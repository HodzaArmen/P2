package DN.DN11;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DN11 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("VELIKE ČRKE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new GridLayout(1, 3));

        // Ustvari besedilni polji
        JTextArea leftTextArea = new JTextArea("To je besedilo z\nVELIKIMI\n\nin\n\nmalimi\n\nčrkami.");
        JTextArea rightTextArea = new JTextArea();
        rightTextArea.setEditable(false);

        // Ustvari gumb
        JButton convertButton = new JButton("--> pretvori");

        // Dodaj poslušalca dogodkov na gumb z anonimnim notranjim razredom
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pridobi besedilo iz levega besedilnega polja, ga pretvori v velike črke in
                // nastavi v desno besedilno polje
                String leftText = leftTextArea.getText();
                String upperCaseText = leftText.toUpperCase();
                rightTextArea.setText(upperCaseText);
            }
        });

        // Dodaj komponente v okvir
        frame.add(new JScrollPane(leftTextArea));
        frame.add(convertButton);
        frame.add(new JScrollPane(rightTextArea));

        // Prikaži okvir
        frame.setVisible(true);
    }
}
