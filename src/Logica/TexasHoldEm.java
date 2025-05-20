package Logica;

import GUI.ControladorDeApuestas;
import GUI.ControladorDelFlop;
import GUI.PantallaDeReparto;
import GUI.SelectorDeJugadores;

import javax.swing.*;
import java.awt.*;
import java.lang.classfile.instruction.ReturnInstruction;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TexasHoldEm extends Poker {

    private AtomicInteger apuestaTotal = new AtomicInteger(0);
    private ArrayList<Carta> cartasComunitarias;
    private PantallaDeReparto pantallaDeReparto;
    private int jugadorActualIndex;
    private int indiceJugadorEnPreFlop;
    private ControladorDeApuestas panelPreFlop;
    private ControladorDelFlop panelFlop;
    private int ciegaGrande;
    private int apuestaAIgualarIndividual;
    private boolean huboApuestaEnFlop;
    private int indiceJugadorFlop;
    private boolean yaSeMostroBotonApostarFlop = false;


    public TexasHoldEm(){
        super();
        this.indiceJugadorFlop=0;
        this.huboApuestaEnFlop=false;
        cartasComunitarias= new ArrayList<>();
        this.jugadorActualIndex=0;
        this.indiceJugadorEnPreFlop=0;
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
                jugadores.add(new Jugador(nombre,new ArrayList<>(), 1000));
            }
            remove(selectorDeJugadores);
            revalidate();
            repaint();
            empezarApuestas();

        });
        add(selectorDeJugadores);
        revalidate();
        repaint();
    }
    //metodo equivalente a la etapa de "las ciegas"
    @Override
    public void empezarApuestas() {
        int minimoCiegaChica=1, maximoCiegChica=150;
        int minimpCiegaGrande=1, maximoCiegaGrande= maximoCiegChica*2;
        AtomicInteger ciegaChica= new AtomicInteger(1);
        int ciegaGrande;

        JLabel textoLasCiegas= new JLabel("LAS CIEGAS");
        textoLasCiegas.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        textoLasCiegas.setBounds(335, 32, 305, 63);
        textoLasCiegas.setVisible(true);
        add(textoLasCiegas);

        JLabel textoCiegaChica= new JLabel("El jugador "+ jugadores.get(0).getNombre()+ " coloca la apuesta chica");
        textoCiegaChica.setBounds(335, 64, 305, 63);
        textoCiegaChica.setVisible(true);
        add(textoCiegaChica);

        JTextField cantidadApuestaChica=new JTextField();
        cantidadApuestaChica.setBounds(10, 160, 100, 25);
        cantidadApuestaChica.setVisible(true);
        add(cantidadApuestaChica);

        JButton botonAceptarCiegaChica = new JButton("aceptar");
        botonAceptarCiegaChica.setBounds(320, 370, 200, 20);
        botonAceptarCiegaChica.setVisible(true);
        add(botonAceptarCiegaChica);

        botonAceptarCiegaChica.addActionListener(e->{
            String cantidadApostada=cantidadApuestaChica.getText();
            try {
                AtomicInteger apuesta = new AtomicInteger(Integer.parseInt(cantidadApostada));
                if (apuesta.get() >=minimoCiegaChica && apuesta.get() < maximoCiegChica) {
                    System.out.println("el jugador aposto extitosamente: " + apuesta + " fichas");
                    int apuestaC= apuesta.get();
                    jugadores.get(0).restarFichas(apuestaC);
                    jugadores.get(0).sumarFichasApostadas(apuestaC);
                    remove(cantidadApuestaChica);
                    remove(textoCiegaChica);
                    remove(botonAceptarCiegaChica);
                    JLabel textoCiegaGrande= new JLabel("El jugador "+ jugadores.get(1).getNombre()+ " coloca la apuesta grande");
                    textoCiegaGrande.setBounds(335, 64, 305, 63);
                    textoCiegaGrande.setVisible(true);
                    add(textoCiegaGrande);

                    JTextField cantidadApuestaGrande=new JTextField();
                    cantidadApuestaGrande.setBounds(10, 160, 100, 25);
                    cantidadApuestaGrande.setVisible(true);
                    add(cantidadApuestaGrande);

                    JButton botonAceptarCiegaGrande=new JButton("aceptar");
                    botonAceptarCiegaGrande.setBounds(320, 370, 200, 20);
                    botonAceptarCiegaGrande.setVisible(true);
                    add(botonAceptarCiegaGrande);

                    botonAceptarCiegaGrande.addActionListener(x->{
                        String cantidadApostadaGrande=cantidadApuestaGrande.getText();
                        try {
                            int apuestaGrande = Integer.parseInt(cantidadApostadaGrande);
                            if (apuestaGrande >=minimpCiegaGrande && apuestaGrande < maximoCiegaGrande) {
                                System.out.println("el jugador aposto"+apuestaGrande+" fichas");
                                jugadores.get(1).restarFichas(apuestaGrande);
                                jugadores.get(1).sumarFichasApostadas(apuestaGrande);
                                apuestaTotal.set(apuestaC + apuestaGrande);
                                this.ciegaGrande=apuestaGrande;
                                remove(cantidadApuestaGrande);
                                remove(textoCiegaGrande);
                                remove(botonAceptarCiegaGrande);
                                remove(textoLasCiegas);
                                revalidate();
                                repaint();
                                reparto();
                            } else {
                                System.out.println("no se realizo la apuesta grande ");
                            }
                        }catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Por favor ingresa un número válido.");
                        }

                    });
                    revalidate();
                    repaint();
                } else {
                    System.out.println("no se realizo la apuesta");
                }
            }catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor ingresa un número válido.");
            }

        });

        revalidate();
        repaint();
    }

    public void reparto() {
        apuesta = apuestaTotal.get();
        System.out.println("apuesta final: " + apuesta);
        System.out.println("FASE DE REPARTO");

        for (Jugador jugador : jugadores) {
            jugador.getMano().addAll(getManoDeNCartas(2));
            System.out.println("CARTAS DE: " + jugador.getNombre());
            jugador.mostrarCartas();
        }

        // Solo se crea una pantalla y se le pasa el primer jugador
        pantallaDeReparto = new PantallaDeReparto(jugadores.get(jugadorActualIndex));
        pantallaDeReparto.setBounds(0, 0, 1000, 550);
        add(pantallaDeReparto);

        // Botón para avanzar al siguiente jugador
        JButton siguiente = new JButton("Siguiente");
        siguiente.setBounds(400, 600, 150, 30);
        siguiente.setVisible(true);
        add(siguiente);
        siguiente.addActionListener(e -> {
            jugadorActualIndex++;
            if (jugadorActualIndex < jugadores.size()) {
                pantallaDeReparto.setBackground(Color.BLACK);
                pantallaDeReparto.setJugador(jugadores.get(jugadorActualIndex));
                revalidate();
                repaint();
            } else {
                remove(pantallaDeReparto);
                remove(siguiente);

                preFlop();
            }
            repaint();
        });
        revalidate();
        repaint();
    }


    public void preFlop(){
        apuestaAIgualarIndividual=ciegaGrande;
        System.out.println("pre flop");
        this.indiceJugadorEnPreFlop=0;
        mostrarTurnoPreFlop();
    }

    private void mostrarTurnoPreFlop() {
        if (panelPreFlop != null) {
            remove(panelPreFlop);
        }

        // Buscar el siguiente jugador que no haya abandonado
        while (indiceJugadorEnPreFlop < jugadores.size() && jugadores.get(indiceJugadorEnPreFlop).haAbandonado()) {
            indiceJugadorEnPreFlop++;
        }

        if (indiceJugadorEnPreFlop < jugadores.size()) {
            Jugador jugador = jugadores.get(indiceJugadorEnPreFlop);

            panelPreFlop = new ControladorDeApuestas(jugador, apuestaAIgualarIndividual);
            panelPreFlop.setBounds(0, 0, 1000, 700);

            JButton botonSiguiente = new JButton("Siguiente jugador");
            botonSiguiente.setBounds(720, 500, 200, 50);
            botonSiguiente.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
            botonSiguiente.setEnabled(false); // Deshabilitado al inicio

            // Este callback se invoca cuando el jugador realiza su acción
            panelPreFlop.setCallbackFinDeTurno(() -> {
                botonSiguiente.setEnabled(true);
                if (panelPreFlop.huboSubida()) {
                    apuestaAIgualarIndividual += panelPreFlop.getSubida();
                    this.indiceJugadorEnPreFlop=0;
                }
            });

            botonSiguiente.addActionListener(e -> {
                apuestaTotal.addAndGet(panelPreFlop.getFichasAgregadas());
                if(!panelPreFlop.huboSubida()){
                    indiceJugadorEnPreFlop++;
                }
                remove(panelPreFlop);  // IMPORTANTE: limpiar antes de mostrar el siguiente
                mostrarTurnoPreFlop();
            });

            panelPreFlop.add(botonSiguiente);
            add(panelPreFlop);
            revalidate();
            repaint();
        } else {
            // Fin del pre-flop
            System.out.println("Fin de las apuestas del pre-flop. Total acumulado: " + apuestaTotal.get());
            flop();
        }
    }

    public void flop(){
        System.out.println("EL REPARTIDOR ELIMINA LA PRIMERA CARTA EN LA PARTE DE ARRIBA: ");
        eliminarPrimeraCartaDelMazo();
        System.out.println("EL REPARTIDOR REVELA TRES CARTAS COMUNITARIAS: ");
        cartasComunitarias.addAll(getManoDeNCartas(3));
        for(int i=0; i<cartasComunitarias.size(); i++){
            cartasComunitarias.get(i).imprimirCarta();
        }
        flopVisual("flop");
    }

    public void flopVisual(String fase){


        while (indiceJugadorFlop < jugadores.size() && jugadores.get(indiceJugadorFlop).haAbandonado()) {
            indiceJugadorFlop++;
        }

        if (indiceJugadorFlop < jugadores.size()) {
            Jugador jugador = jugadores.get(indiceJugadorFlop);
            panelFlop = new ControladorDelFlop(jugador, apuestaAIgualarIndividual, !yaSeMostroBotonApostarFlop, fase);
            panelFlop.actualizarBotonApostar();
            panelFlop.setBounds(0, 0, 1000, 700);

            JButton botonSiguiente = new JButton("Siguiente jugador");
            botonSiguiente.setBounds(720, 500, 200, 50);
            botonSiguiente.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
            botonSiguiente.setEnabled(false); // Deshabilitado al inicio
            panelFlop.setBotonSiguienteJugador(botonSiguiente);


            // Este callback se invoca cuando el jugador realiza su acción
            panelFlop.setCallbackFinDeTurno(() -> {
                botonSiguiente.setEnabled(true);
                if (panelFlop.elJugadorAposto()) {
                    System.out.println("->>>>>>>>>>>>>>>>>>>>>YA NO DEBERIA MOSTRARSE APOSTAR");
                    this.huboApuestaEnFlop = true;
                    this.yaSeMostroBotonApostarFlop = true;
                    this.indiceJugadorFlop=0;
                }

            });


            botonSiguiente.addActionListener(e -> {
                apuestaTotal.addAndGet(panelFlop.getFichasAgregadas());
                if (panelFlop.huboSubida()) {

                    this.apuestaAIgualarIndividual += panelFlop.getSubida();  // importante si hubo subida
                    this.indiceJugadorFlop = 0; // todos deben volver a hablar
                } else {
                    this.indiceJugadorFlop++; // se avanza normalmente al siguiente jugador
                }
                botonSiguiente.setEnabled(true);
                if (panelFlop.elJugadorAposto()) {
                    System.out.println("->>>>>>>>>>>>>>>>>>>>>YA NO DEBERIA MOSTRARSE APOSTAR");
                    this.huboApuestaEnFlop = true;
                    this.yaSeMostroBotonApostarFlop = true;
                }
                remove(panelFlop);
                remove(panelFlop);
                if (indiceJugadorFlop < jugadores.size()) {
                    flopVisual(fase);
                } else {
                    System.out.println("Fin de las apuestas del " + fase + ". Total acumulado: " + apuestaTotal.get());
                    if (fase.equals("flop")) {
                        turn();
                    } else if (fase.equals("Turn")) {
                        river();
                    } // podrías agregar river() o showdown luego
                }

            });
            panelFlop.add(botonSiguiente);
            add(panelFlop);
            revalidate();
            repaint();
        } else {
            System.out.println("Fin de las apuestas del flop. Total acumulado: " + apuestaTotal.get());

        }

    }

    public void turn(){
        System.out.println("EL REPARTIDOR ELIMINA LA PRIMERA CARTA EN LA PARTE DE ARRIBA: ");
        eliminarPrimeraCartaDelMazo();
        System.out.println("EL REPARTIDOR REVELA otra CARTA COMUNITARIAS: ");
        cartasComunitarias.addAll(getManoDeNCartas(1));
        for(int i=0; i<cartasComunitarias.size(); i++){
            cartasComunitarias.get(i).imprimirCarta();
        }

        this.indiceJugadorFlop = 0;
        this.huboApuestaEnFlop = false;
        this.yaSeMostroBotonApostarFlop = false;

        flopVisual("Turn");


    }

    public void river(){
        System.out.println("EL REPARTIDOR ELIMINA LA PRIMERA CARTA EN LA PARTE DE ARRIBA: ");
        eliminarPrimeraCartaDelMazo();
        System.out.println("EL REPARTIDOR REVELA otra CARTA COMUNITARIAS: ");
        cartasComunitarias.addAll(getManoDeNCartas(1));
        for(int i=0; i<cartasComunitarias.size(); i++){
            cartasComunitarias.get(i).imprimirCarta();
        }

        this.indiceJugadorFlop = 0;
        this.huboApuestaEnFlop = false;
        this.yaSeMostroBotonApostarFlop = false;

        flopVisual("River");
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
