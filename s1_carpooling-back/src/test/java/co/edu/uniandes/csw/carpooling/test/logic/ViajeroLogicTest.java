/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.logic;

import co.edu.uniandes.csw.carpooling.ejb.ViajeroLogic;
import co.edu.uniandes.csw.carpooling.entities.ViajeroEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.ViajeroPersistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Santiago Ballesteros
 */
@RunWith(Arquillian.class)
public class ViajeroLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ViajeroLogic viajeroLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ViajeroEntity> data = new ArrayList<ViajeroEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ViajeroEntity.class.getPackage())
                .addPackage(ViajeroLogic.class.getPackage())
                .addPackage(ViajeroPersistence.class.getPackage())
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

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        //     em.createQuery("delete from PublicidadEntity").executeUpdate();
        em.createQuery("delete from ViajeroEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ViajeroEntity entity = factory.manufacturePojo(ViajeroEntity.class);
            em.persist(entity);
            data.add(entity);
        }
        //      PublicidadEntity publicidad = factory.manufacturePojo(PublicidadEntity.class);
        //    publicidad.setViajero(data.get(1));
//     em.persist(viajero);
        //       data.get(1).getPrizes().add(viajero);
    }

    @Test
    public void createViajeroTest() throws BusinessLogicException {

        ViajeroEntity newEntity = factory.manufacturePojo(ViajeroEntity.class);
        ViajeroEntity result = viajeroLogic.createViajero(newEntity);
        Assert.assertNotNull(result);

        ViajeroEntity entity = em.find(ViajeroEntity.class, result.getId());
        Assert.assertEquals(entity.getNombre(), result.getNombre());
        Assert.assertEquals(result.getCorreo(), entity.getCorreo());
        Assert.assertEquals(result.getTelefono(), entity.getTelefono());
        Assert.assertEquals(result.getContrasenha(), entity.getContrasenha());
        Assert.assertEquals(result.getFechaDeNacimiento(), entity.getFechaDeNacimiento());
        Assert.assertEquals(result.getNumDocumento(), entity.getNumDocumento());
        Assert.assertEquals(result.getTipoDocumento(), entity.getTipoDocumento());
    }

    @Test(expected = BusinessLogicException.class)
    public void createViajeroNombreNullTest() throws BusinessLogicException {

        ViajeroEntity newEntity = factory.manufacturePojo(ViajeroEntity.class);
        newEntity.setNombre(null);
        ViajeroEntity result = viajeroLogic.createViajero(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createViajeroCorreoNullTest() throws BusinessLogicException {

        ViajeroEntity newEntity = factory.manufacturePojo(ViajeroEntity.class);
        newEntity.setCorreo(null);
        ViajeroEntity result = viajeroLogic.createViajero(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createViajeroTelefonoNullTest() throws BusinessLogicException {

        ViajeroEntity newEntity = factory.manufacturePojo(ViajeroEntity.class);
        newEntity.setTelefono(null);
        ViajeroEntity result = viajeroLogic.createViajero(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createViajeroContrasenhaNullTest() throws BusinessLogicException {

        ViajeroEntity newEntity = factory.manufacturePojo(ViajeroEntity.class);
        newEntity.setContrasenha(null);
        ViajeroEntity result = viajeroLogic.createViajero(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createViajeroFechaDeNacimientoNullTest() throws BusinessLogicException {

        ViajeroEntity newEntity = factory.manufacturePojo(ViajeroEntity.class);
        newEntity.setFechaDeNacimiento(null);
        ViajeroEntity result = viajeroLogic.createViajero(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createViajeroNumDocumentoNullTest() throws BusinessLogicException {

        ViajeroEntity newEntity = factory.manufacturePojo(ViajeroEntity.class);
        newEntity.setNumDocumento(null);
        ViajeroEntity result = viajeroLogic.createViajero(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createViajeroTipoDocumentoNullTest() throws BusinessLogicException {

        ViajeroEntity newEntity = factory.manufacturePojo(ViajeroEntity.class);
        newEntity.setTipoDocumento(null);
        ViajeroEntity result = viajeroLogic.createViajero(newEntity);
    }

    /**
     * Prueba para crear un Viajero.
     *
     * @throws co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException
     * .
     */
    @Test(expected = BusinessLogicException.class)
    public void createViajeroConCorreoRepetidoTest() throws BusinessLogicException {
        ViajeroEntity newEntity = factory.manufacturePojo(ViajeroEntity.class);
        newEntity.setCorreo(data.get(0).getCorreo());
        viajeroLogic.createViajero(newEntity);
    }

    @Test
    public void getViajerosTest() {
        List<ViajeroEntity> list = viajeroLogic.getViajeros();
        Assert.assertEquals(data.size(), list.size());
        for (ViajeroEntity entity : list) {
            boolean found = false;
            for (ViajeroEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getViajeroTest() {
        ViajeroEntity entity = data.get(0);
        ViajeroEntity resultEntity = viajeroLogic.getViajero(entity.getId());
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

    @Test
    public void updateViajeroTest() throws BusinessLogicException {
        ViajeroEntity entity = data.get(0);
        ViajeroEntity pojoEntity = factory.manufacturePojo(ViajeroEntity.class);
        Date date = new Date();
        pojoEntity.setId(entity.getId());
        pojoEntity.setFechaDeNacimiento(date);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        viajeroLogic.updateViajero(entity.getId(), pojoEntity);
        ViajeroEntity resp = em.find(ViajeroEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getContrasenha(), resp.getContrasenha());
        Assert.assertEquals(pojoEntity.getCorreo(), resp.getCorreo());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getNumDocumento(), resp.getNumDocumento());
        Assert.assertEquals(pojoEntity.getTelefono(), resp.getTelefono());
        Assert.assertEquals(pojoEntity.getTipoDocumento(), resp.getTipoDocumento());
    }

    @Test
    public void deleteViajeroTest() throws BusinessLogicException {
        ViajeroEntity entity = data.get(0);
        viajeroLogic.deleteViajero(entity.getId());
        ViajeroEntity deleted = em.find(ViajeroEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

}
