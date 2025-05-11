package GUI;
import javax.swing.*;
import java.awt.*;

public class PruebaVisual {

    public static void main(String[] args) {
        JFrame ventana= new JFrame("Prueba de pantallas");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setSize(1280, 720);

        CardLayout layout = new CardLayout();
        JPanel contenedor =new JPanel(layout);

        SelectorDeModalidad menu= new SelectorDeModalidad(contenedor, layout);
        PantallaDeInicio incio = new PantallaDeInicio(contenedor, layout);

        contenedor.add(menu, "menu");
        contenedor.add(incio, "inicio");

        ventana.setContentPane(contenedor);
        ventana.setVisible(true);
        layout.show(contenedor, "inicio");

    }
}