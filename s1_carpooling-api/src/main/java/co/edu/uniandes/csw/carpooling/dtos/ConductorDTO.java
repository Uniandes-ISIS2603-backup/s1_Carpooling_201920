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
     ConductorEntity entidad = new ConductorEntity();
       entidad.setId(this.getId());
       entidad.setContrasenha(this.getContrasenha());
       entidad.setCorreo(this.getCorreo());
       entidad.setFechaDeNacimiento(this.getFechaDeNacimiento());
       entidad.setNombre(this.getCorreo());
       entidad.setNumDocumento(this.getNumDocumento());
       entidad.setTelefono(this.getTelefono());
       entidad.setTipoDocumento(this.getTipoDocumento());
       return entidad;
   }
    
    
}
