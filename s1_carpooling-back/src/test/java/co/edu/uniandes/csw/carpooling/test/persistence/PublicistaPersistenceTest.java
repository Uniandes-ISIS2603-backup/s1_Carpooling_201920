/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.PublicistaEntity;
import co.edu.uniandes.csw.carpooling.persistence.PublicistaPersistence;
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
 * @author Santiago Ballesteros
 */
@RunWith(Arquillian.class)
public class PublicistaPersistenceTest {

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class).
                addClass(PublicistaEntity.class).
                addClass(PublicistaPersistence.class).
                addAsManifestResource("META-INF/persistence.xml", "persistence.xml").
                addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    PublicistaPersistence pp;

    @PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction utx;

    private List<PublicistaEntity> data = new ArrayList<PublicistaEntity>();

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void setUp() {
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
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from PublicistaEntity").executeUpdate();
    }

    /**
     * Este metodo inserta tres datos en la base de datos para poder ejecutar
     * las transacciones RUD necesarias.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            PublicistaEntity entity = factory.manufacturePojo(PublicistaEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Publicista.
     */
    @Test
    public void createTest() {
        PodamFactory factory = new PodamFactoryImpl();
        PublicistaEntity publicista = factory.manufacturePojo(PublicistaEntity.class);
        PublicistaEntity result = pp.create(publicista);

        Assert.assertNotNull(result);

        PublicistaEntity entity
                = em.find(PublicistaEntity.class, result.getId());

        Assert.assertEquals(publicista.getCedula(), entity.getCedula());
        Assert.assertEquals(publicista.getCorreo(), entity.getCorreo());
        Assert.assertEquals(publicista.getNit(), entity.getNit());
        Assert.assertEquals(publicista.getNombre(), entity.getNombre());
        Assert.assertEquals(publicista.getRut(), entity.getRut());
        Assert.assertEquals(publicista.getTelefono(), entity.getTelefono());
        Assert.assertEquals(publicista.getTipoPublicista(), entity.getTipoPublicista());
    }

    /**
     * Prueba para consultar la lista de Publicistas.
     */
    @Test
    public void getPublicistasTest() {
        List<PublicistaEntity> list = pp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (PublicistaEntity ent : list) {
            boolean found = false;
            for (PublicistaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Publicista.
     */
    @Test
    public void getPublicistaTest() {
        PublicistaEntity entity = data.get(0);
        PublicistaEntity newEntity = pp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }

    /**
     * Prueba para actualizar un Publicista.
     */
    @Test
    public void updatePublicistaTest() {
        PublicistaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        PublicistaEntity newEntity = factory.manufacturePojo(PublicistaEntity.class);

        newEntity.setId(entity.getId());

        pp.update(newEntity);

        PublicistaEntity resp = em.find(PublicistaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
    }

    /**
     * Prueba para eliminar un Publicista.
     */
    @Test
    public void deletePublicistaTest() {
        PublicistaEntity entity = data.get(0);
        pp.delete(entity.getId());
        PublicistaEntity deleted = em.find(PublicistaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

}
