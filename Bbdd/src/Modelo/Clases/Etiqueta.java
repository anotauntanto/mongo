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
public class Etiqueta {
    
    private int idEtiqueta;
    private String nombreEtiqueta;
    private int idDirectorio;

    public Etiqueta(int idEtiqueta, String nombreEtiqueta, int idDirectorio) {
        this.idEtiqueta = idEtiqueta;
        this.nombreEtiqueta = nombreEtiqueta;
        this.idDirectorio = idDirectorio;
    }

    public int getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(int idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }

    public String getNombreEtiqueta() {
        return nombreEtiqueta;
    }

    public void setNombreEtiqueta(String nombreEtiqueta) {
        this.nombreEtiqueta = nombreEtiqueta;
    }

    public int getIdDirectorio() {
        return idDirectorio;
    }

    public void setIdDirectorio(int idDirectorio) {
        this.idDirectorio = idDirectorio;
    }
    
    
    
}
