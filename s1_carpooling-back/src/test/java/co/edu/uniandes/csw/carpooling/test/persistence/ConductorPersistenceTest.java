/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.entities.VehiculoEntity;
import co.edu.uniandes.csw.carpooling.persistence.ConductorPersistence;
import java.util.ArrayList;
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
 * @author Nicolas Fajardo Ramirez
 */
@RunWith(Arquillian.class)
public class ConductorPersistenceTest {

    /**
     * El despliegue
     *
     * @return
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ConductorEntity.class.getPackage())
                .addPackage(ConductorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * La persistencia del conductor
     */
    @Inject
    ConductorPersistence cp;

    /**
     * El entity manager
     */
    @PersistenceContext
    EntityManager entMan;

    /**
     * componente de transaccion
     */
    @Inject
    UserTransaction utx;

    /**
     * Lista con datos de prueba
     */
    private List<ConductorEntity> data = new ArrayList<ConductorEntity>();

    /**
     * Deja la configuracion lista para las pruebas
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            entMan.joinTransaction();
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
     * Borra los datos de la tabla
     */
    private void clearData() {
        entMan.createQuery("delete from ConductorEntity").executeUpdate();
    }

    /**
     * Inserta datos en la tabla y los deja en la lista data
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ConductorEntity entity = factory.manufacturePojo(ConductorEntity.class);

            entMan.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Rest de crear una entidad. Verifica que el conductor que se acaba de
     * crear tiene los mismos datos que el conductor ingresado a crear<A
     */
    @Test
    public void createTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ConductorEntity conductor = factory.manufacturePojo(ConductorEntity.class);

        ConductorEntity result = cp.create(conductor);
        Assert.assertNotNull(result);

        ConductorEntity entity = entMan.find(ConductorEntity.class, result.getId());

        Assert.assertEquals(conductor.getContrasenha(), entity.getContrasenha());
        Assert.assertEquals(conductor.getCorreo(), entity.getCorreo());
        Assert.assertEquals(conductor.getFechaDeNacimiento(), entity.getFechaDeNacimiento());
        Assert.assertEquals(conductor.getNombre(), entity.getNombre());
        Assert.assertEquals(conductor.getNumDocumento(), entity.getNumDocumento());
        Assert.assertEquals(conductor.getTelefono(), entity.getTelefono());
        Assert.assertEquals(conductor.getTipoDocumento(), entity.getTipoDocumento());
    }

    /**
     * Test del metodo find
     */
    @Test
    public void findTest() {
        ConductorEntity entity = data.get(0);
        ConductorEntity newEntity = cp.find(entity.getId());

        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }

    /**
     * Test del metodo find all
     */
    @Test
    public void findAllTest() {
        List<ConductorEntity> list = cp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ConductorEntity ent : list) {
            boolean found = false;
            for (ConductorEntity ent2 : data) {
                if (ent.getId().equals(ent2.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Test del metodo update
     */
    @Test
    public void updateTest() {
        ConductorEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ConductorEntity newEntity = factory.manufacturePojo(ConductorEntity.class);

        newEntity.setId(entity.getId());

        cp.update(newEntity);

        ConductorEntity resp = entMan.find(ConductorEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(newEntity.getNumDocumento(), resp.getNumDocumento());
        Assert.assertEquals(newEntity.getTipoDocumento(), resp.getTipoDocumento());
        Assert.assertEquals(newEntity.getTelefono(), resp.getTelefono());
        Assert.assertEquals(newEntity.getFechaDeNacimiento(), resp.getFechaDeNacimiento());
        Assert.assertEquals(newEntity.getCorreo(), resp.getCorreo());
        Assert.assertEquals(newEntity.getContrasenha(), resp.getContrasenha());
    }

    /**
     * Test del metodo delete
     */
    @Test
    public void deleteTest() {
        ConductorEntity entity = data.get(0);
        cp.delete(entity.getId());
        ConductorEntity deleted = entMan.find(ConductorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Test del metodo buscar por correo
     */
    @Test
    public void findByCorreoTest() {
        ConductorEntity entity = data.get(0);
        ConductorEntity newEntity = cp.findByCorreo(entity.getCorreo());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getCorreo(), newEntity.getCorreo());

        newEntity = cp.findByCorreo(null);
        Assert.assertNull(newEntity);
    }

}
