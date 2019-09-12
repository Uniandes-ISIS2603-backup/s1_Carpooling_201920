/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;

import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.ConductorPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Nicolas Fajardo
 */
@Stateless
public class ConductorLogic {
    
    @Inject
    private ConductorPersistence persistence;
    
    public ConductorEntity crearConductor(ConductorEntity conductor) throws BusinessLogicException{
        if(!validarCorreo(conductor.getCorreo()))
            throw new BusinessLogicException("El nombre del conductor está vacío");
        else if(persistence.findByCorreo(conductor.getCorreo()) != null){
            throw new BusinessLogicException("Ya hay un conductor con ese correo");
        }
        conductor = persistence.create(conductor);
        return conductor;
    }
    
    public List<ConductorEntity> getConductores(){
        return persistence.findAll();
    }
    
    public ConductorEntity getConductor(Long conductorId){
        return persistence.find(conductorId);
    }
    
    public ConductorEntity actualizarConductor(ConductorEntity conductorEntity) throws BusinessLogicException{
        if(!validarCorreo(conductorEntity.getCorreo())){
            throw new BusinessLogicException("El correo del conductor es invalido");
        }
        return persistence.update(conductorEntity);
    }
    
    public void deleteConductor(Long conductorId) {
        persistence.delete(conductorId);
    }
    
    private boolean validarCorreo(String correo){
        return !(correo == null || correo.isEmpty());
    }
}
