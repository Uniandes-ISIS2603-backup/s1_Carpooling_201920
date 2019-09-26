/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import java.io.Serializable;

/**
 *
 * @author Nicolás Fajardo
 */
public class ConductorDTO implements Serializable {
    
    private String hola;
    public ConductorDTO(){
        super();
    }

    /**
     * @return the hola
     */
    public String getHola() {
        return hola;
    }

    /**
     * @param hola the hola to set
     */
    public void setHola(String hola) {
        this.hola = hola;
    }
}
