/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.ViajeroEntity;
import co.edu.uniandes.csw.carpooling.persistence.ViajeroPersistence;
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
public class ViajeroPersistenceTest {

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ViajeroEntity.class.getPackage())
                .addPackage(ViajeroPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    private ViajeroPersistence vp;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<ViajeroEntity> data = new ArrayList<ViajeroEntity>();

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
     * Este metodo borra los datos que habitaban en la base de datos.
     */
    private void clearData() {
        em.createQuery("delete from ViajeroEntity").executeUpdate();
    }

    /**
     * Este metodo inserta tres datos en la base de datos para poder ejecutar
     * las transacciones RUD necesarias.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ViajeroEntity entity = factory.manufacturePojo(ViajeroEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Viajero.
     */
    @Test
    public void createTest() {
        // Se construye una fabrica de objetos
        PodamFactory factory = new PodamFactoryImpl();

        //A la fabrica de objetos se le pide que nos de un objeto del tipo que le pasamos por parametro
        ViajeroEntity viajero = factory.manufacturePojo(ViajeroEntity.class);

        ViajeroEntity result = vp.create(viajero);
        // Pruebo que el resultado del metodo create no sea null
        Assert.assertNotNull(result);

        // El entity manager busca en la base de datos si hay una entidad que coincida con la 
        // entidad que acabo de crear por su id
        ViajeroEntity entity
                = em.find(ViajeroEntity.class, result.getId());

        // Verifico que para cada entidad creada por podam,
        // en la base de datos se reflejen esos mismos datos
        Assert.assertEquals(viajero.getContrasenha(), entity.getContrasenha());
        Assert.assertEquals(viajero.getCorreo(), entity.getCorreo());
        Assert.assertEquals(viajero.getFechaDeNacimiento(), entity.getFechaDeNacimiento());
        Assert.assertEquals(viajero.getNombre(), entity.getNombre());
        Assert.assertEquals(viajero.getNumDocumento(), entity.getNumDocumento());
        Assert.assertEquals(viajero.getTelefono(), entity.getTelefono());
        Assert.assertEquals(viajero.getTipoDocumento(), entity.getTipoDocumento());
    }

    /**
     * Prueba para consultar la lista de Viajeros.
     */
    @Test
    public void getViajerosTest() {
        List<ViajeroEntity> list = vp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ViajeroEntity ent : list) {
            boolean found = false;
            for (ViajeroEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Viajero.
     */
    @Test
    public void getViajeroTest() {
        ViajeroEntity entity = data.get(0);
        ViajeroEntity newEntity = vp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }

    /**
     * Prueba para actualizar un Viajero.
     */
    @Test
    public void updateViajeroTest() {
        ViajeroEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ViajeroEntity newEntity = factory.manufacturePojo(ViajeroEntity.class);

        newEntity.setId(entity.getId());

        vp.update(newEntity);

        ViajeroEntity resp = em.find(ViajeroEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
    }

    /**
     * Prueba para eliminar un Viajero.
     */
    @Test
    public void deleteViajeroTest() {
        ViajeroEntity entity = data.get(0);
        vp.delete(entity.getId());
        ViajeroEntity deleted = em.find(ViajeroEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    /**
     * Prueba para consultar una Publicista por correo.
     */
    @Test
    public void findViajeroByCorreoTest() {
        ViajeroEntity entity = data.get(0);
        ViajeroEntity newEntity = vp.findByCorreo(entity.getCorreo());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getCorreo(), newEntity.getCorreo());

        newEntity = vp.findByCorreo(null);
        Assert.assertNull(newEntity);
    }
}
