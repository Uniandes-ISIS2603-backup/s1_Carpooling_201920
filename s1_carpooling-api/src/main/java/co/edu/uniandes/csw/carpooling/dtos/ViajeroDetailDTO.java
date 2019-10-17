/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.entities.CalificacionEntity;
import co.edu.uniandes.csw.carpooling.entities.NotificacionEntity;
import co.edu.uniandes.csw.carpooling.entities.ReservaEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeroEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Santiago Ballesteros
 */
public class ViajeroDetailDTO extends ViajeroDTO implements Serializable{

   
    
    private List<ReservaDTO> reservas;
    private List<CalificacionDTO> calificaciones;
    
    private List<NotificacionDTO> notificaciones;
     /**
     * Constructor vacio que llama a super
     */
    public ViajeroDetailDTO(){
        super();
    }

    public List<CalificacionDTO> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<CalificacionDTO> calificaciones) {
        this.calificaciones = calificaciones;
    }
    
    
    
    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param editorialEntity La entidad del Viajero para transformar a DTO.
    */ 
    public ViajeroDetailDTO(ViajeroEntity viajeroEntity){
        super(viajeroEntity);
 //       if(viajeroEntity != null){
//            if(viajeroEntity.getReservas()!=null){
//                reservas = new ArrayList<>();
//                for (ReservaEntity entityReserva : viajeroEntity.getReservas()){
//                    reservas.add(new ReservaDTO(entityReserva));
//                }
//            }
//        }
        
        if(viajeroEntity.getCalificaciones() != null){
            calificaciones = new ArrayList<>();
            for(CalificacionEntity entity: viajeroEntity.getCalificaciones()){
                calificaciones.add(new CalificacionDTO(entity));
            }
        } 
    }
    
    /**    
     * Transformar un DTO a un Entity
     *
     * @return El DTO de la editorial para transformar a Entity
     */
    @Override
    public ViajeroEntity toEntity() {
        ViajeroEntity viajeroEntity = super.toEntity();
        if (reservas != null) {
            List<ReservaEntity> reservaEntity = new ArrayList<>();
            for (ReservaDTO dtoReserva : reservas) {
                reservaEntity.add(dtoReserva.toEntity());
            }
            //viajeroEntity.setReservas(reservaEntity);
        }
        
        if(calificaciones!=null){
            List<CalificacionEntity> calificacionesEntity = new ArrayList<>();
            for(CalificacionDTO calificacionDTO: calificaciones){
                calificacionesEntity.add(calificacionDTO.toEntity());
            }
            viajeroEntity.setCalificaciones(calificacionesEntity);
        }
        
         if(notificaciones!=null){
            List<NotificacionEntity> notificacionesEntity = new ArrayList<>();
            for(NotificacionDTO notificacionDTO: notificaciones){
                notificacionesEntity.add(notificacionDTO.toEntity());
            }
            viajeroEntity.setNotificaciones(notificacionesEntity);
        }
        return viajeroEntity;
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
    
}
