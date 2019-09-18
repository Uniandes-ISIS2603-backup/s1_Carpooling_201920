/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;

import co.edu.uniandes.csw.carpooling.entities.PublicidadEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.PublicidadPersistence;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Nicolas Fajardo Ramirez
 */
@Stateless
public class PublicidadLogic {
     @Inject
    private PublicidadPersistence persistence;
    
    
    public PublicidadEntity createPublicidad(PublicidadEntity publicidadEntity) throws BusinessLogicException{
        if(!validarPublicidad(publicidadEntity)){
            throw new BusinessLogicException("Ya hay una publicidad con ese nombre en ese rango de fechas");
        }
        else if(!validarNombre(publicidadEntity.getNombre())){
            throw new BusinessLogicException("El nombre es invalido");
        }
        else if(!validarMensaje(publicidadEntity.getMensaje())){
            throw new BusinessLogicException("El mensaje es invalido");
        }
        else if(!validarCosto(publicidadEntity.getCosto())){
            throw new BusinessLogicException("El costo es invalido");
        }
        else if(!validarFechas(publicidadEntity.getFechaDeInicio(), publicidadEntity.getFechaDeSalida())){
            throw new BusinessLogicException("Las fechas son invalidas");
        }
        PublicidadEntity retorno = persistence.create(publicidadEntity);
        return retorno;
    }
    
    public List<PublicidadEntity> getPublicidades(){
        List<PublicidadEntity> publicidades = persistence.findAll();
        return publicidades;
    }
    
    public PublicidadEntity getPublicidad(Long publicidadId){
        PublicidadEntity publicidadEntity = persistence.find(publicidadId);
        return publicidadEntity;
    }
    
    public PublicidadEntity updatePublicidad(PublicidadEntity publicidadEntity)throws BusinessLogicException{
        if(!validarPublicidad(publicidadEntity)){
            throw new BusinessLogicException("Ya hay una publicidad con ese nombre en ese rango de fechas");
        }
        else if(!validarNombre(publicidadEntity.getNombre())){
            throw new BusinessLogicException("El nombre es invalido");
        }
        else if(!validarMensaje(publicidadEntity.getMensaje())){
            throw new BusinessLogicException("El mensaje es invalido");
        }
        else if(!validarCosto(publicidadEntity.getCosto())){
            throw new BusinessLogicException("El costo es invalido");
        }
        else if(!validarFechas(publicidadEntity.getFechaDeInicio(), publicidadEntity.getFechaDeSalida())){
            throw new BusinessLogicException("Las fechas son invalidas");
        }
        PublicidadEntity result = persistence.update(publicidadEntity);
        return result;
    }
    
    public void deletePublicidad(Long publicidadId) {
        persistence.delete(publicidadId);
    }
    
    
    
    private boolean validarNombre(String nombre){
        return !(nombre == null || nombre.isEmpty());
    }
    private boolean validarMensaje(String mensaje){
        return !(mensaje == null|| mensaje.isEmpty());
    }
    private boolean validarCosto(double costo){
        return !(costo<0);
    }
    private boolean validarFechas(Date fechaInicio, Date fechaSalida){
        return !(fechaInicio==null||fechaSalida==null||fechaInicio.compareTo(fechaSalida)>0);
    }
    private boolean validarPublicidad(PublicidadEntity entidad){
        String nombre = entidad.getNombre();
        boolean retorno = true;
        PublicidadEntity entidad2 = persistence.findByName(nombre);
        if(entidad2 != null){
            if((entidad.getFechaDeInicio().compareTo(entidad2.getFechaDeInicio())>0 && entidad.getFechaDeInicio().compareTo(entidad2.getFechaDeSalida())<0) || entidad.getFechaDeSalida().compareTo(entidad2.getFechaDeInicio())>0 && entidad.getFechaDeSalida().compareTo(entidad2.getFechaDeSalida())<0)
                    retorno = false;
                }
    
        return retorno;
    }
}
