/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.entities;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 *
 * @author le.perezl
 */
@Entity
public class NotificacionEntity extends BaseEntity implements Serializable {



    
    private String mensaje;
//    @Temporal(TemporalType.DATE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    //@Temporal(TemporalType.HOUR)
//    private Date hora;
    
    //preg si el id va aqui
    /**
     * @return the mensaje
     */
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
//        /**
//     * @return the hora
//     */
//    public Date getHora() {
//        return hora;
//    }
//
//    /**
//     * @param hora the hora to set
//     */
//    public void setHora(Date hora) {
//        this.hora = hora;
//    }

}
