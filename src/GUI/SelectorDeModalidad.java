package GUI;

import Logica.CardDraw;
import Logica.TexasHoldEm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectorDeModalidad extends JPanel {
    private Image imagenFondo;
    public SelectorDeModalidad(JPanel contenedor, CardLayout layout){
        imagenFondo = new ImageIcon(getClass().getResource("/Imagenes/MenuDeInicio.png")).getImage();
        setLayout(null);
        setOpaque(false);

        JButton botonFiveCardDraw= new JButton("FIVE CARD DRAW");
        JButton botonTexaa =new JButton("TEXAS HOLD'EM");

        botonFiveCardDraw.setBorderPainted(true);
        botonFiveCardDraw.setContentAreaFilled(false);
        botonFiveCardDraw.setFocusPainted(true);
        botonFiveCardDraw.setOpaque(false);
        botonFiveCardDraw.setForeground(Color.WHITE);

        botonTexaa.setBorderPainted(true);
        botonTexaa.setContentAreaFilled(false);
        botonTexaa.setFocusPainted(true);
        botonTexaa.setOpaque(false);
        botonTexaa.setForeground(Color.WHITE);

        botonFiveCardDraw.setFont(new Font("Arial", Font.BOLD, 18));

        botonTexaa.setFont(new Font("Arial", Font.BOLD, 18));

        botonFiveCardDraw.setBounds(370, 450, 240, 90);
        botonTexaa.setBounds(660, 450, 240, 90);

        add(botonFiveCardDraw);
        add(botonTexaa);

        botonFiveCardDraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("boton FCD");
                JFrame frame = new JFrame("Poker - Five Card Draw");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                CardDraw panel = new CardDraw();
                frame.add(panel);
                frame.setVisible(true);
            }


        });

        botonTexaa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("boton B");

                TexasHoldEm texas = new TexasHoldEm();
                JFrame frame2 = new JFrame("TEXAS_HOLD_EM");
                frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame2.setSize(1000, 700);
                frame2.add(texas);  // Reusar instancia ya creada
                frame2.setLocationRelativeTo(null);
                frame2.setResizable(false);
                frame2.setVisible(true);
            }
        });


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }

}
