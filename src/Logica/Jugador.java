package Logica;

import java.util.ArrayList;

public abstract class Jugador {
    private ArrayList<Carta> mano;
    String nombre;

    public Jugador() {

    }

    public ArrayList<Carta> getMano() {
        return mano;
    }

}
