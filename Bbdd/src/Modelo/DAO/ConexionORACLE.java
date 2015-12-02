/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author inftel08
 */
public class ConexionORACLE {
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */

    /**
     * Clase ConexionORACLE con la Base de Datos
     */
    private static String url = "jdbc:oracle:thin:INFTEL15_4/INFTEL@olimpia.lcc.uma.es:1521:edgar";
    private static Connection con = null;

    /**
     * Constructor de la clase Conexion
     */
    public ConexionORACLE() {

    }

    /**
     * Metodo que conecta con la Base de Datos
     *
     * @return Connection conexion a devolver
     */
    public static synchronized Connection conectar() {

        if (con == null) {

            try {

                Class.forName("oracle.jdbc.driver.OracleDriver");

                con = DriverManager.getConnection(url, null);
                
            } catch (SQLException ex) {
                System.out.println("Problema al conectar con la base de datos");
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
 
            }
        } 

        return con;
    }

    /**
     * Metodo que desconecta con la Base de Datos
     *
     * @param con Connection conexion a cerrar
     */
    public static void desconexion() {

        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionORACLE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

