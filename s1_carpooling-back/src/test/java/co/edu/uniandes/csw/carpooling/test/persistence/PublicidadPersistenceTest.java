/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.PublicidadEntity;
import co.edu.uniandes.csw.carpooling.entities.PublicistaEntity;
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

    /**
     * La persistencia
     */
    @Inject
    PublicidadPersistence pp;

    /**
     * El entity manager
     */
    @PersistenceContext
    EntityManager em;

    /**
     * La tranasccion del usuario
     */
    @Inject
    UserTransaction utx;

    /**
     * Datos base para los test
     */
    private List<PublicidadEntity> data = new ArrayList<PublicidadEntity>();

    
    /**
     * Datos de publicistas
     */
    private List<PublicistaEntity> dataPublicista = new ArrayList<PublicistaEntity>();

    /**
     * El deployment
     * @return el deployment
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PublicidadEntity.class.getPackage())
                .addPackage(PublicidadPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuracion del test
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
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
     * Limpia los datos de la lista data y datospublicista
     */
    private void clearData() {
        em.createQuery("delete from PublicidadEntity").executeUpdate();
        em.createQuery("delete from PublicistaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            PublicistaEntity entity = factory.manufacturePojo(PublicistaEntity.class);
            em.persist(entity);
            dataPublicista.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            PublicidadEntity entity = factory.manufacturePojo(PublicidadEntity.class);
            if (i == 0) {
                entity.setPublicista(dataPublicista.get(0));
            }
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Test del metodo create
     */
    @Test
    public void createTest() {

        PodamFactory factory = new PodamFactoryImpl();
        PublicidadEntity newEntity = factory.manufacturePojo(PublicidadEntity.class);

        PublicidadEntity pe = pp.create(newEntity);

        Assert.assertNotNull(pe);

        PublicidadEntity entity = em.find(PublicidadEntity.class, pe.getId());

        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getMensaje(), newEntity.getMensaje());
        Assert.assertEquals(entity.getFechaDeSalida(), newEntity.getFechaDeSalida());
        Assert.assertEquals(entity.getFechaDeInicio(), newEntity.getFechaDeInicio());
        Assert.assertEquals(entity.getCosto(), newEntity.getCosto(), 0);
    }

    /**
     * Test del metodo buscar
     */
    @Test
    public void findTest() {
        PublicidadEntity entity = data.get(0);
        PublicidadEntity newEntity = pp.find(dataPublicista.get(0).getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getMensaje(), newEntity.getMensaje());
        Assert.assertEquals(entity.getFechaDeSalida(), newEntity.getFechaDeSalida());
        Assert.assertEquals(entity.getFechaDeInicio(), newEntity.getFechaDeInicio());
        Assert.assertEquals(entity.getCosto(), newEntity.getCosto(), 0);
    }

    /**
     * Test del metodo actualizar
     */
    @Test
    public void updateTest() {
        PublicidadEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        PublicidadEntity newEntity = factory.manufacturePojo(PublicidadEntity.class);

        newEntity.setId(entity.getId());

        pp.update(newEntity);

        PublicidadEntity resp = em.find(PublicidadEntity.class, entity.getId());

        Assert.assertEquals(resp.getNombre(), newEntity.getNombre());
        Assert.assertEquals(resp.getMensaje(), newEntity.getMensaje());
        Assert.assertEquals(resp.getFechaDeSalida(), newEntity.getFechaDeSalida());
        Assert.assertEquals(resp.getFechaDeInicio(), newEntity.getFechaDeInicio());
        Assert.assertEquals(resp.getCosto(), newEntity.getCosto(), 0);
    }

    /**
     * test del metodo borrar
     */
    @Test
    public void deleteTest() {
        PublicidadEntity entity = data.get(0);
        pp.delete(entity.getId());
        PublicidadEntity deleted = em.find(PublicidadEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Test del metodo buscar por nombre
     */
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
