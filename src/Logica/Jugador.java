package Logica;

import java.util.ArrayList;
import java.util.Comparator;

public class Jugador {
    public  ArrayList<Carta> mano;
    public String nombre;
    int fichas;

    public Jugador(String nombre, ArrayList<Carta> mano, int fichas){
        this.nombre=nombre;
        this.mano=mano;
        this.fichas=fichas;
    }
    public Jugador(){

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
    //ordena la mano del jugador de la menor a la mayor (mayor -> menor)
    public void ordenarCartaPorValor(){
      mano.sort(Comparator.comparingInt(Carta::getValor));
    }
    //ordena la mano por palos según el orden alfabetico, no por el valor jerárquico de los palos
    public void ordenarCartaPorPalo(){
        mano.sort(Comparator.comparing(Carta::getPalo));
    }

}
