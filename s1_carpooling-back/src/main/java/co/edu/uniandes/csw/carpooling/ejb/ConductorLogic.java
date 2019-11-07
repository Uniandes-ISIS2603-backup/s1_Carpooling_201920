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

    public List<ConductorEntity> getConductores() {
        return persistence.findAll();
    }

    public ConductorEntity getConductor(Long conductorId) {
        return persistence.find(conductorId);
    }

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

    public void deleteConductor(Long conductorId) {
        persistence.delete(conductorId);
    }

    private boolean validarNombre(String nombre) {
        return !(nombre == null || nombre.isEmpty());
    }

    private boolean validarTelefono(String telefono) {
        return !(telefono == null || telefono.isEmpty());
    }

    private boolean validarNumeroDocumento(String numeroDocumento) {
        return !(numeroDocumento == null || numeroDocumento.isEmpty());
    }

    private boolean validarFechaNacimiento(Date fechaNacimiento) {
        return !(fechaNacimiento.compareTo(new Date()) >= 0 || fechaNacimiento == null);
    }

    private boolean validarCorreo(String correo) {

        return !(correo == null || correo.isEmpty());
    }
}
