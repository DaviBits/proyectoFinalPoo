package Logica;

import java.util.ArrayList;

public class JugadorCardDraw extends Jugador {
    private int apuesta;
    private int saldo;

    public JugadorCardDraw() {

    }


    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getApuesta() {
        return apuesta;
    }

    public void setApuesta(int apuesta) {
        this.apuesta = apuesta;
    }
}
