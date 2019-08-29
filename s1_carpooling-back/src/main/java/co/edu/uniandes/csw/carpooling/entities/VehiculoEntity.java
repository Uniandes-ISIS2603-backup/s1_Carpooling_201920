/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.uniandes.csw.carpooling.entities;

import java.io.Serializable;
import javax.persistence.Entity;

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
    private String nombreComprador;
    private String cedulaComprador;
    private String numMotor;
    private String numChasis;
    private Integer sillas;

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
     * @return the nombreComprador
     */
    public String getNombreComprador() {
        return nombreComprador;
    }

    /**
     * @param nombreComprador the nombreComprador to set
     */
    public void setNombreComprador(String nombreComprador) {
        this.nombreComprador = nombreComprador;
    }

    /**
     * @return the cedulaComprador
     */
    public String getCedulaComprador() {
        return cedulaComprador;
    }

    /**
     * @param cedulaComprador the cedulaComprador to set
     */
    public void setCedulaComprador(String cedulaComprador) {
        this.cedulaComprador = cedulaComprador;
    }

    /**
     * @return the numMotor
     */
    public String getNumMotor() {
        return numMotor;
    }

    /**
     * @param numMotor the numMotor to set
     */
    public void setNumMotor(String numMotor) {
        this.numMotor = numMotor;
    }

    /**
     * @return the numChasis
     */
    public String getNumChasis() {
        return numChasis;
    }

    /**
     * @param numChasis the numChasis to set
     */
    public void setNumChasis(String numChasis) {
        this.numChasis = numChasis;
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
    
}
