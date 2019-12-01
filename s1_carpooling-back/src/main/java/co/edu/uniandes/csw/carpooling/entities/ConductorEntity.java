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

    /**
     * Representa la lista de viajes del conductor
     */
    @PodamExclude
    @OneToMany(
            mappedBy = "conductor",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    private List<ViajeEntity> viajes;

    /**
     * Representa la lista de vehiculos del conductor
     */
    @OneToMany(mappedBy = "conductor",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    private List<VehiculoEntity> vehiculos;

    /**
     * Representa la lista de las calificaciones del conductor
     */
    @PodamExclude
    @OneToMany(mappedBy = "conductor", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<CalificacionEntity> calificaciones = new ArrayList<>();

    /**
     * Representa la lista de las notificaciones que le han llegado al condcutor
     */
    @PodamExclude
    @OneToMany(mappedBy = "conductor", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<NotificacionEntity> notificaciones = new ArrayList<>();

    /**
     * Representa la lista de viajes recurrentes del conductor
     */
    @PodamExclude
    @OneToMany(mappedBy = "conductor", fetch = FetchType.LAZY)
    private List<ViajeRecurrenteEntity> viajesRecurrentes = new ArrayList<>();

    /**
     * @return the notificaciones
     */
    @Override
    public List<NotificacionEntity> getNotificaciones() {
        return notificaciones;
    }

    /**
     * Ajusta las notificaciones del conductor al valor ingresado por paràmetro
     * @param notificaciones the notificaciones to set
     */
    @Override
    public void setNotificaciones(List<NotificacionEntity> notificaciones) {
        this.notificaciones = notificaciones;
    }

    /**
     * @return lalista de los viajes recurrentes del conductor
     */
    public List<ViajeRecurrenteEntity> getViajesRecurrentes() {
        return viajesRecurrentes;
    }
    
    /**
     * Ajusta la lista de viajes recurrentes al valor ingresado por paràmetro
     * @param viajesRecurrentes la nuea lista de viajes recurrentes
     */
    public void setViajesRecurrentes(List<ViajeRecurrenteEntity> viajesRecurrentes) {
        this.viajesRecurrentes = viajesRecurrentes;
    }
    
    /**
     * @return  la lista de las calificaciones del conductor
     */
    @Override
    public List<CalificacionEntity> getCalificaciones() {
        return calificaciones;
    }

    /**
     * Ajusta la lista de las calificaciones del conductor al valor ingresado por paràmetro
     * @param calificaciones las nuevas calificaciones
     */
    @Override
    public void setCalificaciones(List<CalificacionEntity> calificaciones) {
        this.calificaciones = calificaciones;
    }

    /**
     * @return la lista de vehiculos del conductor
     */
    public List<VehiculoEntity> getVehiculos() {
        return vehiculos;
    }

    /**
     * Ajusta la lista de vehìculos del conductor con el valor ingresado por parametro
     * @param vehiculos los nuevos vehiculos del conductor
     */
    public void setVehiculos(List<VehiculoEntity> vehiculos) {
        this.vehiculos = vehiculos;
    }

    /**
     * @return los viajes del conductor
     */
    public List<ViajeEntity> getViajes() {
        return viajes;
    }

    /**
     * Ajusta los viajes del conductor a los ingresados por parametro
     * @param viajes the viajes to set
     */
    public void setViajes(List<ViajeEntity> viajes) {
        this.viajes = viajes;
    }

}
