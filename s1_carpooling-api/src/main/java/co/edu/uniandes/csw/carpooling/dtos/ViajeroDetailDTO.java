/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.entities.ReservaEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeroEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Santiago Ballesteros
 */
public class ViajeroDetailDTO extends ViajeroDTO implements Serializable{
    
    private List<ReservaDTO> reservas;

     /**
     * Constructor vacio que llama a super
     */
    public ViajeroDetailDTO(){
        super();
    }
    
    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param editorialEntity La entidad del Viajero para transformar a DTO.
    */ 
    public ViajeroDetailDTO(ViajeroEntity viajeroEntity){
        super(viajeroEntity);
        if(viajeroEntity != null){
//            if(viajeroEntity.getReservas()!=null){
//                reservas = new ArrayList<>();
//                for (ReservaEntity entityReserva : viajeroEntity.getReservas()){
//                    reservas.add(new ReservaDTO(entityReserva));
//                }
//            }
        }
    }
    
    /**    
     * Transformar un DTO a un Entity
     *
     * @return El DTO de la editorial para transformar a Entity
     */
    @Override
    public ViajeroEntity toEntity() {
        ViajeroEntity viajeroEntity = super.toEntity();
        if (reservas != null) {
            List<ReservaEntity> reservaEntity = new ArrayList<>();
            for (ReservaDTO dtoReserva : reservas) {
                reservaEntity.add(dtoReserva.toEntity());
            }
            //viajeroEntity.setReservas(reservaEntity);
        }
        return viajeroEntity;
    }
    
}
