/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.adapters.DateAdapter;
import co.edu.uniandes.csw.carpooling.entities.PublicidadEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Nicolas Fajardo
 */
public class PublicidadDTO implements Serializable{
    
    /*
        ATRIBUTOS
     */
    private Long id;
    
    private String nombre;
    
    private PublicistaDTO publicista;

    private String mensaje;

    private double costo;

    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fechaDeInicio;

    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fechaDeSalida;

    public PublicidadDTO(){
    
    }
    
        public PublicidadDTO(PublicidadEntity entidad){
        setId(entidad.getId());
        setNombre(entidad.getNombre());
        setMensaje(entidad.getMensaje());
        setCosto(entidad.getCosto());
        setFechaDeInicio(entidad.getFechaDeInicio());
        setFechaDeSalida(entidad.getFechaDeSalida());
    
    }
    
    public PublicidadEntity toEntity(){
        PublicidadEntity entidad = new PublicidadEntity();
        entidad.setId(this.getId());
        entidad.setNombre(this.getNombre());
        entidad.setMensaje(this.getMensaje());
        entidad.setCosto(this.getCosto());
        entidad.setFechaDeInicio(this.getFechaDeInicio());
        entidad.setFechaDeSalida(this.getFechaDeSalida());
        return entidad;
    }
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the costo
     */
    public double getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(double costo) {
        this.costo = costo;
    }

    /**
     * @return the fechaDeInicio
     */
    public Date getFechaDeInicio() {
        return fechaDeInicio;
    }

    /**
     * @param fechaDeInicio the fechaDeInicio to set
     */
    public void setFechaDeInicio(Date fechaDeInicio) {
        this.fechaDeInicio = fechaDeInicio;
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
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }


}
