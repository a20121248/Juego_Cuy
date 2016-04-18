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
    /*1-> terreno p1 (A)
     *2-> terreno p2 (B)
     *3-> terreno accion simple
     *4-> terreno accion duo
     *4-> terreno trigger
     *5-> background
     *6-> terreno final Usado 
    */
    public Terreno(char elementoGrafico,int alto, int ancho,int tipo){
        super(elementoGrafico,alto, ancho);
        this.tipo = tipo;
    }
    public Terreno(char elementoGrafico, int tipo){
        super(elementoGrafico);
        this.tipo = tipo;
    }
    public void setTipo(int value){
        this.tipo = value;
    }
    public int getTipo(){
        return this.tipo;
    }
}
