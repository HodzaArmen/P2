import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Naloga12 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Okno z datumom in miško");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JButton dateButton = new JButton("Datum");
        JTextField dateField = new JTextField(10);
        JButton colorButton = new JButton("Barva");
        topPanel.add(dateButton);
        topPanel.add(dateField);
        topPanel.add(colorButton);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);

        JTextField mouseCoords = new JTextField(10);
        mouseCoords.setEditable(false);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(mouseCoords, BorderLayout.SOUTH);

        // Action listener for the date button
        dateButton.addActionListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd. MM. yyyy");
            String datum = sdf.format(new Date());
            dateField.setText(datum);
        });

        // Action listener for the color button
        Random rand = new Random();
        colorButton.addActionListener(e -> {
            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();
            Color randomColor = new Color(r, g, b);
            centerPanel.setBackground(randomColor);
        });

        // Mouse motion listener for the center panel
        centerPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseCoords.setText("(" + e.getX() + ", " + e.getY() + ")");
            }
        });

        frame.setVisible(true);
    }
}
