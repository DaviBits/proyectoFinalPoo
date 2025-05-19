package Logica;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CardDraw extends Poker {

    private ArrayList<JugadorCardDraw> jugadores = new ArrayList<>();
    private int jugadorActual = 0;
    private int apuestaMayor = 0;
    private int apuestaTotal = 0;
    private JLabel jugadorEnPantalla = new JLabel();
    private JLabel textoApuestas = new JLabel("Ronda de Apuestas");
    private Mazo mazo = new Mazo();
    private JLabel labelApuestaMayor = new JLabel();
    private JLabel labelApuestaTotal = new JLabel();
    private boolean segundaRondaDeApuesta = false;
    public CardDraw() {
        inicializarJugadores();
        mostrarInfoApuestas();
    }
    private void mostrarJugadorActual() {
        jugadorEnPantalla.setBounds(300, 0, 300, 20);
        jugadorEnPantalla.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        jugadorEnPantalla.setVisible(true);
        add(jugadorEnPantalla);
    }
    private void mostrarInfoApuestas() {
        labelApuestaMayor.setBounds(600, 10, 200, 20);
        labelApuestaMayor.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        labelApuestaMayor.setText("Apuesta mayor: $" + apuestaMayor);
        add(labelApuestaMayor);

        labelApuestaTotal.setBounds(600, 40, 200, 20);
        labelApuestaTotal.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        labelApuestaTotal.setText("Pozo total: $" + apuestaTotal);
        add(labelApuestaTotal);
    }

    private void actualizarInfoApuestas() {
        labelApuestaMayor.setText("Apuesta mayor: $" + apuestaMayor);
        labelApuestaTotal.setText("Pozo total: $" + apuestaTotal);
    }

    private void actualizarTurno() {
        jugadorEnPantalla.setText("Turno de Jugador: " + (jugadorActual + 1));
    }

    @Override
    public void inicializarJugadores() {
        setLayout(null);

        JLabel labelJugadores = new JLabel("¿Cuántos jugadores desea?");
        labelJugadores.setBounds(10, 10, 300, 20);
        labelJugadores.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        add(labelJugadores);

        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.setBounds(10, 100, 200, 20);
        botonAceptar.setVisible(false);
        add(botonAceptar);

        String[] opciones = {"2 jugadores", "3 jugadores", "4 jugadores", "5 jugadores"};
        JComboBox<String> comboBox = new JComboBox<>(opciones);
        comboBox.setBounds(10, 50, 200, 20);
        comboBox.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        add(comboBox);

        comboBox.addActionListener(e -> {
            switch ((String) comboBox.getSelectedItem()) {
                case "2 jugadores" -> numJugadores = 2;
                case "3 jugadores" -> numJugadores = 3;
                case "4 jugadores" -> numJugadores = 4;
                case "5 jugadores" -> numJugadores = 5;
            }
            botonAceptar.setVisible(true);
        });

        botonAceptar.addActionListener(e -> {
            for (int i = 0; i < numJugadores; i++) {
                jugadores.add(new JugadorCardDraw());
            }

            remove(comboBox);
            remove(botonAceptar);
            remove(labelJugadores);

            repartirCartas();

            revalidate();
            repaint();
        });
    }
    public void repartirCartas() {
        JLabel textoRepartir = new JLabel("¿Repartir cartas?");
        textoRepartir.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        textoRepartir.setBounds(10, 10, 300, 20);
        add(textoRepartir);

        JButton btnRepartir = new JButton("Sí, repartir");
        btnRepartir.setBounds(10, 100, 200, 20);
        add(btnRepartir);

        btnRepartir.addActionListener(e -> {
            int cartasPorJugador = 5;
            int index = 0;

            for (int i = 0; i < cartasPorJugador; i++) {
                for (JugadorCardDraw jugador : jugadores) {
                    jugador.getMano().add(mazo.getCartasMazo().get(index++));
                }
            }

            remove(btnRepartir);
            remove(textoRepartir);

            empezarApuestas();

            revalidate();
            repaint();
        });
    }

    @Override
    public void empezarApuestas() {
        mostrarInfoApuestas();
        remove(textoApuestas);
        textoApuestas.setBounds(10, 10, 300, 20);
        textoApuestas.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        add(textoApuestas);

        mostrarJugadorActual();
        actualizarTurno();

        JButton btnApostar = new JButton("Apostar");
        JButton btnPasar = new JButton("Pasar");

        btnApostar.setBounds(10, 100, 200, 20);
        btnPasar.setBounds(220, 100, 200, 20);

        add(btnApostar);
        add(btnPasar);

        btnApostar.addActionListener(e -> {
            remove(btnApostar);
            remove(btnPasar);
            insertarApuestaMayor();
        });

        btnPasar.addActionListener(e -> {
            // Avanza al siguiente jugador
            jugadorActual++;

            if (jugadorActual >= jugadores.size()) {
                remove(btnApostar);
                remove(btnPasar);
                ocultarMenuApuestas();

                JOptionPane.showMessageDialog(this, "Todos pasaron. Fin de la ronda de apuestas.");
                if(segundaRondaDeApuesta == true){
                    comenzarEnfrentamiento();
                }
                else if(segundaRondaDeApuesta == false) {
                    comenzarDescarte();
                }
                revalidate();
                repaint();
            }else {
                actualizarTurno();
            }
        });
    }

    private void insertarApuestaMayor() {
        removeAll();

        // Regresa todo lo necesario
        mostrarInfoApuestas();
        actualizarInfoApuestas();

        mostrarJugadorActual();
        actualizarTurno();

        add(textoApuestas);

        // --- Componentes específicos de esta pantalla ---
        JLabel label = new JLabel("Jugador " + (jugadorActual + 1) + ", ingrese su apuesta mayor:");
        label.setBounds(10, 50, 400, 25);
        add(label);

        JTextField campo = new JTextField();
        campo.setBounds(10, 80, 200, 25);
        add(campo);

        JButton boton = new JButton("Apostar");
        boton.setBounds(10, 120, 100, 25);
        add(boton);

        boton.addActionListener(e -> {
            try {
                int cantidad = Integer.parseInt(campo.getText().trim());
                if (cantidad <= apuestaMayor) {
                    JOptionPane.showMessageDialog(this, "Debes subir la apuesta.");
                    return;
                }

                apuestaMayor = cantidad;
                apuestaTotal += cantidad;
                actualizarInfoApuestas();
                jugadores.get(jugadorActual).setApuestaHecha(true);

                siguienteJugadorApuesta();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Número inválido.");
            }
        });

        revalidate();
        repaint();
    }


    private void elegirSubirIgualarRetirarse() {
        removeAll();

        mostrarInfoApuestas();
        actualizarInfoApuestas();
        mostrarJugadorActual();
        actualizarTurno();

        add(textoApuestas); // <- importante si lo quieres presente

        JLabel texto = new JLabel("Jugador " + (jugadorActual + 1) + ", decide:");
        texto.setBounds(10, 50, 300, 25);
        add(texto);

        JButton btnSubir = new JButton("Subir");
        JButton btnIgualar = new JButton("Igualar");
        JButton btnRetirarse = new JButton("Retirarse");

        btnSubir.setBounds(10, 100, 100, 25);
        btnIgualar.setBounds(120, 100, 100, 25);
        btnRetirarse.setBounds(230, 100, 100, 25);

        add(btnSubir);
        add(btnIgualar);
        add(btnRetirarse);

        btnSubir.addActionListener(e -> insertarApuestaMayor());

        btnIgualar.addActionListener(e -> {
            jugadores.get(jugadorActual).setApuestaHecha(true);
            apuestaTotal += apuestaMayor;
            actualizarInfoApuestas();
            siguienteJugadorApuesta();
        });

        btnRetirarse.addActionListener(e -> {
            jugadores.remove(jugadorActual);
            if (jugadores.size() <= 1) {
                JOptionPane.showMessageDialog(this, "Solo queda un jugador se acaba el juego.");

            }
            if (jugadorActual >= jugadores.size()) jugadorActual = 0;
            siguienteJugadorApuesta();
        });

        revalidate();
        repaint();
    }

    private void ocultarMenuApuestas() {
        removeAll();
        revalidate();
        repaint();
    }

    private void siguienteJugadorApuesta() {
        int siguiente = obtenerSiguienteJugadorSinApuesta();
        if (siguiente == -1) {
            JOptionPane.showMessageDialog(this, "Fin de la ronda de apuestas.");
            ocultarMenuApuestas();
            if(!segundaRondaDeApuesta) {
                comenzarDescarte();
            }
            else if(segundaRondaDeApuesta) {
                comenzarEnfrentamiento();
            }


        } else {
            jugadorActual = siguiente;
            elegirSubirIgualarRetirarse();
        }
    }

    private int obtenerSiguienteJugadorSinApuesta() {
        for (int i = 0; i < jugadores.size(); i++) {
            int index = (jugadorActual + i + 1) % jugadores.size();
            if (!jugadores.get(index).getApuestaHecha()) {
                return index;
            }
        }
        return -1;
    }

    public void comenzarDescarte() {
        jugadorActual=0;
        menuMostrarCartasDescarte();
    }
    private void descartarYReponerCartas() {
        JugadorCardDraw jugador = jugadores.get(jugadorActual);
        ArrayList<Carta> mano = jugador.getMano();

        for (int i = 0; i < mano.size(); i++) {
            Carta carta = mano.get(i);
            if (carta.getCartaSeleccionada()) {
                mazo.getCartasMazo().add(carta);
                mazo.barajarMazo();
                if (!mazo.getCartasMazo().isEmpty()) {
                    Carta nuevaCarta = mazo.getCartasMazo().remove(0);
                    nuevaCarta.setCartaSeleccionada(false); // Por seguridad

                    // Reemplazar en la misma posición
                    mano.set(i, nuevaCarta);
                }
            }
        }
    }

    public void menuMostrarCartasDescarte() {
        removeAll();

        mostrarInfoApuestas();
        actualizarInfoApuestas();
        mostrarJugadorActual();
        actualizarTurno();

        ArrayList<Carta> mano = jugadores.get(jugadorActual).getMano();

        ArrayList<JButton> botonesCartas = new ArrayList<>();

        JButton btnDescartarCartas = new JButton("Descartar");
        btnDescartarCartas.setBounds(500, 100, 100, 25);
        btnDescartarCartas.setVisible(false);
        add(btnDescartarCartas);

        JButton btnContinuar = new JButton("Continuar");
        btnContinuar.setBounds(600, 100, 100, 25);
        add(btnContinuar);

        btnContinuar.addActionListener(e -> {
            jugadores.get(jugadorActual).setDescarteHecho(true); // Por si acaso, marcar descarte actual

            jugadorActual++;

            if (jugadorActual >= jugadores.size()) {
                boolean todosDescartaron = true;
                for (JugadorCardDraw j : jugadores) {
                    if (!j.getDescarteHecho()) {
                        todosDescartaron = false;
                        break;
                    }
                }

                if (todosDescartaron) {
                    JOptionPane.showMessageDialog(this, "Todos han terminado de descartar. Comienza la ronda final de apuestas.");
                    segundaRondaDeApuesta = true; // Marca como verdadera la seunda ronda de apuestas.
                    // Resetea las apuestas hechas para que vuelvan a apostar
                    for (JugadorCardDraw j : jugadores) {
                        j.setApuestaHecha(false);
                    }
                    jugadorActual = 0;
                    if(apuestaTotal == 0){
                        removeAll();
                        empezarApuestas();

                    }
                    else {
                        removeAll();

                        elegirSubirIgualarRetirarse();
                    }


                }
                else {
                    jugadorActual = 0; // Reiniciar para mostrar a los que no han descartado aún
                    menuMostrarCartasDescarte();
                }
            } else {
                menuMostrarCartasDescarte();
            }
        });


        btnDescartarCartas.addActionListener(e -> {
            descartarYReponerCartas();

            // Asegurar que las nuevas cartas estén boca abajo

            for (int i = 0; i < mano.size(); i++) {
                BufferedImage nuevaImg = mano.get(i).getImagen();
                Image imgEscalada = nuevaImg.getScaledInstance(80, 120, Image.SCALE_SMOOTH);
                botonesCartas.get(i).setIcon(new ImageIcon(imgEscalada));
            }

            JOptionPane.showMessageDialog(this, "Cartas descartadas y reemplazadas.");
            jugadores.get(jugadorActual).setDescarteHecho(true);

            btnDescartarCartas.setVisible(false);
        });

        int x = 10;
        int y = 50;

        for (int i = 0; i < mano.size(); i++) {
            final int index = i;
            Carta carta = mano.get(i);
            BufferedImage img = carta.getImagen();
            Image imgEscalada = img.getScaledInstance(80, 120, Image.SCALE_SMOOTH);
            ImageIcon icono = new ImageIcon(imgEscalada);

            JButton btnCarta = new JButton(icono);
            btnCarta.setBounds(x, y, 80, 120);
            btnCarta.setContentAreaFilled(false);
            btnCarta.setBorderPainted(false);
            btnCarta.setFocusPainted(false);

            btnCarta.addActionListener(e -> {
                Rectangle bounds = btnCarta.getBounds();

                if (!carta.getCartaSeleccionada()) {
                    btnCarta.setBounds(bounds.x, bounds.y - 10, bounds.width, bounds.height);
                    carta.setCartaSeleccionada(true);
                } else {
                    btnCarta.setBounds(bounds.x, bounds.y + 10, bounds.width, bounds.height);
                    carta.setCartaSeleccionada(false);
                }

                if (!jugadores.get(jugadorActual).getDescarteHecho() && jugadores.get(jugadorActual).tieneCartaSeleccionada()) {
                    btnDescartarCartas.setVisible(true);
                } else {
                    btnDescartarCartas.setVisible(false);
                }

                repaint();
            });

            add(btnCarta);
            botonesCartas.add(btnCarta);
            x += 90;
        }

        revalidate();
        repaint();
    }

    public void comenzarEnfrentamiento() {
        JOptionPane.showMessageDialog(this, "ENFRETAMIENTO");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Poker - Five Card Draw");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        CardDraw panel = new CardDraw();
        frame.add(panel);
        frame.setVisible(true);
    }
}
