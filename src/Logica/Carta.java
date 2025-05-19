package Logica;

import java.awt.image.BufferedImage;

public class Carta {

    private BufferedImage imagen;
    private int valor;
    private String palo;
    private boolean cartaSeleccionada = false;
    private String color;

    public Carta(int valor, String palo, String color, BufferedImage imagen) {
        this.valor = valor;
        this.palo = palo;
        this.color = color;
        this.imagen = imagen;
    }

    public boolean getCartaSeleccionada() {
        return cartaSeleccionada;
    }

    public void setCartaSeleccionada(boolean cartaSeleccionada) {
        this.cartaSeleccionada = cartaSeleccionada;
    }

    public int getValorCarta() {
        return valor;
    }

    public String getPaloCarta() {
        return palo;
    }

    public void imprimirCarta() {
        System.out.println("Carta: " + valor + " de " + palo + " (" + color + ")");
    }

    public BufferedImage getImagen() {
        return imagen;  // Siempre la imagen frontal
    }

    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
    }
}
