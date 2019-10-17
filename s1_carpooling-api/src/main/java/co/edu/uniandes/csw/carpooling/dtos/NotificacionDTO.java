/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.entities.NotificacionEntity;
import co.edu.uniandes.csw.carpooling.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 * NotificacionDTO Objeto de transferencia de datos de Notificaciones. Los DTO contienen las
 * representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": Long,
 *      "titulo": String,
 *      "mensaje": String,
 *      "fecha": Date
 *   }
 * </pre> Por ejemplo un viaje se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id":1,
 *      "titulo": "Cancelacion de servicios",
 *      "mensaje": "por tener 5 infracciones no podrá usar mas la aplicacion",
 *      "fecha": "2019-11-03",         
 *   }
 *
 * </pre>
 *
 * @author le.perezl
 */
public class NotificacionDTO implements Serializable{

 

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
