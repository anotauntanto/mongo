/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.DAO.ConsultasMongoDAO;
import Vistas.VistaMetadatos;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author inftel08
 */
public class ControladorMetadatos {

    private VistaMetadatos miVista;
    private String id_foto = null;

    public ControladorMetadatos(VistaMetadatos miVista, String ruta_completa) {
        System.out.println(ruta_completa);

        int punto = ruta_completa.lastIndexOf(".");
        //System.out.println("parte"+punto);
        String extension = ruta_completa.substring(punto + 1);
        int barra = ruta_completa.lastIndexOf("/");
        String nombre = ruta_completa.substring(barra + 1, punto);

        String ruta = ruta_completa.substring(0, barra);

        System.out.println("extension: " + extension);
        System.out.println("nombre: " + nombre);
        System.out.println("ruta: " + ruta);

        //String[] partes=ruta_completa.split(".");
        //System.out.println("parte0"+partes[0]);
        this.miVista = miVista;

        //System.out.println("FOTO"+ControladorBusquedas.getNombre()+ " " + ControladorBusquedas.getRuta() + " " + ControladorBusquedas.getExtension());
        //DBCursor cursor = ConsultasMongoDAO.obtenerFoto(ControladorBusquedas.getNombre(), ControladorBusquedas.getRuta(), ControladorBusquedas.getExtension());
        DBCursor cursor = ConsultasMongoDAO.obtenerFoto(nombre, ruta, extension);

        while (cursor.hasNext()) {
            DBObject next = cursor.next();
            id_foto = next.get("_id").toString();
            System.out.println(id_foto);
            Map toMap = next.toMap();

            Set keySet = toMap.keySet();

            System.out.println("clave-valores");
            for (Object s : keySet) {

                if (!(s.toString().equals("nombre_ruta") || s.toString().equals("nombre_foto") || s.toString().equals("extension") || s.toString().equals("tamaño") || s.toString().equals("_id"))) {

                    /*System.out.println(s.toString());
                     System.out.println(toMap.get(s).toString());
                     ConsultasMongoDAO.obtenerMetadatosPorDirectorio(id_foto, s.toString());
                    
                     } 
                     }
           
                     } */
                }

            }
        }
    }
}
