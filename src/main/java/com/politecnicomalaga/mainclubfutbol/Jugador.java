/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politecnicomalaga.mainclubfutbol;

import java.time.YearMonth;

/**
 *
 * @author mint
 */
public class Jugador {

    protected String nombre;
    protected String apellidos;
    protected String dni;
    protected String email;
    protected String telefono;
    protected int yNacimiento;
    protected int dorsal;
    protected int goles;

    public Jugador(String nombre, String apellidos, String dni, String email, String telefono, int yNacimiento, int dorsal, int goles) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        if (yNacimiento < 1900 || yNacimiento > YearMonth.now().getYear()) {
            //Si el año de nacimiento que nos pasan es menor de 1900 o mayor que el año actual, le asignamos 2023.
            this.yNacimiento = 2023;
        } else {
            //En otro caso, el pasado por parámetro.
            this.yNacimiento = yNacimiento;
        }
        //Controlo el dorsal introducido.
        if (dorsal < 1 || dorsal > 99) {
            //Si el dorsal no está comprendido entre 1 y 99, se le asigna 100.
            this.dorsal = 100;
        } else {
            //En caso contrario, se asigna el pasado por parámetro.
            this.dorsal = dorsal;
        }
        //Controlo los goles introducidos.
        if (goles < 0) {
            //Si es menor que 0, se le asigna 0.
            this.goles = 0;
        } else {
            //En otro caso, se asigna el pasado por parámetro.
            this.goles = goles;
        }

    }

    //Constructor CSV
    public Jugador(String sCadenaCSV) {
        //Le quito el \n que se añade al final de cada JUGADOR
        String[] lineas = sCadenaCSV.split("\n");
        //Divido por ':' para obtener en columnasJugador[1] los datos
        String[] columnasJugador = lineas[0].split(":");
        //Divido los datos por ';'
        String[] datos = columnasJugador[1].split(";");
        //Si vienen a null los datos se lo salta (los porteros pueden venir a null)
        if("null".equals(datos[0])){
            return;
        }
        //Guardo los datos de jugador
        this.nombre = datos[0];
        this.apellidos = datos[1];
        this.dni = datos[2];
        this.email = datos[3];
        this.telefono = datos[4];
        this.yNacimiento = Integer.parseInt(datos[5]);
        this.dorsal = Integer.parseInt(datos[6]);
        this.goles = Integer.parseInt(datos[7]);

    }

    public boolean mayorEdad() {
        //Con YearMonth.now().getYear() obtengo el año actual.
        int yActual = YearMonth.now().getYear();
        int edad = yActual - yNacimiento;

        //Si edad es mayor o igual que 18, devuelve true. En caso contrario, devuelve false.
        return edad >= 18;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getyNacimiento() {
        return yNacimiento;
    }

    public void setyNacimiento(int yNacimiento) {
        if (yNacimiento < 1900 || yNacimiento > YearMonth.now().getYear()) {
            //Si el año de nacimiento que nos pasan es menor de 1900 o mayor que el año actual, le asignamos 2023.
            this.yNacimiento = 2023;
        } else {
            //En otro caso, el pasado por parámetro.
            this.yNacimiento = yNacimiento;
        }
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(byte dorsal) {
        if (dorsal < 1 || dorsal > 99) {
            //Si el dorsal no está comprendido entre 1 y 99, se le deja el que tenía.
            this.dorsal = this.dorsal;
        } else {
            //En caso contrario, se asigna el pasado por parámetro.
            this.dorsal = dorsal;
        }
    }

    public int getGoles() {
        return goles;
    }

    public void setGoles(int goles) {
        if (goles < 0) {
            //Si los goles son menor que 0, se le deja el que tenía.
            this.goles = this.goles;
        } else {
            //En caso contrario, se suma el pasado por parámetro.
            this.goles += goles;
        }
    }

    @Override
    public String toString() {
        return String.format("JUGADOR:%s;%s;%s;%s;%s;%d;%d;%d\n", nombre, apellidos, dni, email, telefono, yNacimiento, dorsal, goles);

    }

}
