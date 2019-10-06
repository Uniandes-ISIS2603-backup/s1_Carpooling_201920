/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.entities;

import co.edu.uniandes.csw.carpooling.podam.DateStrategy;
import co.edu.uniandes.csw.carpooling.podam.FinalDateStrategy;
import co.edu.uniandes.csw.carpooling.podam.InitialDateStrategy;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Juan David Alarcón
 */
@Entity
public class ViajeRecurrenteEntity extends BaseEntity  implements Serializable{
   
    @Temporal(TemporalType.TIMESTAMP)
    @PodamStrategyValue(InitialDateStrategy.class)
    private Date fechaInicio;
    
    @Temporal(TemporalType.TIMESTAMP)
    @PodamStrategyValue(FinalDateStrategy.class)
    private Date fechaFin;
    
    private String frecuencia;
    
    @PodamExclude
    @ManyToOne
    private ConductorEntity conductor;

    @PodamExclude
    @OneToMany(mappedBy="viajeRecurrente")
    private List<ViajeEntity> viajes;

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the frecuencia
     */
    public String getFrecuencia() {
        return frecuencia;
    }

    /**
     * @param frecuencia the frecuencia to set
     */
    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public ConductorEntity getConductor() {
        return conductor;
    }

    public void setConductor(ConductorEntity conductor) {
        this.conductor = conductor;
    }

    public List<ViajeEntity> getViajes() {
        return viajes;
    }

    public void setViajes(List<ViajeEntity> viajes) {
        this.viajes = viajes;
    }
    
    
}
