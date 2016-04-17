/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Anthony
 */
public class Objeto {
    private int tipo; //1 = obstáculo, 2 = objeto de ayuda
    
    public Objeto(int tipo) {
        this.tipo = tipo;
    }
    public void setTipo(int value){
        this.tipo = value;
    }
    public int getTipo(){
        return this.tipo;
    }
}
