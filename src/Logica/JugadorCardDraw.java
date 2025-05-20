package Logica;

public class JugadorCardDraw extends Jugador {
    private int fichas = 1000;
    private boolean apuestaHecha = false;
    private boolean descarteHecho = false;
    private int cantidadApostada = 0;

    public JugadorCardDraw() {
        super();
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

    public boolean getApuestaHecha() {
        return apuestaHecha;
    }

    public void setApuestaHecha(boolean apuestaHecha) {
        this.apuestaHecha = apuestaHecha;
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
