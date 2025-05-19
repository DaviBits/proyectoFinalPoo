package Logica;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class Jugador {
    public  ArrayList<Carta> mano;
    public String nombre;
    private int fichas;

    public Jugador(String nombre, ArrayList<Carta> mano, int fichas){
        this.nombre=nombre;
        this.mano=mano;
        this.fichas=fichas;
    }

    public Jugador() {

    }

    public void setNombre(String nombre){this.nombre=nombre;}
    public void setMano(ArrayList<Carta> mano){this.mano=mano;}
    public void setFichas(int fichas){this.fichas=fichas;}

    public int getFichas(){return fichas;}
    public void restarFichas(int fichasARestar){this.fichas-=fichasARestar;}
    public void eliminarCarta(Carta cartaEliminada){mano.remove(cartaEliminada);}
    public ArrayList<Carta> getMano() {
        return mano;
    }
    public void ordenarCartaPorValor(){
        mano.sort(Comparator.comparingInt(Carta::getValorCarta));
    }
    public void ordenarCartaPorPalo(){
        mano.sort(Comparator.comparing(Carta::getPaloCarta));
    }
    public boolean tieneCartaSeleccionada() {
        for (Carta carta : mano) {
            if (carta.getCartaSeleccionada()) {
                return true;
            }
        }
        return false;
    }


}
