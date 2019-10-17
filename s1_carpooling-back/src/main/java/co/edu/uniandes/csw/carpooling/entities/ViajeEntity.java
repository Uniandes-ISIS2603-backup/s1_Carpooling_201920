/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.entities;

import co.edu.uniandes.csw.carpooling.podam.DateStrategy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamFloatValue;
import uk.co.jemos.podam.common.PodamIntValue;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Juan David Serrano
 */
@Entity
public class ViajeEntity extends BaseEntity implements Serializable {

    public enum ESTADO_DE_VIAJE {
        PUBLICADO,
        EN_CURSO,
        FINALIZADO,
        CANCELADO,
        CANCELADO_CON_SANCION
    }

    private String destino;

    private String origen;

    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaDeSalida;

    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaDeLlegada;

    @PodamIntValue(minValue = 1)
    private Integer cupos;

    @PodamFloatValue(minValue = (float) .0001)
    private Float costoViaje;

    @PodamExclude
    @ManyToOne
    private VehiculoEntity vehiculo;

    private ESTADO_DE_VIAJE estadoViaje;

    @PodamExclude
    @OneToMany(mappedBy = "viaje",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    private List<CalificacionEntity> calificaciones;

    @PodamExclude
    @ManyToOne
    private ViajeRecurrenteEntity viajeRecurrente;

    @PodamExclude
    @OneToMany(mappedBy = "viaje",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    private List<ReservaEntity> reservas;

    @PodamExclude
    @ManyToOne
    private ConductorEntity conductor;

    @PodamExclude
    @OneToMany(mappedBy = "viaje", fetch = FetchType.LAZY)
    private List<TrayectoEntity> trayectos = new ArrayList<>();

    /**
     * @return the destino
     */
    public String getDestino() {
        return destino;
    }

    /**
     * @param destino the destino to set
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }

    /**
     * @return the fechaDeSalida
     */
    public Date getFechaDeSalida() {
        return fechaDeSalida;
    }

    /**
     * @param fechaDeSalida the fechaDeSalida to set
     */
    public void setFechaDeSalida(Date fechaDeSalida) {
        this.fechaDeSalida = fechaDeSalida;
    }

    /**
     * @return the fechaDeLlegada
     */
    public Date getFechaDeLlegada() {
        return fechaDeLlegada;
    }

    /**
     * @param fechaDeLlegada the fechaDeLlegada to set
     */
    public void setFechaDeLlegada(Date fechaDeLlegada) {
        this.fechaDeLlegada = fechaDeLlegada;
    }

    /**
     * @return the cupos
     */
    public Integer getCupos() {
        return cupos;
    }

    /**
     * @param cupos the cupos to set
     */
    public void setCupos(Integer cupos) {
        this.cupos = cupos;
    }

    /**
     * @return the costoViaje
     */
    public Float getCostoViaje() {
        return costoViaje;
    }

    /**
     * @param costoViaje the costoViaje to set
     */
    public void setCostoViaje(Float costoViaje) {
        this.costoViaje = costoViaje;
    }

    /**
     * @return the estadoViaje
     */
    public ESTADO_DE_VIAJE getEstadoViaje() {
        return estadoViaje;
    }

    /**
     * @param estadoViaje the estadoViaje to set
     */
    public void setEstadoViaje(ESTADO_DE_VIAJE estadoViaje) {
        this.estadoViaje = estadoViaje;
    }

    /**
     * @return the trayectos
     */
    public List<TrayectoEntity> getTrayectos() {
        return trayectos;
    }

    /**
     * @param trayectos the trayectos to set
     */
    public void setTrayectos(List<TrayectoEntity> trayectos) {
        this.trayectos = trayectos;
    }

    /**
     * @return the origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen the origen to set
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * @return the vehiculo
     */
    public VehiculoEntity getVehiculo() {
        return vehiculo;
    }

    /**
     * @param vehiculo the vehiculo to set
     */
    public void setVehiculo(VehiculoEntity vehiculo) {
        this.vehiculo = vehiculo;
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

    /**
     * @return the viajeRecurrente
     */
    public ViajeRecurrenteEntity getViajeRecurrente() {
        return viajeRecurrente;
    }

    /**
     * @param viajeRecurrente the viajeRecurrente to set
     */
    public void setViajeRecurrente(ViajeRecurrenteEntity viajeRecurrente) {
        this.viajeRecurrente = viajeRecurrente;
    }

    /**
     * @return the calificaciones
     */
    public List<CalificacionEntity> getCalificaciones() {
        return calificaciones;
    }

    /**
     * @param calificaciones the calificaciones to set
     */
    public void setCalificaciones(List<CalificacionEntity> calificaciones) {
        this.calificaciones = calificaciones;
    }

    /**
     * @return the conductor
     */
    public ConductorEntity getConductor() {
        return conductor;
    }

    /**
     * @param conductor the conductor to set
     */
    public void setConductor(ConductorEntity conductor) {
        this.conductor = conductor;
    }

}
