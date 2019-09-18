/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;

import co.edu.uniandes.csw.carpooling.entities.PublicistaEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.PublicistaPersistence;
import javax.inject.Inject;

/**
 *
 * @author SantiagoBallesteros
 */
public class PublicistaLogic {
    
    @Inject
    PublicistaPersistence pp;
    
    public PublicistaEntity createPublicista(PublicistaEntity publicista) throws BusinessLogicException{
    
        if(publicista.getTipoPublicista()==null){
            throw new BusinessLogicException("El tipoPublicista del publicista esta vacio");
        }
        if(publicista.getNombre()==null ){
            throw new BusinessLogicException("El nombre del publicista esta vacio");
        }
        if(publicista.getApellido()==null && publicista.getTipoPublicista().compareTo(PublicistaEntity.TIPO_PUBLICISTA.EMPRESA)!=0){
            throw new BusinessLogicException("El apellido del publicista esta vacio");
        }
        if(publicista.getCorreo()==null){
            throw new BusinessLogicException("El correo del publicista esta vacio");
        }
        if(publicista.getNit()==null && publicista.getTipoPublicista().compareTo(PublicistaEntity.TIPO_PUBLICISTA.PERSONA_NATURAL_CON_EMPRESA)!=0){
            throw new BusinessLogicException("El nit del publicista esta vacio");
        }
        if(publicista.getTelefono()==null){
            throw new BusinessLogicException("El telefono del publicista esta vacio");
        }
        if(publicista.getRut()==null && publicista.getTipoPublicista().compareTo(PublicistaEntity.TIPO_PUBLICISTA.EMPRESA)!=0){
            throw new BusinessLogicException("El rut del publicista esta vacio");
        }
        
        publicista = pp.create(publicista);
        return publicista;
    }
    
}
