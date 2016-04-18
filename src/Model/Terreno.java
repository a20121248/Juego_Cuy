/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author USUARIO
 */
public class Terreno extends Dibujable{
    private int tipo;
    private boolean activo;
    /*1-> terreno p1 (A)
     *2-> terreno p2 (B)
     *3-> terreno accion simple
     *4-> terreno accion duo
     *5-> terreno trigger
     *6-> terreno final Usado´como meta 
     *7-> background
    */
    public Terreno(char elementoGrafico,int alto, int ancho,int tipo){
        super(elementoGrafico,alto, ancho);
        this.activo = elementoGrafico != 'D';
        this.tipo = tipo;
    }
    public Terreno(char elementoGrafico, int tipo){
        super(elementoGrafico);
        this.activo = elementoGrafico != 'D';
        this.tipo = tipo;
    }
    public void setTipo(int value){
        this.tipo = value;
    }
    public int getTipo(){
        return this.tipo;
    }
    public void setActivo(boolean value){
        this.activo = value;
    }
    public boolean getActivo(){
        return this.activo;
    }
}
