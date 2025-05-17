package Logica;

import java.util.ArrayList;

public abstract class Jugador {
    public  ArrayList<Carta> mano;
    public String nombre;

    public ArrayList<Carta> getMano() {
        return mano;
    }

}
