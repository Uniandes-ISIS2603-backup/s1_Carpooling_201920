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
        if(entity!=null){
        this.id = entity.getId();
        this.nombre = entity.getNombre();
        this.telefono = entity.getTelefono();
        this.correo = entity.getCorreo();
        this.numDocumento = entity.getNumDocumento();
        this.contrasenha = entity.getContrasenha();
        this.fechaDeNacimiento = entity.getFechaDeNacimiento();
        this.tipoDocumento = entity.getTipoDocumento();
        }  
    }
    
    /**
     * Retorna una entidad de este elemento
     * @return este elemento en forma de entidad
     */
    public ConductorEntity toEntity(){
        ConductorEntity entity = new ConductorEntity();
        super.toEntity(entity);
        return entity;
    }
    
    
}
