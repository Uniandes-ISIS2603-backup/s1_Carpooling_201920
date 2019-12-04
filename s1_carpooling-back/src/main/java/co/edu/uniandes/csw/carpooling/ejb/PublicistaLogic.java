/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;

import co.edu.uniandes.csw.carpooling.entities.PublicistaEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.PublicistaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Santiago Ballesteros
 */
@Stateless
public class PublicistaLogic {

    private static final Logger LOGGER = Logger.getLogger(PublicistaLogic.class.getName());

    @Inject
    PublicistaPersistence pp;

    public PublicistaEntity createPublicista(PublicistaEntity publicista) throws BusinessLogicException {

        if (publicista.getTipoPublicista() == null) {
            throw new BusinessLogicException("El tipoPublicista del publicista esta vacio");
        }
        if (publicista.getNombre() == null) {
            throw new BusinessLogicException("El nombre del publicista esta vacio");
        }
        if (publicista.getContrasenha() == null) {
            throw new BusinessLogicException("La contraseña del publicista esta vacia");
        }
        if (publicista.getCorreo() == null) {
            throw new BusinessLogicException("El correo del publicista esta vacio");
        }
        if (pp.findByCorreo(publicista.getCorreo()) != null) {
            throw new BusinessLogicException("Ya existe un publicista con el correo: " + publicista.getCorreo());
        }
        if (publicista.getTelefono() == null) {
            throw new BusinessLogicException("El telefono del publicista esta vacio");
        }
        // Un publicista que no sea empresa debe tener apellido, rut y cedula
        if (publicista.getTipoPublicista().compareTo(PublicistaEntity.TIPO_PUBLICISTA.EMPRESA) != 0) {
            if (publicista.getApellido() == null) {
                throw new BusinessLogicException("El apellido del publicista esta vacio");
            }
            if (publicista.getRut() == null) {
                throw new BusinessLogicException("El rut del publicista esta vacio");
            }
            if (publicista.getCedula() == null) {
                throw new BusinessLogicException("La cedula del publicista esta vacia");
            }
            if ((pp.findByRut(publicista.getRut()) != null)) {
                throw new BusinessLogicException("Ya existe un publicista con el Rut: " + publicista.getRut());
            }
            if ((pp.findByRut(publicista.getCedula()) != null)) {
                throw new BusinessLogicException("Ya existe un publicista con la Cedula: " + publicista.getCedula());
            }
        }
        if (publicista.getTipoPublicista().compareTo(PublicistaEntity.TIPO_PUBLICISTA.PERSONA_NATURAL_CON_EMPRESA) != 0) {
            if (publicista.getNit() == null) {
                throw new BusinessLogicException("El nit del publicista esta vacio");
            }
            if ((pp.findByNit(publicista.getNit()) != null)) {
                throw new BusinessLogicException("Ya existe un publicista con el Nit: " + publicista.getNit());
            }
        }
        publicista = pp.create(publicista);
        return publicista;
    }

    public List<PublicistaEntity> getPublicistas() {
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        return pp.findAll();
    }

    public PublicistaEntity getPublicista(Long publicistasId) {
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        PublicistaEntity publicista = pp.find(publicistasId);
        if (publicista == null) {
            LOGGER.log(Level.SEVERE, "La publicista con el id = {0} no existe", publicistasId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar publicista con id = {0}", publicistasId);
        return publicista;
    }

    public PublicistaEntity updatePublicista(Long publicistasId, PublicistaEntity publicistaEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar publicista con id = {0}", publicistasId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        PublicistaEntity newEntity = pp.update(publicistaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar publicista con id={0}", publicistaEntity.getId());
        return newEntity;
    }

    public void deletePublicista(Long publicistasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar publicista con id = {0}", publicistasId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        pp.delete(publicistasId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar publicista con id = {0}", publicistasId);
    }
}
