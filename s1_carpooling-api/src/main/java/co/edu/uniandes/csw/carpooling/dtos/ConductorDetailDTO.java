/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Nicolas Fajardo Ramirez
 */
public class ConductorDetailDTO extends ConductorDTO implements Serializable{
    
    private List<ViajeDTO> viajes;
    
    private List<NotificacionDTO> notificaciones;
    
    private List<CalificacionDTO> calificaciones;
    
    private ConductorDetailDTO(){
        super();
    }
    
    private ConductorDetailDTO(ConductorEntity conductor){
        super(conductor);
        if(conductor.getViajes() != null){
            
        }
        
        if(conductor.getCalificaciones() != null){
            
        }
        
        if(conductor.getNotificaciones() != null){
            
        }
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
