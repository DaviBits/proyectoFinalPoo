package Logica;

import java.util.ArrayList;

public class JugadorCardDraw extends Jugador {

    public int fichas = 1000;
    public int apuesta = 0;
    public boolean noAposto;
    public JugadorCardDraw() {
        mano = new ArrayList<>();
    }

    public int apostarFichas(int apuesta) {
        this.fichas -= apuesta;
        this.apuesta = apuesta;
        return apuesta;
    }

    public void setNoAposto(boolean opcion) {
        this.noAposto = opcion;
    }

}
