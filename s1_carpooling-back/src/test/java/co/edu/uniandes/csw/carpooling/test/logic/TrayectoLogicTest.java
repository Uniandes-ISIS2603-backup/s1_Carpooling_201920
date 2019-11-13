/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.logic;

import co.edu.uniandes.csw.carpooling.ejb.TrayectoLogic;
import co.edu.uniandes.csw.carpooling.entities.TrayectoEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
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
 * Pruebas de logica de Trayecto
 * @author Juan David Serrano
 */
@RunWith(Arquillian.class)
public class TrayectoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private TrayectoLogic trayectoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<TrayectoEntity> data = new ArrayList<TrayectoEntity>();

    private List<ViajeEntity> viajeData = new ArrayList<ViajeEntity>();

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
        em.createQuery("delete from TrayectoEntity").executeUpdate();
        em.createQuery("delete from ViajeEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ViajeEntity viajeEntity = factory.manufacturePojo(ViajeEntity.class);
            em.persist(viajeEntity);
            viajeData.add(viajeEntity);
        }

        for (int i = 0; i < 3; i++) {
            TrayectoEntity trayectoEntity = factory.manufacturePojo(TrayectoEntity.class);
            trayectoEntity.setViaje(viajeData.get(1));
            em.persist(trayectoEntity);
            data.add(trayectoEntity);
        }

    }

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TrayectoEntity.class.getPackage())
                .addPackage(TrayectoLogic.class.getPackage())
                .addPackage(TrayectoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Prueba para crear un trayecto
     * @throws BusinessLogicException 
     */
    @Test
    public void createTrayecto() throws BusinessLogicException {
        TrayectoEntity aleatorio = factory.manufacturePojo(TrayectoEntity.class);
        aleatorio.setViaje(viajeData.get(0));
        TrayectoEntity resultAleatorio = trayectoLogic.createTrayecto(viajeData.get(0).getId(), aleatorio);
        TrayectoEntity buscado = em.find(TrayectoEntity.class, resultAleatorio.getId());
        Assert.assertNotNull(resultAleatorio);
        Assert.assertEquals(aleatorio.getDestino(), buscado.getDestino());
        Assert.assertEquals(aleatorio.getOrigen(), buscado.getOrigen());
        Assert.assertEquals(aleatorio.getNumPeajes(), buscado.getNumPeajes());
        Assert.assertEquals(aleatorio.getDuracion(), buscado.getDuracion());
        Assert.assertEquals(aleatorio.getCostoCombustible(), buscado.getCostoCombustible());
    }

    /**
     * Prueba para crear un trayecto con numero de peajes incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createTrayectoNumPeajesIncorrecto() throws BusinessLogicException {
        TrayectoEntity trayectoAleatorio = factory.manufacturePojo(TrayectoEntity.class);
        trayectoAleatorio.setNumPeajes(-1);
        trayectoLogic.createTrayecto(viajeData.get(0).getId(), trayectoAleatorio);
    }

    /**
     * Prueba para crear un trayecto con duracion incorrecta
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createTrayectoDuracionIncorrecto() throws BusinessLogicException {
        TrayectoEntity trayectoAleatorio = factory.manufacturePojo(TrayectoEntity.class);
        trayectoAleatorio.setDuracion(0);
        trayectoLogic.createTrayecto(viajeData.get(0).getId(), trayectoAleatorio);
    }

    /**
     * Prueba para crear un trayecto con costo de combustible incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createTrayectoCostoCombustibleIncorrecto() throws BusinessLogicException {
        TrayectoEntity trayectoAleatorio = factory.manufacturePojo(TrayectoEntity.class);
        trayectoAleatorio.setCostoCombustible(-1.0);
        trayectoLogic.createTrayecto(viajeData.get(0).getId(), trayectoAleatorio);
    }

    /**
     * Prueba para crear un trayecto con origen incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createTrayectoOrigenIncorrecto() throws BusinessLogicException {
        TrayectoEntity trayectoAleatorio = factory.manufacturePojo(TrayectoEntity.class);
        trayectoAleatorio.setOrigen("");
        trayectoLogic.createTrayecto(viajeData.get(0).getId(), trayectoAleatorio);
    }

    /**
     * Prueba para crear un trayecto con destino incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createTrayectoDestinoIncorrecto() throws BusinessLogicException {
        TrayectoEntity trayectoAleatorio = factory.manufacturePojo(TrayectoEntity.class);
        trayectoAleatorio.setDestino("");
        trayectoLogic.createTrayecto(viajeData.get(0).getId(), trayectoAleatorio);
    }

    /**
     * Prueba para obtener los trayectos
     */
    @Test
    public void getTrayectosTest() {
        List<TrayectoEntity> lista = trayectoLogic.getTrayectos(viajeData.get(1).getId());
        Assert.assertEquals(lista.size(), data.size());
        for (TrayectoEntity entity1 : lista) {
            boolean encontrado = false;
            for (TrayectoEntity entity2 : data) {
                if (entity1.getId().equals(entity2.getId())) {
                    encontrado = true;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }

    /**
     * Prueba para obtener un trayecto
     */
    @Test
    public void getTrayectoTest() {
        TrayectoEntity entity1 = data.get(0);
        TrayectoEntity entity2 = trayectoLogic.getTrayecto(entity1.getId(), viajeData.get(1).getId());
        Assert.assertNotNull(entity2);
        Assert.assertEquals(entity1.getCostoCombustible(), entity2.getCostoCombustible());
        Assert.assertEquals(entity1.getDestino(), entity2.getDestino());
        Assert.assertEquals(entity1.getOrigen(), entity2.getOrigen());
        Assert.assertEquals(entity1.getNumPeajes(), entity2.getNumPeajes());
        Assert.assertEquals(entity1.getDuracion(), entity2.getDuracion());
    }

    /**
     * Prueba para actualizar un trayecto
     * @throws BusinessLogicException 
     */
    @Test
    public void updateTrayectoTest() throws BusinessLogicException {
        TrayectoEntity entity0 = data.get(0);
        TrayectoEntity entity2 = factory.manufacturePojo(TrayectoEntity.class);
        entity2.setId(entity0.getId());
        trayectoLogic.updateTrayecto(entity2, viajeData.get(1).getId());
        TrayectoEntity entity1 = em.find(TrayectoEntity.class, entity0.getId());
        Assert.assertNotNull(entity2);
        Assert.assertEquals(entity1.getCostoCombustible(), entity2.getCostoCombustible());
        Assert.assertEquals(entity1.getDestino(), entity2.getDestino());
        Assert.assertEquals(entity1.getOrigen(), entity2.getOrigen());
        Assert.assertEquals(entity1.getDuracion(), entity2.getDuracion());
        Assert.assertEquals(entity1.getNumPeajes(), entity2.getNumPeajes());
    }

    /**
     * Prueba para actualizar un trayecto con destino incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateTrayectoDestinoIncorrecto() throws BusinessLogicException {
        TrayectoEntity entity1 = data.get(0);
        TrayectoEntity entity2 = factory.manufacturePojo(TrayectoEntity.class);
        entity2.setDestino("");
        entity2.setId(entity1.getId());
        trayectoLogic.updateTrayecto(entity2, viajeData.get(1).getId());
    }

    /**
     * Prueba para actualizar un trayecto con origen incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateTrayectoOrigenIncorrecto() throws BusinessLogicException {
        TrayectoEntity entity1 = data.get(0);
        TrayectoEntity entity2 = factory.manufacturePojo(TrayectoEntity.class);
        entity2.setDestino("");
        entity2.setId(entity1.getId());
        trayectoLogic.updateTrayecto(entity2, viajeData.get(1).getId());
    }

    /**
     * Prueba para actualizar un trayecto con numero de peajes incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateTrayectoNumPeajesIncorrecto() throws BusinessLogicException {
        TrayectoEntity entity1 = data.get(0);
        TrayectoEntity entity2 = factory.manufacturePojo(TrayectoEntity.class);
        entity2.setNumPeajes(-1);
        entity2.setId(entity1.getId());
        trayectoLogic.updateTrayecto(entity2, viajeData.get(1).getId());
    }

    /**
     * Prueba para actualizar un trayecto con duracion incorrecta
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateTrayectoDuracionIncorrecto() throws BusinessLogicException {
        TrayectoEntity entity1 = data.get(0);
        TrayectoEntity entity2 = factory.manufacturePojo(TrayectoEntity.class);
        entity2.setDuracion(0);
        entity2.setId(entity1.getId());
        trayectoLogic.updateTrayecto(entity2, viajeData.get(1).getId());
    }

    /**
     * Prueba para actualziar un trayecto con costo de combustible incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateTrayectoCostoCombustibleIncorrecto() throws BusinessLogicException {
        TrayectoEntity entity1 = data.get(0);
        TrayectoEntity entity2 = factory.manufacturePojo(TrayectoEntity.class);
        entity2.setCostoCombustible(-1.0);
        entity2.setId(entity1.getId());
        trayectoLogic.updateTrayecto(entity2, viajeData.get(1).getId());
    }

    /**
     * Prueba para borrar un trayecto
     * @throws BusinessLogicException 
     */
    @Test
    public void deleteViajeTest() throws BusinessLogicException {
        TrayectoEntity entity = data.get(0);
        trayectoLogic.deleteTrayecto(entity.getId(), viajeData.get(1).getId());
        TrayectoEntity deleted = em.find(TrayectoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

}
