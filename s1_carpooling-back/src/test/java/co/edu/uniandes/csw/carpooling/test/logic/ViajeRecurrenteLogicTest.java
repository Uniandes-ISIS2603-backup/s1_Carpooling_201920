/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.logic;

import co.edu.uniandes.csw.carpooling.ejb.ViajeRecurrenteLogic;
import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeRecurrenteEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.ViajeRecurrentePersistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Juan David Alarcón
 */
@RunWith(Arquillian.class)
public class ViajeRecurrenteLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @PersistenceContext
    protected EntityManager em;

    @Inject
    private ViajeRecurrenteLogic viajeRecurrenteLogic;

    @Inject
    private UserTransaction utx;

    private List<ViajeRecurrenteEntity> data = new ArrayList<ViajeRecurrenteEntity>();
    private List<ConductorEntity> dataConductor = new ArrayList<ConductorEntity>();

    @Deployment
    public static JavaArchive createDeployment() {

        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ViajeRecurrenteEntity.class.getPackage())
                .addPackage(ViajeRecurrenteLogic.class.getPackage())
                .addPackage(ViajeRecurrentePersistence.class.getPackage())
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
        em.createQuery("delete from ViajeRecurrenteEntity").executeUpdate();
        em.createQuery("delete from ConductorEntity").executeUpdate();
    }

    private void insertData() {

        for (int i = 0; i < 3; i++) {
            ConductorEntity entity = factory.manufacturePojo(ConductorEntity.class);
            em.persist(entity);
            dataConductor.add(entity);
        }

        for (int i = 0; i < 3; i++) {
            ViajeRecurrenteEntity entity = factory.manufacturePojo(ViajeRecurrenteEntity.class);
            if (i == 0) {
                entity.setConductor(dataConductor.get(0));
            }

            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createViajeRecurrenteTest() throws BusinessLogicException {
        ViajeRecurrenteEntity newEntity = factory.manufacturePojo(ViajeRecurrenteEntity.class);
        System.out.println(newEntity.getFechaInicio().toString() + " new ");
        System.out.println(newEntity.getFechaFin().toString() + " new");
        ViajeRecurrenteEntity result = viajeRecurrenteLogic.createViajeRecurrente(newEntity);
        Assert.assertNotNull(result);
        System.out.println(result.getFechaInicio().toString() + " result");
        System.out.println(result.getFechaFin().toString() + " result");

        ViajeRecurrenteEntity entity = em.find(ViajeRecurrenteEntity.class, result.getId());

        System.out.println(entity.getFechaInicio().toString() + " entity");
        System.out.println(entity.getFechaFin().toString() + " Entity");
        Assert.assertEquals(entity.getFechaInicio(), result.getFechaInicio());
        Assert.assertEquals(entity.getFechaFin(), result.getFechaFin());
        Assert.assertEquals(entity.getFrecuencia(), result.getFrecuencia());

    }

    @Test
    public void createViajeRecurrenteForConductorTest() throws BusinessLogicException {
        ViajeRecurrenteEntity newEntity = factory.manufacturePojo(ViajeRecurrenteEntity.class);
        newEntity.setConductor(dataConductor.get(0));
        ViajeRecurrenteEntity result = viajeRecurrenteLogic.createViajeRecurrente(dataConductor.get(0).getId(), newEntity);
        Assert.assertNotNull(result);
        ViajeRecurrenteEntity entity = em.find(ViajeRecurrenteEntity.class, result.getId());

        Assert.assertEquals(entity.getFechaInicio(), result.getFechaInicio());
        Assert.assertEquals(entity.getFechaFin(), result.getFechaFin());
        Assert.assertEquals(entity.getFrecuencia(), result.getFrecuencia());
    }

    @Test
    public void getViajesRecurrentesByConductorTest() throws BusinessLogicException {
        List<ViajeRecurrenteEntity> list = viajeRecurrenteLogic.getViajesRecurrentes(dataConductor.get(0).getId());
        for (ViajeRecurrenteEntity entity : list) {
            boolean found = false;
            for (ViajeRecurrenteEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getViajeRecurrenteByConductorTest() {
        ViajeRecurrenteEntity entity = data.get(0);
        ViajeRecurrenteEntity resultEntity = viajeRecurrenteLogic.getViajeRecurrente(dataConductor.get(0).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());

        Assert.assertEquals(entity.getFechaInicio(), resultEntity.getFechaInicio());
        Assert.assertEquals(entity.getFechaFin(), resultEntity.getFechaFin());
        Assert.assertEquals(entity.getFrecuencia(), resultEntity.getFrecuencia());
    }

    public void updateViajeRecurrenteByConductorTest() {
        ViajeRecurrenteEntity entity = data.get(0);
        ViajeRecurrenteEntity pojoEntity = factory.manufacturePojo(ViajeRecurrenteEntity.class);

        pojoEntity.setId(entity.getId());

        viajeRecurrenteLogic.updateViajeRecurrente(dataConductor.get(0).getId(), pojoEntity);

        ViajeRecurrenteEntity resp = em.find(ViajeRecurrenteEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getFechaInicio(), resp.getFechaInicio());
        Assert.assertEquals(pojoEntity.getFechaFin(), resp.getFechaFin());
        Assert.assertEquals(pojoEntity.getFrecuencia(), resp.getFrecuencia());
    }

    @Test
    public void deleteViajeRecurrenteByConductorTest() throws BusinessLogicException {
        ViajeRecurrenteEntity entity = data.get(0);
        viajeRecurrenteLogic.deleteViajeRecurrente(dataConductor.get(0).getId(), entity.getId());
        ViajeRecurrenteEntity deleted = em.find(ViajeRecurrenteEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test(expected = BusinessLogicException.class)
    public void createViajeRecurrenteTestFechaInicioInvalida() throws BusinessLogicException {
        ViajeRecurrenteEntity newEntity = factory.manufacturePojo(ViajeRecurrenteEntity.class);

        Date inicio = new Date();

        inicio.setTime(System.currentTimeMillis() - 9999 * 900000);

        newEntity.setFechaInicio(inicio);

        ViajeRecurrenteEntity result = viajeRecurrenteLogic.createViajeRecurrente(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createViajeRecurrenteTestFechaFinInvalida() throws BusinessLogicException {
        ViajeRecurrenteEntity newEntity = factory.manufacturePojo(ViajeRecurrenteEntity.class);

        Date fin = new Date();

        fin.setTime(System.currentTimeMillis() - 9999 * 900000);

        newEntity.setFechaFin(fin);

        ViajeRecurrenteEntity result = viajeRecurrenteLogic.createViajeRecurrente(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createViajeRecurrenteTestFechaInicioFinInvalida() throws BusinessLogicException {
        ViajeRecurrenteEntity newEntity = factory.manufacturePojo(ViajeRecurrenteEntity.class);
        Date inicio = new Date();
        Date fin = new Date();

        long actual = System.currentTimeMillis();

        inicio.setTime(actual + 9999 * 900000);

        fin.setTime(actual + 9999 * 9000);

        newEntity.setFechaInicio(inicio);
        newEntity.setFechaFin(fin);

        ViajeRecurrenteEntity result = viajeRecurrenteLogic.createViajeRecurrente(newEntity);
    }

    @Test
    public void createViajeRecurrenteTestFechasValidas() throws BusinessLogicException {
        ViajeRecurrenteEntity newEntity = factory.manufacturePojo(ViajeRecurrenteEntity.class);
        Date inicio = new Date();
        Date fin = new Date();

        long actual = System.currentTimeMillis();

        inicio.setTime(actual + 9999 * 9000);

        fin.setTime(actual + 9999 * 999999);

        newEntity.setFechaInicio(inicio);
        newEntity.setFechaFin(fin);

        ViajeRecurrenteEntity result = viajeRecurrenteLogic.createViajeRecurrente(newEntity);
    }
}
