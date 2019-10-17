/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;

import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.entities.VehiculoEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.ConductorPersistence;
import co.edu.uniandes.csw.carpooling.persistence.VehiculoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan David Alarcon
 */
@Stateless
public class VehiculoLogic {

    @Inject
    private VehiculoPersistence persistence;

    @Inject
    ConductorPersistence conductorPersistence;

    public VehiculoEntity createVehiculo(VehiculoEntity vehiculo) throws BusinessLogicException {

        if (vehiculo.getPlaca().length() != 7) {
            throw new BusinessLogicException("La placa tiene que tener 7 caracteres (incluyendo el espacio en la mitad)");
        }

        String[] placa = vehiculo.getPlaca().split(" ");

        if (!placa[0].matches("[A-Z]+") || !placa[1].matches("[0-9]+")) {
            throw new BusinessLogicException("La placa tiene que tener el formato 'XXX 123' ");
        }

        if (vehiculo.getSillas() < 1 || vehiculo.getSillas() > 10) {
            throw new BusinessLogicException("El vehiculo tienen que tener entre 1 y 10 sillas");
        }

        vehiculo = persistence.create(vehiculo);
        return vehiculo;
    }

    public VehiculoEntity createVehiculo(Long conductorId, VehiculoEntity vehiculo) throws BusinessLogicException {

        if (vehiculo.getPlaca().length() != 7) {
            throw new BusinessLogicException("La placa tiene que tener 7 caracteres (incluyendo el espacio en la mitad)");
        }

        String[] placa = vehiculo.getPlaca().split(" ");

        if (!placa[0].matches("[A-Z]+") || !placa[1].matches("[0-9]+")) {
            throw new BusinessLogicException("La placa tiene que tener el formato 'XXX 123' ");
        }

        if (vehiculo.getSillas() < 1 || vehiculo.getSillas() > 10) {
            throw new BusinessLogicException("El vehiculo tienen que tener entre 1 y 10 sillas");
        }

        ConductorEntity conductor = conductorPersistence.find(conductorId);
        vehiculo.setConductor(conductor);

        vehiculo = persistence.create(vehiculo);
        return vehiculo;
    }

    public List<VehiculoEntity> getVehiculos(Long conductorsId) {

        ConductorEntity conductorEntity = conductorPersistence.find(conductorsId);

        return conductorEntity.getVehiculos();
    }

    public VehiculoEntity getVehiculo(Long conductoresId, Long vehiculosId) {

        return persistence.find(conductoresId, vehiculosId);
    }

    public VehiculoEntity updateVehiculo(Long conductorsId, VehiculoEntity vehiculoEntity) {
        ConductorEntity conductorEntity = conductorPersistence.find(conductorsId);
        vehiculoEntity.setConductor(conductorEntity);
        persistence.update(vehiculoEntity);

        return vehiculoEntity;
    }

    public void deleteVehiculo(Long conductoresId, Long vehiculosId) throws BusinessLogicException {

        VehiculoEntity old = getVehiculo(conductoresId, vehiculosId);
        if (old == null) {
            throw new BusinessLogicException("El vehiculo con id = " + vehiculosId + " no esta asociado a el libro con id = " + conductoresId);
        }
        persistence.delete(old.getId());
    }

}
