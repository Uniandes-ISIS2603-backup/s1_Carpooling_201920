/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.logic;

import co.edu.uniandes.csw.carpooling.ejb.NotificacionLogic;
import co.edu.uniandes.csw.carpooling.ejb.ReservaLogic;
import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.entities.NotificacionEntity;
import co.edu.uniandes.csw.carpooling.entities.ReservaEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeroEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.ReservaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Estudiante
 */
public class ReservaLogicTest {
        private PodamFactory factory = new PodamFactoryImpl();

    @PersistenceContext
    protected EntityManager em;

    @Inject
    private ReservaLogic reservaLogic;

    @Inject
    private UserTransaction utx;

    private List<ReservaEntity> data = new ArrayList<ReservaEntity>();
    private ViajeEntity viaje = new ViajeEntity();
    private ViajeroEntity viajero = new ViajeroEntity();

    @Deployment
    public static JavaArchive createDeployment() {

        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ReservaEntity.class.getPackage())
                .addPackage(ReservaLogic.class.getPackage())
                .addPackage(ReservaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");

    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void clearData() {
        em.createQuery("delete from ReservaEntity").executeUpdate();
        em.createQuery("delete from ViajeEntity").executeUpdate();
        em.createQuery("delete from ViajeroEntity").executeUpdate();
    }

    private void insertData() {
        viajero = factory.manufacturePojo(ViajeroEntity.class);
        em.persist(viajero);
        viaje = factory.manufacturePojo(ViajeEntity.class);
        em.persist(viaje);
        for (int i = 0; i < 3; i++) {
            ReservaEntity reservaEntity = factory.manufacturePojo(ReservaEntity.class);
            reservaEntity.setViajero(viajero);
            reservaEntity.setViaje(viaje);
            em.persist(reservaEntity);
            data.add(reservaEntity);
        }
    }
        @Test
        public void createReservaTest() throws BusinessLogicException {
        ReservaEntity newEntity = factory.manufacturePojo(ReservaEntity.class);
        newEntity.setViaje(viaje);
         newEntity.setViajero(viajero);
        ReservaEntity result = reservaLogic.createReserva(viaje.getId() ,newEntity);
        Assert.assertNotNull(result);

        ReservaEntity entity = em.find(ReservaEntity.class, result.getId());
        Assert.assertEquals(entity.getNumeroDeReserva(), result.getNumeroDeReserva());
        Assert.assertEquals(entity.getFecha(), result.getFecha());
        Assert.assertEquals(entity.getEstado(), result.getEstado());
    }
}

