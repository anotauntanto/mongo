/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.DAO;

import Modelo.Clases.Directorio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author inftel08
 */
public class DirectorioDAO {
    
    private static Map <Integer, String> mapaDirectorio;
    private static List <Directorio> listaDirectorio;
    private static List <Integer> listaDirectorio2;
    private static Connection con = null;
    
    public static Map<Integer, String> getAllDirectories(){
        
        Directorio directorio;
        con=ConexionORACLE.conectar();
        
        mapaDirectorio = new HashMap<>();
        
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from directorio");

            while (rs.next()) {
                mapaDirectorio.put(rs.getInt(1), rs.getString(2));
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return mapaDirectorio;

    }
    
    public static List<Directorio> getAllDirectoriesByPhoto(int id_foto){
        
        Directorio directorio;
        con=ConexionORACLE.conectar();
        
        listaDirectorio = new ArrayList<>();
        
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select distinct directorio.idDirectorio, directorio.nombreDirectorio"
                    + " from directorio,etiqueta,valor where directorio.idDirectorio = etiqueta.idDirectorio and Etiqueta.idEtiqueta = valor.idEtiqueta "
                    + "and valor.idFoto=foto.idFoto and foto.idFoto="+ id_foto);

            while (rs.next()) {
                directorio = new Directorio (rs.getInt(1), rs.getString(2));
                listaDirectorio.add(directorio);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return listaDirectorio;

    }
    
    public static List<Integer> getAllIdDirectoriesByPhoto(int id_foto){
        
        con=ConexionORACLE.conectar();
        
        listaDirectorio2 = new ArrayList<>();
        
        try{
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement("select distinct directorio.idDirectorio from etiqueta, valor, foto, directorio where directorio.idDirectorio = etiqueta.idDirectorio and Etiqueta.idEtiqueta = valor.idEtiqueta and valor.idFoto=foto.idFoto and foto.idFoto= ?");
            ps.setInt(1, id_foto);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getInt(1));
                listaDirectorio2.add(rs.getInt(1));
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return listaDirectorio2;

    }
    
}
