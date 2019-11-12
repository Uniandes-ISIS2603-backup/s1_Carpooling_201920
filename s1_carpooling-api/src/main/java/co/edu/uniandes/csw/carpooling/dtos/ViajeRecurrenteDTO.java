/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.adapters.DateAdapter;
import co.edu.uniandes.csw.carpooling.entities.ViajeRecurrenteEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ViajeRecurrenteDTO Objeto de transferencia de datos de Premios. Los DTO contienen las
 * representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "fechaInicio": date,
 *      "fechaFin": date,
 *      "frecuencia": string,
 *      "conductor": {@link ConductorDTO}
 *   }
 * </pre> Por ejemplo un viajeRecurrente se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1,
 *      "fechaInicio": "2000-08-20T00:00:00-05:00"
 *      "fechaFin": "2001-08-20T00:00:00-05:00",
 *      "frecuencia": "!"
 *      "conductor":
 *      {
 * 
 *      }
 *  }
 * </pre>
 *
 * @author ISIS2603
 */




/**
 *
 * @author Juan David Alarcón
 */
public class ViajeRecurrenteDTO implements Serializable{
    
    private Long id;
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fechInicio;
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fechaFin;
    private String frecuencia;
    
    
        
    public ViajeRecurrenteDTO()
    {
        //Vacio
    }
   
    public ViajeRecurrenteDTO(ViajeRecurrenteEntity viajeRecurrenteEntity)
    {
        if(viajeRecurrenteEntity != null)
        {
                        this.id = viajeRecurrenteEntity.getId();
            this.fechInicio = viajeRecurrenteEntity.getFechaInicio();
            this.fechaFin = viajeRecurrenteEntity.getFechaFin();
            this.frecuencia = viajeRecurrenteEntity.getFrecuencia();               
        }
    }
    
    public ViajeRecurrenteEntity toEntity()
    {
        ViajeRecurrenteEntity viajeRecurrenteEntity = new ViajeRecurrenteEntity();
        viajeRecurrenteEntity.setId(this.id);
        viajeRecurrenteEntity.setFechaFin(this.fechaFin);
        viajeRecurrenteEntity.setFechaInicio(this.fechInicio);
        viajeRecurrenteEntity.setFrecuencia(this.frecuencia);  
        return viajeRecurrenteEntity;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechInicio() {
        return fechInicio;
    }

    public void setFechInicio(Date fechInicio) {
        this.fechInicio = fechInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
