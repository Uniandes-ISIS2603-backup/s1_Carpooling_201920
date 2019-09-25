/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.adapters.DateAdapter;
import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeEntity.ESTADO_DE_VIAJE;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 *
 * @author Estudiante
 */
public class ViajeDTO implements Serializable{
    private Long id;
    
    private String destino;
    
    private String origen;
    
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fechaDeSalida;
    
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fechaDeLlegada;
    
    private Integer cupos;
    
    private Float costoViaje;
    
    private String vehiculo;
    
    private ESTADO_DE_VIAJE estadoViaje;
    
    public ViajeDTO(){
        
    }
    
    
     public ViajeDTO(ViajeEntity viaje){
        if(viaje != null){
            this.id = viaje.getId();
            this.destino = viaje.getDestino();
            this.origen = viaje.getOrigen();
            this.fechaDeSalida = viaje.getFechaDeSalida();
            this.fechaDeLlegada = viaje.getFechaDeLlegada();
            this.cupos = viaje.getCupos();
            this.costoViaje = viaje.getCostoViaje();
            this.vehiculo = viaje.getVehiculo();
            this.estadoViaje = viaje.getEstadoViaje();
        }  
    }
    
    
    public ViajeEntity toEntity(){
        ViajeEntity entity = new ViajeEntity();
        entity.setId(this.id);
        entity.setDestino(this.destino);
        entity.setOrigen(this.origen);
        entity.setFechaDeSalida(this.fechaDeSalida);
        entity.setFechaDeLlegada(this.fechaDeLlegada);
        entity.setCupos(this.cupos);
        entity.setCostoViaje(this.costoViaje);
        entity.setVehiculo(this.vehiculo);
        entity.setEstadoViaje(this.estadoViaje);
        return entity;
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
     * @return the fechaDeLlegada
     */
    public Date getFechaDeLlegada() {
        return fechaDeLlegada;
    }

    /**
     * @param fechaDeLlegada the fechaDeLlegada to set
     */
    public void setFechaDeLlegada(Date fechaDeLlegada) {
        this.fechaDeLlegada = fechaDeLlegada;
    }

    /**
     * @return the cupos
     */
    public Integer getCupos() {
        return cupos;
    }

    /**
     * @param cupos the cupos to set
     */
    public void setCupos(Integer cupos) {
        this.cupos = cupos;
    }

    /**
     * @return the costoViaje
     */
    public Float getCostoViaje() {
        return costoViaje;
    }

    /**
     * @param costoViaje the costoViaje to set
     */
    public void setCostoViaje(Float costoViaje) {
        this.costoViaje = costoViaje;
    }

    /**
     * @return the vehiculo
     */
    public String getVehiculo() {
        return vehiculo;
    }

    /**
     * @param vehiculo the vehiculo to set
     */
    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    /**
     * @return the estadoViaje
     */
    public ESTADO_DE_VIAJE getEstadoViaje() {
        return estadoViaje;
    }

    /**
     * @param estadoViaje the estadoViaje to set
     */
    public void setEstadoViaje(ESTADO_DE_VIAJE estadoViaje) {
        this.estadoViaje = estadoViaje;
    }
}
