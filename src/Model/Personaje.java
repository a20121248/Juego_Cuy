package Model;

/**
 *
 * @author Godievski
 */
public class Personaje extends Dibujable{
    private int vida;
    private int posX;
    private int posY;
    static int N = 10;
    private String[] accionEspecial;
    static String[] accionDuo = new String[5];
    
    public Personaje(char elementoGrafico){
        super(elementoGrafico);
        this.vida = N;
        this.accionEspecial = new String[5];
    }   
    public Personaje (int posX, int posY, char elementoGrafico) {
        super(elementoGrafico);
        this.vida = N;
        this.posX = posX;
        this.posY = posY;
        this.accionEspecial = new String[5];
    }
    public Personaje (int posX, int posY,char elementoGrafico,int alto, int ancho) {
        super(elementoGrafico,alto,ancho);
        this.vida = N;
        this.posX = posX;
        this.posY = posY;
        this.accionEspecial = new String[5];
    }

    public void Mover(int x, int y){
        this.posX = x;
        this.posY = y;
    }

    public int getVida(){
        return vida;
    }
    
    public void setVida(int value){
        vida = value;
    }
    
    public int getPosX() {
        return posX;
    }
    
    public void setPosX(int posX) {
        this.posX = posX;
    }
    
    public int getPosY() {
        return posY;
    }
    
    public void setPosY(int posY) {
        this.posY = posY;
    }
    public void setAccionEspecial(String accion, int nivel){
        this.accionEspecial[nivel] = accion;
    }
    public String getAccionEspecial(int nivel){
        return this.accionEspecial[nivel];
    }
    public void setAccionDuo(String accion, int nivel){
        accionDuo[nivel] = accion;
    }
    public String getAccionDuo(int nivel){
        return accionDuo[nivel];
    }
}
