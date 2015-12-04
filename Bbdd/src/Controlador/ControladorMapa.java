/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vistas.VistaMapa;
import java.awt.Dimension;
import java.awt.Image;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import maps.java.Geocoding;
import maps.java.StaticMaps;

/**
 *
 * @author inftel08
 */
public class ControladorMapa {
    
    VistaMapa mapa;

    public ControladorMapa(VistaMapa mapa) {
        this.mapa = mapa;
  
    }
    
    public void verMapa(String lon, String lat) throws MalformedURLException {
        StaticMaps ObjStatMap=new StaticMaps();
        
        //Cambiar a direcciones  "Madrid, Puerta del Sol"
        Geocoding ObjGeocod = new Geocoding();
        //ArrayList<String> resultadoCI=
        //57° 46' 32,57 lat
        //-5° 35' 59,65 lon
         String[] horaLat=lat.split("° ");
         String[] minutosLat=horaLat[1].split("' ");
         String[] segundosLat=minutosLat[1].split(",");
         
         String[] horaLon=lon.split("° ");
         String[] minutosLon=horaLon[1].split("' ");
         String[] segundosLon=minutosLon[1].split(",");
       // System.out.println("Lon: "+Integer.parseInt(horaLat[0])+" "+Integer.parseInt(minutosLat[0])+" "+Integer.parseInt(segundosLat[0]));
        double gradLat=Integer.parseInt(horaLat[0]);
        double minLat=Double.parseDouble(minutosLat[0])/60;
        double segLat;
        if (segundosLat.length > 1) {
            segLat=Double.parseDouble(segundosLat[0])/60;
        } else {
            segLat = 0.0;
        }
        
        
        double gradLon=Integer.parseInt(horaLon[0]);
        double minLon=Double.parseDouble(minutosLon[0])/60;
        double segLon;
        if (segundosLon.length > 1) {
            segLon=Double.parseDouble(segundosLon[0])/60;
        } else {
            segLon = 0.0;
        }
        //System.out.println("Lat: "+gradLat+" "+minutosLat+" "+segundosLat);
        //double LatResult= (double) Integer.parseInt(horaLat[0]) + 
        //        Integer.parseInt(minutosLat[0]) / 60 + 
        //        Integer.parseInt(segundosLat[0]) / 3600;
        
        
        
        double LatResult=gradLat+minLat+segLat;
        double LonResult=gradLon+minLon+segLon;
        System.out.println("Lat: "+LatResult );
        System.out.println("Lon: "+LonResult);
        
           Image  resultadoMapa = null;
        try {
            resultadoMapa = ObjStatMap.getStaticMap(ObjGeocod.getAddress(LonResult,LatResult).get(0),
                    8,new Dimension(400,400), 1, StaticMaps.Format.png, StaticMaps.Maptype.terrain);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ControladorMapa.class.getName()).log(Level.SEVERE, null, ex);
        }
           //Icon icono = new ImageIcon(resultadoMapa.getStatMap().getScaledInstance(miVista.getjLabel1().getWidth(), miVista.getjLabel1().getHeight(), Image.SCALE_DEFAULT));
           ImageIcon icon=new ImageIcon(resultadoMapa);
           mapa.getjLabelMapa().setIcon(icon);
        
         //ImageIcon img = new ImageIcon(nombre_fichero);
        
        
     

    }
    
    
    
}
