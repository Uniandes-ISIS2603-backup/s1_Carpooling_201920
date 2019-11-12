/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.adapters.DateAdapter;
import co.edu.uniandes.csw.carpooling.entities.ReservaEntity;
import co.edu.uniandes.csw.carpooling.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 * ReservaDTO Objeto de transferencia de datos de Reservas. Los DTO contienen las
 * representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": Long,
 *      "numeroReserva": String,
 *      "confirmacion": String,
 *      "fecha": Date,
 *      "estado": String
 *   }
 * </pre> Por ejemplo un viaje se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id":1,
 *      "numeroReserva": "a1b2",
 *      "confirmacion": "listo",
 *      "fecha": "2019-11-03",
 *      "estado": "CONFIRMADA",          
 *   }
 *
 * </pre>
 *
 * @author le.perezl
 */
public class ReservaDTO implements Serializable{

    /**
     * @return the viajero
     */
    public ViajeroDTO getViajero() {
        return viajero;
    }

    /**
     * @param viajero the viajero to set
     */
    public void setViajero(ViajeroDTO viajero) {
        this.viajero = viajero;
    }

    /**
     * @return the viaje
     */
    public ViajeDTO getViaje() {
        return viaje;
    }

    /**
     * @param viaje the viaje to set
     */
    public void setViaje(ViajeDTO viaje) {
        this.viaje = viaje;
    }

    private Long id;
    private String numeroDeReserva;
    private String confirmacion;
    
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fecha;
    private String estado;
    private ViajeDTO viaje;
    private ViajeroDTO viajero;
    
    public ReservaDTO(){
        
    }
     
     public ReservaDTO(ReservaEntity reserva){
        if(reserva != null){
            this.id = reserva.getId();
            this.numeroDeReserva = reserva.getNumeroDeReserva();
            this.confirmacion = reserva.getConfirmacion();
            this.fecha = reserva.getFecha();
            this.estado = reserva.getEstado();
        }  
    }
     
    public ReservaEntity toEntity(){
        ReservaEntity entity = new ReservaEntity();
        entity.setId(this.getId());
        entity.setNumeroDeReserva(this.numeroDeReserva);
        entity.setConfirmacion(this.confirmacion);
        entity.setFecha(this.fecha);
        entity.setEstado(this.estado);
        return entity;
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
