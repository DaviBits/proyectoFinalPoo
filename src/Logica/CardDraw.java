package Logica;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CardDraw extends Poker {

    private ArrayList<JugadorCardDraw> jugadores = new ArrayList<>();
    public int jugadorActual = 0;
    public int apuestaMayor = 0;
    public JLabel turnoActual = new JLabel();
    boolean apuestasTerminadas = false;
    private boolean mostrarCartas = false;
    private JButton btnCheck;
    private JButton btnBet;
    private ArrayList<JButton> botonesCartas = new ArrayList<>();

    public CardDraw() {
        Mazo mazo = new Mazo();
        inicializarJugadores(); 

        setLayout(null);

        // Configuración del JLabel para mostrar el turno
        turnoActual.setBounds(10, 400, 200, 20);
        turnoActual.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        turnoActual.setVisible(false); // No visible hasta que inicie algo
        add(turnoActual);
    }
    private void mostrarCartasComoBotones(int indiceJugador) {
        // Eliminar botones anteriores
        for (JButton btn : botonesCartas) {
            remove(btn);
        }
        botonesCartas.clear();

        ArrayList<Carta> mano = jugadores.get(indiceJugador).getMano();
        int x = 244;
        int y = 240;
        int espacio = 60;

        for (int i = 0; i < mano.size(); i++) {
            Carta carta = mano.get(i);

            BufferedImage imagenCarta;
            if (carta.getCartaBocaAbajo()) {
                try {
                    imagenCarta = javax.imageio.ImageIO.read(new java.io.File("cartaDeEspalda.png"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    continue; // no mostramos la carta si falla la imagen
                }
            } else {
                imagenCarta = carta.getImagen();
            }

            Image imgEscalada = imagenCarta.getScaledInstance(100, 140, Image.SCALE_SMOOTH);
            JButton btnCarta = new JButton(new ImageIcon(imgEscalada));
            btnCarta.setBounds(x + i * espacio, y, 100, 140);
            btnCarta.setBorder(null);
            btnCarta.setContentAreaFilled(false);

            if (!carta.getCartaBocaAbajo()) {
                btnCarta.addActionListener(e -> {
                    System.out.println("Carta seleccionada: " + carta.getValorCarta() + " de " + carta.getPalo());
                    // lógica adicional si se requiere
                });
            } else {
                btnCarta.setEnabled(false); // opcional: no clickeable si está boca abajo
            }

            botonesCartas.add(btnCarta);
            add(btnCarta);
        }

        revalidate();
        repaint();
    }


    @Override
    public void inicializarJugadores() {
        setLayout(null);
        JLabel labelJugadores = new JLabel("Cuantos jugadores desea?");
        labelJugadores.setBounds(10, 10, 200, 20);
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
            String seleccion = (String) comboBox.getSelectedItem();
            switch (seleccion) {
                case "2 jugadores":
                    numJugadores = 2;
                    break;
                case "3 jugadores":
                    numJugadores = 3;
                    break;
                case "4 jugadores":
                    numJugadores = 4;
                    break;
                case "5 jugadores":
                    numJugadores = 5;
                    break;
                default:
                    numJugadores = 0;
            }
            botonAceptar.setVisible(true);
            repaint();
        });

        botonAceptar.addActionListener(e -> {
            // Crear jugadores
            for (int i = 0; i < numJugadores; i++) {
                jugadores.add(new JugadorCardDraw());
            }

            botonAceptar.setVisible(false);
            comboBox.setVisible(false);
            labelJugadores.setVisible(false);

            repartirCartas();

            revalidate();
            repaint();
        });
    }


    public void repartirCartas() {
        JLabel textoRepartirCartas = new JLabel("Repartir Cartas?");
        textoRepartirCartas.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        textoRepartirCartas.setBounds(10, 10, 300, 20);
        add(textoRepartirCartas);

        JButton btnRepartirCartas = new JButton("Si, repartir Cartas");
        btnRepartirCartas.setBounds(10, 100, 200, 20);
        add(btnRepartirCartas);

        btnRepartirCartas.addActionListener(e -> {
            int cartasPorJugador = 5;
            int indiceCarta = 0;

            for (int i = 0; i < cartasPorJugador; i++) {
                for (int j = 0; j < numJugadores; j++) {
                   Carta carta = tomarCarta();
                   jugadores.get(j).getMano().add(carta);
                }
            }
            btnRepartirCartas.setVisible(false);
            textoRepartirCartas.setVisible(false);

            empezarApuestas();

            revalidate();
            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 16));

    }

    public void mostrarCartasJugadorActual() {
        mostrarCartas = true;
        mostrarCartasComoBotones(jugadorActual);
    }

    // AVANZAR JUGADOR: cambia jugadorActual, actualiza el texto y repinta
    private void avanzarJugador() {
        jugadorActual++;
        if (jugadorActual >= jugadores.size()) {
            jugadorActual = 0;
        }
        mostrarTurnoActual();
        repaint();
    }

    private void mostrarTurnoActual() {
        turnoActual.setText("Turno Actual: " + (jugadorActual + 1));
        turnoActual.setVisible(true);
    }

    @Override
    public void empezarApuestas() {
        mostrarTurnoActual();

        JLabel textoApuestas = new JLabel("Ronda de apuestas");
        textoApuestas.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        textoApuestas.setBounds(10, 10, 300, 20);
        add(textoApuestas);

        btnCheck = new JButton("CHECK");
        btnCheck.setBounds(200, 10, 200, 20);
        btnCheck.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        add(btnCheck);

        btnBet = new JButton("BET");
        btnBet.setBounds(200, 30, 200, 20);
        btnBet.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        add(btnBet);

        btnCheck.addActionListener(e -> {
            jugadores.get(jugadorActual).setNoAposto(true);
            avanzarJugador();

            if (saberSiNadieAposto()) {
                apuestasTerminadas = true;

                btnCheck.setVisible(false);
                btnBet.setVisible(false);
                turnoActual.setVisible(false);
                textoApuestas.setVisible(false);


                revalidate();
                repaint();

                comenzarDescarte();
            }
        });

        btnBet.addActionListener(e -> {
            btnCheck.setVisible(false); // Si apostó, ya no puede hacer check

            JTextField campoNumero = new JTextField();
            campoNumero.setBounds(10, 160, 100, 25);
            add(campoNumero);

            JButton btnLeerApuesta = new JButton("Aceptar");
            btnLeerApuesta.setBounds(120, 160, 100, 25);
            add(btnLeerApuesta);

            btnLeerApuesta.addActionListener(ev -> {
                try {
                    int numero = Integer.parseInt(campoNumero.getText());
                    apuestaMayor = numero;
                    jugadores.get(jugadorActual).setNoAposto(false);

                    avanzarJugador();

                    campoNumero.setVisible(false);
                    btnLeerApuesta.setVisible(false);

                    revalidate();
                    repaint();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Por favor ingresa un número válido.");
                }
            });

            repaint();
        });
    }

    public boolean saberSiNadieAposto() {
        for (JugadorCardDraw jugador : jugadores) {
            if (!jugador.noAposto) {
                return false; // Alguien sí apostó
            }
        }
        return true; // Todos tienen noAposto = true
    }

    public void comenzarDescarte() {
        mostrarTurnoActual();
        menuVerMano();
        mostrarCartasJugadorActual();
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Ejemplo de JPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 480);
        CardDraw panel = new CardDraw();
        frame.add(panel);
        frame.setVisible(true);
    }

    public void menuVerMano() {
        turnoActual.setVisible(true);
        mostrarTurnoActual();

        JButton voltearCartas = new JButton("Voltear Cartas");
        voltearCartas.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        voltearCartas.setBounds(10, 100, 200, 20);
        add(voltearCartas);

        voltearCartas.addActionListener(e -> {
            ArrayList<Carta> mano = jugadores.get(jugadorActual).getMano();
            if (mano.isEmpty()) return;

            boolean actualmenteBocaAbajo = mano.get(0).getCartaBocaAbajo();

            for (Carta carta : mano) {
                carta.setCartaBocaAbajo(!actualmenteBocaAbajo);
            }

            mostrarCartasComoBotones(jugadorActual); // actualizar la vista
        });


       /* JButton izquierda = new JButton("<--");
        izquierda.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        izquierda.setBounds(0, 300, 100, 20);
        add(izquierda);
        izquierda.addActionListener(e -> {
            jugadorActual--;
            if (jugadorActual < 0) {
                jugadorActual = jugadores.size() - 1;
            }
            mostrarTurnoActual();
            mostrarCartasComoBotones(jugadorActual);
            repaint();
        });*/

        JButton derecha = new JButton("Jugar Mano");
        derecha.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        derecha.setBounds(600, 300, 100, 20);
        add(derecha);

        derecha.addActionListener(e -> {
            jugadorActual++;

            if (jugadorActual >= jugadores.size()) {
                // ✅ Todos jugaron su mano

                // 🧹 Ocultar y limpiar botones de cartas
                for (JButton btn : botonesCartas) {
                    btn.setVisible(false);
                    remove(btn);
                }
                botonesCartas.clear();

                voltearCartas.setVisible(false);
                remove(voltearCartas);
                derecha.setVisible(false);
                remove(derecha);

                // 🕹️ Reiniciar al primer jugador
                jugadorActual = 0;

                // 💰 Iniciar nueva ronda de apuestas
                empezarApuestas();
            } else {
                // 🔁 Todavía hay jugadores que no han jugado

                mostrarTurnoActual();
                mostrarCartasComoBotones(jugadorActual);
            }

            revalidate();
            repaint();
        });


    }
}
