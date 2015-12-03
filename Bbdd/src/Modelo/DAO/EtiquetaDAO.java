/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.DAO;

import Modelo.Clases.Etiqueta;
import Modelo.Clases.Foto;
import java.sql.Connection;
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
public class EtiquetaDAO {

    private static Connection con = null;
    public static Map<Integer,Etiqueta> mapaEtiquetas;

    public static Map<Integer, Etiqueta> getAllTags() {
        Etiqueta tag = null;

        con = ConexionORACLE.conectar();
        mapaEtiquetas = new HashMap<>();
        
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from Etiqueta");

            while (rs.next()) {
                tag = new Etiqueta(rs.getInt(1), rs.getString(2), rs.getInt(3));
                mapaEtiquetas.put(tag.getIdEtiqueta(), tag);
            }
            
            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return mapaEtiquetas;

    }

}
