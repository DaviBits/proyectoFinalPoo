package Logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Jugador {
    protected ArrayList<Carta> mano;
    protected String nombre;
    protected int puntuacionMano;
    private String nombreJugada = "Carta Alta"; // Valor por defecto
    private int fichas;
    private boolean enJuego;
    private int fichasApostadas;

    public Jugador() {
        this.mano = new ArrayList<>();
    }
    public void mostrarCartas(){
        for(int i=0; i< mano.size(); i++){
            System.out.println(mano.get(i));
        }
    }
    public Jugador(String nombre, ArrayList<Carta> mano, int fichas){
        this.nombre=nombre;
        this.mano=mano;
        this.fichas=fichas;
        this.enJuego=true;
        this.fichasApostadas=0;
    }


    public boolean haAbandonado(){return !enJuego;}
    public int getFichasApostadas(){return fichasApostadas;}
    public void sumarFichasApostadas(int n){this.fichasApostadas+=n;}
    public void abandonarJuego(){this.enJuego=false;}
    public int getFichas(){return fichas;}
    public void restarFichas(int fichasARestar){this.fichas-=fichasARestar;}


    public String getNombreJugada() {
        return nombreJugada;
    }

    public void setNombreJugada(String nombreJugada) {
        this.nombreJugada = nombreJugada;
    }

    public ArrayList<Carta> getMano() {
        return mano;
    }
    public String getNombre() {
        return nombre;
    }
    public int getPuntuacionMano() {
        return puntuacionMano;
    }

    public void setPuntuacionMano(int puntuacionMano) {
        this.puntuacionMano = puntuacionMano;
    }

    public void evaluarMano() {
        if (mano.size() != 5) {
            nombreJugada = "Mano inválida";
            puntuacionMano = 0;
            return;
        }

        // Ordenar copia de la mano por valor
        ArrayList<Carta> manoOrdenada = new ArrayList<>(mano);
        manoOrdenada.sort(Comparator.comparingInt(Carta::getValor));


        // Dependiendo que cartas posea el jugador
        // elige un nombre y puntaje para el jugador.
        boolean esColor = esColor(manoOrdenada);
        boolean esEscalera = esEscalera(manoOrdenada);
        HashMap<Integer, Integer> frecuencias = contarFrecuencias(manoOrdenada);

        String mejorNombre = "Carta Alta";
        int mejorPuntuacion = 1;

        if (esColor && esEscalera && contieneValores(manoOrdenada, new int[]{10, 11, 12, 13, 14})) {
            mejorNombre = "Escalera Real";
            mejorPuntuacion = 10;
        } else if (esColor && esEscalera) {
            mejorNombre = "Escalera de Color";
            mejorPuntuacion = 9;
        } else if (tieneNDeUnTipo(frecuencias, 4)) {
            mejorNombre = "Póquer";
            mejorPuntuacion = 8;
        } else if (tieneFull(frecuencias)) {
            mejorNombre = "Full";
            mejorPuntuacion = 7;
        } else if (esColor) {
            mejorNombre = "Color";
            mejorPuntuacion = 6;
        } else if (esEscalera) {
            mejorNombre = "Escalera";
            mejorPuntuacion = 5;
        } else if (tieneNDeUnTipo(frecuencias, 3)) {
            mejorNombre = "Trío";
            mejorPuntuacion = 4;
        } else if (tieneDoblePar(frecuencias)) {
            mejorNombre = "Doble Par";
            mejorPuntuacion = 3;
        } else if (tieneNDeUnTipo(frecuencias, 2)) {
            mejorNombre = "Par";
            mejorPuntuacion = 2;
        }
        nombreJugada = mejorNombre;
        puntuacionMano = mejorPuntuacion;
    }

     // Verifica si todas las cartas de la mano tienen el mismo palo.
    private boolean esColor(ArrayList<Carta> mano) {
        String palo = mano.get(0).getPalo().toLowerCase().trim();
        for (Carta c : mano) {
            if (!c.getPalo().toLowerCase().trim().equals(palo)) {
                return false;
            }
        }
        return true;
    }
     //Verifica si la mano forma una escalera.
    private boolean esEscalera(ArrayList<Carta> mano) {
        ArrayList<Integer> valores = new ArrayList<>();
        for (Carta c : mano) {
            valores.add(c.getValor());
        }
        Collections.sort(valores);

        // Comprobar escalera normal (valores consecutivos)
        boolean escaleraNormal = true;
        for (int i = 0; i < valores.size() - 1; i++) {
            if (valores.get(i + 1) != valores.get(i) + 1) {
                escaleraNormal = false;
                break;
            }
        }

        // Comprobar escalera baja (A=14 actuando como 1, secuencia 2-3-4-5-A)
        boolean escaleraBaja = valores.equals(List.of(2, 3, 4, 5, 14));

        return escaleraNormal || escaleraBaja;
    }

    // Verifica si la mano contiene todos los valores indicados.
    private boolean contieneValores(ArrayList<Carta> mano, int[] valores) {
        ArrayList<Integer> valoresMano = new ArrayList<>();
        for (Carta c : mano) {
            valoresMano.add(c.getValor());
        }
        for (int val : valores) {
            if (!valoresMano.contains(val)) return false;
        }
        return true;
    }


    //Cuenta la frecuencia de cada valor en la mano.
    private HashMap<Integer, Integer> contarFrecuencias(ArrayList<Carta> mano) {
        HashMap<Integer, Integer> freq = new HashMap<>();
        for (Carta c : mano) {
            freq.put(c.getValor(), freq.getOrDefault(c.getValor(), 0) + 1);
        }
        return freq;
    }

    //Verifica si hay al menos un valor que se repite exactamente n veces.

    private boolean tieneNDeUnTipo(HashMap<Integer, Integer> freq, int n) {
        for (int count : freq.values()) {
            if (count == n) return true;
        }
        return false;
    }
     //Verifica si la mano tiene un full (un trío y una pareja).
    private boolean tieneFull(HashMap<Integer, Integer> freq) {
        boolean tiene3 = false;
        boolean tiene2 = false;
        for (int count : freq.values()) {
            if (count == 3) tiene3 = true;
            else if (count == 2) tiene2 = true;
        }
        return tiene3 && tiene2;
    }


     // Verifica si la mano tiene dos pares distintos.
    private boolean tieneDoblePar(HashMap<Integer, Integer> freq) {
        int pares = 0;
        for (int count : freq.values()) {
            if (count == 2) pares++;
        }
        return pares == 2;
    }

    public int getPuntuacion() {
        return puntuacionMano;
    }
}
