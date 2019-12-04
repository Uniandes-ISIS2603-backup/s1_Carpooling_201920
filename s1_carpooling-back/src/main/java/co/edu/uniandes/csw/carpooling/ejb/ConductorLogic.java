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

    /**
     * Persistencia de los conductores
     */
    @Inject
    private ConductorPersistence persistence;

    /**
     * Crea un conductor
     * @param conductor el conductor a crear
     * @return el conductor creado
     * @throws BusinessLogicException si el conductor a crear no cumple con alguna de las reglas de negocio
     */
    public ConductorEntity createConductor(ConductorEntity conductor) throws BusinessLogicException {
        if (!validarCorreo(conductor.getCorreo())) {
            throw new BusinessLogicException("El correo del conductor es inválido");
        } if (persistence.findByCorreo(conductor.getCorreo()) != null) {
            throw new BusinessLogicException("Ya hay un conductor con ese correo");
        }  if (!validarNombre(conductor.getNombre())) {
            throw new BusinessLogicException("El nombre es inválido");
        }  if (!validarTelefono(conductor.getTelefono())) {
            throw new BusinessLogicException("El número de teléfono es inválido");
        }  if (!validarNumeroDocumento(conductor.getNumDocumento())) {
            throw new BusinessLogicException("El número de documento es inválido");
        }  if (!validarFechaNacimiento(conductor.getFechaDeNacimiento())) {
            throw new BusinessLogicException("La fecha de nacimiento es inválida");
        }
        conductor = persistence.create(conductor);
        return conductor;
    }

    /**
     * retorna una lista con todos los conductores de la base de datos
     * @return la lista con todos los conductores de la base de datos
     */
    public List<ConductorEntity> getConductores() {
        return persistence.findAll();
    }

    /**
     * retorna un conductor dado su id
     * @param conductorId el id del conductor buscado
     * @return el conductor buscado
     */
    public ConductorEntity getConductor(Long conductorId) {
        return persistence.find(conductorId);
    }

    /**
     * Actualiza un conductor por uno ingresado por parametro
     * @param conductorEntity el conductor actualizado
     * @return el conductor actualizado
     * @throws BusinessLogicException si el conductor actualizado no cumple con alguna de las reglas de negocio
     */
    public ConductorEntity actualizarConductor(ConductorEntity conductorEntity) throws BusinessLogicException {
        if (!validarCorreo(conductorEntity.getCorreo())) {
            throw new BusinessLogicException("El correo del conductor es invalido");
        }  if (!validarNombre(conductorEntity.getNombre())) {
            throw new BusinessLogicException("El nombre es inválido");
        }  if (!validarTelefono(conductorEntity.getTelefono())) {
            throw new BusinessLogicException("El número de teléfono es inválido");
        }  if (!validarNumeroDocumento(conductorEntity.getNumDocumento())) {
            throw new BusinessLogicException("El número de documento es inválido");
        }  if (!validarFechaNacimiento(conductorEntity.getFechaDeNacimiento())) {
            throw new BusinessLogicException("La fecha de nacimiento es inválida");
        }
        return persistence.update(conductorEntity);
    }

    /**
     * Borra un conductor dado su id
     * @param conductorId el id del conductor a borrar
     */
    public void deleteConductor(Long conductorId) {
        persistence.delete(conductorId);
    }

    /**
     * valida si el nombre del conductor es valido
     * @param nombre el nombre a validar
     * @return true si el nombre sigue con las reglas de negocio, false de lo contrario
     */
    private boolean validarNombre(String nombre) {
        return !(nombre == null || nombre.isEmpty());
    }

    /**
     * valida si el numero de telefono cumple con las reglas de negocio
     * @param telefono el numero de telefono a validar
     * @return true si el numero de telefono sigue las reglas de negocio, false de lo contrario
     */
    private boolean validarTelefono(String telefono) {
        return !(telefono == null || telefono.isEmpty());
    }

    /**
     * valida si el numero de documento cumple con las reglas de negocio
     * @param numeroDocumento el numero de documento a validar
     * @return  true si el numero de documento sigue las reglas de negocio, false de lo contrario
     */
    private boolean validarNumeroDocumento(String numeroDocumento) {
        return !(numeroDocumento == null || numeroDocumento.isEmpty());
    }

    /**
     * valida si la fecha de nacimiento cumple con las reglas de negocio
     * @param fechaNacimiento la fecha de nacimiento a validar
     * @return true si la fecha de nacimiento cumple con las reglas de negocio, false de lo contrario
     */
    private boolean validarFechaNacimiento(Date fechaNacimiento) {
        return !(fechaNacimiento.compareTo(new Date()) >= 0 || fechaNacimiento == null);
    }

    /**
     * valida si el correo electronico cumple con las reglas de negocio
     * @param correo el correo a validar
     * @return true si el correo electronico cumple con las reglas de negocio, false de lo contrario
     */
    private boolean validarCorreo(String correo) {
        return !(correo == null || correo.isEmpty());
    }
}
