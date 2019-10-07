/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Nicolas Fajardo
 */
@Entity
public class ConductorEntity extends UsuarioEntity implements Serializable{
    @PodamExclude
    @OneToMany(mappedBy = "conductorPlanes")
    private List<ViajeEntity> viajesPlaneados;
    
    @PodamExclude
    @OneToMany(mappedBy = "conductorHistorial")
    private List<ViajeEntity> historialViajes;

    /**
     * @return the viajesPlaneados
     */
    public List<ViajeEntity> getViajesPlaneados() {
        return viajesPlaneados;
    }

    /**
     * @param viajesPlaneados the viajesPlaneados to set
     */
    public void setViajesPlaneados(List<ViajeEntity> viajesPlaneados) {
        this.viajesPlaneados = viajesPlaneados;
    }

    /**
     * @return the historialViajes
     */
    public List<ViajeEntity> getHistorialViajes() {
        return historialViajes;
    }

    /**
     * @param historialViajes the historialViajes to set
     */
    public void setHistorialViajes(List<ViajeEntity> historialViajes) {
        this.historialViajes = historialViajes;
    }
    
    
  
    
}
