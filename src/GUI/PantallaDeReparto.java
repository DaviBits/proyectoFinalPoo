package GUI;

import Logica.Carta;
import Logica.Jugador;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PantallaDeReparto extends JPanel {
    private Jugador jugador;
    private JLabel labelJugadores;
    public PantallaDeReparto(Jugador jugador){
        this.jugador=jugador;

        setLayout(null);
        setBackground(Color.WHITE);
        labelJugadores = new JLabel("Cartas de; jugador "+jugador.getNombre());
        labelJugadores.setBounds(320, 10, 380, 65);
        labelJugadores.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        add(labelJugadores);


    }

    public void dibujarInformacion(){
        setLayout(null);
        remove(labelJugadores);
        setBackground(Color.WHITE);
        labelJugadores = new JLabel("Cartas de; jugador "+jugador.getNombre());
        labelJugadores.setBounds(320, 10, 380, 65);
        labelJugadores.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        add(labelJugadores);
    }

    public void setJugador(Jugador nuevoJugador) {
        this.jugador = nuevoJugador;
        dibujarInformacion();
        revalidate();
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("se esta intentando dibujar algo");
        super.paintComponent(g);
        ArrayList<Carta> cartas = jugador.getMano();
        if (cartas == null || cartas.isEmpty()) {
            return;
        }

        int x = 50;
        int y = 100;

        for (Carta carta : cartas) {
            BufferedImage img = carta.getCartaReverso();
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