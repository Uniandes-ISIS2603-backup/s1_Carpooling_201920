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
    
    public ViajeroEntity toEntity(){
        ViajeroEntity entity = new ViajeroEntity();
        super.toEntity(entity);
        return entity;
    }
}
