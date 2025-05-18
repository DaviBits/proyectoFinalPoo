package Logica;

import GUI.SelectorDeJugadores;

import javax.swing.*;
import java.util.ArrayList;

public class TexasHoldEm extends Poker {

    public TexasHoldEm(){
        super();
        inicializarJugadores();
    }

    @Override
    public void inicializarJugadores() {
        SelectorDeJugadores selectorDeJugadores=new SelectorDeJugadores();
        selectorDeJugadores.setBounds(0, 0, 1000, 700);
        selectorDeJugadores.setCallbackAceptar(() -> {

            numJugadores=selectorDeJugadores.getNumJugadores();
            System.out.println("numero de jugadores: "+numJugadores);
            for(int i=0; i<numJugadores; i++){
                String nombre =JOptionPane.showInputDialog("Ingrese el nombre del jugador"+ (i+1));
                ArrayList<Carta> mazo= new ArrayList<>();
                jugadores.add(new Jugador(nombre,new ArrayList<Carta>(), 1500));
            }
            empezarApuestas();
        });
        add(selectorDeJugadores);
        revalidate();
        repaint();
    }


    @Override
    public void empezarApuestas() {
        System.out.println("RONDA DE APUESTAS");
    }

    // Método para probarlo en una ventana
    public static void main(String[] args) {
        JFrame frame = new JFrame("Selector de Jugadores");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.add(new TexasHoldEm());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
