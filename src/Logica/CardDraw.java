package Logica;

import javax.swing.*;
import java.awt.*;

public class CardDraw extends Poker {

    public CardDraw() {
        setBackground(Color.LIGHT_GRAY);
        JButton boton = new JButton("BOTON PRUEBA");
        add(boton);
    }

    @Override
    public void repartirCartas() {
    }

    @Override
    public void empezarApuestas() {
    }

    @Override
    public void comenzarEnfrentamiento() {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ejemplo de JPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        CardDraw panel = new CardDraw();
        frame.add(panel);
        frame.setVisible(true);
    }
}
