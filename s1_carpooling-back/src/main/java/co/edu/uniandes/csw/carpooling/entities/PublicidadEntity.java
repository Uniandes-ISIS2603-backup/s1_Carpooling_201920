/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.entities;

import co.edu.uniandes.csw.carpooling.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Nicolas Fajardo
 */
@Entity
public class PublicidadEntity extends BaseEntity implements Serializable {

    /**
     * @return the publicista
     */
    public PublicistaEntity getPublicista() {
        return publicista;
    }

    /**
     * @param publicista the publicista to set
     */
    public void setPublicista(PublicistaEntity publicista) {
        this.publicista = publicista;
    }

    
    public enum DISPONIBILIDAD {
        UNA_SEMANA,
        TRES_SEMANAS
    }
    /*
        ATRIBUTOS
     */
    private String nombre;

    private String mensaje;

    private double costo;
    
    private DISPONIBILIDAD disponibilidadPublicidad;

    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaDeInicio;

    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaDeSalida;
    
    @PodamExclude
    @ManyToOne
    private PublicistaEntity publicista;

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
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the costo
     */
    public double getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(double costo) {
        this.costo = costo;
    }

    /**
     * @return the fechaDeInicio
     */
    public Date getFechaDeInicio() {
        return fechaDeInicio;
    }

    /**
     * @param fechaDeInicio the fechaDeInicio to set
     */
    public void setFechaDeInicio(Date fechaDeInicio) {
        this.fechaDeInicio = fechaDeInicio;
    }

    /**
     * @return the fechaDeSalida
     */
    public Date getFechaDeSalida() {
        return fechaDeSalida;
    }

    /**
     * @param fechaDeSalida the fechaDeSalida to set
     */
    public void setFechaDeSalida(Date fechaDeSalida) {
        this.fechaDeSalida = fechaDeSalida;
    }

    /**
     * @return the disponibilidadPublicidad
     */
    public DISPONIBILIDAD getDisponibilidadPublicidad() {
        return disponibilidadPublicidad;
    }

    /**
     * @param disponibilidadPublicidad the disponibilidadPublicidad to set
     */
    public void setDisponibilidadPublicidad(DISPONIBILIDAD disponibilidadPublicidad) {
        this.disponibilidadPublicidad = disponibilidadPublicidad;
    }
}
