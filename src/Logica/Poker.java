package Logica;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class Poker extends JPanel {
    protected Mazo mazo;
    protected ArrayList<Jugador> jugadores;
    protected int numJugadores;
    protected int ronda;
    protected int apuesta;
    protected Image imagenFondo;
    private HashMap<String, Integer> multiplicadoresDeJugadas;

    public Poker(){
        multiplicadoresDeJugadas=new HashMap<>();
        setLayout(null);
        mazo = new Mazo();
        this.numJugadores=0;
        this.ronda=1;
        this.apuesta=0;
        this.jugadores=new ArrayList<Jugador>();
        llenarMultiplicadoresDeJugadas();
    }

    public abstract void inicializarJugadores();
    public abstract void empezarApuestas();

    public ArrayList<Carta> getManoDeNCartas(int n){
        ArrayList<Carta> mano= new ArrayList<>();
        for(int i=0; i<n; i++){
            Carta cartaAgregada=mazo.getCartaEn(mazo.getTamañoDellMazo()-1);
            mano.add(cartaAgregada);
            mazo.eliminarCarta(cartaAgregada);
        }
        return mano;
    }
    public void eliminarPrimeraCartaDelMazo(){
        Carta cartaEliminada=mazo.getCartaEn(mazo.getTamañoDellMazo()-1);
        System.out.println("La carta eliminada es: "+cartaEliminada);
        mazo.eliminarCarta(cartaEliminada);
    }

    public Carta tomarCarta(){
        Carta cartaParaTomar= mazo.getCartaEn(mazo.getTamañoDellMazo()-1);
        mazo.eliminarCarta(cartaParaTomar);
        return cartaParaTomar;
    }

    public void llenarMultiplicadoresDeJugadas(){
        multiplicadoresDeJugadas.put("cartaAlta", 1);
        multiplicadoresDeJugadas.put("par",2);
        multiplicadoresDeJugadas.put("doblePar", 3);
        multiplicadoresDeJugadas.put("tercia", 4);
        multiplicadoresDeJugadas.put("escalera", 5);
        multiplicadoresDeJugadas.put("color", 6);
        multiplicadoresDeJugadas.put("fullHouse", 7);
        multiplicadoresDeJugadas.put("Poker", 8);
        multiplicadoresDeJugadas.put("escaleraDeColor", 9);
        multiplicadoresDeJugadas.put("EscaleraReal", 10);
    }

    public int getMultiplicador(String jugada) {
        Iterator<Map.Entry<String, Integer>> iterador = multiplicadoresDeJugadas.entrySet().iterator();

        while (iterador.hasNext()) {
            Map.Entry<String, Integer> entrada = iterador.next();
            if (entrada.getKey().equalsIgnoreCase(jugada)) {
                return entrada.getValue();
            }
        }
        return 0;
    }


    public void mezclarMazo(){
        mazo.barajarMazo();
    }
}
