/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Clases;

/**
 *
 * @author inftel08
 */
public class Jam {
    
   private String nombreDirectorio;
   private String nombreEtiqueta;
   private String valor;

    public Jam(String nombreDirectorio, String nombreEtiqueta, String valor) {
        this.nombreDirectorio = nombreDirectorio;
        this.nombreEtiqueta = nombreEtiqueta;
        this.valor = valor;
    }

    public String getNombreDirectorio() {
        return nombreDirectorio;
    }

    public void setNombreDirectorio(String nombreDirectorio) {
        this.nombreDirectorio = nombreDirectorio;
    }

    public String getNombreEtiqueta() {
        return nombreEtiqueta;
    }

    public void setNombreEtiqueta(String nombreEtiqueta) {
        this.nombreEtiqueta = nombreEtiqueta;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Jam{" + "nombreDirectorio=" + nombreDirectorio + ", nombreEtiqueta=" + nombreEtiqueta + ", valor=" + valor + "}\n";
    }
   
   
    
}
