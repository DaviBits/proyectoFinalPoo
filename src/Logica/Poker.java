package Logica;

import javax.swing.*;
import java.util.ArrayList;

public abstract class Poker extends JPanel {
    protected Mazo mazo = new Mazo(); // Usando la clase correcta
    protected ArrayList<Jugador> Jugadores;
    public int numJugadores = 0;
    public int ronda = 1;

    public abstract void inicializarJugadores();
    public abstract void repartirCartas();
    public abstract void empezarApuestas();
    public abstract void comenzarEnfrentamiento();

    public void jerarquizarManos() {
        // lógica para comparar manos, si aplica
    }
}
