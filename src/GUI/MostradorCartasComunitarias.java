package GUI;

import Logica.Carta;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MostradorCartasComunitarias extends JPanel{
    private ArrayList<Carta> cartas;
    private JLabel labelCartasComunitarias;

    public MostradorCartasComunitarias(ArrayList<Carta> cartasComunitarias){
        cartas=cartasComunitarias;
        setLayout(null);
        setBackground(Color.WHITE);
        labelCartasComunitarias = new JLabel("Cartas de comunitarias");
        labelCartasComunitarias.setBounds(320, 175, 380, 65);
        labelCartasComunitarias.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        add(labelCartasComunitarias);
    }
    protected void paintComponent(Graphics g) {
        System.out.println("se esta intentando dibujar algo");
        super.paintComponent(g);
        // ArrayList<Carta> cartas ;
        if (cartas == null || cartas.isEmpty()) {
            return;
        }

        int x = 50;
        int y = 100;

        for (Carta carta : cartas) {
            BufferedImage img = carta.getImagen();
            if (img != null) {
                g.drawImage(img, x, y, 80, 120, null);  // dibuja carta con tamaño 80x120
            } else {
                g.setColor(Color.GRAY);
                g.fillRect(x, y, 80, 120);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, 80, 120);
                g.drawString("No image", x + 10, y + 60);
            }
            x += 90;
        }
    }
}