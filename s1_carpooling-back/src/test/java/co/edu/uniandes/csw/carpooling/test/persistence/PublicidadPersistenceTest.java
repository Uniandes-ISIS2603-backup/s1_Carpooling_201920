/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.PublicidadEntity;
import co.edu.uniandes.csw.carpooling.persistence.PublicidadPersistence;
import java.util.ArrayList;
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
 * @author Nicolas Fajardi Ramirez
 */
@RunWith(Arquillian.class)
public class PublicidadPersistenceTest {

    @Inject
    PublicidadPersistence pp;
    @PersistenceContext
    EntityManager entMan;
    @Inject
    UserTransaction utx;

    private List<PublicidadEntity> data = new ArrayList<PublicidadEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(PublicidadEntity.class)
                .addClass(PublicidadPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

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
        entMan.createQuery("delete from PublicidadEntity").executeUpdate();
    }

    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            PublicidadEntity entity = factory.manufacturePojo(PublicidadEntity.class);

            entMan.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createTest() {

        PodamFactory factory = new PodamFactoryImpl();
        PublicidadEntity newentity = factory.manufacturePojo(PublicidadEntity.class);

        PublicidadEntity pe = pp.create(newentity);

        Assert.assertNotNull(pe);

        PublicidadEntity entity = entMan.find(PublicidadEntity.class, pe.getId());

        Assert.assertEquals(newentity.getNombre(), entity.getNombre());
    }

    @Test
    public void findTest() {
        PublicidadEntity entity = data.get(0);
        PublicidadEntity newEntity = pp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getMensaje(), newEntity.getMensaje());
        Assert.assertEquals(entity.getFechaDeSalida(), newEntity.getFechaDeSalida());
        Assert.assertEquals(entity.getFechaDeInicio(), newEntity.getFechaDeInicio());
        Assert.assertEquals(entity.getCosto(), newEntity.getCosto(), 0);
    }

    @Test
    public void findAllTest() {
        List<PublicidadEntity> list = pp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (PublicidadEntity ent : list) {
            boolean found = false;
            for (PublicidadEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void updateTest() {
        PublicidadEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        PublicidadEntity newEntity = factory.manufacturePojo(PublicidadEntity.class);

        newEntity.setId(entity.getId());

        pp.update(newEntity);

        PublicidadEntity resp = entMan.find(PublicidadEntity.class, entity.getId());

        Assert.assertEquals(resp.getNombre(), newEntity.getNombre());
        Assert.assertEquals(resp.getMensaje(), newEntity.getMensaje());
        Assert.assertEquals(resp.getFechaDeSalida(), newEntity.getFechaDeSalida());
        Assert.assertEquals(resp.getFechaDeInicio(), newEntity.getFechaDeInicio());
        Assert.assertEquals(resp.getCosto(), newEntity.getCosto(), 0);
    }

    @Test
    public void deleteTest() {
        PublicidadEntity entity = data.get(0);
        pp.delete(entity.getId());
        PublicidadEntity deleted = entMan.find(PublicidadEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void findByNameTest() {
        PublicidadEntity entity = data.get(0);
        PublicidadEntity newEntity = pp.findByName(entity.getNombre());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());

        newEntity = pp.findByName(null);
        Assert.assertNull(newEntity);
    }

}
