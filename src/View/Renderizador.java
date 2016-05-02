package View;

import Controller.GestorMapas;
import Model.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class Renderizador {

    Graphics g_pnlGrafico, g_pnlTexto;

    public Renderizador(PanelGrafico pnlGrafico, PanelTexto pnlTexto) {
        /* Saco la parte grafica de los paneles para pintar directamente */
        g_pnlGrafico = pnlGrafico.getGraphics();
        g_pnlTexto = pnlTexto.getGraphics();

    }

    //x: columna, y: fila :S
    private void dibujar_PnlGrafico(BufferedImage img, int x, int y) {
        g_pnlGrafico.drawImage(img, x * 64, y * 64, null);
    }

    private void dibujar_PnlTexto(String out, int x, int y) {
        g_pnlTexto.drawString(out, x, y);
    }

    private void imprimir(char c) {
        System.out.print(c);
        System.out.print(' ');
    }

    private void imprimirLinea() {
        for (int i = 0; i <= 34; i++) {
            System.out.print('-');
        }
        System.out.println();
    }

    public void mostrarMapa(GestorMapas gm, int nivel, Personaje p1, Personaje p2) {
        Mapa mapa = gm.getMapa(nivel);
        for (int fila = 0; fila < 12; ++fila) {
            for (int columna = 0; columna < 16; ++columna) {
                Celda celda = mapa.getMapaAt(fila, columna);
                if (celda == null) {
//                    imprimir(' ');
                    continue;
                }
                Dibujable obj = celda.getObj();
                if (obj == null) {
//                    imprimir(' ');
                    continue;
                }
                char caracterGrafico = obj.getElementoGrafico();
                
                
                dibujar_PnlGrafico(celda.getObj().getImg(), columna, fila);
                
                
                imprimir(obj.getElementoGrafico());
            }
        }
        dibujar_PnlGrafico(p1.getImg(), p1.getPosX(), p1.getPosY());
        dibujar_PnlGrafico(p2.getImg(), p2.getPosX(), p2.getPosY());
    }

    public void mostrarMapa_Consola(GestorMapas gm, int nivel, Personaje p1, Personaje p2) {
        Mapa mapa = gm.getMapa(nivel);
        imprimirLinea();
        for (int fila = 0; fila < 12; fila++) {
            System.out.print("| ");
            for (int columna = 0; columna < 16; columna++) {
                Celda celda = mapa.getMapaAt(fila, columna);
                if (celda == null) {
                    imprimir(' ');
                    continue;
                }
                Dibujable obj = celda.getObj();
                if (obj == null) {
                    imprimir(' ');
                    continue;
                }
                if (p1.getPosX() == columna && p1.getPosY() == fila) {
                    imprimir('A');
                    continue;
                }
                if (p2.getPosX() == columna && p2.getPosY() == fila) {
                    imprimir('B');
                    continue;
                }
                imprimir(obj.getElementoGrafico());
            }
            System.out.println('|');
        }
        imprimirLinea();
    }

}






/*----------------------------------------------------*/
class PanelTexto extends JPanel {

    private static final long serialVersionUID = 1065949227120247586L;
    List<String> textos;

    public void addTexto(String texto) {
        textos.add(texto);
    }

    public void removeTexto() {
        textos.remove(textos.size() - 1);
    }

    public void inicializarTexto(String nombre) {
        textos.clear();
        textos.add("Nombre: ");
        textos.add(nombre);
        textos.add("Nivel: Tutorial");
        textos.add("Vida: 10");
        textos.add("Comando actual:");
        textos.add("");
    }

    public void cambiarNivel(int index) {
        textos.set(2, "Nivel: " + index);
    }

    public void quitarVida(int val) {
        int vidaActual = Integer.parseInt(textos.get(3).substring(6));
        vidaActual -= val;
        textos.set(3, "Vida: " + vidaActual);
    }

    public void anadirUltimo(char c) {
        String aux = textos.get(textos.size() - 1);
        aux += c;
        textos.set(textos.size() - 1, aux);
    }

    public void eliminarUltimo() {
        String aux = textos.get(textos.size() - 1);
        aux = aux.substring(0, aux.length() - 1);
        textos.set(textos.size() - 1, aux);
    }

    public void paint(Graphics g) {
        super.paint(g);

        int aux = 10;
        for (int i = 0; i < textos.size(); ++i) {
            // System.out.println(textos.get(i));
            String[] s = textos.get(i).split(" ");
            String out = "";
            int l = 0;
            for (int j = 0; j < s.length; ++j) {
                if (l + s[j].length() >= 25) {
                    g.drawString(out, 10, aux);
                    out = "";
                    aux += 30;
                    l = 0;
                }
                l += s[j].length();
                out += " ";
                out += s[j];
                if (out.charAt(out.length() - 1) == '.') {
                    g.drawString(out, 10, aux);
                    out = "";
                    aux += 30;
                    l = 0;
                }
            }
            g.drawString(out, 10, aux);
            aux += 30;
        }
    }

    public PanelTexto() {
        textos = new ArrayList<String>();
    }

}

class PanelGrafico extends JPanel {

    public PanelGrafico() {

    }

    public void paint(Graphics g) {
        super.paint(g);
//        g.drawImage(, WIDTH, WIDTH, this)
    }

//    public PanelGraficos(Object flagMovimiento, Ventana v) {
//        return;
//    }
}
