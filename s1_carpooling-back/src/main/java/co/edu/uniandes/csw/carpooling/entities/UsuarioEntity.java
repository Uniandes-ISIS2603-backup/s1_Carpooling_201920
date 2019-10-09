/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.entities;

import co.edu.uniandes.csw.carpooling.podam.DateStrategy;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;
import uk.co.jemos.podam.common.PodamStringValue;

/**
 *
 * @author Santiago Ballesteros
 * @author Nicolas Fajardo
 */
@MappedSuperclass
public abstract class UsuarioEntity extends BaseEntity{
    
    public enum TIPO_DE_DOCUMENTO {
        CEDULA_DE_CIUDADANA,
        TARJETA_DE_IDENTIDAD,
        PASAPORTE,
        CEDULA_DE_EXTRANJERIA
    }

    /*
    *   Atributos
    */
    private String nombre;
    private String telefono;
    private String correo;
    private String numDocumento;
       
    @Size(min = 5)
    private String contrasenha;

    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaDeNacimiento;
    

    
    

    private TIPO_DE_DOCUMENTO tipoDocumento;

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

}
