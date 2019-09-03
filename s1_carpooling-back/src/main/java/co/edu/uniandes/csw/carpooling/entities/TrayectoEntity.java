/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Juan David Serrano
 */
@Entity
public class TrayectoEntity extends BaseEntity implements Serializable{
    
    private Integer numPeajes;
    
    private Integer duracion;
    
    private Double costoCombustible;
    
    private String origen;
    
    private String destino;
    

    /**
     * @return the numPeajes
     */
    public Integer getNumPeajes() {
        return numPeajes;
    }

    /**
     * @param numPeajes the numPeajes to set
     */
    public void setNumPeajes(Integer numPeajes) {
        this.numPeajes = numPeajes;
    }

    /**
     * @return the duracion
     */
    public Integer getDuracion() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    /**
     * @return the costoCombustible
     */
    public Double getCostoCombustible() {
        return costoCombustible;
    }

    /**
     * @param costoCombustible the costoCombustible to set
     */
    public void setCostoCombustible(Double costoCombustible) {
        this.costoCombustible = costoCombustible;
    }

    /**
     * @return the origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen the origen to set
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * @return the destino
     */
    public String getDestino() {
        return destino;
    }

    /**
     * @param destino the destino to set
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }
    
}
