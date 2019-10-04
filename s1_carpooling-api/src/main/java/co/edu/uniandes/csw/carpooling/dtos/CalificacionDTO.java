/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.entities.CalificacionEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * CalificacionDTO Objeto de transferencia de datos de Premios. Los DTO contienen las
 * representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "puntuacion": number,
 *      "comentarios": string,
 *      "usuario": {@link UsuarioDTO}
 *      "viaje": {@link ViajeDTO}
 *   }
 * </pre> Por ejemplo una calificacion se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1,
 *      "puntuacion": "4"
 *      "description": "Good Job!",
 *      "usuario":
 *      {
 * 
 *      }
 *      "viaje":
 *      {
 *      
 *      }
 *  }
 * </pre>
 *
 * @author ISIS2603
 */




/**
 *
 * @author Juan David Alarcón
 */
public class CalificacionDTO implements Serializable{
    
    private Long id;
    private Integer puntuacion;
    private String comentarios;
   
    private UsuarioDTO usuario;
    private ViajeDTO viaje;
            
    public CalificacionDTO()
    {
        //Vacio
    }
   
    public CalificacionDTO(CalificacionEntity calificacionEntity)
    {
        if(calificacionEntity != null)
        {
            this.id = calificacionEntity.getId();
            this.puntuacion = calificacionEntity.getPuntuacion();
            this.comentarios= calificacionEntity.getComentarios();

            /** 
            if(calificacionEntity.getUsuario() != null)
                this.usuario = new UsuarioDTO(calificacionEntity.getUsuario());
            else
                calificacionEntity.setUsuario(null);
            */
            if(calificacionEntity.getViaje() != null)
                this.viaje = new ViajeDTO(calificacionEntity.getViaje());
            else
                calificacionEntity.setUsuario(null);
                
        }
    }
    
    public CalificacionEntity toEntity()
    {
        CalificacionEntity calificacionEntity = new CalificacionEntity();
        calificacionEntity.setId(this.id);
        calificacionEntity.setPuntuacion(this.puntuacion);
        calificacionEntity.setComentarios(this.comentarios);

//        if(usuario != null)
  //          calificacionEntity.setUsuario(usuario.toEntity());
        if(viaje != null)
            calificacionEntity.setViaje(viaje.toEntity());
        
        return calificacionEntity;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public ViajeDTO getViaje() {
        return viaje;
    }

    public void setViaje(ViajeDTO viaje) {
        this.viaje = viaje;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
