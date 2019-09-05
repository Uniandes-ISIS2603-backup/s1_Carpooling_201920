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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author le.perezl
 */
@Entity
public class ReservaEntity  extends BaseEntity implements Serializable {
    
    private String numeroDeReserva;
    private String confirmacion;
    @Temporal(TemporalType.TIMESTAMP)
    @PodamStrategyValue(DateStrategy.class)
    private Date fecha;
    private String estado;
    
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
    

}
