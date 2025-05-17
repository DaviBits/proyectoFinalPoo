package Logica;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class Carta {

    private BufferedImage imagen;
    private static final BufferedImage imagenReverso = cargarImagenReverso();

    private int valor;
    private String palo;
    private boolean cartaBocaAbajo = true;

    public Carta(int valor, String palo, BufferedImage imagen) {
        this.valor = valor;
        this.palo = palo;
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

    public int getValorCarta() {
        return valor;
    }

    public String getPaloCarta() {
        return palo;
    }

    public void imprimirCarta() {
        System.out.println(valor + " de " + palo);
    }

    public void setCartaBocaAbajo(boolean cartaBocaAbajo) {
        this.cartaBocaAbajo = cartaBocaAbajo;
    }

    public boolean getCartaBocaAbajo() {
        return cartaBocaAbajo;
    }
    public BufferedImage getCartaReverso(){
        return imagenReverso;
    }
    public BufferedImage getImagen() {
        return cartaBocaAbajo ? imagenReverso : imagen;
    }

    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
    }
}
