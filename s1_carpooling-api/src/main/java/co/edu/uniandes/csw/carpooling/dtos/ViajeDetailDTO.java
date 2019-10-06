/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.entities.TrayectoEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase que extiende de {@link ViajeDTO} para manejar las relaciones entre
 * los Viaje JSON y otros DTOs. Para conocer el contenido de un
 * Viaje vaya a la documentacion de {@link ViajeDTO}
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": Long,
 *      "origen": String,
 *      "destino": String,
 *      "fechaDeSalida": Date,
 *      "fechaDeLlegada": Date,
 *      "cupos": Integer,
 *      "costoViaje": Double,
 *      "vehiculo": String,
 *      "estadoViaje: ESTADO_DE_VIAJE,
 *      "trayectos": [{@link TrayectoDTO}]
 *   }
 * </pre> Por ejemplo una editorial se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id":1,
 *      "destino": "Bucaramanga",
 *      "origen": "Bogota",
 *      "fechaDeSalida": "2019-11-03",
 *      "fechaDeLlegada": "2019-11-03",
 *      "cupos": 2,
 *      "costoViaje": 30000.0,
 *      "vehiculo":"DBS594",
 *      "estadoViaje": "PUBLICADO",
 *      "trayectos" : [
 *          {
 *              "id" : 1,
 *              "numPeajes" :3,
 *              "duracion": 120,
 *              "costoCombustible" : 50000,
 *              "origen" : "Calle 113 #40-25",
 *              "destino" : "Transversal 67 #170-15"
 *          }
 *      ]
 *   }
 *
 * </pre>
 *
 * @author Juan David Serrano
 */
public class ViajeDetailDTO extends ViajeDTO implements Serializable{
    
    
    private List<TrayectoDTO> trayectos;
    
    
    public ViajeDetailDTO(){
        super();
    }
    
    public ViajeDetailDTO(ViajeEntity viajeEntity){
        super(viajeEntity);
        if(viajeEntity != null){
            if(viajeEntity.getTrayectos() != null){
                trayectos = new ArrayList<TrayectoDTO>();
                for(TrayectoEntity trayectoEntity: viajeEntity.getTrayectos()){
                    trayectos.add(new TrayectoDTO(trayectoEntity));
                }
            }
        }
    }
    
    public ViajeEntity toEntity(){
        ViajeEntity entidad = super.toEntity();
        if(trayectos != null){
            List<TrayectoEntity> trayectosEntity = new ArrayList<TrayectoEntity>();
            for(TrayectoDTO trayectoDTO: trayectos){
                trayectosEntity.add(trayectoDTO.toEntity());
            }
            entidad.setTrayectos(trayectosEntity);
        }
        return entidad;
    }
    
    

    /**
     * @return the trayectos
     */
    public List<TrayectoDTO> getTrayectos() {
        return trayectos;
    }

    /**
     * @param trayectos the trayectos to set
     */
    public void setTrayectos(List<TrayectoDTO> trayectos) {
        this.trayectos = trayectos;
    }
    
    
    
}
