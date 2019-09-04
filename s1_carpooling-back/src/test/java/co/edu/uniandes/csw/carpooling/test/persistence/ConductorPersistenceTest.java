/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
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

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(ConductorEntity.class)
                .addClass(ConductorPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    ConductorPersistence cp;
    @PersistenceContext
    EntityManager entMan;

    @Inject
    UserTransaction utx;

    private List<ConductorEntity> data = new ArrayList<ConductorEntity>();

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

    private void clearData() {
        entMan.createQuery("delete from ConductorEntity").executeUpdate();
    }

    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ConductorEntity entity = factory.manufacturePojo(ConductorEntity.class);

            entMan.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ConductorEntity conductor = factory.manufacturePojo(ConductorEntity.class);
        ConductorEntity result = cp.create(conductor);
        Assert.assertNotNull(result);

        ConductorEntity entity = entMan.find(ConductorEntity.class, result.getId());

        Assert.assertEquals(conductor.getNombre(), entity.getNombre());
    }

    @Test
    public void findTest() {
        ConductorEntity entity = data.get(0);
        ConductorEntity newEntity = cp.find(entity.getId());

        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }

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

    @Test
    public void deleteTest() {
        ConductorEntity entity = data.get(0);
        cp.delete(entity.getId());
        ConductorEntity deleted = entMan.find(ConductorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void findByNameTest() {
        ConductorEntity entity = data.get(0);
        ConductorEntity newEntity = cp.findByName(entity.getNombre());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());

        newEntity = cp.findByName(null);
        Assert.assertNull(newEntity);
    }
}
