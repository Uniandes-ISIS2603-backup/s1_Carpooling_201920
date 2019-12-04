/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import co.edu.uniandes.csw.carpooling.persistence.ConductorPersistence;
import co.edu.uniandes.csw.carpooling.persistence.ViajePersistence;
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
 * Pruebas de persistencia de Viaje
 * @author Juan David Serrano
 */
@RunWith(Arquillian.class)
public class ViajePersistenceTest {

    @Inject
    private ViajePersistence vp;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<ViajeEntity> data = new ArrayList<ViajeEntity>();
    
    
    private List<ConductorEntity> dataConductor = new ArrayList<ConductorEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ViajeEntity.class.getPackage())
                .addPackage(ViajePersistence.class.getPackage())
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
        em.createQuery("delete from ViajeEntity").executeUpdate();
    }

    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i< 3 ; i++){
            ConductorEntity entity = factory.manufacturePojo(ConductorEntity.class);
            em.persist(entity);
            dataConductor.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            ViajeEntity entity = factory.manufacturePojo(ViajeEntity.class);
            entity.setConductor(dataConductor.get(i));
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Viaje.
     */
    @Test
    public void createTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ViajeEntity viaje = factory.manufacturePojo(ViajeEntity.class);
        ViajeEntity result = vp.create(viaje);
        Assert.assertNotNull(result);
        ViajeEntity entity = em.find(ViajeEntity.class, result.getId());
        Assert.assertEquals(viaje.getCostoViaje(), entity.getCostoViaje());
        Assert.assertEquals(viaje.getCupos(), entity.getCupos());
        Assert.assertEquals(viaje.getDestino(), entity.getDestino());
        Assert.assertEquals(viaje.getEstadoViaje(), entity.getEstadoViaje());
        Assert.assertEquals(viaje.getFechaDeLlegada(), entity.getFechaDeLlegada());
        Assert.assertEquals(viaje.getFechaDeSalida(), entity.getFechaDeSalida());
        Assert.assertEquals(viaje.getOrigen(), entity.getOrigen());
    }

    /**
     * Prueba para consultar un Viaje.
     */
    @Test
    public void getViajeTest() {
        ViajeEntity viaje = data.get(0);
        ViajeEntity newViaje = vp.find(viaje.getId());
        Assert.assertNotNull(newViaje);
        Assert.assertEquals(viaje, newViaje);
    }

    /**
     * Prueba para consultar la lista de Viaje.
     */
    @Test
    public void getViajesTest() {
        List<ViajeEntity> newViajes = vp.findAll();
        Assert.assertEquals(newViajes.size(), data.size());
        for (ViajeEntity viaje : data) {
            boolean found = false;
            for (ViajeEntity newViaje : newViajes) {
                if (newViaje.equals(viaje)) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para actualizar un Viaje.
     */
    @Test
    public void updateViajeTest() {
        ViajeEntity viaje = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ViajeEntity newViaje = factory.manufacturePojo(ViajeEntity.class);
        newViaje.setId(viaje.getId());
        vp.update(newViaje);
        ViajeEntity resp = em.find(ViajeEntity.class, viaje.getId());
        Assert.assertEquals(resp, newViaje);
    }

    /**
     * Prueba para borrar un Viaje.
     */
    @Test
    public void deleteViajeTest() {
        ViajeEntity viaje = data.get(0);
        vp.delete(viaje.getId());
        ViajeEntity borrado = em.find(ViajeEntity.class, viaje.getId());
        Assert.assertNull(borrado);
    }
    
    /**
     * Prueba para consultar la lista de Viajes segun Conductor.
     */
    @Test 
    public void getViajesByConductorTest(){
        for(int i = 0; i < 3; i++){
            List<ViajeEntity> entities = vp.findAll(dataConductor.get(i).getId());
            Assert.assertNotNull(entities);
            Assert.assertEquals(1, entities.size());
            Assert.assertEquals(entities.get(0), data.get(i));
        }
    }
    
    /**
     * Prueba para consultar un Viaje de un Conductor.
     */
    @Test
    public void getViajeByConductorTest(){
        for(int i = 0; i < 3; i++){
            ViajeEntity entity = vp.find(dataConductor.get(i).getId(), data.get(i).getId());
            Assert.assertNotNull(entity);
            Assert.assertEquals(entity, data.get(i));
        }
    }
    
}
