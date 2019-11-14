/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.entities.TrayectoEntity;
import java.io.Serializable;

/**
 * ViajeDTO Objeto de transferencia de datos de Viajes. Los DTO contienen las
 * representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id":Long,
 *      "numPeajes": Integer,
 *      "duracion": Integer,
 *      "costoCombustible": Double,
 *      "origen": String,
 *      "destino": String  
 *   }
 * </pre> Por ejemplo un viaje se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id":1,
 *      "numPeajes": 14,
 *      "duracion": 145,
 *      "costoCombustible": 40000,
 *      "origen": "Calle 113 #7-5",
 *      "destino": "Carrera 12 # 13-18"        
 *   }
 *
 * </pre>
 *
 * @author Juan David Serrano
 */
public class TrayectoDTO implements Serializable{
    
    private Long id;
    
    private Integer numPeajes;
   
    private Integer duracion;
    
    private Double costoCombustible;
    
    private String origen;
    
    private String destino;
    
    /**
     * Constructor vacio
     */
    public TrayectoDTO(){
        
    }
    
    /**
     * Crea un objeto TrayectoDTO a partir de un objeto TrayectoEntity.
     * @param trayecto Entidad TrayectoEntity desde la cual se va a crear el
     * nuevo objeto.
     */
    public TrayectoDTO(TrayectoEntity trayecto){
        if(trayecto != null){
            this.id = trayecto.getId();
            this.numPeajes = trayecto.getNumPeajes();
            this.duracion = trayecto.getDuracion();
            this.costoCombustible = trayecto.getCostoCombustible();
            this.origen = trayecto.getOrigen();
            this.destino = trayecto.getDestino();
        }  
    }
    
    /**
     * Convierte un objeto TrayectoDTO a TrayectoEntity.
     *
     * @return Nueva objeto TrayectoEntity.
     *
     */
    public TrayectoEntity toEntity(){
        TrayectoEntity entity = new TrayectoEntity();
        entity.setId(this.id);
        entity.setNumPeajes(this.numPeajes);
        entity.setDuracion(this.duracion);
        entity.setCostoCombustible(this.costoCombustible);
        entity.setOrigen(this.origen);
        entity.setDestino(this.destino);
        return entity;
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
