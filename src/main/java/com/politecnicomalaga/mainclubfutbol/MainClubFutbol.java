/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.politecnicomalaga.mainclubfutbol;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author mint
 */
public class MainClubFutbol {

    private static Equipo miEquipo;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean continuar = true;
        int opcion;

        do {
            //mostrar menú
            menuPrincipal();
            opcion = leerIntTeclado(sc);
            //Si se escoge una opcion diferente a generarOficina y la Oficina no existe, avisa de que hay que generarla primero.
            if (opcion > 1 && opcion < 8 && MainClubFutbol.miEquipo == null) {
                System.out.println("El Equipo no ha sido creado.");
            } else {
                switch (opcion) {
                    case 1:
                        generarEquipo(sc);
                        break;

                    case 2:
                        altaJugador(sc);
                        break;

                    case 3:
                        eliminarJugador(sc);
                        break;

                    case 4:
                        altaPortero(sc);
                        break;

                    case 5:
                        eliminarPortero(sc);
                        break;

                    case 6:
                        mostrarJugadoresMenores();
                        break;

                    case 7:
                        mostrarJugadoresTitulares();
                        break;

                    case 8:
                        saveEquipo();
                        break;

                    case 9:
                        loadOficina();
                        break;

                    case -1:
                        System.out.println("Introduzca un número como opción.");
                        break;

                    default:
                        continuar = false;
                        sc.close();
                        break;
                }
            }
        } while (continuar);
    }

    private static void menuPrincipal() {
        System.out.println("\n---------------------------------------");
        System.out.println("MENÚ");
        System.out.println("\n1. Crear/modificar Equipo.");
        System.out.println("2. Añadir Jugador.");
        System.out.println("3. Eliminar Jugador.");
        System.out.println("4. Añadir Portero.");
        System.out.println("5. Eliminar Portero.");
        System.out.println("6. Listar Jugadores menores de edad.");
        System.out.println("7. Listar titulares.");
        System.out.println("8. Guardar datos Equipo CSV.");
        System.out.println("9. Cargar datos Equipo CSV.");
        System.out.println("Otra opción: SALIR");
        System.out.println("---------------------------------------");
        System.out.println("\nSeleccione una opción:");
    }

    // Recogemos un número de teclado, si nos dan algo que no es un número, ponemos
    // -1 para repetir entrada
    private static int leerIntTeclado(Scanner sc) {
        int iOpcion;
        try {
            iOpcion = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e) {
            sc.nextLine();
            return -1;
        }
        return iOpcion;
    }

    // Recogemos un número float de teclado, si nos dan algo que no es un número
    // float, ponemos -1f
    private static float leerFloatTeclado(Scanner sc) {
        float fOpcion;
        try {
            fOpcion = sc.nextFloat();
            sc.nextLine();
        } catch (InputMismatchException e) {
            sc.nextLine();
            return -1f;
        }
        return fOpcion;
    }

    // Recoger un string de teclado
    private static String leerStringTeclado(Scanner sc) {

        String sEntrada;
        try {
            sEntrada = sc.nextLine();
        } catch (InputMismatchException e) {
            return "";
        }
        return sEntrada;
    }

    private static void generarEquipo(Scanner sc) {
        String nombre, ciudad, email;

        System.out.println("\n---------------------------------------");
        System.out.println("GENERAR EQUIPO");
        System.out.println("---------------------------------------");
        //Pido los datos y los almaceno.
        System.out.println("Nombre del Equipo:");
        nombre = leerStringTeclado(sc);
        System.out.println("Ciudad del Equipo:");
        ciudad = leerStringTeclado(sc);
        System.out.println("Email del Equipo:");
        email = leerStringTeclado(sc);
        System.out.println("---------------------------------------");

        //Si la Oficina no existe, la creo. Si existe, cambio los datos.
        if (miEquipo == null) {
            miEquipo = new Equipo(nombre, ciudad, email);
            System.out.println("Equipo generada con éxito.");
        } else {
            miEquipo.setNombre(nombre);
            miEquipo.setCiudad(ciudad);
            miEquipo.setEmail(email);
            System.out.println("Los datos del Equipo se han actualizado con éxito.");
        }
    }

    private static void altaJugador(Scanner sc) {
        String dni, nombre, apellidos, telefono, email;
        int yNacimiento, goles, dorsal;

        System.out.println("\n---------------------------------------");
        System.out.println("ALTA JUGADOR");
        System.out.println("---------------------------------------");
        //Pido los datos y los almaceno.
        System.out.println("Nombre:");
        nombre = leerStringTeclado(sc);
        System.out.println("Apellidos:");
        apellidos = leerStringTeclado(sc);
        System.out.println("DNI:");
        dni = leerStringTeclado(sc);
        System.out.println("Email:");
        email = leerStringTeclado(sc);
        System.out.println("Teléfono:");
        telefono = leerStringTeclado(sc);
        System.out.println("Año nacimiento:");
        yNacimiento = leerIntTeclado(sc);
        System.out.println("Dorsal:");
        dorsal = leerIntTeclado(sc);
        System.out.println("Goles:");
        goles = leerIntTeclado(sc);

        System.out.println("---------------------------------------");

        Jugador j = new Jugador(nombre, apellidos, dni, email, telefono, yNacimiento, dorsal, goles);
        if (miEquipo.addJugador(j)) {
            System.out.println("Jugador dado de alta con éxito.");
        } else {
            System.out.println("El Jugador no se ha podido dar de alta.");
        }
    }

    private static void altaPortero(Scanner sc) {
        String dni, nombre, apellidos, telefono, email;
        int yNacimiento, goles, dorsal, golesEncajados;

        System.out.println("\n---------------------------------------");
        System.out.println("ALTA PORTERO");
        System.out.println("---------------------------------------");
        //Si ambos porteros tienen valores...
        if (miEquipo.getPorteros()[0] != null && miEquipo.getPorteros()[1] != null) {            
            System.out.println("La lista de porteros ya está llena");
        } else {
            //Pido los datos y los almaceno.
            System.out.println("Nombre:");
            nombre = leerStringTeclado(sc);
            System.out.println("Apellidos:");
            apellidos = leerStringTeclado(sc);
            System.out.println("DNI:");
            dni = leerStringTeclado(sc);
            System.out.println("Email:");
            email = leerStringTeclado(sc);
            System.out.println("Teléfono:");
            telefono = leerStringTeclado(sc);
            System.out.println("Año nacimiento:");
            yNacimiento = leerIntTeclado(sc);
            System.out.println("Dorsal:");
            dorsal = leerIntTeclado(sc);
            System.out.println("Goles:");
            goles = leerIntTeclado(sc);
            System.out.println("Goles encajados:");
            golesEncajados = leerIntTeclado(sc);
            System.out.println("---------------------------------------");

            Portero p = new Portero(nombre, apellidos, dni, email, telefono, yNacimiento, dorsal, goles, golesEncajados);
            if (miEquipo.addPortero(p)) {
                System.out.println("Portero dado de alta con éxito.");
            } else {
                System.out.println("El Portero no se ha podido dar de alta.");
            }
        }
    }

    private static void eliminarJugador(Scanner sc) {
        String dni;

        System.out.println("\n---------------------------------------");
        System.out.println("ELIMINAR JUGADOR");
        System.out.println("---------------------------------------");

        System.out.println("DNI:");
        dni = leerStringTeclado(sc);

        if (miEquipo.eliminaJugador(dni)) {
            System.out.println("Jugador eliminado con éxito.");
        } else {
            System.out.println("El Jugador no se ha podido eliminar.");
            System.out.println("Comprube que el DNI introducido es correcto.");
        }
    }

    private static void eliminarPortero(Scanner sc) {
        String dni;

        System.out.println("\n---------------------------------------");
        System.out.println("ELIMINAR PORTERO");
        System.out.println("---------------------------------------");

        System.out.println("DNI:");
        dni = leerStringTeclado(sc);

        if (miEquipo.eliminaPortero(dni)) {
            System.out.println("Portero eliminado con éxito.");
        } else {
            System.out.println("El Portero no se ha podido eliminar.");
            System.out.println("Compruebe que el DNI introducido es correcto.");
        }
    }

    private static void mostrarJugadoresMenores() {

        System.out.println("\n---------------------------------------");
        System.out.println("MOSTRAR MENORES");
        System.out.println("---------------------------------------");

        if (!miEquipo.menoresEdad().isEmpty()) {
            ArrayList<Jugador> j = miEquipo.menoresEdad();
            for (int i = 0; i < j.size(); i++) {
                Jugador jg = j.get(i);
                System.out.println(jg.toString());
            }
        } else {
            System.out.println("Aún no se ha añadido ningún jugador menor de edad.");
        }
    }

    private static void mostrarJugadoresTitulares() {

        System.out.println("\n---------------------------------------");
        System.out.println("MOSTRAR JUGADORES TITULARES");
        System.out.println("---------------------------------------");

        if (miEquipo.jugadoresTitulares() != null) {
            ArrayList<Jugador> j = miEquipo.jugadoresTitulares();
            for (int i = 0; i < j.size(); i++) {
                Jugador jg = j.get(i);
                System.out.println(jg.toString());
            }
        } else {
            System.out.println("El equipo no cuenta con suficientes jugadores o porteros para hacer una alineación titular.");
        }
    }

    private static void saveEquipo() {
        try {
            if (ControladorFicheros.grabarEquipoCSV(miEquipo, "Equipo.csv")) {
                System.out.println("Se ha exportado el equipo...");
            } else {
                System.out.println("Algo ha ido mal.");
            }

        } catch (IOException io) {
            System.out.println("No se ha encontrado el archivo.");
        }
    }

    private static void loadOficina() {
        //leerEquipoCSV devuelve un objeto Equipo, este lo guardo en la variable static miEquipo
        miEquipo = ControladorFicheros.leerEquipoCSV("Equipo.csv");
        System.out.println("Fichero cargado con éxito.");
    }

}
