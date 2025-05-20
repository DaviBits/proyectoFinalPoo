package Logica;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Carta implements Comparable <Carta>{
    private static final BufferedImage imagenReverso = cargarImagenReverso();
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

    public String getPalo() {
        return palo;
    }

    public void imprimirCarta() {
        System.out.println("Carta: " + valor + " de " + palo + " (" + color + ")");
    }

    public BufferedImage getImagen() {
        return imagen;  // Siempre la imagen frontal
    }
    public int getValor(){return valor;}
    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
    }

    private static BufferedImage cargarImagenReverso() {
        try {
            return ImageIO.read(new File("cartaDeEspalda.png"));
        } catch (IOException e) {
            System.err.println("No se pudo cargar la imagen de reverso.");
            return null;
        }
    }
    public BufferedImage getCartaReverso(){
        return imagenReverso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Carta carta = (Carta) o;

        return valor == carta.valor &&
                palo.equals(carta.palo) &&
                color.equals(carta.color);
    }

    @Override
    public int compareTo(Carta otra) {
        if (this.valor != otra.valor) {
            return Integer.compare(this.valor, otra.valor);
        } else {
            return this.palo.compareTo(otra.palo); // ordena alfabéticamente si tienen mismo valor
        }
    }


}
