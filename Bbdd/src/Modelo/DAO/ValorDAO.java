/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author inftel08
 */
public class ValorDAO {
    
     private static ConexionORACLE con = null;
    
    public static void insertMetadata(int id_foto, String directorio, String etiqueta, String valor) {

        Connection c = con.conectar();

        try {
            //llamar al procedimiento de comprobar_ruta
            CallableStatement cs = c.prepareCall("{call INSERTMETADATA(?,?,?,?)}");
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
    
}
