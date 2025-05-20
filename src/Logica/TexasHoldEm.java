package Logica;

import GUI.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static javax.management.Query.or;

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
    private int indiceJugadorShowdown;


    public TexasHoldEm(){
        super();
        setLayout(null);
        indiceJugadorShowdown=0;
        this.indiceJugadorFlop=0;
        this.huboApuestaEnFlop=false;
        cartasComunitarias= new ArrayList<>();
        this.jugadorActualIndex=0;
        this.indiceJugadorEnPreFlop=0;
        inicializarJugadores();
    }
    //sirve para leer la cantidad de jugadores (2-10) a traves de la GUI
    //tambien crea las instancias de los jugadores y los mete a un ArrayList
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

    //método equivalente a la etapa de "las ciegas",
    //el jugador 1, el que se registra primero será la ciega chica
    //el jugador que le sigue en el arrayList sera  la ciega grande
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
    //como su nombre lo dice, solo le reparte sus cartas volteadas a los jugadores
    //además maneja el flujo en que se muestren
    public void reparto() {
        apuesta = apuestaTotal.get();
        System.out.println("apuesta final: " + apuesta);
        System.out.println("FASE DE REPARTO");

        for (Jugador jugador : jugadores) {
            jugador.getMano().addAll(getManoDeNCartas(2));
            System.out.println("CARTAS DE: " + jugador.getNombre());
            jugador.mostrarCartas();
        }


        pantallaDeReparto = new PantallaDeReparto(jugadores.get(jugadorActualIndex));
        pantallaDeReparto.setBounds(0, 0, 1000, 550);
        add(pantallaDeReparto);

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

    //este método modela al preflop, usa un índice para manejar que
    //la ciega chica y grande no comiencen aquí
    public void preFlop(){
        apuestaAIgualarIndividual=ciegaGrande;
        System.out.println("pre flop");
        this.indiceJugadorEnPreFlop=2;
        mostrarTurnoPreFlop();
    }
    //este método es la parte grafica del preflop, muestra las
    //acciones que puede realizar por medio de la GUI
    //por asi decirlo, el método"pre flop" son los preparativos de este
    private void mostrarTurnoPreFlop() {
        if (panelPreFlop != null) {
            remove(panelPreFlop);
        }

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
                remove(panelPreFlop);
                mostrarTurnoPreFlop();
            });

            panelPreFlop.add(botonSiguiente);
            add(panelPreFlop);
            revalidate();
            repaint();
        } else {
            System.out.println("Fin de las apuestas del pre-flop. Total acumulado: " + apuestaTotal.get());
            flop();
        }
    }

    //método que modela la etapa del flop, este método es de suma
    //importancia, ya que maneja la lógica de apuestas que se usa casi
    //todas las rondas y se reusa varias veces
    public void flop(){
        System.out.println("EL REPARTIDOR ELIMINA LA PRIMERA CARTA EN LA PARTE DE ARRIBA: ");
        eliminarPrimeraCartaDelMazo();
        System.out.println("EL REPARTIDOR REVELA TRES CARTAS COMUNITARIAS: ");
        cartasComunitarias.addAll(getManoDeNCartas(3));
        for(int i=0; i<cartasComunitarias.size(); i++){
            cartasComunitarias.get(i).imprimirCarta();
        }
        mostrarCartasComunitariasConDelay(() -> flopVisual("flop"));
    }
    // este métodoes el complemento visual del método del flop
    //este método es el que si se recicla varias veces puesto que en las rondas
    //de apuestas se manejan las mismas opciones
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
            botonSiguiente.setEnabled(false);
            panelFlop.setBotonSiguienteJugador(botonSiguiente);

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

                    this.apuestaAIgualarIndividual += panelFlop.getSubida();
                    this.indiceJugadorFlop = 0;
                } else {
                    this.indiceJugadorFlop++;
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
                    } else if (fase.equals("turn")) {
                        river();
                    } else if (fase.equals("river")){
                        enfrentamiento();
                    }
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
    //preparativos lógicos para poder reutilizar el flop para la fase del turn
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
        //llamada a un método que va mostrando las cartas comunitarias
        //y posteriormente llama al turn para volver a apostar
        mostrarCartasComunitariasConDelay(() -> flopVisual("turn"));


    }

    //de nuevo, este método hace como preparativo para la siguiente ronda de apuestas
    //llama a mostrar las cartas comunitarias y ahora sí, muestra la última fase
    //de apuestas
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
        mostrarCartasComunitariasConDelay(() -> flopVisual("river"));
    }

    //en esta fase por cada jugador que siga en juego, se les permite
    //armar su mano con sus cartas y las cartas comunitarias
    //posteriormente se procesan y comparan resultados de cada quien

    public void enfrentamiento() {
        System.out.println("BATALLA FINAL");

        while (indiceJugadorShowdown < jugadores.size()) {
            Jugador jugador = jugadores.get(indiceJugadorShowdown);
            if (jugador.haAbandonado()) {
                indiceJugadorShowdown++;
                continue;
            }

            removeAll();
            JLabel nombre = new JLabel("Jugador: " + jugador.getNombre());
            nombre.setBounds(50, 20, 300, 30);
            nombre.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
            add(nombre);

            JButton aceptarJugada = new JButton("Aceptar");
            aceptarJugada.setBounds(400, 600, 150, 30);
            aceptarJugada.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
            aceptarJugada.setEnabled(false);
            add(aceptarJugada);

            ArrayList<Carta> manoTotal = new ArrayList<>(jugador.getMano());
            manoTotal.addAll(cartasComunitarias);


            ArrayList<Carta> seleccionadas = new ArrayList<>();
            int x = 50, y = 100;

            for (Carta carta : manoTotal) {
                JButton botonCarta = new JButton(new ImageIcon(carta.getImagen()));
                botonCarta.setBounds(x, y, 80, 120);
                botonCarta.setBackground(Color.WHITE);

                botonCarta.addActionListener(e -> {
                    if (seleccionadas.contains(carta)) {
                        seleccionadas.remove(carta);
                        botonCarta.setBorder(null);
                    } else {
                        if (seleccionadas.size() < 5) {
                            seleccionadas.add(carta);
                            botonCarta.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                        }
                    }
                    aceptarJugada.setEnabled(seleccionadas.size() <= 5);
                });

                add(botonCarta);
                x += 90;
            }
            aceptarJugada.addActionListener(e -> {
                int suma = seleccionadas.stream()
                        .mapToInt(carta -> carta.getValor())
                        .sum();

                String jugada= new EvaluadorDeManos(seleccionadas).evaluar();
                int puntajeTotal=suma*getMultiplicador(jugada);
                jugador.setPuntuacionFinal(puntajeTotal);
                jugador.setJugadaFinal(jugada);
                System.out.println("la jugada puesta fue: "+jugador.getJugadaFinal());
                System.out.println("Puntos del jugador: "+jugador.getPuntuacionFinal());
                indiceJugadorShowdown++;
                enfrentamiento();
            });

            revalidate();
            repaint();
            return;
        }
        AnunciarGanador();

    }
    //esta ya no modela como tal ninguna clase del juego, pero es importante
    //para dar fin, solo busca el mayor puntaje y muestra a quien pertenece
    public void AnunciarGanador(){
        removeAll();
        int puntuacionMasAlta=0;
        String nombreJugadorMasAlto="";
        for(Jugador jugador: jugadores){
            if(jugador.getPuntuacionFinal()>puntuacionMasAlta){
                nombreJugadorMasAlto=jugador.getNombre();
                puntuacionMasAlta=jugador.getPuntuacionFinal();
            }
        }
        JLabel anuncioGanador=new JLabel("EL GANADOR ES: "+ nombreJugadorMasAlto);
        anuncioGanador.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        anuncioGanador.setBounds(150, 200, 800, 60);
        add(anuncioGanador);
        revalidate();
        repaint();



    }


    public void mostrarCartasComunitariasConDelay(Runnable siguienteFase) {
        MostradorCartasComunitarias mostrador = new MostradorCartasComunitarias(cartasComunitarias);
        mostrador.setBounds(0, 0, 1000, 550);
        add(mostrador);
        revalidate();
        repaint();

        // Esperar 3 segundos antes de pasar al flopVisual
        Timer timer = new Timer(3000, e -> {
            remove(mostrador);
            revalidate();
            repaint();
            siguienteFase.run();
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }

}