/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.entities.UsuarioEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import co.edu.uniandes.csw.carpooling.adapters.DateAdapter;

/**
 *
 * @author Nicolas Fajardo
 */
public class ConductorDTO extends UsuarioDTO implements Serializable{
    
    public ConductorDTO(){
        
    }
   
    public ConductorDTO(ConductorEntity entity){
        //super(entity);  
    }
    
    public ConductorEntity toEntity(){
        ConductorEntity entity = new ConductorEntity();
        entity.setId(this.getId());
        entity.setNombre(this.getNombre());
        entity.setTelefono(this.getTelefono());
        entity.setCorreo(this.getCorreo());
        entity.setNumDocumento(this.getNumDocumento());
        entity.setContrasenha(this.getContrasenha());
       // entity.setFechaDeNacimiento(this.getFechaDeNacimiento());
        entity.setTipoDocumento(this.getTipoDocumento());
        return entity;
    }
    
    
}
