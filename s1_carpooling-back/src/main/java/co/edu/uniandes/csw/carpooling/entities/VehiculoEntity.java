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
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Estudiante
 */
@Entity
public class VehiculoEntity extends BaseEntity implements Serializable{
    private String soat;
    private String placa;
    private String aseguradora;
    private String vigenciaSoat;
    private String modelo;
    private Integer sillas;
    
   // @PodamExclude
   // @ManyToMany(mappedBy = "vehiculos")
   // private List<ConductorEntity> conductores;
    
    @PodamExclude
    @OneToOne
    private ViajeEntity viaje;

    /**
     * @return the soat
     */
    public String getSoat() {
        return soat;
    }

    /**
     * @param soat the soat to set
     */
    public void setSoat(String soat) {
        this.soat = soat;
    }

    /**
     * @return the placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * @param placa the placa to set
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * @return the aseguradora
     */
    public String getAseguradora() {
        return aseguradora;
    }

    /**
     * @param aseguradora the aseguradora to set
     */
    public void setAseguradora(String aseguradora) {
        this.aseguradora = aseguradora;
    }

    /**
     * @return the vigenciaSoat
     */
    public String getVigenciaSoat() {
        return vigenciaSoat;
    }

    /**
     * @param vigenciaSoat the vigenciaSoat to set
     */
    public void setVigenciaSoat(String vigenciaSoat) {
        this.vigenciaSoat = vigenciaSoat;
    }

    /**
     * @return the sillas
     */
    public Integer getSillas() {
        return sillas;
    }

    /**
     * @param sillas the sillas to set
     */
    public void setSillas(Integer sillas) {
        this.sillas = sillas;
    }

    /**
     * @return the modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the conductores
     
    public List<ConductorEntity> getConductores() {
        return conductores;
    }

    /**
     * @param conductores the conductores to set
     
    public void setConductores(List<ConductorEntity> conductores) {
        this.conductores = conductores;
    }*/
    
}
