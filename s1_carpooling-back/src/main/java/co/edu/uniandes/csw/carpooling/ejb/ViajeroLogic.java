/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;

import co.edu.uniandes.csw.carpooling.entities.ViajeroEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.ViajeroPersistence;
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
public class ViajeroLogic {

    private static final Logger LOGGER = Logger.getLogger(ViajeroLogic.class.getName());

    @Inject
    ViajeroPersistence pp;

    public ViajeroEntity createViajero(ViajeroEntity viajero) throws BusinessLogicException {

        if (viajero.getNombre() == null) {
            throw new BusinessLogicException("El nombre del viajero esta vacio");
        }
        if (viajero.getTelefono() == null) {
            throw new BusinessLogicException("El telefono del viajero esta vacio");
        }
        if (viajero.getCorreo() == null) {
            throw new BusinessLogicException("El correo del viajero esta vacio");
        }
        if (pp.findByCorreo(viajero.getCorreo()) != null) {
            throw new BusinessLogicException("Ya existe un viajero con el correo: " + viajero.getCorreo());
        }
        if (viajero.getContrasenha() == null) {
            throw new BusinessLogicException("La contraseña del viajero esta vacio");
        }
        if (viajero.getFechaDeNacimiento() == null) {
            throw new BusinessLogicException("La fecha de nacimiento del viajero esta vacio");
        }
        if (viajero.getNumDocumento() == null) {
            throw new BusinessLogicException("El numero de documento del viajero esta vacio");
        }
        if (viajero.getTipoDocumento() == null) {
            throw new BusinessLogicException("El tipo de documento del viajero esta vacio");
        }
        viajero = pp.create(viajero);
        return viajero;
    }

    public List<ViajeroEntity> getViajeros() {
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.        
        return pp.findAll();
    }

    public ViajeroEntity getViajero(Long viajerosId) {
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        ViajeroEntity viajero = pp.find(viajerosId);
        if (viajero == null) {
            LOGGER.log(Level.SEVERE, "La viajero con el id = {0} no existe", viajerosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar viajero con id = {0}", viajerosId);
        return viajero;
    }

    public ViajeroEntity updateViajero(Long viajerosId, ViajeroEntity viajeroEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar viajero con id = {0}", viajerosId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        ViajeroEntity newEntity = pp.update(viajeroEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar viajero con id={0}", viajeroEntity.getId());
        return newEntity;
    }

    public void deleteViajero(Long viajerosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar viajero con id = {0}", viajerosId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        pp.delete(viajerosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar viajero con id = {0}", viajerosId);
    }
}
