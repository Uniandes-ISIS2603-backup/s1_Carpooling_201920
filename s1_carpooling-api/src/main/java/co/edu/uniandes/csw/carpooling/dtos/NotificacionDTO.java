/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.entities.NotificacionEntity;
import co.edu.uniandes.csw.carpooling.podam.DateStrategy;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author le.perezl
 */
public class NotificacionDTO {

 

    private Long id;
    private String mensaje;
    private String titulo;
    @Temporal(TemporalType.TIMESTAMP)
    @PodamStrategyValue(DateStrategy.class)
    private Date fecha;
    /**
     * @return the mensaje
     */
    public NotificacionDTO(){
        
    }
     
     public NotificacionDTO(NotificacionEntity notificacion){
        if(notificacion != null){
            this.id = notificacion.getId();
            this.mensaje = notificacion.getMensaje();
            this.titulo = notificacion.getTitulo();
            this.fecha = notificacion.getFecha();
        }  
    }
     
    public NotificacionEntity toEntity(){
        NotificacionEntity entity = new NotificacionEntity();
        entity.setId(this.getId());
        entity.setMensaje(this.mensaje);
        entity.setTitulo(this.getTitulo());
        entity.setFecha(this.fecha);
        return entity;
    }
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

   
}
