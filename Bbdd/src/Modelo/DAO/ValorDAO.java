/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.DAO;

import Modelo.Clases.Valor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author inftel08
 */
public class ValorDAO {
    
     private static Connection con = null;
    private static List<Valor> listaValores;
    
    public static void insertMetadata(int id_foto, String directorio, String etiqueta, String valor) {

        con = ConexionORACLE.conectar();

        try {
            //llamar al procedimiento de comprobar_ruta
            CallableStatement cs = con.prepareCall("{call INSERTMETADATA(?,?,?,?)}");
            cs.setInt(1, id_foto);
            cs.setString(2, directorio);
            cs.setString(3, etiqueta);
            cs.setString(4, valor);
            cs.execute(); //executeQuery por si no funciona

            cs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    public static List<Valor> getAllValor(){
        
        Valor valor= null;

        con=ConexionORACLE.conectar();
        listaValores= new ArrayList<>();
        
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from Valor");

            while (rs.next()) {
                valor = new Valor(rs.getString(1), rs.getInt(2), rs.getInt(3));
                listaValores.add(valor);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return listaValores;

    }
    
    public static List<Valor> getAllIdDirectoriesByPhoto(int id_foto){
        
        con=ConexionORACLE.conectar();
        Valor valor= null;
        listaValores = new ArrayList<>();
        
        try{
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement("select * from Valor where idFoto=?");
            ps.setInt(1, id_foto);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                valor = new Valor(rs.getString(1), rs.getInt(2), rs.getInt(3));
                listaValores.add(valor);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return listaValores;

    }
    
   
}
