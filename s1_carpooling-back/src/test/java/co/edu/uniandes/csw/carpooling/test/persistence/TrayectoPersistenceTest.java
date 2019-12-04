/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.TrayectoEntity;
import co.edu.uniandes.csw.carpooling.persistence.TrayectoPersistence;
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
 * Pruebas de perstistencia de Trayecto
 * @author Estudiante
 */
@RunWith(Arquillian.class)
public class TrayectoPersistenceTest {

    @Inject
    private TrayectoPersistence tp;

    @PersistenceContext(unitName = "carpoolingPU")
    protected EntityManager em;

    @Inject
    UserTransaction utx;

    private List<TrayectoEntity> data = new ArrayList<TrayectoEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TrayectoEntity.class.getPackage())
                .addPackage(TrayectoPersistence.class.getPackage())
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
        em.createQuery("delete from TrayectoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            TrayectoEntity entity = factory.manufacturePojo(TrayectoEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Trayecto.
     */
    @Test
    public void createTest() {
        PodamFactory factory = new PodamFactoryImpl();
        TrayectoEntity trayecto = factory.manufacturePojo(TrayectoEntity.class);
        TrayectoEntity result = tp.create(trayecto);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getNumPeajes(), trayecto.getNumPeajes());
        Assert.assertEquals(result.getDuracion(), trayecto.getDuracion());
        Assert.assertEquals(result.getCostoCombustible(), trayecto.getCostoCombustible());
        Assert.assertEquals(result.getOrigen(), trayecto.getOrigen());
        Assert.assertEquals(result.getDestino(), trayecto.getDestino());
    }

    /**
     * Prueba para traer un Trayecto.
     */
    @Test
    public void getTrayectoTest() {
        TrayectoEntity trayecto = data.get(0);
        TrayectoEntity newTrayecto = tp.find(trayecto.getId());
        Assert.assertNotNull(newTrayecto);
        Assert.assertEquals(trayecto, newTrayecto);
    }

    /**
     * Prueba para traer los trayectos.
     */
    @Test
    public void getTrayectosTest() {
        List<TrayectoEntity> newTrayectos = tp.findAll();
        Assert.assertEquals(newTrayectos.size(), data.size());
        for (TrayectoEntity trayecto : data) {
            boolean found = false;
            for (TrayectoEntity newTrayecto : newTrayectos) {
                if (newTrayecto.equals(trayecto)) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para actualizar un Trayecto.
     */
    @Test
    public void updateTrayectoTest() {
        TrayectoEntity trayecto = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        TrayectoEntity newTrayecto = factory.manufacturePojo(TrayectoEntity.class);
        newTrayecto.setId(trayecto.getId());
        tp.update(newTrayecto);
        TrayectoEntity resp = em.find(TrayectoEntity.class, trayecto.getId());
        Assert.assertEquals(resp, newTrayecto);
    }

    /**
     * Prueba para eliminar un Trayecto.
     */
    @Test
    public void deleteTrayectoTest() {
        TrayectoEntity trayecto = data.get(0);
        tp.delete(trayecto.getId());
        TrayectoEntity borrado = em.find(TrayectoEntity.class, trayecto.getId());
        Assert.assertNull(borrado);
    }
}
