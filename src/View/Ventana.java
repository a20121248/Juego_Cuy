package View;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Ventana extends JFrame implements KeyListener {

    private JPanel contentPane;
    private PanelGrafico pnlGrafico;
    private PanelTexto pnlTexto;
    private Juego juego;

    public Ventana() {
        inicializar();
        this.setVisible(true);
        juego = new Juego(this, pnlGrafico, pnlTexto);
        juego.start();
    }

    public void inicializar() {
        this.setBounds(100, 100, 1324, 806);
        addKeyListener(this);

        /* INICIALIZAR PANEL GRAFICO */
        pnlGrafico = new PanelGrafico();
        pnlGrafico.setBounds(0, 0, 1024, 806);
        pnlGrafico.setFocusable(false);

        /* INICIALIZAR PANEL TEXTO */
        pnlTexto = new PanelTexto();
        pnlTexto.setBounds(1024, 0, 200, 806);
        pnlTexto.setFocusable(false);

        /* INICIALIZAR CANVAS */
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        /* AGREGARLO AL CONTENTPANE */
        contentPane.add(pnlGrafico);
        contentPane.add(pnlTexto);
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        juego.procesarEvento(evt);
    }

    @Override
    public void keyReleased(KeyEvent evt) {
    }

    @Override
    public void keyTyped(KeyEvent evt) {
    }

}

