/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.entities.UsuarioEntity;

/**
 *
 * @author Estudiante
 */
public class UsuarioDTO {
  
        //Creada solo para no generar error
    public UsuarioDTO()
    {
        
    }
    
    public UsuarioDTO(UsuarioEntity usuarioEntity)
    {
        
    }
    
    public UsuarioEntity toEntity()
    {
      return null;  
    }
    
}

