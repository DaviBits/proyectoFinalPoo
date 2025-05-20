package Logica;

import java.util.*;

public class EvaluadorDeManos {
    private ArrayList<Carta> mano;
    private String jugadaMasAlta;
    public EvaluadorDeManos(ArrayList<Carta> cartas){
        this.mano=cartas;

    }

    public String evaluar(){
        if(esEscaleraReal()) return "escaleraReal";
        if(esEscaleraDeColor()) return "escaleraDeColor";
        if(esPoker()) return "poker";
        if(esFullHouse()) return "fullHouse";
        if(esColor()) return "color";
        if(esEscalera()) return "escalera";
        if(tieneDoblePar()) return "doblePar";
        if(esTercia()) return "tercia";
        if(esPar()) return "par";
        return "cartaAlta";
    }


    public boolean esPar() {
        HashMap<Integer, Integer> conteo = new HashMap<>();
        for (Carta carta : mano) {
            conteo.put(carta.getValor(), conteo.getOrDefault(carta.getValor(), 0) + 1);
        }
        for (int cantidad : conteo.values()) {
            if (cantidad == 2) return true;
        }
        return false;
    }


    public boolean esTercia() {
        HashMap<Integer, Integer> conteo = new HashMap<>();
        for (Carta carta : mano) {
            conteo.put(carta.getValor(), conteo.getOrDefault(carta.getValor(), 0) + 1);
        }
        for (int cantidad : conteo.values()) {
            if (cantidad == 3) return true;
        }
        return false;
    }


    public boolean tieneDoblePar() {
        HashMap<Integer, Integer> conteoValores = new HashMap<>();
        for (Carta carta : mano) {
            int valor = carta.getValor();
            conteoValores.put(valor, conteoValores.getOrDefault(valor, 0) + 1);
        }
        int pares = 0;
        for (int cantidad : conteoValores.values()) {
            if (cantidad == 2) {
                pares++;
            }
        }
        return pares == 2;
    }


    public boolean esEscalera() {
        ArrayList<Integer> valores = new ArrayList<>();
        for (Carta carta : mano) {
            valores.add(carta.getValor());
        }
        Set<Integer> setValores = new HashSet<>(valores);
        if (setValores.size() < 5) return false;

        valores = new ArrayList<>(setValores);
        Collections.sort(valores);

        if (valores.contains(14) && valores.contains(2) &&
                valores.contains(3) && valores.contains(4) &&
                valores.contains(5)) {
            return true;
        }

        for (int i = 0; i < valores.size() - 4; i++) {
            if (valores.get(i + 1) == valores.get(i) + 1 &&
                    valores.get(i + 2) == valores.get(i) + 2 &&
                    valores.get(i + 3) == valores.get(i) + 3 &&
                    valores.get(i + 4) == valores.get(i) + 4) {
                return true;
            }
        }

        return false;
    }


    public boolean esColor(){
        int iguales = 0;
        String palo = mano.get(0).getPalo();
        for (int i = 1; i < mano.size(); i++) {
            if (mano.get(i).getPalo().equals(palo)) {
                iguales++;
            }
        }
        return iguales == 4; // Las otras 4 deben ser iguales al primero
    }


    public boolean esEscaleraReal() {
        if (!esColor()) return false;

        ArrayList<Integer> valores = new ArrayList<>();
        for (Carta carta : mano) {
            valores.add(carta.getValor());
        }
        Collections.sort(valores);

        // Valores específicos para escalera real
        return valores.equals(Arrays.asList(10, 11, 12, 13, 14)); // 10-J-Q-K-A
    }

    public boolean esEscaleraDeColor() {
        return esColor() && esEscalera(); // Ya tienes esColor y luego definimos bien esEscalera
    }

    public boolean esPoker() {
        HashMap<Integer, Integer> conteo = new HashMap<>();
        for (Carta carta : mano) {
            int valor = carta.getValor();
            conteo.put(valor, conteo.getOrDefault(valor, 0) + 1);
        }

        return conteo.containsValue(4);
    }

    public boolean esFullHouse() {
        HashMap<Integer, Integer> conteo = new HashMap<>();
        for (Carta carta : mano) {
            int valor = carta.getValor();
            conteo.put(valor, conteo.getOrDefault(valor, 0) + 1);
        }

        boolean hayTercia = conteo.containsValue(3);
        boolean hayPar = conteo.containsValue(2);

        return hayTercia && hayPar;
    }




}
