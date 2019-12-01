/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.entities;

import co.edu.uniandes.csw.carpooling.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
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
     * Enumeracion con las disponibilidades de la publicidad
     */
    public enum DISPONIBILIDAD {
        UNA_SEMANA,
        TRES_SEMANAS
    }
    /*
        ATRIBUTOS
     */
    
    /**
     * nombre de la publicidad
     */
    private String nombre;

    /**
     * El mensaje de la publicidad
     */
    private String mensaje;

    /**
     * El costo de la publicidad
     */
    private double costo;

    /**
     * La disponibilidad de la publicidad
     */
    private DISPONIBILIDAD disponibilidadPublicidad;

    
    /**
     * Fecha de inicio de la publicidad
     */
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaDeInicio;

    /**
     * Fecha de fin de la publicidad
     */
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaDeSalida;

    /**
     * Publicista duenho de la publicidad
     */
    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private PublicistaEntity publicista;

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Ajusta el nombre de la publicidad al ingresado por parametro
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
     * Ajusta el mensaje de la publicidad al mensaje ingresado por parametro
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
     * Ajusta el costo de la publicidad al ingresado por parametro
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
     * Ajusta la fecha de inicio de la publicidad con el valor ingresado por parametro
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
     * Ajusta la fecha de salida de la publicidad con el valor ingresado por parametro
     * @param fechaDeSalida the fechaDeSalida to set
     */
    public void setFechaDeSalida(Date fechaDeSalida) {
        this.fechaDeSalida = fechaDeSalida;
    }

    /**
     * @return the disponibilidadPublicidad
     */
    public DISPONIBILIDAD getDisponibilidad() {
        return disponibilidad;
    }

    /**
     * Ajusta la disponibilidad de la publicidad con el valor ingresado por parametro
     * @param disponibilidadPublicidad the disponibilidadPublicidad to set
     */
    public void setDisponibilidad(DISPONIBILIDAD disponibilidadPublicidad) {
        this.disponibilidad = disponibilidadPublicidad;
    }

    /**
     * @return the publicista
     */
    public PublicistaEntity getPublicista() {
        return publicista;
    }

    /**
     * Ajusta el publicista deuenho de la publicidad con el valor ingresado por parametro
     * @param publicista the publicista to set
     */
    public void setPublicista(PublicistaEntity publicista) {
        this.publicista = publicista;
    }

}
