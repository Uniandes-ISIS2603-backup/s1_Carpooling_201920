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
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the numDocumento
     */
    public String getNumDocumento() {
        return numDocumento;
    }

    /**
     * @param numDocumento the numDocumento to set
     */
    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    /**
     * @return the contrasenha
     */
    public String getContrasenha() {
        return contrasenha;
    }

    /**
     * @param contrasenha the contrasenha to set
     */
    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    /**
     * @return the tipoDocumento
     */
    public UsuarioEntity.TIPO_DE_DOCUMENTO getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(UsuarioEntity.TIPO_DE_DOCUMENTO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
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
