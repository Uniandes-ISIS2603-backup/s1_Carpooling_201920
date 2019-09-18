/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.entities;

import co.edu.uniandes.csw.carpooling.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Juan David Serrano
 */
@Entity
public class ViajeEntity extends BaseEntity implements Serializable {
    
    private String destino;
    
    
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaDeSalida;
    
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaDeLlegada;
    
    private String puntoDeSalida;
    
    private Integer cupos;
    
    private Float costoViaje;
    
    private String vehiculo;
    
    private String estadoViaje;
    
   // @PodamExclude
  //  @ManyToOne
    //private ConductorEntity conductor; 
    
  //  @PodamExclude
   // @OneToMany
   // private List<CalificacionEntity> calificaciones;
    
    //@PodamExclude
    //@ManyToOne
    //private ViajeRecurrenteEntity viajesRecurrentes;
    
    // (Aun no listo)@PodamExclude
    // (Aun no Listo)@OneToOne
    // (Aun no Listo)private VehiculoEntity vehiculo;
   

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
     * @return the puntoDeSalida
     */
    public String getPuntoDeSalida() {
        return puntoDeSalida;
    }

    /**
     * @param puntoDeSalida the puntoDeSalida to set
     */
    public void setPuntoDeSalida(String puntoDeSalida) {
        this.puntoDeSalida = puntoDeSalida;
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
     * @return the vehiculo
     */
    public String getVehiculo() {
        return vehiculo;
    }

    /**
     * @param vehiculo the vehiculo to set
     */
    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    /**
     * @return the estadoViaje
     */
    public String getEstadoViaje() {
        return estadoViaje;
    }

    /**
     * @param estadoViaje the estadoViaje to set
     */
    public void setEstadoViaje(String estadoViaje) {
        this.estadoViaje = estadoViaje;
    }
/*
    /**
     * @return the conductor
     
    public ConductorEntity getConductor() {
        return conductor;
    }

    /**
     * @param conductor the conductor to set
     
    public void setConductor(ConductorEntity conductor) {
        this.conductor = conductor;
    }*/
    
    /**
    public List<CalificacionEntity> getCalificaciones()
    {
        return calificaciones;
    }
    public void setCalificaciones(List<CalificacionEntity> calificaciones)
    {
        this.calificaciones = calificaciones;
    }
    */
    
    /**
    public ViajeRecurrenteEntity getViajesRecurrentes()
    {
        return viajesRecurrentes;
    }
    
    public void setViajesRecurrentes(ViajeRecurrenteEntity viajesRecurrentes)
    {
        this.viajesRecurrentes = viajesRecurrentes;
    }
    */
}
