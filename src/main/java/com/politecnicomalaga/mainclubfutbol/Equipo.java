/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politecnicomalaga.mainclubfutbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mint
 */
public class Equipo {

    private String nombre;
    private int puntuacion;
    private String ciudad;
    private String email;

    private Map<String, Jugador> jugadores;
    private Portero[] porteros;

    private boolean inactivo;

    public Equipo(String nombre, String ciudad, String email) {
        this.nombre = nombre;
        this.puntuacion = 0;
        this.ciudad = ciudad;
        this.email = email;

        this.jugadores = new HashMap<>();
        //Array de tamaño 2.
        this.porteros = new Portero[2];
        //Está inactivo porque a la hora de crear el equipo no hay Jugadores ni Porteros.
        this.inactivo = true;
    }

    public Equipo(String sCadenaCSV) {
        //Divido las líneas por salto de línea y las almaceno en un Array.
        String[] lineas = sCadenaCSV.split("\n");
        //Divido estas líneas en columnas por el :.
        //En la primera columna de la segunda fila, debe estar el nombre de la clase.
        //Es la segunda fila porque al leerlo se introduce un \n al inicio.
        String[] columnas = lineas[1].split(":");

        if (columnas[0].equals("EQUIPO")) {
            //Si es Oficina, tenemos un objeto Oficina.
            String[] datos = columnas[1].split(";");
            this.nombre = datos[0];
            this.puntuacion = Integer.parseInt(datos[1]);
            this.ciudad = datos[2];
            this.email = datos[3];
            this.inactivo = Boolean.parseBoolean(datos[4]);
        } else {
            //Si no, nos salimos.
            return;
        }

        //Inicializo el Map de jugadores
        this.jugadores = new HashMap<>();
        //Divido por JUGADOR para obtener los jugadores que tengo.
        String[] jugadoresPosibles = sCadenaCSV.split("JUGADOR");
        String miJugadorCSV;

        for (int i = 1; i < jugadoresPosibles.length; i++) {
            //Recorro los jugadores y lo guardo en el String, añadiéndole el JUGADOR que se ha eliminado antes.
            miJugadorCSV = "JUGADOR" + jugadoresPosibles[i];
            //Este String lo paso al constructor de Jugador por CSV.
            Jugador c = new Jugador(miJugadorCSV);
            //Añado este jugador a la lista.
            jugadores.put(c.getDni(), c);
        }

        //Inicializo el Array de porteros
        this.porteros = new Portero[2];

        String[] porterosPosibles = sCadenaCSV.split("PORTERO");
        String miPorteroCSV;

        for (int i = 1; i < porterosPosibles.length; i++) {
            //Recorro los porteros y lo guardo en el String, añadiéndole el PORTERO que se ha eliminado antes.
            miPorteroCSV = "PORTERO" + porterosPosibles[i];
            //Este String lo paso al constructor de Jugador por CSV.
            Portero p = new Portero(miPorteroCSV);
            //Añado este portero a la lista.
            //Si me han pasado un portero con el año de nacimiento a 0, significa que era null
            if(p.getyNacimiento() == 0){
                //Inicializo a null
                porteros[i-1] = null;
            }else{
                //En otro caso guardo el portero
                porteros[i - 1] = p;
            }            
        }
    }

    public boolean addJugador(Jugador j) {
        //Añadimos el jugador al HashMap.
        //Lo que devuelve lo guardamos en una objeto tipo Jugador.
        //Si lo que devuelve es null, se ha añadido correctamente. Si devuelve otro jugador, significa que ya había un juagador con ese mismo DNI (key).
        Jugador antigua = jugadores.put(j.getDni(), j);
        //En caso de que devuelva un Jugador...
        if (antigua != null) {
            //Volvemos a poner el Jugador que nos ha devuelto donde estaba, y devolvemos null porque no se ha podido añadir el nuevo Jugador.
            jugadores.put(antigua.getDni(), antigua);
            return false;
        }
        //Si devolvió null, se ha añadido el  Jugador correctamente.
        return true;
    }

    public boolean addPortero(Portero p) {
        //Si no hay portero en la posicion 0...
        if (porteros[0] == null) {
            //Comparo los dni
            if (porteros[1] != null) {
                if (p.getDni().equals(porteros[1].getDni())) {
                    return false;
                }
            }
            porteros[0] = p;
            return true;
        }

        if (porteros[1] == null) {
            //Comparo los dni
            if (porteros[0] != null) {
                if (p.getDni().equals(porteros[0].getDni())) {
                    return false;
                }
            }
            porteros[1] = p;
            return true;
        }
        //Ya hay dos porteros asi que no se puede añadir ninguno más.
        return false;
    }

    public boolean eliminaJugador(String dni) {
        //Le pido que busque un Jugador con ese DNI.
        //Si lo encuentra...
        if (jugadores.get(dni) != null) {
            //Lo elimina
            jugadores.remove(dni);
            return true;
        }
        //En caso de que no lo encuentre (devuelve null), se devuelve false porque no existe ese jugador.
        return false;
    }

    public boolean eliminaPortero(String dni) {
        //Recorro el Array de porteros
        for (int i = 0; i < porteros.length; i++) {
            //Controlo que no sea null
            if (porteros[i] != null) {
                //Si el dni obtenido por parámetro es igual que el dni del portero...
                if (dni.equals(porteros[i].getDni())) {
                    //Lo elimino y devuelvo true
                    porteros[i] = null;
                    return true;
                }
            }
        }
        //Se devuelve false en caso de no haber porteros o que ninguno coincida con el dni obtenido por parámetros
        return false;
    }

    public boolean isActivo() {
        //Si hay al menos 1 portero y al menos 5 jugadores...
        if ((porteros[0] != null || porteros[1] != null) && jugadores.size() > 4) {
            //Está activo y devuelve true.
            this.setInactivo(false);
            return true;
        }
        //En otro caso, está inactivo y devuelve false.
        this.setInactivo(true);
        return false;
    }

    public ArrayList<Jugador> menoresEdad() {
        ArrayList<Jugador> menores = new ArrayList<>();
        for (Map.Entry<String, Jugador> entry : jugadores.entrySet()) {
            //Esto es un forEach para HashMap 
            //Guardo los valores en el objeto Jugador
            Jugador j = entry.getValue();
            //Si el Jugador no es mayor de Edad...            
            if (!j.mayorEdad()) {
                menores.add(j);
            }
        }

        for (Portero portero : porteros) {
            if (portero != null) {
                //Si el portero es menor de edad...
                if (!portero.mayorEdad()) {
                    menores.add(portero);
                }
            }
        }
        //Ordeno la lista por Apellidos con comparator.
        Collections.sort(menores, new JugadorComparadorApellidos());
        //Ahora, una vez ordenada, los ordeno por Nombre.
        //Collections.sort(menores, new JugadorComparadorNombre());
        return menores;
    }

    public ArrayList<Jugador> jugadoresTitulares() {
        ArrayList<Jugador> titulares = new ArrayList<>();

        //Si no está activo, o sea, no tiene al menos 1 portero y 5 jugadores...
        if (!isActivo()) {
            return null;
        }
        //Si hay dos porteros...
        if (porteros[0] != null && porteros[1] != null) {
            //Añado el que menos goles encajados tenga
            if (porteros[0].getGolesEncajados() <= porteros[1].getGolesEncajados()) {
                titulares.add(porteros[0]);
            } else {
                titulares.add(porteros[1]);
            }
            //Si el primer portero es el único que existe, lo añado.
        } else if (porteros[0] != null) {
            titulares.add(porteros[0]);
            //Si el segundo portero es el único que existe, lo añado.
        } else {
            titulares.add(porteros[1]);
        }
        //Creo una lista para almacenar en ella los jugadores. Esto lo hago porque no se puede hacer sort a un HashMap
        List<Jugador> lista = new ArrayList<>(jugadores.values());
        //Ordeno los jugadores por goles.
        Collections.sort(lista, new JugadorComparadorGoles());
        for (int i = 0; i < 5; i++) {
            titulares.add(lista.get(i));
        }

        return titulares;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(Map<String, Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public Portero[] getPorteros() {
        return porteros;
    }

    public void setPorteros(Portero[] porteros) {
        this.porteros = porteros;
    }

    public boolean isInactivo() {
        return inactivo;
    }

    public void setInactivo(boolean inactivo) {
        this.inactivo = inactivo;
    }

    @Override
    public String toString() {
        String cadena = String.format("EQUIPO:%s;%d;%s;%s;%b\n", nombre, puntuacion, ciudad, email, inactivo);
        for (Portero p : porteros) {
            if (p == null) {
                cadena += "PORTERO:null;null;null;null;null;null;null;null;null\n";
            } else {
                cadena += p.toString();
            }
        }
        for (Map.Entry<String, Jugador> entry : jugadores.entrySet()) {
            //Esto es un forEach para HashMap
            //Paso a CSV este objeto.
            cadena += entry.getValue().toString();
        }
        return cadena;
    }

}
