/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.entities.VehiculoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * VehiculoDTO Objeto de transferencia de datos de Premios. Los DTO contienen las
 * representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "puntuacion": number,
 *      "comentarios": string,
 *      "usuario": {@link UsuarioDTO}
 *      "viaje": {@link ViajeDTO}
 *   }
 * </pre> Por ejemplo una vehiculo se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1,
 *      "puntuacion": "4"
 *      "description": "Good Job!",
 *      "usuario":
 *      {
 * 
 *      }
 *      "viaje":
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
public class VehiculoDTO implements Serializable{
    
    private Long id;
    private String soat;
    private String placa;
    private String aseguradora;
    private String vigenciaSoat;
    private String modelo;
    private Integer sillas;
    
    private ConductorDTO conductor;
            
    public VehiculoDTO()
    {
    }
   
    public VehiculoDTO(VehiculoEntity vehiculoEntity)
    {
        if(vehiculoEntity != null)
        {
            this.id = vehiculoEntity.getId();
            this.soat = vehiculoEntity.getSoat();
            this.placa = vehiculoEntity.getPlaca();
            this.aseguradora = vehiculoEntity.getAseguradora();
            this.modelo = vehiculoEntity.getModelo();
            this.sillas = vehiculoEntity.getSillas();
            this.vigenciaSoat = vehiculoEntity.getVigenciaSoat();
        }
    }
    
    public VehiculoEntity toEntity()
    {
        VehiculoEntity vehiculoEntity = new VehiculoEntity();
        vehiculoEntity.setId(this.id);
        vehiculoEntity.setSoat(this.soat);
        vehiculoEntity.setPlaca(this.placa);
        vehiculoEntity.setAseguradora(this.aseguradora);
        vehiculoEntity.setModelo(this.modelo);
        vehiculoEntity.setSillas(this.sillas);
        vehiculoEntity.setVigenciaSoat(this.vigenciaSoat);
       // if(conductor != null)
         //   vehiculoEntity.setConductor(conductor.toEntity());
        
        return vehiculoEntity;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoat() {
        return soat;
    }

    public void setSoat(String soat) {
        this.soat = soat;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getAseguradora() {
        return aseguradora;
    }

    public void setAseguradora(String aseguradora) {
        this.aseguradora = aseguradora;
    }

    public String getVigenciaSoat() {
        return vigenciaSoat;
    }

    public void setVigenciaSoat(String vigenciaSoat) {
        this.vigenciaSoat = vigenciaSoat;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getSillas() {
        return sillas;
    }

    public void setSillas(Integer sillas) {
        this.sillas = sillas;
    }

    public ConductorDTO getConductor() {
        return conductor;
    }

    public void setConductor(ConductorDTO conductor) {
        this.conductor = conductor;
    }
    
    

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
