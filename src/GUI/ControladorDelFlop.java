package GUI;

import Logica.Jugador;

import javax.swing.*;
import java.awt.*;

public class ControladorDelFlop extends ControladorDeApuestas{
//esta clase maneja toda la GUI y la lógica de los botones de las etapas de apuesta,
    //extiende de ControladorDeApuesta, ya que esa clase modela al flop y ya tiene casi
    //todas las acciones implementadas
    private JButton botonApostar;
    private boolean nadieHaApostado;
    private boolean jugadorAposto;
    private String nombreDeLaFase;

    public ControladorDelFlop(Jugador jugador, int apuestaActual, boolean nadieHaApostado, String fase){

        super(jugador, apuestaActual);
        this.nombreDeLaFase=fase;
        this.nadieHaApostado=nadieHaApostado;
        this.jugadorAposto=false;
        mensajeJugador.setText("Turno de apostar en  "+fase+" de: "+jugador.getNombre());
        mensajeJugador.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        revalidate();
        repaint();

        if(nadieHaApostado){
            botonApostar= new JButton("apostar");
            botonApostar.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            botonApostar.setBounds(300, 500, 150, 60);
            botonApostar.setVisible(true);
            add(botonApostar);

            botonApostar.addActionListener(e-> {
                jugadorAposto=true;
                String apuestaTexto=JOptionPane.showInputDialog("¿Cuánto quieres subir?");
                try{
                    int apuesta= Integer.parseInt(apuestaTexto);
                    if(apuesta<= jugador.getFichas()){
                        int diferenciaDeFichas=fichasEnApuesta-jugador.getFichasApostadas();
                        jugadorAposto=true;
                        this.nadieHaApostado=false;
                        System.out.println("el jugador aposto: "+ apuesta);
                        subida=apuesta;
                        huboSubida=true;
                        fichasAgregadas = apuesta+diferenciaDeFichas;
                        jugador.restarFichas(fichasAgregadas);
                        jugador.sumarFichasApostadas(fichasAgregadas);

                        botonIgualar.setEnabled(false);
                        botonSubir.setEnabled(false);
                        botonApostar.setEnabled(false);
                        botonPasar.setEnabled(false);
                        botonRetirarse.setEnabled(false);
                        botonApostar.setEnabled(false);
                        botonSiguienteJugador.setEnabled(true);
                        actualizarLabelsDeFichas();
                    }
                }catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor ingresa un número válido.");
                }
            });
            actualizarBotonApostar();


        }
    }

    public void actualizarBotonApostar() {
        if (sube || pasa || iguala || abandona) {
            if (botonApostar != null) {
                botonApostar.setEnabled(false);
                botonApostar.setVisible(false);
            }
        }
    }


    public void setBotonSiguienteJugador(JButton boton) {this.botonSiguienteJugador = boton;}


    public boolean elJugadorAposto(){return jugadorAposto;}
}