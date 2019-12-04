/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.logic;

import co.edu.uniandes.csw.carpooling.ejb.ViajeLogic;
import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.entities.VehiculoEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
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
 * Pruebas de logica de Viaje
 * @author Juan David Serrano
 */
@RunWith(Arquillian.class)
public class ViajeLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ViajeLogic viajeLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ViajeEntity> data = new ArrayList<ViajeEntity>();

    private VehiculoEntity vehiculo;

    private ConductorEntity conductor;

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
        em.createQuery("delete from ViajeEntity").executeUpdate();
        em.createQuery("delete from VehiculoEntity").executeUpdate();
        em.createQuery("delete from ConductorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        conductor = factory.manufacturePojo(ConductorEntity.class);
        em.persist(conductor);
        vehiculo = factory.manufacturePojo(VehiculoEntity.class);
        em.persist(vehiculo);
        for (int i = 0; i < 3; i++) {
            ViajeEntity viajeEntity = factory.manufacturePojo(ViajeEntity.class);
            viajeEntity.setConductor(conductor);
            viajeEntity.setVehiculo(vehiculo);
            em.persist(viajeEntity);
            data.add(viajeEntity);
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
                .addPackage(ViajeEntity.class.getPackage())
                .addPackage(ViajeLogic.class.getPackage())
                .addPackage(ViajePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Prueba para crear un viaje
     * @throws BusinessLogicException 
     */
    @Test
    public void createViaje() throws BusinessLogicException {
        ViajeEntity aleatorio = factory.manufacturePojo(ViajeEntity.class);
        aleatorio.setConductor(conductor);
        aleatorio.setVehiculo(vehiculo);
        ViajeEntity resultAleatorio = viajeLogic.createViaje(aleatorio);
        ViajeEntity buscado = em.find(ViajeEntity.class, resultAleatorio.getId());
        Assert.assertNotNull(resultAleatorio);
        Assert.assertEquals(aleatorio.getCostoViaje(), buscado.getCostoViaje());
        Assert.assertEquals(aleatorio.getDestino(), buscado.getDestino());
        Assert.assertEquals(aleatorio.getOrigen(), buscado.getOrigen());
        Assert.assertEquals(aleatorio.getFechaDeSalida(), buscado.getFechaDeSalida());
        Assert.assertEquals(aleatorio.getFechaDeLlegada(), buscado.getFechaDeLlegada());
        Assert.assertEquals(aleatorio.getCupos(), buscado.getCupos());
        Assert.assertEquals(aleatorio.getEstadoViaje(), buscado.getEstadoViaje());
    }

    /**
     * Prueba para crear un viaje con el numero de cupos incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createViajeCuposIncorrecto() throws BusinessLogicException {
        ViajeEntity viajeAleatorio = factory.manufacturePojo(ViajeEntity.class);
        viajeAleatorio.setCupos(-1);
        ViajeEntity result = viajeLogic.createViaje(viajeAleatorio);
    }

    /**
     * Prueba para crear un viaje con destino incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createViajeDestinoIncorrecto() throws BusinessLogicException {
        ViajeEntity viajeAleatorio = factory.manufacturePojo(ViajeEntity.class);
        viajeAleatorio.setDestino("");
        viajeLogic.createViaje(viajeAleatorio);
    }

    /**
     * Prueba para crear un viaje con origen incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createViajeOrigenIncorrecto() throws BusinessLogicException {
        ViajeEntity viajeAleatorio = factory.manufacturePojo(ViajeEntity.class);
        viajeAleatorio.setOrigen(null);
        viajeLogic.createViaje(viajeAleatorio);
    }

    /**
     * Prueba para crear un viaje con fecha de llegada incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createViajeFechaDeLlegadaIncorrecto() throws BusinessLogicException {
        ViajeEntity viajeAleatorio = factory.manufacturePojo(ViajeEntity.class);
        viajeAleatorio.setFechaDeLlegada(null);
        viajeLogic.createViaje(viajeAleatorio);
    }

    /**
     * Prueba para crear un viaje con fecha de salida incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createViajeFechaDeSalidaIncorrecto() throws BusinessLogicException {
        ViajeEntity viajeAleatorio = factory.manufacturePojo(ViajeEntity.class);
        viajeAleatorio.setFechaDeSalida(null);
        viajeLogic.createViaje(viajeAleatorio);
    }

    /**
     * Prueba para crear un viaje con costo incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createViajeCostoViajeIncorrecto() throws BusinessLogicException {
        ViajeEntity viajeAleatorio = factory.manufacturePojo(ViajeEntity.class);
        viajeAleatorio.setCostoViaje((float) -10.65);
        ViajeEntity result = viajeLogic.createViaje(viajeAleatorio);
    }

    /**
     * Prueba para crear un viaje con estado incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createViajeEstadoViajeIncorrecto() throws BusinessLogicException {
        ViajeEntity viajeAleatorio = factory.manufacturePojo(ViajeEntity.class);
        viajeAleatorio.setOrigen("");
        viajeLogic.createViaje(viajeAleatorio);
    }

    /**
     * Prueba para crear un viaje con vehiculo incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createViajeVehiculoInexistene() throws BusinessLogicException {
        ViajeEntity viajeAleatorio = factory.manufacturePojo(ViajeEntity.class);
        viajeAleatorio.setVehiculo(factory.manufacturePojo(VehiculoEntity.class));
        viajeAleatorio.setConductor(conductor);
        viajeLogic.createViaje(viajeAleatorio);
    }

    /**
     * Prueba para crear un viaje con conductor incorrecto 
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createViajeConductorInexistene() throws BusinessLogicException {
        ViajeEntity viajeAleatorio = factory.manufacturePojo(ViajeEntity.class);
        viajeAleatorio.setConductor(factory.manufacturePojo(ConductorEntity.class));
        viajeAleatorio.setVehiculo(vehiculo);
        viajeLogic.createViaje(viajeAleatorio);
    }

    /**
     * Prueba para obtener los viajes
     */
    @Test
    public void getViajesTest() {
        List<ViajeEntity> lista = viajeLogic.getViajes();
        Assert.assertEquals(lista.size(), data.size());
        for (ViajeEntity entity1 : lista) {
            boolean encontrado = false;
            for (ViajeEntity entity2 : data) {
                if (entity1.getId().equals(entity2.getId())) {
                    encontrado = true;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }

    /**
     * Prueba para obtener un viaje
     */
    @Test
    public void getViajeTest() {
        ViajeEntity entity1 = data.get(0);
        ViajeEntity entity2 = viajeLogic.getViaje(entity1.getId());
        Assert.assertNotNull(entity2);
        Assert.assertEquals(entity1.getCostoViaje(), entity2.getCostoViaje());
        Assert.assertEquals(entity1.getDestino(), entity2.getDestino());
        Assert.assertEquals(entity1.getOrigen(), entity2.getOrigen());
        Assert.assertEquals(entity1.getFechaDeSalida(), entity2.getFechaDeSalida());
        Assert.assertEquals(entity1.getFechaDeLlegada(), entity2.getFechaDeLlegada());
        Assert.assertEquals(entity1.getCupos(), entity2.getCupos());
        Assert.assertEquals(entity1.getEstadoViaje(), entity2.getEstadoViaje());
    }

    /**
     * Prueba para actializar un viaje
     * @throws BusinessLogicException 
     */
    @Test
    public void updateViajeTest() throws BusinessLogicException {
        ViajeEntity entity0 = data.get(0);
        ViajeEntity entity2 = factory.manufacturePojo(ViajeEntity.class);
        entity2.setId(entity0.getId());
        entity2.setConductor(conductor);
        entity2.setVehiculo(vehiculo);
        viajeLogic.updateViaje(entity2);
        ViajeEntity entity1 = em.find(ViajeEntity.class, entity0.getId());
        Assert.assertNotNull(entity2);
        Assert.assertEquals(entity1.getCostoViaje(), entity2.getCostoViaje());
        Assert.assertEquals(entity1.getDestino(), entity2.getDestino());
        Assert.assertEquals(entity1.getOrigen(), entity2.getOrigen());
        Assert.assertEquals(entity1.getFechaDeSalida(), entity2.getFechaDeSalida());
        Assert.assertEquals(entity1.getFechaDeLlegada(), entity2.getFechaDeLlegada());
        Assert.assertEquals(entity1.getCupos(), entity2.getCupos());
        Assert.assertEquals(entity1.getEstadoViaje(), entity2.getEstadoViaje());
    }

    /**
     * Prueba para actualizar un viaje con destino incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateViajeDestinoIncorrecto() throws BusinessLogicException {
        ViajeEntity entity1 = data.get(0);
        ViajeEntity entity2 = factory.manufacturePojo(ViajeEntity.class);
        entity2.setDestino("");
        entity2.setId(entity1.getId());
        viajeLogic.updateViaje(entity2);
    }

    /**
     * Prueba para actulizar un viaje con origen incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateViajeOrigenIncorrecto() throws BusinessLogicException {
        ViajeEntity entity1 = data.get(0);
        ViajeEntity entity2 = factory.manufacturePojo(ViajeEntity.class);
        entity2.setOrigen(null);
        entity2.setId(entity1.getId());
        viajeLogic.updateViaje(entity2);
    }
    
    /**
     * Prueba para actualizar un viaje con fecha de salida incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateViajeFehchaDeSalidaIncorrecto() throws BusinessLogicException {
        ViajeEntity entity1 = data.get(0);
        ViajeEntity entity2 = factory.manufacturePojo(ViajeEntity.class);
        entity2.setFechaDeSalida(null);
        entity2.setId(entity1.getId());
        viajeLogic.updateViaje(entity2);
    }

    /**
     * Prueba para actualizar un viaje con fecha de llegada incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateViajeFehchaDeLlegadaIncorrecto() throws BusinessLogicException {
        ViajeEntity entity1 = data.get(0);
        ViajeEntity entity2 = factory.manufacturePojo(ViajeEntity.class);
        entity2.setFechaDeLlegada(null);
        entity2.setId(entity1.getId());
        viajeLogic.updateViaje(entity2);
    }

    /**
     * Prueba para actualizar un viaje con cupos incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateViajeCuposIncorrecto() throws BusinessLogicException {
        ViajeEntity entity1 = data.get(0);
        ViajeEntity entity2 = factory.manufacturePojo(ViajeEntity.class);
        entity2.setCupos(-1);
        entity2.setId(entity1.getId());
        viajeLogic.updateViaje(entity2);
    }

    /**
     * Prueba para actualizar un viaje con un costo de viaje incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateViajeCostoViajeIncorrecto() throws BusinessLogicException {
        ViajeEntity entity1 = data.get(0);
        ViajeEntity entity2 = factory.manufacturePojo(ViajeEntity.class);
        entity2.setCostoViaje((float) -1);
        entity2.setId(entity1.getId());
        viajeLogic.updateViaje(entity2);
    }

    /**
     * Prueba para actualizar un viaje con estado incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateViajeEstadoViajeIncorrecto() throws BusinessLogicException {
        ViajeEntity entity1 = data.get(0);
        ViajeEntity entity2 = factory.manufacturePojo(ViajeEntity.class);
        entity2.setEstadoViaje(null);
        entity2.setId(entity1.getId());
        viajeLogic.updateViaje(entity2);
    }

    /**
     * Prueba para actualizar un viaje con vehiculo incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateViajeVehiculoInexistente() throws BusinessLogicException {
        ViajeEntity entity1 = data.get(0);
        ViajeEntity entity2 = factory.manufacturePojo(ViajeEntity.class);
        entity2.setId(entity1.getId());
        entity2.setConductor(conductor);
        entity2.setVehiculo(null);
        viajeLogic.updateViaje(entity2);
    }

    /**
     * Prueba para actualizar un viaje con conductor incorrecto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateViajeConductorInexistente() throws BusinessLogicException {
        ViajeEntity entity1 = data.get(0);
        ViajeEntity entity2 = factory.manufacturePojo(ViajeEntity.class);
        entity2.setId(entity1.getId());
        entity2.setConductor(null);
        entity2.setVehiculo(vehiculo);
        viajeLogic.updateViaje(entity2);
    }

    /**
     * Prueba para eliminar un viaje
     * @throws BusinessLogicException 
     */
    @Test
    public void deleteViajeTest() throws BusinessLogicException {
        ViajeEntity entity = data.get(0);
        viajeLogic.deleteViaje(entity.getId());
        ViajeEntity deleted = em.find(ViajeEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
