/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.entities.CalificacionEntity;
import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.entities.NotificacionEntity;
import co.edu.uniandes.csw.carpooling.entities.VehiculoEntity;
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
        if(conductor.getCalificaciones() != null){
            calificaciones = new ArrayList<CalificacionDTO>();
            for(CalificacionEntity entity: conductor.getCalificaciones()){
                calificaciones.add(new CalificacionDTO(entity));
            }
        } 
        if(conductor.getNotificaciones() != null){
            notificaciones = new ArrayList<NotificacionDTO>();
            for(NotificacionEntity entity: conductor.getNotificaciones()){
                notificaciones.add(new NotificacionDTO(entity));
            }
        }
        
        if(conductor.getVehiculos() != null){
            vehiculos = new ArrayList<VehiculoDTO>();
            for(VehiculoEntity entity: conductor.getVehiculos()){
                vehiculos.add(new VehiculoDTO(entity));
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
        if(calificaciones!=null){
            List<CalificacionEntity> calificacionesEntity = new ArrayList<CalificacionEntity>();
            for(CalificacionDTO calificacionDTO: calificaciones){
                calificacionesEntity.add(calificacionDTO.toEntity());
            }
            entity.setCalificaciones(calificacionesEntity);
        }
        if(notificaciones!=null){
            List<NotificacionEntity> notificacionesEntity = new ArrayList<NotificacionEntity>();
            for(NotificacionDTO notificacionDTO: notificaciones){
                notificacionesEntity.add(notificacionDTO.toEntity());
            }
            entity.setNotificaciones(notificacionesEntity);
        }
        
        if(vehiculos !=null){
            List<VehiculoEntity> vehiculosEntity = new ArrayList<VehiculoEntity>();
            for(VehiculoDTO vehiculoDTO:vehiculos){
                vehiculosEntity.add(vehiculoDTO.toEntity());
            }
            entity.setVehiculos(vehiculosEntity);
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

    /**
     * @return the vehiculos
     */
    public List<VehiculoDTO> getVehiculos() {
        return vehiculos;
    }

    /**
     * @param vehiculos the vehiculos to set
     */
    public void setVehiculos(List<VehiculoDTO> vehiculos) {
        this.vehiculos = vehiculos;
    }

    /**
     * @return the viajesRecurrentes
     */
    public List<ViajeRecurrenteDTO> getViajesRecurrentes() {
        return viajesRecurrentes;
    }

    /**
     * @param viajesRecurrentes the viajesRecurrentes to set
     */
    public void setViajesRecurrentes(List<ViajeRecurrenteDTO> viajesRecurrentes) {
        this.viajesRecurrentes = viajesRecurrentes;
    }
    
}
