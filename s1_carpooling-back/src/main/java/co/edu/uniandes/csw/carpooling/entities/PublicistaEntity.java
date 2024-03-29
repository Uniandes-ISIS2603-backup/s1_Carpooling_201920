/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Santiago Ballesteros
 */
@Entity
public class PublicistaEntity extends BaseEntity {

    public enum TIPO_PUBLICISTA {
        PERSONA_NATURAL_CON_EMPRESA,
        EMPRESA
    }

    private String nombre;
    private String apellido;
    private String contrasenha;
    private String telefono;
    private String correo;
    private String cedula;
    private String rut;
    private String nit;
    private TIPO_PUBLICISTA tipoPublicista;

    @PodamExclude
    @OneToMany(mappedBy = "publicista", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PublicidadEntity> publicidades = new ArrayList<>();

    /**
     * Constructor vacio
     */
    public PublicistaEntity() {

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
    public TIPO_PUBLICISTA getTipoPublicista() {
        return tipoPublicista;
    }

    /**
     * @param tipoPublicista the tipoPublicista to set
     */
    public void setTipoPublicista(TIPO_PUBLICISTA tipoPublicista) {
        this.tipoPublicista = tipoPublicista;
    }

    /**
     * @return the publicidades
     */
    public List<PublicidadEntity> getPublicidades() {
        return publicidades;
    }

    /**
     * @param publicidades the publicidades to set
     */
    public void setPublicidades(List<PublicidadEntity> publicidades) {
        this.publicidades = publicidades;
    }

}
