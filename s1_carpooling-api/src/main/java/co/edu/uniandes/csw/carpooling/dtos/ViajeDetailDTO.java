/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Estudiante
 */
public class ViajeDetailDTO extends ViajeDTO implements Serializable{
    
    
    private List<TrayectoDTO> trayectos;
    
    
    public ViajeDetailDTO(){
        
    }
    
    
    
}
