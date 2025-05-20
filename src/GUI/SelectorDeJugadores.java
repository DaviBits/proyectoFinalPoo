package GUI;

import javax.swing.*;
import java.awt.*;

public class SelectorDeJugadores extends JPanel {

    private int numJugadores = 2;
    private final int MIN_JUGADORES = 2;
    private final int MAX_JUGADORES = 10;
    private boolean jugadoresSeleccioandos;
    private JButton botonMenos;
    private JButton botonMas;
    private JButton botonAceptar;
    private JLabel labelNumero;

    public SelectorDeJugadores() {
        jugadoresSeleccioandos=false;
        setLayout(null);

        setBackground(Color.WHITE);
        JLabel labelJugadores = new JLabel("Ingrese el numero de jugadores: ");
        labelJugadores.setBounds(320, 175, 380, 65);
        labelJugadores.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        add(labelJugadores);

        botonMenos = new JButton("<-");
        botonMas = new JButton("->");
        botonAceptar = new JButton("Aceptar");

        labelNumero = new JLabel(String.valueOf(numJugadores));
        labelNumero.setFont(new Font("Comic Sans MS", Font.BOLD, 28));

        botonMenos.setBounds(320,260, 95,110);
        botonMas.setBounds(500, 260, 95, 110);
        labelNumero.setBounds(440, 260, 50, 50);
        botonAceptar.setBounds(320, 370, 200, 20);


        botonMenos.addActionListener(e -> {
            if (numJugadores > MIN_JUGADORES) {
                numJugadores--;
                labelNumero.setText(String.valueOf(numJugadores));
            }
        });

        botonMas.addActionListener(e -> {
            if (numJugadores < MAX_JUGADORES) {
                numJugadores++;
                labelNumero.setText(String.valueOf(numJugadores));
            }
        });

        botonAceptar.addActionListener(e->{
            jugadoresSeleccioandos=true;
        });

        add(botonMenos);
        add(labelNumero);
        add(botonMas);
        add(botonAceptar);
    }

    public boolean seleccionoJugadores(){return jugadoresSeleccioandos;}
    public void setCallbackAceptar(Runnable callback) {
        botonAceptar.addActionListener(e -> {
            jugadoresSeleccioandos = true;
            callback.run();
        });
    }


    public int getNumJugadores() {
        return numJugadores;
    }


}