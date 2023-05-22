/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politecnicomalaga.mainclubfutbol;

/**
 *
 * @author mint
 */
public class Portero extends Jugador {

    protected int golesEncajados;

    public Portero(String nombre, String apellidos, String dni, String email, String telefono, int yNacimiento, int dorsal, int goles, int golesEncajados) {
        super(nombre, apellidos, dni, email, telefono, yNacimiento, dorsal, goles);
        if (golesEncajados < 0) {
            this.golesEncajados = 0;
        } else {
            this.golesEncajados = golesEncajados;
        }
    }

    public Portero(String sCadenaCSV) {        
        super(sCadenaCSV);
        
        //Le quito el \n que se añade al final de cada JUGADOR
        String[] lineas = sCadenaCSV.split("\n");
        //Divido por ':' para obtener en columnas[1] los datos
        String[] columnas = lineas[0].split(":");
        //Divido los datos por ';'
        String datos[] = columnas[1].split(";");
        //Si vienen a null los datos se lo salta (los porteros pueden venir a null)
        if("null".equals(datos[0])){
            return;
        }
        //Guardo el último dato
        this.golesEncajados = Integer.parseInt(datos[8]);                
    }

    public int getGolesEncajados() {
        return golesEncajados;
    }

    public void setGolesEncajados(int golesEncajados) {
        if (golesEncajados < 0) {
            this.golesEncajados = this.golesEncajados;
        } else {
            this.golesEncajados += golesEncajados;
        }
    }

    @Override
    public String toString() {
        return String.format("PORTERO:%s;%s;%s;%s;%s;%d;%d;%d;%d\n", nombre, apellidos, dni, email, telefono, yNacimiento, dorsal, goles, golesEncajados);
    }
}
