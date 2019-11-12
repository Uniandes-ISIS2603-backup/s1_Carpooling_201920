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
 * @author Santiago Ballesteros
 */
public class PublicidadDTO implements Serializable{
    
    private Long id;
    
    private String nombre;

    private String mensaje;

    private Double costo;

    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fechaDeInicio;

    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fechaDeSalida;
    
    protected PublicidadEntity.DISPONIBILIDAD disponibilidad;
    
    protected PublicistaDTO publicista;

    public PublicidadDTO(){
    
    }
    
        public PublicidadDTO(PublicidadEntity entidad){
        if(entidad!=null){
        setId(entidad.getId());
        setNombre(entidad.getNombre());
        setMensaje(entidad.getMensaje());
        setCosto(entidad.getCosto());
        setFechaDeInicio(entidad.getFechaDeInicio());
        setFechaDeSalida(entidad.getFechaDeSalida());
        setDisponibilidad(entidad.getDisponibilidad());
        if (entidad.getPublicista() != null) {
                this.publicista = new PublicistaDTO(entidad.getPublicista());
            } else {
                this.publicista = null;
            }
        }
        }
    
    public PublicidadEntity toEntity(){
        PublicidadEntity entidad = new PublicidadEntity();
        entidad.setId(this.getId());
        entidad.setNombre(this.getNombre());
        entidad.setMensaje(this.getMensaje());
        entidad.setCosto(this.getCosto());
        entidad.setFechaDeInicio(this.getFechaDeInicio());
        entidad.setFechaDeSalida(this.getFechaDeSalida());
        entidad.setDisponibilidad(this.getDisponibilidad());
        if (this.getPublicista() != null) {
            entidad.setPublicista(this.getPublicista().toEntity());
        }
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
    public Double getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(Double costo) {
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

    /**
     * @return the disponibilidad
     */
    public PublicidadEntity.DISPONIBILIDAD getDisponibilidad() {
        return disponibilidad;
    }

    /**
     * @param disponibilidad the disponibilidad to set
     */
    public void setDisponibilidad(PublicidadEntity.DISPONIBILIDAD disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    /**
     * @return the publicista
     */
    public PublicistaDTO getPublicista() {
        return publicista;
    }

    /**
     * @param publicista the publicista to set
     */
    public void setPublicista(PublicistaDTO publicista) {
        this.publicista = publicista;
    }

    
    
}
