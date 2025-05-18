package Logica;

import java.awt.*;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Mazo mazo = new Mazo();
        JPanel panelCartas = new JPanel();
        panelCartas.setLayout(new BoxLayout(panelCartas, BoxLayout.Y_AXIS));
        for (int i = 0; i < mazo.getTamañoDellMazo(); i++) {
            JLabel cartaLabel = new JLabel(new ImageIcon(mazo.getCartaEn(i).getImagen()));
            cartaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelCartas.add(cartaLabel);
            panelCartas.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        JScrollPane scrollPane = new JScrollPane(panelCartas);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JFrame frame = new JFrame("Mazo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(100, 800);
        frame.add(scrollPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
