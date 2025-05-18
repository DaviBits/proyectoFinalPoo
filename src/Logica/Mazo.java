package Logica;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;

public class Mazo {

    String[] palos = { "Corazones", "Treboles", "Diamantes", "Picas" }; // Orden del sprite
    ArrayList<Carta> cartasMazo;
    BufferedImage imagenCartas;

    public Mazo() {
        try {
            imagenCartas = ImageIO.read(new File("Cartas.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        generarMazo();
        barajarMazo();
        imprimirMazo();
    }
    public void generarMazo() {
        cartasMazo = new ArrayList<>();
        for (int i = 0; i < palos.length; i++) {
            for (int j = 0; j < 13; j++) {
                int x = j * 71;
                int y = i * 95;

                BufferedImage sprite = imagenCartas.getSubimage(x, y, 71, 95);

                int valorReal = j + 2;
                if (valorReal > 13) valorReal = 1; // A es valor 1

                cartasMazo.add(new Carta(valorReal, palos[i], sprite));
            }
        }
    }


    public void imprimirMazo() {
        for (int i = 0; i < cartasMazo.size(); i++) {
            cartasMazo.get(i).imprimirCarta();
        }
    }
    public void barajarMazo() {
        Collections.shuffle(cartasMazo);
    }

    public static void main(String[] args) {
        Mazo mazo = new Mazo();
        mazo.barajarMazo();
        mazo.imprimirMazo();
    }
    public ArrayList<Carta> getCartasMazo() {
        return cartasMazo;
    }

}
