/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Clases;

import static java.lang.Math.log;
import static java.lang.StrictMath.log;

/**
 *
 * @author inftel08
 */
public class CoordenadaUtils {
    
   
 /*
    public static Double[] toDecimal(String latitude, String longitude) {
        try {
            String[] lat = latitude.replaceAll("[^0-9.\\s-]", "").split(" ");
            String[] lng = longitude.replaceAll("[^0-9.\\s-]", "").split(" ");
            Double dlat = toDecimal(lat); 
            Double dlng = toDecimal(lng);
            return new Double[]{dlat, dlng};
        } catch(Exception ex) {
            //log.error(String.format("Error en el formato de las coordenadas: %s %s", new Object[]{latitude, longitude}), ex);
            return null;
        }
    }
 
    public static Double toDecimal(String latOrLng) {
        try {
            String[] latlng = latOrLng.replaceAll("[^0-9.\\,s-]", "").split(" ");
            //Double dlatlng = toDecimal(latlng); 
            return dlatlng;
        } catch(Exception ex) {
            //log.error(String.format("Error en el formato de las coordenadas: %s ", new Object[]{latOrLng}), ex);
            return null;
        }
    }*/
 
    public static Double toDecimalaUsar(String[] coord) {
        double d = Double.parseDouble(coord[0]);
        double m = Double.parseDouble(coord[1]);
        double s = Double.parseDouble(coord[2]);
        double signo = 1;
        if (coord[0].startsWith("-"))
            signo = -1;
        return signo * (Math.abs(d) + (m / 60.0) + (s / 3600.0));
    }
 
    public static void main(String[] args) {
        //Double[] coord = toDecimal("40° 20.2' 55.68\"", "3° 21' 3.29\"");
        //Double res = toDecimal("-4° 28' 42,33");
        //System.out.println(res);
    }
 
}
    

