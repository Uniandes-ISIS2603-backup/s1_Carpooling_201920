/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.entities.CalificacionEntity;
import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.entities.NotificacionEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nicolas Fajardo
 */
public class ConductorDetailDTO extends ConductorDTO{
    private List<ViajeDTO> viajes;
    private List<VehiculoDTO> vehiculos;
    private List<ViajeRecurrenteDTO> viajesRecurrentes;
    
    private List<NotificacionDTO> notificaciones;
    
    private List<CalificacionDTO> calificaciones;
    
    private ConductorDetailDTO(){
        super();
    }
    
    public ConductorDetailDTO(ConductorEntity conductor){
        super(conductor);
        if(conductor.getViajes() != null){
            viajes = new ArrayList<ViajeDTO>();
            for(ViajeEntity entity: conductor.getViajes()){
                viajes.add(new ViajeDTO(entity));
            }
        }
    }
    
    
    public ConductorEntity toEntity(){
        ConductorEntity entity = super.toEntity();
        if(viajes!=null){
            List<ViajeEntity> viajesEntity = new ArrayList<ViajeEntity>();
            for(ViajeDTO viajeDTO: viajes){
                viajesEntity.add(viajeDTO.toEntity());
            }
            entity.setViajes(viajesEntity);
        
        }
        return entity;
    }

    /**
     * @return the viajes
     */
    public List<ViajeDTO> getViajes() {
        return viajes;
    }

    /**
     * @param viajes the viajes to set
     */
    public void setViajes(List<ViajeDTO> viajes) {
        this.viajes = viajes;
    }

    /**
     * @return the notificaciones
     */
    public List<NotificacionDTO> getNotificaciones() {
        return notificaciones;
    }

    /**
     * @param notificaciones the notificaciones to set
     */
    public void setNotificaciones(List<NotificacionDTO> notificaciones) {
        this.notificaciones = notificaciones;
    }

    /**
     * @return the calificaciones
     */
    public List<CalificacionDTO> getCalificaciones() {
        return calificaciones;
    }

    /**
     * @param calificaciones the calificaciones to set
     */
    public void setCalificaciones(List<CalificacionDTO> calificaciones) {
        this.calificaciones = calificaciones;
    }
  
    
}
