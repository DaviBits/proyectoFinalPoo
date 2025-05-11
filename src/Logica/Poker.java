package Logica;

import javax.smartcardio.Card;
import javax.swing.*;
import java.util.ArrayList;

public abstract class Poker extends JPanel {
    Mazo mazo;
    ArrayList<Jugador> Jugadores;

    public class Mazo {

    }

    public abstract void repartirCartas();
    public abstract void empezarApuestas();
    public abstract void comenzarEnfrentamiento();
    public  void jerarquizarManos(){

    }
}
