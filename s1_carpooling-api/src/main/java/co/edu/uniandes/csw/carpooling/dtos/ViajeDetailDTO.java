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
 *
 * @author Estudiante
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
