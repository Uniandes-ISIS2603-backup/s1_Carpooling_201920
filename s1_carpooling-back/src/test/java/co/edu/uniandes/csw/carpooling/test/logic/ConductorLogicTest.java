/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.logic;

import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.ConductorPersistence;
import co.edu.uniandes.csw.carpooling.ejb.ConductorLogic;
import co.edu.uniandes.csw.carpooling.entities.VehiculoEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Nicolas Fajardo
 */
@RunWith(Arquillian.class)
public class ConductorLogicTest {

    /**
     * la logica del conductor
     */
    @Inject
    private ConductorLogic conductorLogic;

    /**
     * el podam factory
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * el entity manager
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * la transaccion
     */
    @Inject
    private UserTransaction utx;

    /**
     * lista con datos para pruebas
     */
    private List<ConductorEntity> data = new ArrayList<ConductorEntity>();

    /**
     * crea el deployment
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ConductorEntity.class.getPackage())
                .addPackage(ConductorLogic.class.getPackage())
                .addPackage(ConductorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * configuraciòn de los datos de prueba
     */
    @Before
    public void ConfigTest() {
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

    /**
     * borra los datos de la lista data
     */
    private void clearData() {
        em.createQuery("delete from ConductorEntity").executeUpdate();
    }

    /**
     * llena la lista data con conductores para las pruebas
     */
    private void insertData() {

        for (int i = 0; i < 3; i++) {
            ConductorEntity entity = factory.manufacturePojo(ConductorEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * test de crear conductor
     * @throws BusinessLogicException 
     */
    @Test
    public void crearConductorTest() throws BusinessLogicException {
        ConductorEntity newEntity = factory.manufacturePojo(ConductorEntity.class);
        Date date1 = new Date();
        newEntity.setFechaDeNacimiento(date1);

        ConductorEntity result = conductorLogic.createConductor(newEntity);
        Assert.assertNotNull(result);
        ConductorEntity entity = em.find(ConductorEntity.class, result.getId());
        Assert.assertEquals(newEntity.getContrasenha(), entity.getContrasenha());
        Assert.assertEquals(newEntity.getCorreo(), entity.getCorreo());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getNumDocumento(), entity.getNumDocumento());
        Assert.assertEquals(newEntity.getTelefono(), entity.getTelefono());
        Assert.assertEquals(newEntity.getTipoDocumento(), entity.getTipoDocumento());
    }

    /**
     * test de crear un conductor con un correo que ya existe
     * @throws BusinessLogicException en caso de que se incumpla alguna regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void crearConductorConCorreoExistente() throws BusinessLogicException {
        ConductorEntity newEntity = factory.manufacturePojo(ConductorEntity.class);
        newEntity.setCorreo(data.get(0).getCorreo());
        conductorLogic.createConductor(newEntity);
    }

    /**
     * test de obtener todos los conductores
     */
    @Test
    public void getConductoresTest() {
        List<ConductorEntity> list = conductorLogic.getConductores();
        Assert.assertEquals(data.size(), list.size());
        for (ConductorEntity entity : list) {
            boolean found = false;
            for (ConductorEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * test de obtener un conductor dado su id
     */
    @Test
    public void getConductorTest() {
        ConductorEntity entity = data.get(0);
        ConductorEntity resultEntity = conductorLogic.getConductor(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getContrasenha(), entity.getContrasenha());
        Assert.assertEquals(resultEntity.getCorreo(), entity.getCorreo());
        Assert.assertEquals(resultEntity.getFechaDeNacimiento(), entity.getFechaDeNacimiento());
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(resultEntity.getNumDocumento(), entity.getNumDocumento());
        Assert.assertEquals(resultEntity.getTelefono(), entity.getTelefono());
        Assert.assertEquals(resultEntity.getTipoDocumento(), entity.getTipoDocumento());
    }

    /**
     * Test de actualizar un conductor
     * @throws BusinessLogicException en caso de que al actualizar el conductor se rompa alguna regla de negocio
     */
    @Test
    public void updateConductorTest() throws BusinessLogicException {
        ConductorEntity entity = data.get(0);
        ConductorEntity pojoEntity = factory.manufacturePojo(ConductorEntity.class);
        Date date = new Date();
        pojoEntity.setId(entity.getId());
        pojoEntity.setFechaDeNacimiento(date);
        conductorLogic.actualizarConductor(pojoEntity);
        ConductorEntity resp = em.find(ConductorEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getContrasenha(), resp.getContrasenha());
        Assert.assertEquals(pojoEntity.getCorreo(), resp.getCorreo());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getNumDocumento(), resp.getNumDocumento());
        Assert.assertEquals(pojoEntity.getTelefono(), resp.getTelefono());
        Assert.assertEquals(pojoEntity.getTipoDocumento(), resp.getTipoDocumento());
    }

    /**
     * Test deactualizar un conductor con un correo invalido 1
     * @throws BusinessLogicException  en caso de que al actualizar el conductor se rompa alguna regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void actualizarLibroConCorreoInvalido() throws BusinessLogicException {
        ConductorEntity entity = data.get(0);
        ConductorEntity pojoEntity = factory.manufacturePojo(ConductorEntity.class);
        pojoEntity.setCorreo("");
        conductorLogic.actualizarConductor(pojoEntity);
    }

    /**
     * Test deactualizar un conductor con un correo invalido 2
     * @throws BusinessLogicException  en caso de que al actualizar el conductor se rompa alguna regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void actualizarLibroConCorreoInvalido2() throws BusinessLogicException {
        ConductorEntity entity = data.get(0);
        ConductorEntity pojoEntity = factory.manufacturePojo(ConductorEntity.class);
        pojoEntity.setCorreo(null);
        conductorLogic.actualizarConductor(pojoEntity);
    }

    /**
     * Test de borrar un conductor
     * @throws BusinessLogicException  en caso de que al borrar al conductor se rompa una regla de negocio
     */
    @Test
    public void deleteConductorTest() throws BusinessLogicException {
        ConductorEntity entity = data.get(0);
        conductorLogic.deleteConductor(entity.getId());
        ConductorEntity deleted = em.find(ConductorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
