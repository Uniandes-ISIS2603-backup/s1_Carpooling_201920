/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import java.io.Serializable;

/**
 *
 * @author Nicolas Fajardo
 */
public class ConductorDTO extends UsuarioDTO implements Serializable{
    
    /**
     * Constructor vacio
     */
    public ConductorDTO(){
        
    }
   
    /**
     * constructor de la superClase
     * @param entity el conductor a construir
     */
    public ConductorDTO(ConductorEntity entity){
        super(entity);  
    }
    
    /**
     * Retorna una entidad de este elemento
     * @return este elemento en forma de entidad
     */
    public ConductorEntity toEntity(){
        ConductorEntity entity = new ConductorEntity();
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
