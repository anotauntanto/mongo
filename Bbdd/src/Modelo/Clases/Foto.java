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
public class Foto {
    
    private int idFoto;
    private String nombreFoto;
    private String tamano;
    private String extension;
    private int idRuta;

    public Foto(int idFoto, String nombreFoto, String tamano, String extension, int idRuta) {
        this.idFoto = idFoto;
        this.nombreFoto = nombreFoto;
        this.tamano = tamano;
        this.extension = extension;
        this.idRuta = idRuta;
    }
    
    public Foto(String nombreFoto, String tamano, String extension) {
        this.idFoto = 0;
        this.nombreFoto = nombreFoto;
        this.tamano = tamano;
        this.extension = extension;
        this.idRuta = 0;
    }

    public int getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(int idFoto) {
        this.idFoto = idFoto;
    }

    public String getNombreFoto() {
        return nombreFoto;
    }

    public void setNombreFoto(String nombreFoto) {
        this.nombreFoto = nombreFoto;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    @Override
    public String toString() {
        return "Foto{" + "idFoto=" + idFoto + ", nombreFoto=" + nombreFoto + ", tamano=" + tamano + ", extension=" + extension + ", idRuta=" + idRuta + '}';
    }
    
    
    
}
