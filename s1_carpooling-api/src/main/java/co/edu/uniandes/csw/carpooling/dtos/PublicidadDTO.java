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
 * 
 * AuthorDTO Objeto de transferencia de datos de Autores. Los DTO contienen las
 * representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *	"nombre":"String",
 *	"costo":"double",
 *	"mensaje":"String",
 *	"fechaDeInicio" : "Date",
 *	"fechaDeSalida" : "Date",
 *      "publicista" : "PublicistaDTO",
 *	
 *   }
 * </pre> Por ejemplo un autor se representa asi:<br>
 *
 * <pre>
 *
 * {
 *      "nombre": "Joker",
 *      "costo": 150000,
 *      "mensaje": "Why so serious?",
 *      "fechaDeInicio": "2019-10-10T00:00:00-05:00",
 *      "fechaDeSalida": "2019-11-10T00:00:00-05:00",
 *      "publicista":
 *      {
 *          "nombre":"Santiago",
 *          "apellido":"Ballesteros",
 *          "tipoPublicista":"PERSONA_NATURAL_CON_EMPRESA",
 *          "telefono" : "3123456784",
 *        "contrasenha" : "password",
 *          "correo" : "s.ballesteros@uniandes.edu.co",
 *          "cedula" : "1123456780",
 *          "rut" : "qdbkhfebjhk"
 *      }
 * }
 *
 * @author Nicolas Fajardo
 */
public class PublicidadDTO implements Serializable{
    
    /*
        ATRIBUTOS
     */
    /**
     * Representa el id de la publicidad
     */
    private Long id;
    
    /**
     * Representa el nombre de la publicidad
     */
    private String nombre;
    
    /**
     * Representa el publicista duenho de esta publicidad
     */
    private PublicistaDTO publicista;

    /**
     * Representa el mensaje de la publicidad
     */
    private String mensaje;

    /**
     * Representa el costo de esta publicidad
     */
    private double costo;

    /**
     * Representa la fecha de inicio de la publicidad
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fechaDeInicio;

    /**
     * Representa la fecha de salida de la publicidad
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fechaDeSalida;

    /**
     * Constructor vacio
     */
    public PublicidadDTO() {

    }

    /**
     * Constructor
     * @param entidad endidad de la que se crea ael DTO
     */
    public PublicidadDTO(PublicidadEntity entidad) {
        setId(entidad.getId());
        setNombre(entidad.getNombre());
        setMensaje(entidad.getMensaje());
        setCosto(entidad.getCosto());
        setFechaDeInicio(entidad.getFechaDeInicio());
        setFechaDeSalida(entidad.getFechaDeSalida());

    }

    /**
     * Pasa esta publicidad a una entidad
     * @return la publicidad transformada en entidad
     */
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
