/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import co.edu.uniandes.csw.carpooling.entities.PublicidadEntity;
import co.edu.uniandes.csw.carpooling.entities.PublicistaEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Santiago Ballesteros
 */
public class PublicistaDetailDTO extends PublicistaDTO {
    
    private List<PublicidadDTO> publicidades;

     /**
     * Constructor vacio que llama a super
     */
    public PublicistaDetailDTO(){
        super();
    }
    
    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param editorialEntity La entidad del Publicista para transformar a DTO.
     */
    public PublicistaDetailDTO(PublicistaEntity publicistaEntity){
        super(publicistaEntity);
        if(publicistaEntity != null&&publicistaEntity.getPublicidades()!=null){
                publicidades = new ArrayList<>();
                for (PublicidadEntity entityPublicidad : publicistaEntity.getPublicidades()){
                    publicidades.add(new PublicidadDTO(entityPublicidad));
                }
            }
        }
    
    
        /**
     * Transformar un DTO a un Entity
     *
     * @return El DTO de la editorial para transformar a Entity
     */
    @Override
    public PublicistaEntity toEntity() {
        PublicistaEntity publicistaEntity = super.toEntity();
        if (publicidades != null) {
            List<PublicidadEntity> publicidadEntity = new ArrayList<>();
            for (PublicidadDTO dtoPublicidad : publicidades) {
                publicidadEntity.add(dtoPublicidad.toEntity());
            }
            publicistaEntity.setPublicidades(publicidadEntity);
        }
        return publicistaEntity;
    }
    
    /**
     * @return the publicidades
     */
    public List<PublicidadDTO> getPublicidades() {
        return publicidades;
    }

    /**
     * @param publicidades the publicidades to set
     */
    public void setPublicidades(List<PublicidadDTO> publicidades) {
        this.publicidades = publicidades;
    }
    
    
}
