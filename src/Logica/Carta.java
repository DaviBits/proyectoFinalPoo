package Logica;
import java.awt.image.BufferedImage;


public class Carta {

    BufferedImage imagen;
    int valor;
    String palo;
    boolean cartaBocaAbajo = true;




    public Carta(int valor, String palo , BufferedImage imagen) {
        this.valor = valor;
        this.palo = palo;
        this.imagen = imagen;

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

    public BufferedImage getImagen() {
        return imagen;
    }
}
