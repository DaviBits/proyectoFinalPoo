package GUI;

import Logica.Jugador;

import javax.swing.*;
import java.awt.*;

public class ControladorDeApuestas extends JPanel {
    private Jugador jugador;
    protected int fichasEnApuesta;
    protected int fichasAgregadas;
    protected JButton botonSiguienteJugador;
    protected boolean huboSubida;
    protected int subida;
    protected JButton botonPasar;
    protected  JButton botonRetirarse;
    private Runnable callbackFinDeTurno;
    protected  JButton botonIgualar;
    protected  JButton botonSubir;
    protected JLabel mensajeJugador;
    private JLabel labelFichasDisponibles;
    private JLabel labelFichasApostadas;
    protected boolean sube, abandona, iguala, pasa;


    public ControladorDeApuestas(Jugador jugador, int apuestaActual){
        this.jugador=jugador;
        this.huboSubida=false;
        this.subida=0;
        this.fichasEnApuesta=apuestaActual;
        this.sube=false;
        this.abandona=false;
        this.iguala=false;
        this.pasa=false;

        fichasAgregadas=0;


        this.botonSiguienteJugador = new JButton("Siguiente jugador");
        botonSiguienteJugador.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        botonSiguienteJugador.setBounds(720, 590, 220, 70);
        botonSiguienteJugador.setEnabled(false); // para que se active cuando termine el turno
        //add(botonSiguienteJugador);


        setLayout(null);
        labelFichasDisponibles = new JLabel("Fichas: " + jugador.getFichas());
        labelFichasDisponibles.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        labelFichasDisponibles.setBounds(20, 10, 400, 30);
        add(labelFichasDisponibles);

        labelFichasApostadas = new JLabel("Apostado: " + jugador.getFichasApostadas());
        labelFichasApostadas.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        labelFichasApostadas.setBounds(20, 45, 400, 30);
        add(labelFichasApostadas);

        System.out.println("fichas apostadas del jugador: "+jugador.getFichasApostadas()+" fichas que debe igualar: "+ apuestaActual);
        botonPasar= new JButton("pasar");
        botonPasar.setVisible(false);

        mensajeJugador= new JLabel("Turno de apostar en el pre-flop de: "+jugador.getNombre());
        mensajeJugador.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        mensajeJugador.setBounds(320, 175, 380, 65);
        mensajeJugador.setVisible(true);
        add(mensajeJugador);

        JLabel textoFichas=new JLabel("Tus Fichas disponibles son: "+jugador.getFichas());
        textoFichas.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        textoFichas.setBounds(10,10, 100, 50);
        textoFichas.setVisible(true);
        add(textoFichas);


        botonRetirarse= new JButton("retirarse");
        botonRetirarse.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        botonRetirarse.setBounds(290, 590, 140, 70);
        botonRetirarse.setVisible(true);
        add(botonRetirarse);

        botonIgualar= new JButton("Igualar");
        botonIgualar.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        botonIgualar.setBounds(440, 590, 140, 70);
        botonIgualar.setVisible(true);
        add(botonIgualar);

        botonSubir=new JButton("Subir");
        botonSubir.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        botonSubir.setBounds(570, 590, 140, 70);
        botonSubir.setVisible(true);

        add(botonSubir);

        if(jugador.getFichasApostadas()==fichasEnApuesta){
            botonPasar.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            botonPasar.setBounds(720, 590, 140, 70);
            botonPasar.setVisible(true);
            add(botonPasar);

            botonPasar.addActionListener(e->{
                this.pasa=true;
                botonIgualar.setVisible(false);
                botonPasar.setVisible(false);
                botonSubir.setVisible(false);
                botonRetirarse.setVisible(false);
                botonSiguienteJugador.setEnabled(true);
                if (callbackFinDeTurno != null) callbackFinDeTurno.run();
                actualizarLabelsDeFichas();

            });
        }


        botonRetirarse.addActionListener(e->{
            this.abandona=true;
            jugador.abandonarJuego();
            botonIgualar.setVisible(false);
            botonSubir.setVisible(false);
            botonRetirarse.setVisible(false);
            botonSiguienteJugador.setVisible(true);
            botonSiguienteJugador.setEnabled(true);
            if (callbackFinDeTurno != null) callbackFinDeTurno.run();
        });

        botonIgualar.addActionListener(e -> {
            int diferenciaDeFichas = fichasEnApuesta - jugador.getFichasApostadas();
            if (diferenciaDeFichas <= jugador.getFichas()) {
                this.iguala=true;
                fichasAgregadas = diferenciaDeFichas;
                jugador.sumarFichasApostadas(fichasAgregadas);
                jugador.restarFichas(fichasAgregadas);
                System.out.println("el jugador puso"+ fichasAgregadas);
                botonIgualar.setEnabled(false);
                botonSubir.setEnabled(false);
                botonRetirarse.setEnabled(false);
                botonSiguienteJugador.setEnabled(true);
                actualizarLabelsDeFichas();
            } else {
                JOptionPane.showMessageDialog(null, "No puedes igualar, te faltan fichas.");
            }
            if (callbackFinDeTurno != null) callbackFinDeTurno.run();

        });


        botonSubir.addActionListener(e -> {

            String input = JOptionPane.showInputDialog("¿Cuánto quieres subir?");
            try {
                int apuestaNum = Integer.parseInt(input);
                if (apuestaNum > jugador.getFichas() || apuestaNum <= 0) {
                    JOptionPane.showMessageDialog(null, "Apuesta inválida.");
                } else {
                    this.sube=true;
                    huboSubida=true;
                    int diferenciaDeFichas=fichasEnApuesta-jugador.getFichasApostadas();
                    subida=apuestaNum;
                    fichasAgregadas = apuestaNum+diferenciaDeFichas;
                    jugador.restarFichas(fichasAgregadas+diferenciaDeFichas);
                    jugador.sumarFichasApostadas(fichasAgregadas+diferenciaDeFichas);
                    System.out.println("el jugador puso: "+(fichasAgregadas+diferenciaDeFichas));

                    botonIgualar.setEnabled(false);
                    botonSubir.setEnabled(false);
                    botonRetirarse.setEnabled(false);
                    botonPasar.setEnabled(false);
                    botonSiguienteJugador.setEnabled(true);
                    actualizarLabelsDeFichas();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor ingresa un número válido.");
            }

            botonIgualar.setVisible(false);
            botonSubir.setVisible(false);
            botonRetirarse.setVisible(false);
            botonSiguienteJugador.setVisible(true);
            if (callbackFinDeTurno != null) callbackFinDeTurno.run();

        });

    }

    public void actualizarLabelsDeFichas() {
        labelFichasDisponibles.setText("Fichas: " + jugador.getFichas());
        labelFichasApostadas.setText("Apostado: " + jugador.getFichasApostadas());
    }

    //metodo para sumar las fichas de estas acciones a la logica central del juego
    public int getFichasAgregadas(){return fichasAgregadas;}
    //metodo para reemplazar/actualizar al jugador en turno
    public Jugador getJugador(){return jugador;}
    public boolean huboSubida(){return huboSubida;}
    public int getSubida(){return subida;}
    public void setCallbackFinDeTurno(Runnable callback) {
        this.callbackFinDeTurno = callback;
    }


}