/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Santiago Ballesteros
 */
@Entity
public class ViajeroEntity extends UsuarioEntity implements Serializable {

    @PodamExclude
    @OneToMany(mappedBy = "viajero", fetch = FetchType.LAZY)
    private List<ReservaEntity> reservas = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "viajero", fetch = FetchType.LAZY)
    private List<CalificacionEntity> calificaciones = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "conductor", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<NotificacionEntity> notificaciones = new ArrayList<>();

    /**
     * @return the notificaciones
     */
    @Override
    public List<NotificacionEntity> getNotificaciones() {
        return notificaciones;
    }

    /**
     * @param notificaciones the notificaciones to set
     */
    @Override
    public void setNotificaciones(List<NotificacionEntity> notificaciones) {
        this.notificaciones = notificaciones;
    }

    @Override
    public List<CalificacionEntity> getCalificaciones() {
        return calificaciones;
    }

    @Override
    public void setCalificaciones(List<CalificacionEntity> calificaciones) {
        this.calificaciones = calificaciones;
    }

    /**
     * @return the reservas
     */
    public List<ReservaEntity> getReservas() {
        return reservas;
    }

    /**
     * @param reservas the reservas to set
     */
    public void setReservas(List<ReservaEntity> reservas) {
        this.reservas = reservas;
    }

}
