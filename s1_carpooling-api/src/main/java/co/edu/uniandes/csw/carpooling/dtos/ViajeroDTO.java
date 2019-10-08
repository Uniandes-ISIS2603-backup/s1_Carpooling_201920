/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.entities.ViajeroEntity;
import java.io.Serializable;

/**
 *
 * @author Santiago Ballesteros
 */
public class ViajeroDTO extends UsuarioDTO implements Serializable {
    
    public ViajeroDTO(){
        super();
    }
   
    public ViajeroDTO(ViajeroEntity entity){
        super(entity);  
    }
    
    public ViajeroEntity toEntity(){
        ViajeroEntity entity = new ViajeroEntity();
        entity.setId(super.getId());
        entity.setNombre(super.getNombre());
        entity.setTelefono(super.getTelefono());
        entity.setCorreo(super.getCorreo());
        entity.setNumDocumento(super.getNumDocumento());
        entity.setContrasenha(super.getContrasenha());
        entity.setFechaDeNacimiento(super.getFechaDeNacimiento());
        entity.setTipoDocumento(super.getTipoDocumento());
        return entity;
    }
}
