/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.logic;

import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import co.edu.uniandes.csw.carpooling.persistence.ConductorPersistence;
import co.edu.uniandes.csw.carpooling.persistence.ViajePersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author JuanDavidSerrano
 */
@Stateless
public class ConductorViajesLogic {

    private static final Logger LOGGER = Logger.getLogger(ConductorViajesLogic.class.getName());

    @Inject
    private ConductorPersistence conductorPersistence;

    @Inject
    private ViajePersistence viajePersistence;

    public ViajeEntity getViaje(Long conductoresId, Long viajesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un viajes al condcutor con id = {0}", conductoresId);
        return null;

    }

}
