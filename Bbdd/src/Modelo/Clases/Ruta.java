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
public class Ruta {
    
    private int idRuta;
    private String nombreRuta;
    private int idPadre;

    public Ruta(int idRuta, String nombreRuta, int idPadre) {
        this.idRuta = idRuta;
        this.nombreRuta = nombreRuta;
        this.idPadre = idPadre;
    }

    
    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public int getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(int idPadre) {
        this.idPadre = idPadre;
    }
    
    
    
}
