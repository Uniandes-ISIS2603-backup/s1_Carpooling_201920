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
 * @author Nicolás Fajardo
 */
public class ConductorDTO extends UsuarioDTO implements Serializable {
    
    
    public ConductorDTO(){
        super();
    }
   
    public ConductorDTO(ConductorEntity entity){
        super(entity);  
    }
    
        public ConductorDTO(ConductorEntity entidad){
        if(entidad!=null){
        this.id = entidad.getId();
        this.correo = entidad.getCorreo();
        this.contrasenha = entidad.getContrasenha();
        this.nombre = entidad.getNombre();
        this.telefono = entidad.getTelefono();
        this.numDocumento = entidad.getNumDocumento();
        this.fechaDeNacimiento = entidad.getFechaDeNacimiento();
        this.tipoDocumento = entidad.getTipoDocumento();
        }
    }
    
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
