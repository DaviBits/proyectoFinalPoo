package Logica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CardDraw extends Poker {

    private int numeroJugadores = 0;
    private ArrayList<JugadorCardDraw> jugadoresCardDraw;



    public CardDraw() {
        jugadoresCardDraw = new ArrayList<>();
        elegirCantidadJugadores();


    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void elegirCantidadJugadores() {
        setLayout(null);

        JLabel label = new JLabel("CUANTOS JUGADORES DESEA ELEGIR?");
        label.setBounds(50, 20, 500, 30);
        label.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        add(label);

        String[] opciones = { "2 jugadores", "3 jugadores", "4 jugadores" };
        JComboBox<String> comboBox = new JComboBox<>(opciones);
        comboBox.setBounds(50, 60, 200, 30);
        comboBox.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        add(comboBox);


        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(50, 100, 100, 30);
        btnAceptar.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        btnAceptar.setVisible(false);
        add(btnAceptar);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String eleccion = (String) comboBox.getSelectedItem();
                switch (eleccion) {
                    case "2 jugadores":
                        numeroJugadores = 2;
                        break;
                    case "3 jugadores":
                        numeroJugadores = 3;
                        break;
                    case "4 jugadores":
                        numeroJugadores = 4;
                        break;
                    default:
                        numeroJugadores = 0;
                        break;
                }

                btnAceptar.setVisible(true);

            }
        });
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < numeroJugadores; i++) {
                    jugadoresCardDraw.add(new JugadorCardDraw());
                }
                JOptionPane.showMessageDialog(null, "Jugadores creados: " +numeroJugadores);
                label.setVisible(false);
                btnAceptar.setVisible(false);
                comboBox.setVisible(false);
                remove(label);
                remove(btnAceptar);
                remove(comboBox);
                iniciarJuego();
            }
        });
    }
    @Override
    public void repartirCartas() {
    JButton btnAceptarReparto = new JButton("Aceptar Cartas");
    btnAceptarReparto.setBounds(50, 100, 200, 30);
    btnAceptarReparto.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        btnAceptarReparto.setVisible(true);
        add(btnAceptarReparto);

    JLabel label = new JLabel("Repartiendo cartas uwu");
        label.setBounds(50, 20, 500, 30);
        label.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        label.setVisible(true);
        add(label);

        btnAceptarReparto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                remove(label);
                remove(btnAceptarReparto);
                label.setVisible(false);
                btnAceptarReparto.setVisible(false);

                revalidate();
                repaint();
                empezarApuestas();
            }
        });


        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < numeroJugadores; j++) {
                jugadoresCardDraw.get(j).getMano().add(mazo.cartasMazo.get(0));
                mazo.cartasMazo.remove(0);
            }
        }

    }


@Override
    public void empezarApuestas() {
        JLabel label = new JLabel("Empezando Apuestas");
        label.setBounds(50, 20, 500, 30);
        label.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        label.setVisible(true);
        add(label);

        JPanel panelApuestas = new JPanel();
        panelApuestas.setLayout(new GridLayout(jugadoresCardDraw.size(), 2, 10, 10));
        panelApuestas.setBounds(50, 60, 600, 300);
        add(panelApuestas);

        for (int i = 0; i < jugadoresCardDraw.size(); i++) {
            JugadorCardDraw jugador = jugadoresCardDraw.get(i);

            JLabel jugadorLabel = new JLabel("Jugador " + (i + 1) + " - Saldo: " + jugador.getSaldo() + " fichas");
            panelApuestas.add(jugadorLabel);

            JTextField apuestaField = new JTextField();
            apuestaField.setColumns(10); // Tamaño del campo de texto
            panelApuestas.add(apuestaField);


            JButton btnApostar = new JButton("Apostar");
            panelApuestas.add(btnApostar);

            int finalI = i;
            btnApostar.addActionListener(e -> {
                String apuestaStr = apuestaField.getText();

                if (!apuestaStr.isEmpty()) {
                    try {
                        int apuesta = Integer.parseInt(apuestaStr);

                        if (apuesta > jugador.getSaldo()) {
                            JOptionPane.showMessageDialog(this,
                                    "No puedes apostar más de lo que tienes. Tu saldo es " + jugador.getSaldo(),
                                    "Error en la apuesta", JOptionPane.ERROR_MESSAGE);
                        } else if (apuesta < 0) {
                            JOptionPane.showMessageDialog(this,
                                    "La apuesta no puede ser negativa.", "Error en la apuesta", JOptionPane.ERROR_MESSAGE);
                        } else {
                            jugador.setApuesta(apuesta);
                            apuestaField.setEditable(false);
                            btnApostar.setEnabled(false); 
                            JOptionPane.showMessageDialog(this,
                                    "Jugador " + (finalI + 1) + " apostó: " + apuesta, "Apuesta registrada",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this,
                                "Por favor, ingresa un número válido.", "Error de entrada",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }

        // Finalmente, hacer visible el panel
        revalidate();
        repaint();
    }

    @Override
    public void comenzarEnfrentamiento() {
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("FAIB CART DRO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 480);
        frame.add(new CardDraw());
        frame.setVisible(true);
    }


    private void iniciarJuego() {
        repartirCartas();

    }

}
