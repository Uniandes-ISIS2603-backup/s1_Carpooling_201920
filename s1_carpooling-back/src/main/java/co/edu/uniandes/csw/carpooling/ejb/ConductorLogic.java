/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;

import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.ConductorPersistence;
import java.util.Date;
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
    
    public ConductorEntity createConductor(ConductorEntity conductor) throws BusinessLogicException{
        if(!validarCorreo(conductor.getCorreo()))
            throw new BusinessLogicException("El correo del conductor es inválido");
        else if(persistence.findByCorreo(conductor.getCorreo()) != null){
            throw new BusinessLogicException("Ya hay un conductor con ese correo");
        }
        else if(!validarNombre(conductor.getNombre())){
            throw new BusinessLogicException("El nombre es inválido");
        }
        else if(!validarTelefono(conductor.getTelefono())){
            throw new BusinessLogicException("El número de teléfono es inválido");
        }
        else if(!validarNumeroDocumento(conductor.getNumDocumento())){
            throw new BusinessLogicException("El número de documento es inválido");
        }
        else if(!validarFechaNacimiento(conductor.getFechaDeNacimiento())){
            throw new BusinessLogicException("La fecha de nacimiento es inválida");
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
        else if(!validarNombre(conductorEntity.getNombre())){
            throw new BusinessLogicException("El nombre es inválido");
        }
        else if(!validarTelefono(conductorEntity.getTelefono())){
            throw new BusinessLogicException("El número de teléfono es inválido");
        }
        else if(!validarNumeroDocumento(conductorEntity.getNumDocumento())){
            throw new BusinessLogicException("El número de documento es inválido");
        }
        else if(!validarFechaNacimiento(conductorEntity.getFechaDeNacimiento())){
            throw new BusinessLogicException("La fecha de nacimiento es inválida");
        }
        return persistence.update(conductorEntity);
    }
    
    public void deleteConductor(Long conductorId) {
        persistence.delete(conductorId);
    }
    
    
    
    private boolean validarNombre(String nombre){
        return !(nombre == null ||nombre.isEmpty());
    }
    
    private boolean validarTelefono(String telefono){
        boolean retorno = !(telefono == null||telefono.isEmpty());
        /*try{
            long tel = Long.parseLong(telefono);
        }catch(Exception e){
            retorno = false;
        }*/
        return retorno;
    }
    
    private boolean validarNumeroDocumento(String numeroDocumento){
        boolean retorno = !(numeroDocumento == null||numeroDocumento.isEmpty());
       /* try{
            long numDoc = Long.parseLong(numeroDocumento);
        }catch(Exception e){
            retorno = false;
        }*/
        return retorno;
    }
    
    private boolean validarFechaNacimiento(Date fechaNacimiento){
        return !(fechaNacimiento.compareTo(new Date())>=0|| fechaNacimiento == null);
    }
    
    private boolean validarCorreo(String correo){
        
        return !(correo == null || correo.isEmpty());
    }
}
