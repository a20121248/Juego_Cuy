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
    
    public void Accion(int tipo) { 
        switch (tipo) {
            case 1:
                posX = posX - 1; //Mover a la izquierda
                break;
            case 2:
                posX = posX + 1; //Mover a la derecha
                break;
            case 3:
                posY = posY - 1; //Mover hacia arriba
                break;
            case 4:
                posY = posY + 1; //Mover hacia abajo
                break;
            default:
                break;
        }
    }
    
    public boolean verificaMeta(int col, int fila){
        return posX == col && posY == fila;
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
