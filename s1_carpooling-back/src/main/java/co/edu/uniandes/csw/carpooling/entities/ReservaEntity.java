/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.entities;

import co.edu.uniandes.csw.carpooling.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author le.perezl
 */
@Entity
public class ReservaEntity  extends BaseEntity implements Serializable {
    
    /**
     * Enumeraciones
     */
    public static final Integer POR_CONFIRMAR=0;
    public static final Integer TERMINADA=1;
    public static final Integer CONFIRMADA=2;
    public static final Integer DENEGADA=3;
    public static final Integer CANCELADA=4;
    public static final Integer CANCELADA_CON_SANCION=5;
    
    private String numeroDeReserva;
    private String confirmacion;
    
    @Temporal(TemporalType.TIMESTAMP)
    @PodamStrategyValue(DateStrategy.class)
    private Date fecha;
    private String estado;
    
    @PodamExclude
    @ManyToOne
    private ViajeEntity viaje;
    
    @PodamExclude
    @ManyToOne
    private ViajeroEntity viajero;
    
    /**
     * @return the numeroDeReserva
     */
    public String getNumeroDeReserva() {
        return numeroDeReserva;
    }

    /**
     * @param numeroDeReserva the numeroDeReserva to set
     */
    public void setNumeroDeReserva(String numeroDeReserva) {
        this.numeroDeReserva = numeroDeReserva;
    }

    /**
     * @return the confirmacion
     */
    public String getConfirmacion() {
        return confirmacion;
    }

    /**
     * @param confirmacion the confirmacion to set
     */
    public void setConfirmacion(String confirmacion) {
        this.confirmacion = confirmacion;
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
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the viaje
     */
    public ViajeEntity getViaje() {
        return viaje;
    }

    /**
     * @param viaje the viaje to set
     */
    public void setViaje(ViajeEntity viaje) {
        this.viaje = viaje;
    }
    

}
