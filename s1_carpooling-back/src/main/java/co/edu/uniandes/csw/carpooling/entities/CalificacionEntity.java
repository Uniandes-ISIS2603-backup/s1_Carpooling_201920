/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Juan David Alarcón
 */
//La anotación le indica a JPA que esta clase corresponderá a una tabla
@Entity
public class CalificacionEntity extends BaseEntity implements Serializable {
    
    private Integer puntuacion;
    private String comentarios;

    @PodamExclude
    @ManyToOne
    private ViajeEntity viaje;

    @PodamExclude
    @ManyToOne
    private ConductorEntity conductor;
    
    @PodamExclude
    @ManyToOne
    private ViajeroEntity viajero;
    
    
    public ViajeEntity getViaje() {
        return viaje;
    }

    public void setViaje(ViajeEntity viaje) {
        this.viaje = viaje;
    }

    public ConductorEntity getConductor() {
        return conductor;
    }

    public void setConductor(ConductorEntity conductor) {
        this.conductor = conductor;
    }

    public ViajeroEntity getViajero() {
        return viajero;
    }

    public void setViajero(ViajeroEntity viajero) {
        this.viajero = viajero;
    }
    
    /**
     * @return the puntuacion
     */
    public Integer getPuntuacion() {
        return puntuacion;
    }

    /**
     * @param puntuacion the puntuacion to set
     */
    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    /**
     * @return the comentarios
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    
    
}
