package Logica;

import java.util.ArrayList;

public class JugadorCardDraw {

    public int fichas = 1000;
    public int apuesta = 0;
    public boolean noAposto;
    private ArrayList<Carta> mano = new ArrayList<>();
    public JugadorCardDraw() {
        mano = new ArrayList<>();
    }

    public ArrayList<Carta> getMano(){return mano;}


    public int apostarFichas(int apuesta) {
        this.fichas -= apuesta;
        this.apuesta = apuesta;
        return apuesta;
    }

    public void setNoAposto(boolean opcion) {
        this.noAposto = opcion;
    }

}