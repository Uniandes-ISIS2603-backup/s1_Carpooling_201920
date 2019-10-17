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
 * @author Nicolas Fajardo
 */
@Entity
public class ConductorEntity extends UsuarioEntity implements Serializable {

    @PodamExclude
    @OneToMany(
            mappedBy = "conductor",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    private List<ViajeEntity> viajes;

    @OneToMany(mappedBy = "conductor",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    private List<VehiculoEntity> vehiculos;

    @PodamExclude
    @OneToMany(mappedBy = "conductor", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<CalificacionEntity> calificaciones = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "conductor", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<NotificacionEntity> notificaciones = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "conductor", fetch = FetchType.LAZY)
    private List<ViajeRecurrenteEntity> viajesRecurrentes = new ArrayList<>();

    /**
     * @return the notificaciones
     */
    public List<NotificacionEntity> getNotificaciones() {
        return notificaciones;
    }

    /**
     * @param notificaciones the notificaciones to set
     */
    public void setNotificaciones(List<NotificacionEntity> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public List<ViajeRecurrenteEntity> getViajesRecurrentes() {
        return viajesRecurrentes;
    }

    public void setViajesRecurrentes(List<ViajeRecurrenteEntity> viajesRecurrentes) {
        this.viajesRecurrentes = viajesRecurrentes;
    }

    public List<CalificacionEntity> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<CalificacionEntity> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public List<VehiculoEntity> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<VehiculoEntity> vehiculos) {
        this.vehiculos = vehiculos;
    }

    /**
     * @return the viajes
     */
    public List<ViajeEntity> getViajes() {
        return viajes;
    }

    /**
     * @param viajes the viajes to set
     */
    public void setViajes(List<ViajeEntity> viajes) {
        this.viajes = viajes;
    }

}
