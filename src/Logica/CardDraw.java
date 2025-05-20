package Logica;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CardDraw extends Poker {

    private ArrayList<JugadorCardDraw> jugadores = new ArrayList<>();//ArrayList que guarda los jugadores.
    private int jugadorActual = 0;
    private int apuestaMayor = 0;   //  Apuesta más grande hecha por un jugador.
    private int apuestaTotal = 0;   //  Apuesta total en el pozo.
    private JLabel jugadorEnPantalla = new JLabel();//  Label que muestra quien posee el turno actual.
    private JLabel textoApuestas = new JLabel("Ronda de Apuestas");
    private Mazo mazo = new Mazo(); //  Inicializa mazo ya que los métodos con hilos lo requieren.
    private JLabel labelApuestaMayor = new JLabel();
    private JLabel labelApuestaTotal = new JLabel();
    private boolean segundaRondaDeApuesta = false; // Bandera que marca si ya se hizo la ronda 2 de apuestas.
    private Image imagenFondo;

    public CardDraw() {
        setLayout(null);
        // Cargar imagen de fondo
        imagenFondo = new ImageIcon("fondoCardDraw.jpg").getImage();

        inicializarJugadores();
        mostrarInfoApuestas();
    }

    //Utiliza paint para cargar el fondo y que nunca se borre.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }



    // Da las propiedades al label jugadorEnPantalla.
    private void mostrarJugadorActual() {
        jugadorEnPantalla.setBounds(300, 0, 300, 20);
        jugadorEnPantalla.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        jugadorEnPantalla.setVisible(true);
        add(jugadorEnPantalla);
    }

    // Da las propiedades a los labels que muestran las apuestas.
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

    // Refresca la información de los labels de apuesta.
    private void actualizarInfoApuestas() {
        labelApuestaMayor.setText("Apuesta mayor: $" + apuestaMayor);
        labelApuestaTotal.setText("Pozo total: $" + apuestaTotal);
    }
    // Refresca la información del label de jugadorEnPantalla.
    private void actualizarTurno() {
        jugadorEnPantalla.setText("Turno de Jugador: " + (jugadorActual + 1));
    }

    // Método abstracto heredado que pregunta por el número de jugadores.
    @Override
    public void inicializarJugadores() {
        // Label que muestra una frase.
        setLayout(null);
        limpiarPanel();
        JLabel labelJugadores = new JLabel("¿Cuántos jugadores desea?");
        labelJugadores.setBounds(10, 10, 300, 20);
        labelJugadores.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        add(labelJugadores);
        //Botón para aceptar el número de jugadores.
        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.setBounds(10, 100, 200, 20);
        botonAceptar.setVisible(false);
        add(botonAceptar);

        String[] opciones = {"2 jugadores", "3 jugadores", "4 jugadores", "5 jugadores", "6 jugadores", "7 jugadores"};
        JComboBox<String> comboBox = new JComboBox<>(opciones);
        comboBox.setBounds(10, 50, 200, 20);
        comboBox.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        add(comboBox);
        // Dependiendo la elección de la comboBox, se decide el número de jugadores.
        comboBox.addActionListener(e -> {
            switch ((String) comboBox.getSelectedItem()) {
                case "2 jugadores" -> numJugadores = 2;
                case "3 jugadores" -> numJugadores = 3;
                case "4 jugadores" -> numJugadores = 4;
                case "5 jugadores" -> numJugadores = 5;
                case "6 jugadores" -> numJugadores = 6;
                case "7 jugadores" -> numJugadores = 7;
            }
            botonAceptar.setVisible(true);
        });
        // Si presioñas el botón se crean jugadores y se agregan al ArrayList.
        botonAceptar.addActionListener(e -> {
            for (int i = 0; i < numJugadores; i++) {
                jugadores.add(new JugadorCardDraw());
            }

            // Limpiar panel después de elegir jugadores.
            limpiarPanel();

            //Reparte cartas
            repartirCartas();

            revalidate();
            repaint();
        });
    }
    public void repartirCartas() {
        // Crear y configurar etiqueta que pregunta si se desea repartir cartas
        JLabel textoRepartir = new JLabel("¿Repartir cartas?");
        textoRepartir.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        textoRepartir.setBounds(10, 10, 300, 20);
        add(textoRepartir);

        // Crear y configurar botón para confirmar repartir cartas
        JButton btnRepartir = new JButton("Sí, repartir");
        btnRepartir.setBounds(10, 40, 200, 30);
        add(btnRepartir);

        // Agregar listener al botón para repartir las cartas cuando se presione
        btnRepartir.addActionListener(e -> {
            int cartasPorJugador = 5;  // Número de cartas a repartir a cada jugador
            int totalCartasNecesarias = cartasPorJugador * jugadores.size();

            // Validar que el mazo tenga suficientes cartas
            if (mazo.getCartasMazo().size() < totalCartasNecesarias) {
                JOptionPane.showMessageDialog(this,
                        "No hay suficientes cartas en el mazo para repartir.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Repartir cartas y removerlas del mazo
            for (int i = 0; i < cartasPorJugador; i++) {
                for (JugadorCardDraw jugador : jugadores) {
                    Carta cartaRepartida = mazo.getCartasMazo().remove(0);
                    jugador.getMano().add(cartaRepartida);
                }
            }

            // Remover los componentes de la interfaz relacionados con repartir cartas
            remove(btnRepartir);
            remove(textoRepartir);

            // Iniciar la ronda de apuestas luego de repartir las cartas
            empezarApuestas();

            // Actualizar la interfaz gráfica para reflejar los cambios
            revalidate();
            repaint();
        });
    }


    @Override
    public void empezarApuestas() {
        // Mostrar la información actualizada de las apuestas (mayor apuesta y pozo total)
        mostrarInfoApuestas();

        // Remover la etiqueta de textoApuestas para poder reajustarla
        remove(textoApuestas);

        // Configurar la posición y estilo del texto que indica que es la ronda de apuestas
        textoApuestas.setBounds(10, 10, 300, 20);
        textoApuestas.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        add(textoApuestas);

        // Mostrar cuál es el jugador actual y actualizar el texto que indica su turno
        mostrarJugadorActual();
        actualizarTurno();

        // Crear botones para que el jugador pueda apostar o pasar
        JButton btnApostar = new JButton("Apostar");
        JButton btnPasar = new JButton("Pasar");

        // Definir posición y tamaño de los botones
        btnApostar.setBounds(10, 100, 200, 20);
        btnPasar.setBounds(220, 100, 200, 20);

        // Añadir los botones a la interfaz
        add(btnApostar);
        add(btnPasar);

        // Acción cuando el jugador presiona el botón "Apostar"
        btnApostar.addActionListener(e -> {
            // Remover botones para evitar múltiples clics
            remove(btnApostar);
            remove(btnPasar);

            // Mostrar interfaz para que el jugador ingrese su apuesta
            insertarApuestaMayor();
        });

        // Acción cuando el jugador presiona el botón "Pasar"
        btnPasar.addActionListener(e -> {
            // Avanzar al siguiente jugador
            jugadorActual++;

            // Si ya se recorrieron todos los jugadores, finalizar la ronda de apuestas
            if (jugadorActual >= jugadores.size()) {
                // Remover botones de apostar y pasar
                remove(btnApostar);
                remove(btnPasar);

                // Limpiar panel para preparar la siguiente fase
                limpiarPanel();

                // Mostrar mensaje indicando que todos pasaron y la ronda terminó
                JOptionPane.showMessageDialog(this, "Todos pasaron. Fin de la ronda de apuestas.");

                // Dependiendo de si es la segunda ronda de apuestas, iniciar la fase siguiente
                if (segundaRondaDeApuesta == true) {
                    comenzarEnfrentamiento(); // Comenzar la fase de enfrentamiento de manos
                } else if (segundaRondaDeApuesta == false) {
                    comenzarDescarte(); // Comenzar la fase de descarte
                }

                // Actualizar la interfaz gráfica
                revalidate();
                repaint();
            } else {
                // Si aún quedan jugadores, actualizar el texto para mostrar el turno actual
                actualizarTurno();
            }
        });
    }

    private void insertarApuestaMayor() {
        // Limpiar el panel para mostrar solo los componentes de esta etapa
        limpiarPanel();

        // Mostrar la información actualizada de apuestas (mayor apuesta y pozo total)
        mostrarInfoApuestas();
        actualizarInfoApuestas();

        // Mostrar el jugador actual y actualizar el texto que indica su turno
        mostrarJugadorActual();
        actualizarTurno();

        // Volver a añadir la etiqueta que indica que estamos en la ronda de apuestas
        add(textoApuestas);

        // --- Componentes específicos para ingresar la apuesta mayor ---
        // Etiqueta para indicar al jugador que debe ingresar su apuesta mayor
        JLabel label = new JLabel("Jugador " + (jugadorActual + 1) + ", ingrese su apuesta mayor:");
        label.setBounds(10, 50, 400, 25);
        add(label);

        // Campo de texto para que el jugador escriba la cantidad a apostar
        JTextField campo = new JTextField();
        campo.setBounds(10, 80, 200, 25);
        add(campo);

        // Botón para confirmar la apuesta
        JButton botonApostar = new JButton("Apostar");
        botonApostar.setBounds(10, 120, 100, 25);
        add(botonApostar);

        // Acción al presionar el botón "Apostar"
        botonApostar.addActionListener(e -> {
            try {
                // Leer la cantidad ingresada y convertirla a entero
                int cantidad = Integer.parseInt(campo.getText().trim());

                // Verificar que la apuesta sea mayor a la apuesta mayor actual
                if (cantidad <= apuestaMayor) {
                    JOptionPane.showMessageDialog(this, "Debes subir la apuesta.");
                    return;
                }

                // Calcular la diferencia entre la nueva apuesta y la anterior del jugador actual
                int diferencia = cantidad - jugadores.get(jugadorActual).getCantidadApostada();

                // Actualizar la apuesta mayor y el pozo total con la diferencia
                apuestaMayor = cantidad;
                apuestaTotal += diferencia;

                // Guardar la apuesta actual del jugador y marcar que ya hizo su apuesta
                jugadores.get(jugadorActual).setCantidadApostada(cantidad);
                jugadores.get(jugadorActual).setApuestaHecha(true);

                // Reiniciar el estado "apuestaHecha" para todos los jugadores excepto el que subió la apuesta
                for (int i = 0; i < jugadores.size(); i++) {
                    if (i != jugadorActual) {
                        jugadores.get(i).setApuestaHecha(false);
                    }
                }

                // Avanzar al siguiente jugador cíclicamente
                jugadorActual = (jugadorActual + 1) % jugadores.size();

                // Pasar a la siguiente etapa donde el siguiente jugador debe elegir subir, igualar o retirarse
                elegirSubirIgualarRetirarse();

            } catch (Exception ex) {
                // Mostrar mensaje de error si el texto no es un número válido
                JOptionPane.showMessageDialog(this, "Número inválido.");
            }
        });

        // Actualizar la interfaz gráfica
        revalidate();
        repaint();
    }

    private void elegirSubirIgualarRetirarse() {
        // Limpiar el panel para mostrar únicamente los elementos relevantes a esta fase
        limpiarPanel();

        // Mostrar y actualizar la información de apuestas
        mostrarInfoApuestas();
        actualizarInfoApuestas();
        mostrarJugadorActual();
        actualizarTurno();

        // Añadir el texto de apuestas al panel (si deseas que se muestre permanentemente)
        add(textoApuestas);

        // Etiqueta que indica al jugador que debe tomar una decisión
        JLabel texto = new JLabel("Jugador " + (jugadorActual + 1) + ", decide:");
        texto.setBounds(10, 50, 300, 25);
        add(texto);

        // Botones para elegir entre subir, igualar o retirarse
        JButton btnSubir = new JButton("Subir");
        JButton btnIgualar = new JButton("Igualar");
        JButton btnRetirarse = new JButton("Retirarse");

        btnSubir.setBounds(10, 100, 100, 25);
        btnIgualar.setBounds(120, 100, 100, 25);
        btnRetirarse.setBounds(230, 100, 100, 25);

        // Agregar los botones al panel
        add(btnSubir);
        add(btnIgualar);
        add(btnRetirarse);

        // Acción al presionar "Subir": se dirige a ingresar una nueva apuesta mayor
        btnSubir.addActionListener(e -> insertarApuestaMayor());


        btnIgualar.addActionListener(e -> {
            JugadorCardDraw jugador = jugadores.get(jugadorActual);
            int diferencia = apuestaMayor - jugador.getCantidadApostada();

            // Si hay diferencia entre lo apostado y la apuesta mayor, el jugador iguala
            if (diferencia > 0) {
                apuestaTotal += diferencia;
                jugador.setCantidadApostada(apuestaMayor);
            }

            // Marcar que el jugador ya ha hecho su apuesta
            jugador.setApuestaHecha(true);

            // Actualizar la información en pantalla (importante para reflejar cambios)
            actualizarInfoApuestas();

            // Pasar al siguiente jugador
            siguienteJugadorApuesta();
        });

        // Acción al presionar "Retirarse"
        btnRetirarse.addActionListener(e -> {
            // Eliminar al jugador actual del juego
            jugadores.remove(jugadorActual);

            // Si solo queda un jugador, termina el juego
            if (jugadores.size() <= 1) {
                JOptionPane.showMessageDialog(this, "Solo queda un jugador, se acaba el juego.");
                pantallaGanador();
                return; // Se detiene aquí si el juego termina
            }

            // Ajustar el índice si el jugador retirado era el último de la lista
            if (jugadorActual >= jugadores.size()) jugadorActual = 0;

            // Pasar al siguiente jugador
            siguienteJugadorApuesta();
        });

        // Actualizar la interfaz gráfica
        revalidate();
        repaint();
    }

    // Método para limpiar todos los componentes del panel actual
    private void limpiarPanel() {
        removeAll();     // Elimina todos los componentes del panel
        revalidate();    // Revalida el layout para reflejar los cambios
        repaint();       // Repinta el panel actualizado
    }

    // Método que determina qué jugador debe apostar a continuación
    private void siguienteJugadorApuesta() {
        // Busca el índice del siguiente jugador que aún no ha hecho su apuesta
        int siguiente = obtenerSiguienteJugadorSinApuesta();

        if (siguiente == -1) {
            // Si todos los jugadores han apostado, termina la ronda de apuestas
            JOptionPane.showMessageDialog(this, "Fin de la ronda de apuestas.");
            limpiarPanel();

            // Si es la primera ronda de apuestas, se pasa a la fase de descarte
            if (!segundaRondaDeApuesta) {
                comenzarDescarte();
            }
            // Si ya fue la segunda ronda, se pasa directamente al enfrentamiento final
            else if (segundaRondaDeApuesta) {
                comenzarEnfrentamiento();
            }

        } else {
            // Si aún hay jugadores por apostar, se actualiza el turno y se continúa
            jugadorActual = siguiente;
            elegirSubirIgualarRetirarse();
        }
    }

    // Devuelve el índice del siguiente jugador que aún no ha hecho su apuesta.
// Si todos han apostado, devuelve -1.
    private int obtenerSiguienteJugadorSinApuesta() {
        for (int i = 0; i < jugadores.size(); i++) {
            // Avanza cíclicamente desde el jugador actual
            int index = (jugadorActual + i + 1) % jugadores.size();
            if (!jugadores.get(index).getApuestaHecha()) {
                return index;
            }
        }
        return -1; // Todos los jugadores han apostado
    }

    // Inicia la fase de descarte y reinicia el turno al primer jugador
    public void comenzarDescarte() {
        jugadorActual = 0;
        menuMostrarCartasDescarte(); // Muestra la GUI para seleccionar cartas a descartar
    }

    // Método que descarta y repone las cartas seleccionadas de la mano del jugador actual
    private void descartarYReponerCartas() {
        JugadorCardDraw jugador = jugadores.get(jugadorActual);
        ArrayList<Carta> mano = jugador.getMano();

        for (int i = 0; i < mano.size(); i++) {
            Carta carta = mano.get(i);
            // Si la carta fue seleccionada para descartarse
            if (carta.getCartaSeleccionada()) {
                mazo.getCartasMazo().add(carta); // Devuelve la carta al mazo
                mazo.barajarMazo();              // Baraja el mazo para que sea aleatorio

                // Si aún hay cartas en el mazo, se toma una nueva
                if (!mazo.getCartasMazo().isEmpty()) {
                    Carta nuevaCarta = mazo.getCartasMazo().remove(0);
                    nuevaCarta.setCartaSeleccionada(false); // Asegura que no venga ya marcada

                    // Reemplaza la carta en la misma posición
                    mano.set(i, nuevaCarta);
                }
            }
        }
    }

    public void menuMostrarCartasDescarte() {
        limpiarPanel(); // Limpia el panel antes de mostrar las cartas

        // Muestra información relacionada con las apuestas y el jugador actual
        mostrarInfoApuestas();
        actualizarInfoApuestas();
        mostrarJugadorActual();
        actualizarTurno();

        ArrayList<Carta> mano = jugadores.get(jugadorActual).getMano(); // Mano actual del jugador
        ArrayList<JButton> botonesCartas = new ArrayList<>(); // Botones visuales para cada carta

        // Botón para descartar las cartas seleccionadas
        JButton btnDescartarCartas = new JButton("Descartar");
        btnDescartarCartas.setBounds(500, 100, 100, 25);
        btnDescartarCartas.setVisible(false); // Oculto hasta que haya selección
        add(btnDescartarCartas);

        // Botón para continuar al siguiente jugador o a la siguiente fase
        JButton btnContinuar = new JButton("Continuar");
        btnContinuar.setBounds(600, 100, 100, 25);
        add(btnContinuar);

        // Acción al presionar "Continuar"
        btnContinuar.addActionListener(e -> {
            jugadores.get(jugadorActual).setDescarteHecho(true); // Marca que el jugador descartó
            jugadorActual++; // Avanza al siguiente jugador

            // Si ya todos los jugadores han descartado
            if (jugadorActual >= jugadores.size()) {
                boolean todosDescartaron = true;
                for (JugadorCardDraw j : jugadores) {
                    if (!j.getDescarteHecho()) {
                        todosDescartaron = false;
                        break;
                    }
                }

                // Si todos descartaron, se inicia la segunda ronda de apuestas
                if (todosDescartaron) {
                    JOptionPane.showMessageDialog(this, "Todos han terminado de descartar. Comienza la ronda final de apuestas.");
                    segundaRondaDeApuesta = true;

                    // Reinicia las apuestas
                    for (JugadorCardDraw j : jugadores) {
                        j.setApuestaHecha(false);
                    }

                    jugadorActual = 0;

                    // Si no hubo apuestas anteriores, comienza la ronda desde cero
                    if (apuestaTotal == 0) {
                        removeAll();
                        empezarApuestas();
                    } else {
                        removeAll();
                        elegirSubirIgualarRetirarse();
                    }
                } else {
                    jugadorActual = 0; // Reinicia para mostrar a los que aún no han descartado
                    menuMostrarCartasDescarte();
                }
            } else {
                menuMostrarCartasDescarte(); // Muestra la interfaz para el siguiente jugador
            }
        });

        // Acción al presionar "Descartar"
        btnDescartarCartas.addActionListener(e -> {
            descartarYReponerCartas(); // Ejecuta el descarte y reposición

            // Actualiza las imágenes con las nuevas cartas
            for (int i = 0; i < mano.size(); i++) {
                BufferedImage nuevaImg = mano.get(i).getImagen();
                Image imgEscalada = nuevaImg.getScaledInstance(80, 120, Image.SCALE_SMOOTH);
                botonesCartas.get(i).setIcon(new ImageIcon(imgEscalada));
            }

            JOptionPane.showMessageDialog(this, "Cartas descartadas y reemplazadas.");
            jugadores.get(jugadorActual).setDescarteHecho(true); // Marca que ya descartó
            btnDescartarCartas.setVisible(false); // Oculta el botón para evitar múltiples descartes
        });

        int x = 10;
        int y = 50;

        // Muestra gráficamente las cartas del jugador actual
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

            // Acción al hacer clic en una carta
            btnCarta.addActionListener(e -> {
                Rectangle bounds = btnCarta.getBounds();

                if (!carta.getCartaSeleccionada()) {
                    // Mueve la carta hacia arriba visualmente para indicar selección
                    btnCarta.setBounds(bounds.x, bounds.y - 10, bounds.width, bounds.height);
                    carta.setCartaSeleccionada(true);
                } else {
                    // Vuelve la carta a su posición original si se deselecciona
                    btnCarta.setBounds(bounds.x, bounds.y + 10, bounds.width, bounds.height);
                    carta.setCartaSeleccionada(false);
                }

                // Muestra el botón de "Descartar" solo si hay cartas seleccionadas
                if (!jugadores.get(jugadorActual).getDescarteHecho() &&
                        jugadores.get(jugadorActual).tieneCartaSeleccionada()) {
                    btnDescartarCartas.setVisible(true);
                } else {
                    btnDescartarCartas.setVisible(false);
                }

                repaint();
            });

            add(btnCarta);
            botonesCartas.add(btnCarta);
            x += 90; // Posición horizontal para la siguiente carta
        }

        revalidate();
        repaint();
    }


    // Inicia la fase final del juego donde se muestran las manos de los jugadores
    public void comenzarEnfrentamiento() {
        jugadorActual = 0; // Comienza desde el primer jugador

        verCartasEnfrentamiento(); // Muestra su mano
    }




    // Muestra las cartas del jugador actual en el enfrentamiento
    public void verCartasEnfrentamiento() {
        limpiarPanel(); // Limpia la interfaz anterior

        // Muestra información del juego (apuestas, turno, etc.)
        mostrarInfoApuestas();
        actualizarInfoApuestas();
        mostrarJugadorActual();
        actualizarTurno();


        // Título del enfrentamiento
        JLabel textoEnfrentamiento = new JLabel("Enfrentamiento");
        textoEnfrentamiento.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        textoEnfrentamiento.setBounds(10, 0, 200, 25);
        textoEnfrentamiento.setVisible(true);
        add(textoEnfrentamiento);

        ArrayList<Carta> mano = jugadores.get(jugadorActual).getMano(); // Mano del jugador actual
        ArrayList<JButton> botonesCartas = new ArrayList<>();

        // Botón para pasar al siguiente jugador
        JButton btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setBounds(500, 100, 100, 25);
        btnSiguiente.setVisible(true);
        add(btnSiguiente);

        // Botón para pasar a la pantalla final de ganador
        JButton btnVerGanador = new JButton("Ver Ganador");
        btnVerGanador.setBounds(600, 100, 100, 25);
        btnVerGanador.setVisible(true);
        add(btnVerGanador);


        // Acción al presionar "Siguiente"
        btnSiguiente.addActionListener(e -> {
            jugadorActual = (jugadorActual + 1) % numJugadores; // Avanza circularmente
            verCartasEnfrentamiento(); // Muestra la siguiente mano
        });

        btnVerGanador.addActionListener(e -> {
            if(hayEmpate()){
                pantallaEmpate();
            }
            else {
                pantallaGanador();
            }
        });

        // Coordenadas para colocar las cartas visualmente
        int x = 10;
        int y = 50;

        // Recorre y muestra las cartas del jugador actual
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

            // Acción visual opcional al seleccionar una carta
            btnCarta.addActionListener(e -> {
                Rectangle bounds = btnCarta.getBounds();

                if (!carta.getCartaSeleccionada()) {
                    btnCarta.setBounds(bounds.x, bounds.y - 10, bounds.width, bounds.height);
                    carta.setCartaSeleccionada(true);
                } else {
                    btnCarta.setBounds(bounds.x, bounds.y + 10, bounds.width, bounds.height);
                    carta.setCartaSeleccionada(false);
                }

                repaint();
            });

            add(btnCarta);
            botonesCartas.add(btnCarta);
            x += 90; // Mueve el siguiente botón más a la derecha
        }

        Jugador jugador = jugadores.get(jugadorActual);
        jugador.evaluarMano(); // Analiza la mano para saber su jugada

        JLabel labelJugada = new JLabel("Jugada: " + jugador.getNombreJugada() + " | Puntuación: " + jugador.getPuntuacionMano());
        labelJugada.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        labelJugada.setBounds(10, y + 130, 400, 25);
        add(labelJugada);



        revalidate();
        repaint();
    }


    public boolean hayEmpate() {

        int mayorPuntuacion = -1; // Guarda la puntuación más alta encontrada
        int cantidadConMayorPuntuacion = 0; // Cuenta cuántos jugadores tienen esa puntuación

        // Recorre todos los jugadores para determinar el número de empates
        for (JugadorCardDraw jugador : jugadores) {
            int puntuacion = jugador.getPuntuacion();

            if (puntuacion > mayorPuntuacion) {
                // Se ha encontrado una nueva puntuación alta
                mayorPuntuacion = puntuacion;
                cantidadConMayorPuntuacion = 1; // Reinicia el contador
            } else if (puntuacion == mayorPuntuacion) {
                // Otro jugador tiene la misma puntuación alta
                cantidadConMayorPuntuacion++;
            }
        }
        return cantidadConMayorPuntuacion > 1;
    }

    // Muestra el ganador o si hay empate
    public JugadorCardDraw getJugadorConMasPuntuacion() {
        if (jugadores == null || jugadores.isEmpty()) {
            return null; //Si no hay jugadores
        }

        JugadorCardDraw mejorJugador = jugadores.get(0);

        for (JugadorCardDraw jugador : jugadores) {//Ciclo para verificar el mejor jugador
            if (jugador.getPuntuacion() > mejorJugador.getPuntuacion()) {
                mejorJugador = jugador;
            }
        }

        return mejorJugador;
    }
    public void pantallaEmpate() {
        limpiarPanel();

        // Obtener la mayor puntuación
        int mayorPuntuacion = -1;
        for (JugadorCardDraw jugador : jugadores) {
            if (jugador.getPuntuacion() > mayorPuntuacion) {
                mayorPuntuacion = jugador.getPuntuacion();
            }
        }
        // Obtener los jugadores empatados
        ArrayList<Integer> indicesEmpatados = new ArrayList<>();
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getPuntuacion() == mayorPuntuacion) {
                indicesEmpatados.add(i);
            }
        }

        // Calcular apuesta dividida
        int apuestaPorJugador = apuestaTotal / indicesEmpatados.size();

        // Título
        JLabel titulo = new JLabel("¡Hay un empate!");
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setBounds(10, 20, 400, 30);
        add(titulo);

        // Mostrar info de cada jugador empatado
        int y = 70; // posición vertical inicial
        for (int i = 0; i < indicesEmpatados.size(); i++) {
            int indice = indicesEmpatados.get(i);
            JugadorCardDraw jugador = jugadores.get(indice);

            JLabel info = new JLabel("Jugador #" + (indice + 1) + " - Jugada: "
                    + jugador.getNombreJugada() + " Recibe " + apuestaPorJugador + " unidades.");
            info.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
            info.setBounds(10, y, 700, 25);
            add(info);
            y += 30; // espacio entre líneas
        }

        // Botón para salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(220, y + 20, 100, 30);
        add(btnSalir);
        btnSalir.addActionListener(e -> System.exit(0));

        revalidate();
        repaint();
    }

    public void pantallaGanador() {
        // Limpiar la pantalla
        limpiarPanel();
        // Obtener al jugador con mayor puntuación
        JugadorCardDraw ganador = getJugadorConMasPuntuacion();

        // Obtener número del jugador
        int numeroJugador = jugadores.indexOf(ganador) + 1;

        // Crear etiqueta de título
        JLabel titulo = new JLabel("¡Tenemos un ganador!");
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setBounds(10, 20, 400, 30);
        add(titulo);

        // Mostrar número, nombre de la jugada y apuesta total
        // Uso de este formato para no hacer tan larga la línea
        String textoInfo = String.format(
                "Jugador #%d ganó con la jugada \"%s\" y se llevó %d unidades.",
                numeroJugador,
                ganador.getNombreJugada(),
                apuestaTotal
        );
        // Aqui info ganador toma el texto de textoInfo
        JLabel infoGanador = new JLabel(textoInfo);
        infoGanador.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        infoGanador.setBounds(10, 70, 600, 25);
        add(infoGanador);

        // Botón para salir del juego
        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(220, 120, 100, 30);
        add(btnSalir);

        btnSalir.addActionListener(e -> System.exit(0));

        revalidate();
        repaint();
    }
}
