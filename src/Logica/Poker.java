package Logica;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Poker extends JPanel {
    protected Mazo mazo;
    protected ArrayList<Jugador> jugadores;
    protected int numJugadores;
    protected int ronda;
    protected int apuesta;

    public Poker(){
        setLayout(null);
        mazo = new Mazo();
        this.numJugadores=0;
        this.ronda=1;
        this.apuesta=0;
        this.jugadores=new ArrayList<Jugador>();
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

    public Carta tomarCarta(){
        Carta cartaParaTomar= mazo.getCartaEn(mazo.getTamañoDellMazo()-1);
        mazo.eliminarCarta(cartaParaTomar);
        return cartaParaTomar;
    }

    public void mezclarMazo(){
        mazo.barajarMazo();
    }
}
