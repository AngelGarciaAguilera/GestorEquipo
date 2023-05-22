/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politecnicomalaga.mainclubfutbol;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author mint
 */
public class ControladorFicheros {
    
    public static boolean writeText(String fileName, String data) {
        //Ayudante de escritura.
        PrintWriter out = null;

        try {
            //Al ayudante de escritura le pasamos el escritor con la ruta del fichero (FileWriter(fileName));
            out = new PrintWriter(new FileWriter(fileName));
            //LLamamos al metodo print() para escribir el texto que nos han pasado al fichero.
            out.print(data);
            //Aseguramos que todo pasa a disco
            out.flush(); 
        } catch (IOException io) {
            System.out.println(io.getMessage());
            return false;
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return true;
    }

    
    public static String readText(String fileName) {  
        //Ayudante de lectura.
        Scanner sc = null;
        String texto = "";
        
        try{
            //Al ayudante de lectura le pasamos el lector con la ruta del fichero (FileReader(fileName));
            sc = new Scanner(new FileReader (fileName));
            //Mientras el fichero tenga texto...
            while(sc.hasNext()){
                //Lo lee línea a línea y lo almacena en la variable String que hemos creado
                texto += "\n" + sc.nextLine();
            }
        }catch (IOException e) {
            return e.getMessage();
        }finally{
            if(sc != null){
                sc.close();
            }            
        }
        //si todo ha ido bien, devolvemos el texto.
        return texto;
    }
    
    public static boolean grabarEquipoCSV(Equipo miEquipo, String sNombreFichero) throws IOException {
        if(writeText(sNombreFichero, miEquipo.toString())){
            return true;
        }
        return false;
    }

    public static Equipo leerEquipoCSV(String sNombreFichero) {
        return new Equipo(readText(sNombreFichero));
    }
}
