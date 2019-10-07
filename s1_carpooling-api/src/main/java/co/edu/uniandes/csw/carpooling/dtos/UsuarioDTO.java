/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.adapters.DateAdapter;
import co.edu.uniandes.csw.carpooling.entities.UsuarioEntity;
import co.edu.uniandes.csw.carpooling.entities.UsuarioEntity.TIPO_DE_DOCUMENTO;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Nicolás Fajardo
 */
public class UsuarioDTO implements Serializable{
    private Long id;
    protected String nombre;
    protected String telefono;
    protected String correo;
    protected String numDocumento;
    protected String contrasenha;
    @XmlJavaTypeAdapter(DateAdapter.class)
    protected Date fechaDeNacimiento;
    protected TIPO_DE_DOCUMENTO tipoDocumento;

    
    public UsuarioDTO(){
        
    }
    
    public UsuarioDTO(UsuarioEntity entity){
        this.id = entity.getId();
        this.nombre = entity.getNombre();
        this.telefono = entity.getTelefono();
        this.correo = entity.getCorreo();
        this.numDocumento = entity.getNumDocumento();
        this.contrasenha = entity.getContrasenha();
        this.fechaDeNacimiento = entity.getFechaDeNacimiento();
        this.tipoDocumento = entity.getTipoDocumento();
    }
    
    /**
     TIPO_DE_DOCUMENTO* @return the nombre
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
     * @return the fechaDeNacimiento
     */
    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    /**
     * @param fechaDeNacimiento the fechaDeNacimiento to set
     */
    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    /**
     * @return the tipoDocumento
     */
    public TIPO_DE_DOCUMENTO getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(TIPO_DE_DOCUMENTO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * @return the id
     */
    protected Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    
}
