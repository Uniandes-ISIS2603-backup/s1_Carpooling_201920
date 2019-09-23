/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.dtos;

import java.util.List;

/**
 *
 * @author Santiago Ballesteros
 */
public class PublicistaDetailDTO extends PublicistaDTO {
    
    protected List<PublicidadDTO> publicidades;

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
