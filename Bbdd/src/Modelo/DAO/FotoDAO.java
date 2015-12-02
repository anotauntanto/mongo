/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.DAO;

import Modelo.Clases.Foto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author inftel08
 */
public class FotoDAO {

    private static Conexion con = null;

    //buscar una foto por ruta (absoluta) y nombre
    public static Foto getPhoto(String nombre_foto, String ruta, String extension) {

        Foto foto = null;
        int res;
        Connection c = con.conectar();

        try {
            //llamar al procedimiento de comprobar_ruta
            CallableStatement cs = c.prepareCall("{?=call COMPROBAR_RUTA(?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, ruta.toUpperCase());
            cs.execute(); //executeQuery por si no funciona
            res = cs.getInt(1);

            if (res != -1) { //la ruta existe

                PreparedStatement ps = c.prepareStatement("select * from Foto where nombre = ? and idRuta = ? and extension = ?");
                ps.setString(1, nombre_foto);
                ps.setInt(2, res);
                ps.setString(3, extension);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    foto = new Foto(rs.getInt(1), nombre_foto, rs.getString(3), rs.getString(4), res);
                }
                rs.close();
                ps.close();
            }

            cs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return foto;
    }

    public static Foto insertPhoto(Foto foto, String ruta) {

        int id_foto;
        Connection c = con.conectar();

        try {
            //llamar al procedimiento de comprobar_ruta
            CallableStatement cs = c.prepareCall("{?=call INSERTIMAGE(?,?,?,?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, ruta.toUpperCase());
            cs.setString(3, foto.getNombreFoto());
            cs.setString(4, foto.getExtension());
            cs.setString(5, foto.getTamano());
            cs.execute(); //executeQuery por si no funciona
            id_foto = cs.getInt(1);

            foto.setIdFoto(id_foto);
            PreparedStatement ps = c.prepareStatement("select idRuta from Foto where idFoto = ?");
            ps.setInt(1, id_foto);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                foto.setIdRuta(rs.getInt(1));
            }

            rs.close();
            ps.close();
            cs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return foto;
    }

}
