package Logica;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public abstract class Poker extends JPanel {
    protected Mazo mazo;
    protected ArrayList<Jugador> Jugadores;
    protected int numJugadores;
    protected int ronda;
    protected int apuesta;
    private HashMap <String, Integer> multiplicadoresDeJugadas;



    public Poker(){
        mazo = new Mazo();
        this.numJugadores=0;
        this.ronda=1;
        this.apuesta=0;
        multiplicadoresDeJugadas= new HashMap<>();
        llenarMultiplicadoresDeJugadas();
    }

    public abstract void inicializarJugadores();
    public abstract void empezarApuestas();
    public abstract void comenzarEnfrentamiento();

    public Carta tomarCarta(){
        Carta cartaParaTomar= mazo.getCartaEn(mazo.getTamañoDellMazo()-1);
        mazo.eliminarCarta(cartaParaTomar);
        return cartaParaTomar;
    }

    public void mezclarMazo(){
        mazo.barajarMazo();
    }

    public void llenarMultiplicadoresDeJugadas(){
        multiplicadoresDeJugadas.put("cartaAlta", 1);
        multiplicadoresDeJugadas.put("par",2);
        multiplicadoresDeJugadas.put("doblePar", 3);
        multiplicadoresDeJugadas.put("tercia", 4);
        multiplicadoresDeJugadas.put("escalera", 5);
        multiplicadoresDeJugadas.put("color", 6);
        multiplicadoresDeJugadas.put("fullHouse", 7);
        multiplicadoresDeJugadas.put("Póquer", 8);
        multiplicadoresDeJugadas.put("escaleraDeColor", 9);
        multiplicadoresDeJugadas.put("EscaleraReal", 10);
    }

    public void determinarJugada(ArrayList<Carta> manoDelJugador){

    }

}
