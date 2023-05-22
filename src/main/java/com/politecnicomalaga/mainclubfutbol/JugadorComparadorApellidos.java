/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politecnicomalaga.mainclubfutbol;

import java.util.Comparator;

/**
 *
 * @author mint
 */
public class JugadorComparadorApellidos implements Comparator<Jugador>{

    @Override
    public int compare(Jugador o1, Jugador o2) {
        return o1.getApellidos().compareTo(o2.getApellidos()) + o1.getNombre().compareTo(o2.getNombre());
    }
    
}