/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.DAO;

import Modelo.Clases.Directorio;
import Modelo.Clases.Jam;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author inftel08
 */
public class JamDAO {
    
    private static List <Jam> listaJam;
    private static Connection con = null;
    
    public static List<Jam> listJamByIdPhoto (int id_foto) {
        
        Jam jam;
        con=ConexionORACLE.conectar();
        
        listaJam = new ArrayList<>();
        
        try{
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement("select distinct directorio.nombreDirectorio, etiqueta.nombreEtiqueta, valor.Valor from Etiqueta, Valor, Foto, Directorio where Etiqueta.idEtiqueta = Valor.idEtiqueta and Directorio.idDirectorio = Etiqueta.idDirectorio and valor.idFoto = Foto.idFoto and Foto.idFoto = ? order by directorio.nombreDirectorio");
            ps.setInt(1, id_foto);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                jam = new Jam (rs.getString(1), rs.getString(2), rs.getString(3));
                listaJam.add(jam);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return listaJam;
        
    }
    
}
