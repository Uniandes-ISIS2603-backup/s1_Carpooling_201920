/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.logic;

import co.edu.uniandes.csw.carpooling.ejb.PublicidadLogic;
import co.edu.uniandes.csw.carpooling.entities.PublicidadEntity;
import co.edu.uniandes.csw.carpooling.entities.PublicistaEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.PublicidadPersistence;
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
 * @author Nicolas Fajardo Ramirez
 */
@RunWith(Arquillian.class)
public class PublicidadLogicTest {

    /**
     * El podam factory
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * la logica de la publicidad
     */
    @Inject
    private PublicidadLogic publicidadLogic;

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
     * la informacion de publicidades para las pruebas
     */
    private List<PublicidadEntity> data = new ArrayList<PublicidadEntity>();

    /**
     * la informacion de publicistas para las pruebas
     */
    private List<PublicistaEntity> dataPublicista = new ArrayList<PublicistaEntity>();

    /**
     * crea el deployment
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PublicidadEntity.class.getPackage())
                .addPackage(PublicidadLogic.class.getPackage())
                .addPackage(PublicidadPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * configura la informaciòn para las pruebas
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
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * limpia la tabla de informaciòn de publicidad
     */
    private void clearData() {
        em.createQuery("delete from PublicidadEntity").executeUpdate();
    }

    /**
     * agrega informaciòn a las listas de publicista y publicidad para poder hacer las pruebas
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PublicistaEntity entity = factory.manufacturePojo(PublicistaEntity.class);
            em.persist(entity);
            dataPublicista.add(entity);
        }

        for (int i = 0; i < 3; i++) {
            PublicidadEntity entity = factory.manufacturePojo(PublicidadEntity.class);
            entity.setPublicista(dataPublicista.get(1));
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * test de crear publicidad
     */
    @Test
    public void createPublicidadTest() throws BusinessLogicException {

        PublicidadEntity newEntity = factory.manufacturePojo(PublicidadEntity.class);

        Date fechaI = new Date();
        Date fechaS = new Date();
        newEntity.setFechaDeInicio(fechaI);
        newEntity.setFechaDeSalida(fechaS);
        newEntity.setCosto(32);

        newEntity.setPublicista(dataPublicista.get(1));
        PublicidadEntity result = publicidadLogic.createPublicidad(dataPublicista.get(1).getId(), newEntity);
        Assert.assertNotNull(result);

        PublicidadEntity entity = em.find(PublicidadEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getCosto(), entity.getCosto(), 0);
        Assert.assertEquals(newEntity.getMensaje(), entity.getMensaje());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    /**
     * test de dar todas las publicidades
     * @throws BusinessLogicException 
     */
    @Test
    public void getPublicidadesTest() throws BusinessLogicException {
        List<PublicidadEntity> list = publicidadLogic.getPublicidades(dataPublicista.get(1).getId());
        Assert.assertEquals(data.size(), list.size());
        for (PublicidadEntity entity : list) {
            boolean found = false;
            for (PublicidadEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * test de dar publicidad por id
     */
    @Test
    public void getPublicidadTest() {
        PublicidadEntity entity = data.get(0);
        PublicidadEntity result = publicidadLogic.getPublicidad(dataPublicista.get(1).getId(), entity.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getId(), entity.getId());
        Assert.assertEquals(result.getCosto(), entity.getCosto(), 0);
        Assert.assertEquals(result.getFechaDeInicio(), entity.getFechaDeInicio());
        Assert.assertEquals(result.getFechaDeSalida(), entity.getFechaDeSalida());
        Assert.assertEquals(result.getMensaje(), entity.getMensaje());
        Assert.assertEquals(result.getNombre(), entity.getNombre());
    }

    /**
     * test de actualizar publicidad
     */
    @Test
    public void updatePublicidadTest() throws BusinessLogicException {
        PublicidadEntity entity = data.get(0);
        Date fechaI = new Date();
        Date fechaS = new Date();
        PublicidadEntity pojoEntity = factory.manufacturePojo(PublicidadEntity.class);
        pojoEntity.setFechaDeInicio(fechaI);
        pojoEntity.setFechaDeSalida(fechaS);
        pojoEntity.setCosto(32);
        pojoEntity.setId(entity.getId());

        publicidadLogic.updatePublicidad(dataPublicista.get(1).getId(), pojoEntity);
        PublicidadEntity result = em.find(PublicidadEntity.class, pojoEntity.getId());

        Assert.assertEquals(result.getId(), pojoEntity.getId());
        Assert.assertEquals(result.getCosto(), pojoEntity.getCosto(), 0);
        Assert.assertEquals(result.getMensaje(), pojoEntity.getMensaje());
        Assert.assertEquals(result.getNombre(), pojoEntity.getNombre());
    }

    /**
     * test de borrar publicidad
     * @throws BusinessLogicException  si al borrar la publicidad se rompe alguna regla de negocio
     */
    @Test
    public void deletePublicidadTest() throws BusinessLogicException {
        PublicidadEntity entity = data.get(0);
        publicidadLogic.deletePublicidad(dataPublicista.get(1).getId(), entity.getId());
        PublicidadEntity deleted = em.find(PublicidadEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
