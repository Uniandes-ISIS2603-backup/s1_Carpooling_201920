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
public class ConductorDTO implements Serializable{
    
    private Long id;
    private String nombre;
    private String telefono;
    private String correo;
    private String numDocumento;
    private String contrasenha;
    
    @XmlJavaTypeAdapter(DateAdapter.class)
    protected Date fechaDeNacimiento;
    
    private UsuarioEntity.TIPO_DE_DOCUMENTO tipoDocumento;
    
    public ConductorDTO(){
        
    }
   
    public ConductorDTO(ConductorEntity entity){
        super(entity);  
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
