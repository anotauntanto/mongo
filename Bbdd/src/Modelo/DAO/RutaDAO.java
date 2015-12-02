/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author inftel08
 */
public class RutaDAO {
    private static Connection con = null;
    private final static String barra="/";
    
    public static String getStringRuta(int id_ruta) {
        con = Conexion.conectar();
        int id_padre;
        String ruta="";
        String ruta_temp=null;
        id_padre=id_ruta;
        
        
        try{
       
            PreparedStatement ps = con.prepareStatement("select idpadre, nombreruta from Ruta where idruta=?");
            
            while (id_padre!=1){ //Hago hasta que id_padre=1
                ps.setInt(1, id_padre);

                ResultSet rs=ps.executeQuery();

                if (rs.next()){

                    id_padre = rs.getInt(1);
                    
                    ruta_temp=barra+rs.getString(2);
                   
                    ruta=ruta_temp+ruta;

                }

                rs.close();
            }
            
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return ruta;
    }
    
     public static void main(String[] args) {
         System.out.println("Ruta: "+getStringRuta(6));
     }
}
