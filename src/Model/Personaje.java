package Model;

/**
 *
 * @author Godievski
 */
public class Personaje extends Dibujable{
    private int vida;
    private int posX;
    private int posY;
    private int idPlayer; /*1-> playerA 2-> playerB*/
    static int N = 10;
    
    public Personaje(char elementoGrafico){
        super(elementoGrafico);
        this.vida = N;
    }   
    public Personaje (int posX, int posY, char elementoGrafico) {
        super(elementoGrafico);
        this.vida = N;
        this.posX = posX;
        this.posY = posY;
    }
    public Personaje (int posX, int posY,char elementoGrafico,int alto, int ancho) {
        super(elementoGrafico,alto,ancho);
        this.vida = N;
        this.posX = posX;
        this.posY = posY;
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
   
}
