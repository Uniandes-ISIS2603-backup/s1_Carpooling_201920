/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;

/**
 *
 * @author Estudiante
 */
public class ConductorDTO {
  
        //Creada solo para no generar error
    public ConductorDTO()
    {
        
    }
    
    public ConductorDTO(ConductorEntity conductorEntity)
    {
        
    }
    
    public ConductorEntity toEntity()
    {
      return null;  
    }
    
}

