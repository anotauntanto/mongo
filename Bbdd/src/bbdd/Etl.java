/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbdd;

import Modelo.Clases.Foto;
import Modelo.DAO.FotoDAO;
import Modelo.DAO.RutaDAO;
import java.util.List;

/**
 *
 * @author inftel07
 */
public class Etl {
    
    static List<Foto> allFotos;
    
    public static void main (String[] arg) {
        List<Foto> allPhoto = FotoDAO.getAllPhoto();
        String ruta;
        
        for (Foto f:allPhoto) {
            f.setRuta(RutaDAO.getStringRuta(f.getIdRuta()));
            System.out.println(f);
        }
      
        
        
    }
    
}
