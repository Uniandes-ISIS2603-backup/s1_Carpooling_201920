/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.entities;

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
public class ConductorEntity extends UsuarioEntity {
    
    @PodamExclude
    @ManyToMany
    private List<VehiculoEntity> vehiculos;
    
    @PodamExclude
    @OneToMany(mappedBy = "conductor")
    private List<ViajeRecurrenteEntity> viajesRecurrentes;
    
    @PodamExclude
    @OneToMany(mappedBy = "conductor")
    private List<ViajeEntity> viajes;

    /**
     * @return the vehiculos
     */
    public List<VehiculoEntity> getVehiculos() {
        return vehiculos;
    }

    /**
     * @param vehiculos the vehiculos to set
     */
    public void setVehiculos(List<VehiculoEntity> vehiculos) {
        this.vehiculos = vehiculos;
    }

    /**
     * @return the viajesRecurrentes
     */
    public List<ViajeRecurrenteEntity> getViajesRecurrentes() {
        return viajesRecurrentes;
    }

    /**
     * @param viajesRecurrentes the viajesRecurrentes to set
     */
    public void setViajesRecurrentes(List<ViajeRecurrenteEntity> viajesRecurrentes) {
        this.viajesRecurrentes = viajesRecurrentes;
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
