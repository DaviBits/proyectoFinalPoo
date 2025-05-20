package Logica;

import java.util.ArrayList;
import java.util.Comparator;

public class Jugador {
    private  ArrayList<Carta> mano;
    private String nombre;
    private int fichas;
    private boolean enJuego;
    private int fichasApostadas;

    public Jugador(String nombre, ArrayList<Carta> mano, int fichas){
        this.nombre=nombre;
        this.mano=mano;
        this.fichas=fichas;
        this.enJuego=true;
        this.fichasApostadas=0;
    }
    public Jugador(){

    }

    public boolean haAbandonado(){return !enJuego;}
    public int getFichasApostadas(){return fichasApostadas;}
    public void sumarFichasApostadas(int n){this.fichasApostadas+=n;}
    public void abandonarJuego(){this.enJuego=false;}
    public void setNombre(String nombre){this.nombre=nombre;}
    public void setMano(ArrayList<Carta> mano){this.mano=mano;}
    public void setFichas(int fichas){this.fichas=fichas;}
    public String getNombre(){return nombre;}

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

    public void mostrarCartas(){
        for(int i=0; i< mano.size(); i++){
            System.out.println(mano.get(i));
        }
    }

}
