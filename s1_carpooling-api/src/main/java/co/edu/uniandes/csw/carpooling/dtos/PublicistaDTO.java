/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.entities.PublicistaEntity;
import java.io.Serializable;

/**
 * 
 * AuthorDTO Objeto de transferencia de datos de Autores. Los DTO contienen las
 * representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *	"nombre":"String",
 *	"apellido":"String",
 *	"tipoPublicista":"TIPO_PUBLICISTA",
 *	"telefono" : "String",
 *	"correo" : "String",
 *      "contrasenha" : "String",
 *	"cedula" : "String",
 *	"rut" : "String"
 *   }
 * </pre> Por ejemplo un autor se representa asi:<br>
 *
 * <pre>
 *
 * {
 *	"nombre":"Santiago",
 *	"apellido":"Ballesteros",
 *	"tipoPublicista":"PERSONA_NATURAL_CON_EMPRESA",
 *	"telefono" : "3123456784",
 *      "contrasenha" : "password",
 *	"correo" : "s.ballesteros@uniandes.edu.co",
 *	"cedula" : "1123456780",
 *	"rut" : "qdbkhfebjhk"
 * }
 *
 *  * </pre>
 * @author Santiago Ballesteros
 */
public class PublicistaDTO implements Serializable{
    
    private Long id;
    public enum TIPO_PUBLICISTA {
        PERSONA_NATURAL_CON_EMPRESA,
        EMPRESA
    }

    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String contrasenha;
    private String cedula;
    private String rut;
    private String nit;
    private PublicistaEntity.TIPO_PUBLICISTA tipoPublicista;

    /**
     * Constructor vacio
     */
    public PublicistaDTO(){
    
    }
    
    public PublicistaDTO(PublicistaEntity entidad){
        setId(entidad.getId());
        setApellido(entidad.getApellido());
        setCedula(entidad.getCedula());
        setCorreo(entidad.getCorreo());
        setContrasenha(entidad.getContrasenha());
        setNit(entidad.getNit());
        setNombre(entidad.getNombre());
        setRut(entidad.getRut());
        setTelefono(entidad.getTelefono());
        setTipoPublicista(entidad.getTipoPublicista());
    
    }
    
    public PublicistaEntity toEntity(){
        PublicistaEntity entidad = new PublicistaEntity();
        entidad.setId(this.getId());
        entidad.setApellido(this.getApellido());
        entidad.setCorreo(this.getCorreo());
        entidad.setContrasenha(this.getContrasenha());
        entidad.setNit(this.getNit());
        entidad.setNombre(this.getNombre());
        entidad.setTelefono(this.getTelefono());
        entidad.setRut(this.getRut());
        entidad.setTipoPublicista(this.getTipoPublicista());
        entidad.setCedula(this.getCedula());
        return entidad;
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
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the cedula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * @return the rut
     */
    public String getRut() {
        return rut;
    }

    /**
     * @param rut the rut to set
     */
    public void setRut(String rut) {
        this.rut = rut;
    }

    /**
     * @return the nit
     */
    public String getNit() {
        return nit;
    }

    /**
     * @param nit the nit to set
     */
    public void setNit(String nit) {
        this.nit = nit;
    }

    /**
     * @return the tipoPublicista
     */
    public PublicistaEntity.TIPO_PUBLICISTA getTipoPublicista() {
        return tipoPublicista;
    }

    /**
     * @param tipoPublicista the tipoPublicista to set
     */
    public void setTipoPublicista(PublicistaEntity.TIPO_PUBLICISTA tipoPublicista) {
        this.tipoPublicista = tipoPublicista;
    }

    /**
     * @return the contrasenha
     */
    public String getContrasenha() {
        return contrasenha;
    }

    /**
     * @param contrasenha the contrasenha to set
     */
    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }
    
}
