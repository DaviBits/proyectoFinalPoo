package Logica;

public class JugadorCardDraw extends Jugador {
    private int fichas = 1000;
    private int apuesta = 0;
    private boolean apuestaHecha = false;
    private boolean pasoPrimeraRonda = false;
    private boolean descarteHecho = false;
    private int cantidadApostada = 0;

    public JugadorCardDraw() {
        super();
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

    public int getFichas() {
        return fichas;
    }

    public void setFichas(int fichas) {
        this.fichas = fichas;
    }

    public int getApuesta() {
        return apuesta;
    }

    public void setApuesta(int apuesta) {
        this.apuesta = apuesta;
    }

    public boolean getApuestaHecha() {
        return apuestaHecha;
    }

    public void setApuestaHecha(boolean apuestaHecha) {
        this.apuestaHecha = apuestaHecha;
    }

    public boolean getPasoPrimeraRonda() {
        return pasoPrimeraRonda;
    }

    public void setPasoPrimeraRonda(boolean pasoPrimeraRonda) {
        this.pasoPrimeraRonda = pasoPrimeraRonda;
    }

    public boolean getDescarteHecho() {
        return descarteHecho;
    }

    public void setDescarteHecho(boolean descarteHecho) {
        this.descarteHecho = descarteHecho;
    }

    public int getCantidadApostada() {
        return cantidadApostada;
    }

    public void setCantidadApostada(int cantidadApostada) {
        this.cantidadApostada = cantidadApostada;
    }
}
