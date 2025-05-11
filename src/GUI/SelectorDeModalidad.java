package GUI;

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

        JButton botonFiveCardDraw= new JButton(new ImageIcon(getClass().getResource("/Imagenes/FiveCardDrawButton.png")));
        JButton botonSevenCardStud= new JButton(new ImageIcon(getClass().getResource("/Imagenes/SevenCardStudButton.png")));

        botonFiveCardDraw.setBorderPainted(false);
        botonFiveCardDraw.setContentAreaFilled(false);
        botonFiveCardDraw.setFocusPainted(false);
        botonFiveCardDraw.setOpaque(false);

        botonSevenCardStud.setBorderPainted(false);
        botonSevenCardStud.setContentAreaFilled(false);
        botonSevenCardStud.setFocusPainted(false);
        botonSevenCardStud.setOpaque(false);

        botonFiveCardDraw.setFont(new Font("Arial", Font.BOLD, 24));
        botonSevenCardStud.setFont(new Font("Arial", Font.BOLD, 24));

        botonFiveCardDraw.setBounds(370, 450, 240, 90);
        botonSevenCardStud.setBounds(660, 450, 240, 90);

        add(botonFiveCardDraw);
        add(botonSevenCardStud);

        botonFiveCardDraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("boton A");
            }
        });

        botonSevenCardStud.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("boton B");
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }

}
