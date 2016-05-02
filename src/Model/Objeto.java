package Model;

import java.awt.image.BufferedImage;

public class Objeto extends Dibujable {

    //1 = obstáculo, 2 = objeto de ayuda
    private int tipo;

    public Objeto(BufferedImage img, char elementoGrafico, int tipo) {
        super(img, elementoGrafico);
        this.tipo = tipo;
    }

    public void setTipo(int value) {
        this.tipo = value;
    }

    public int getTipo() {
        return this.tipo;
    }
}
