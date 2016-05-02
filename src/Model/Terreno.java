package Model;

import java.awt.image.BufferedImage;

public class Terreno extends Dibujable {

    private int tipo;
    private boolean activo;

    /*1-> terreno p1 (A)
     *2-> terreno p2 (B)
     *3-> terreno accion simple
     *4-> terreno accion duo
     *5-> terreno trigger de A
     *6-> terreno final usado como meta 
     *7-> terreno trigger de B
     */
    public Terreno(BufferedImage img, char elementoGrafico, int alto, int ancho, int tipo) {
        super(img, elementoGrafico, alto, ancho);
        this.activo = true;
        this.tipo = tipo;
    }

    public Terreno(BufferedImage img, char elementoGrafico, int tipo) {
        super(img, elementoGrafico);
        this.activo = true;
        this.tipo = tipo;
    }

    public void setTipo(int value) {
        this.tipo = value;
    }

    public int getTipo() {
        return this.tipo;
    }

    public void setActivo(boolean value) {
        this.activo = value;
    }

    public boolean getActivo() {
        return this.activo;
    }
}
