/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;

import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeRecurrenteEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.ConductorPersistence;
import co.edu.uniandes.csw.carpooling.persistence.ViajeRecurrentePersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan David Alarcon
 */
@Stateless
public class ViajeRecurrenteLogic {

    @Inject
    private ViajeRecurrentePersistence persistence;

    @Inject
    ConductorPersistence conductorPersistence;

    public ViajeRecurrenteEntity createViajeRecurrente(ViajeRecurrenteEntity viajeRecurrente) throws BusinessLogicException {

        if (viajeRecurrente.getFechaInicio().getTime() < System.currentTimeMillis()) {
            throw new BusinessLogicException("La fecha de inicio no puede ser menor a la actual");
        }

        if (viajeRecurrente.getFechaFin().getTime() < System.currentTimeMillis()) {
            throw new BusinessLogicException("La fecha final no puede ser menor a la actual");
        }

        if (viajeRecurrente.getFechaFin().getTime() < viajeRecurrente.getFechaInicio().getTime()) {
            throw new BusinessLogicException("La fecha final no puede ser menor a la fecha inicial");
        }

        viajeRecurrente = persistence.create(viajeRecurrente);
        return viajeRecurrente;
    }

    public ViajeRecurrenteEntity createViajeRecurrente(Long conductorId, ViajeRecurrenteEntity viajeRecurrente) throws BusinessLogicException {

        if (viajeRecurrente.getFechaInicio().getTime() < System.currentTimeMillis()) {
            throw new BusinessLogicException("La fecha de inicio no puede ser menor a la actual");
        }

        if (viajeRecurrente.getFechaFin().getTime() < System.currentTimeMillis()) {
            throw new BusinessLogicException("La fecha final no puede ser menor a la actual");
        }

        if (viajeRecurrente.getFechaFin().getTime() < viajeRecurrente.getFechaInicio().getTime()) {
            throw new BusinessLogicException("La fecha final no puede ser menor a la fecha inicial");
        }

        ConductorEntity conductor = conductorPersistence.find(conductorId);

        viajeRecurrente.setConductor(conductor);
        viajeRecurrente = persistence.create(viajeRecurrente);

        return viajeRecurrente;
    }

    public List<ViajeRecurrenteEntity> getViajesRecurrentes(Long conductorsId) {

        ConductorEntity conductorEntity = conductorPersistence.find(conductorsId);

        return conductorEntity.getViajesRecurrentes();
    }

    public ViajeRecurrenteEntity getViajeRecurrente(Long conductoresId, Long viajesRecurrentesId) {

        return persistence.find(conductoresId, viajesRecurrentesId);
    }

    public ViajeRecurrenteEntity updateViajeRecurrente(Long conductoresId, ViajeRecurrenteEntity viajeRecurrenteEntity) {
        ConductorEntity conductorEntity = conductorPersistence.find(conductoresId);
        viajeRecurrenteEntity.setConductor(conductorEntity);
        persistence.update(viajeRecurrenteEntity);

        return viajeRecurrenteEntity;
    }

    public void deleteViajeRecurrente(Long conductoresId, Long viajesRecurrentesId) throws BusinessLogicException {

        ViajeRecurrenteEntity old = getViajeRecurrente(conductoresId, viajesRecurrentesId);
        if (old == null) {
            throw new BusinessLogicException("El viajeRecurrente con id = " + viajesRecurrentesId + " no esta asociado a el libro con id = " + conductoresId);
        }
        persistence.delete(old.getId());
    }

}
