package Model;
/**
 *
 * @author Godievski
 */
public class Celda {
    private Objeto obj;
    private int tipoSuelo;
    
    public Celda() {
        obj = null;
    }
    public int getTipoSuelo(){
        return tipoSuelo;
    }
    public void setTipoSuelo(int value){
        tipoSuelo = value;
    }
    public Objeto getObj(){
        return obj;
    }
    public void setObj(Objeto value){
        obj = value;
    }
}
