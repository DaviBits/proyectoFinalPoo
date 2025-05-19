package Logica;

import java.util.ArrayList;

public class JugadorCardDraw extends Jugador {

    public int fichas = 1000;
    public int apuesta = 0;
    public boolean apuestaHecha = false;
    private boolean pasoPrimeraRonda = false; // NUEVO
    private boolean descarteHecho = false;
    public JugadorCardDraw() {
        super();

        mano = new ArrayList<>();
    }

    public int apostarFichas(int apuesta) {
        this.fichas -= apuesta;
        this.apuesta = apuesta;
        return apuesta;
    }
    public boolean tieneCartaSeleccionada() {
        for (Carta carta : mano) {
            if (carta.getCartaSeleccionada()) {
                return true;
            }
        }
        return false;
    }

    public boolean getApuestaHecha() {
        return apuestaHecha;
    }
    public void setApuestaHecha(boolean opcion) {
        this.apuestaHecha = opcion;
    }
    public boolean getPasoPrimeraRonda() {
        return pasoPrimeraRonda;
    }

    public void setPasoPrimeraRonda(boolean paso) {
        this.pasoPrimeraRonda = paso;
    }
    public boolean getDescarteHecho() {
        return descarteHecho;
    }
    public void setDescarteHecho(boolean descarte) {
        this.descarteHecho = descarte;
    }
}
