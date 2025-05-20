package Logica;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;

public class Mazo {
//esta clase contiene un arreglo de cartas, simulando una baraja o mazo.
    
    private String[] palos = { "Corazones", "Treboles", "Diamantes", "Picas" }; // Orden del sprite
    private ArrayList<Carta> cartasMazo;
    private BufferedImage imagenCartas;

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
            String palo = palos[i];
            String color;

            switch (palo) {
                case "Corazones":
                    color = "Rojo";
                    break;
                case "Treboles":
                    color = "Azul";
                    break;
                case "Diamantes":
                    color = "Naranja";
                    break;
                case "Picas":
                    color = "Negro";
                    break;
                default:
                    color = "Desconocido";
            }

            for (int j = 0; j < 13; j++) {
                int x = j * 71;
                int y = i * 95;

                BufferedImage sprite = imagenCartas.getSubimage(x, y, 71, 95);

                int valorReal = j + 2;
                if (valorReal > 13) valorReal = 1;

                cartasMazo.add(new Carta(valorReal, palo, color, sprite));
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

    public ArrayList<Carta> getCartasMazo() {
        return cartasMazo;
    }

    public Carta getCartaEn(int n){return cartasMazo.get(n);}

    public int getTamañoDellMazo(){return cartasMazo.size();}

    public void eliminarCarta(Carta carta){cartasMazo.remove(carta);}

}
