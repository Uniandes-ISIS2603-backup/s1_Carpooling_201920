/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.logic;

import co.edu.uniandes.csw.carpooling.ejb.ViajeRecurrenteLogic;
import co.edu.uniandes.csw.carpooling.entities.ViajeRecurrenteEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.ViajeRecurrentePersistence;
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
 * @author Juan David Alarcón
 */

@RunWith(Arquillian.class)
public class ViajeRecurrenteLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext
    protected EntityManager em;
    
    @Inject
    private ViajeRecurrenteLogic viajeRecurrenteLogic;
        
    @Inject
    private UserTransaction utx;
    
    private List<ViajeRecurrenteEntity> data = new ArrayList<ViajeRecurrenteEntity>();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
       
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ViajeRecurrenteEntity.class.getPackage())
                .addPackage(ViajeRecurrenteLogic.class.getPackage())
                .addPackage(ViajeRecurrentePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
                
    }
    
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
    
        private void clearData() {
        em.createQuery("delete from ViajeRecurrenteEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ViajeRecurrenteEntity entity = factory.manufacturePojo(ViajeRecurrenteEntity.class);
            
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test (expected = BusinessLogicException.class )
    public void createViajeRecurrenteTest() throws BusinessLogicException{
        ViajeRecurrenteEntity newEntity = factory.manufacturePojo(ViajeRecurrenteEntity.class);
        ViajeRecurrenteEntity result = viajeRecurrenteLogic.createViajeRecurrente(newEntity);
        Assert.assertNotNull(result);
        
        ViajeRecurrenteEntity entity = em.find(ViajeRecurrenteEntity.class, result.getId());
        Assert.assertEquals(entity.getFechaInicio(), result.getFechaInicio());
        Assert.assertEquals(entity.getFechaFin(), result.getFechaFin());
        Assert.assertEquals(entity.getFrecuencia(), result.getFrecuencia());
        
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createViajeRecurrenteTestFechaInicioInvalida() throws BusinessLogicException
    {
        ViajeRecurrenteEntity newEntity = factory.manufacturePojo(ViajeRecurrenteEntity.class);
        Date fin = new Date();
        
        fin.setTime(System.currentTimeMillis() + 9999*900000);
        newEntity.setFechaFin(fin);
        
        
        Date inicio = new Date();
        
        inicio.setTime(System.currentTimeMillis() - 9999*900000);
        
        
        newEntity.setFechaInicio(inicio);
        
        ViajeRecurrenteEntity result = viajeRecurrenteLogic.createViajeRecurrente(newEntity); 
    }
    
    
    @Test (expected = BusinessLogicException.class)
    public void createViajeRecurrenteTestFechaFinInvalida() throws BusinessLogicException
    {
        ViajeRecurrenteEntity newEntity = factory.manufacturePojo(ViajeRecurrenteEntity.class);
        Date inicio = new Date();
        
        inicio.setTime(System.currentTimeMillis() + 9999*900000);
        newEntity.setFechaInicio(inicio);
        
        
        Date fin = new Date();
        
        fin.setTime(System.currentTimeMillis() - 9999*900000);
        
        
        newEntity.setFechaFin(fin);
        
        ViajeRecurrenteEntity result = viajeRecurrenteLogic.createViajeRecurrente(newEntity); 
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createViajeRecurrenteTestFechaInicioFinInvalida() throws BusinessLogicException
    {
        ViajeRecurrenteEntity newEntity = factory.manufacturePojo(ViajeRecurrenteEntity.class);
        Date inicio = new Date();
        Date fin = new Date();
        
        long actual = System.currentTimeMillis();
        
        inicio.setTime(actual + 9999*900000);
        
        fin.setTime(actual + 9999*9000 );
        
        newEntity.setFechaInicio(inicio);
        newEntity.setFechaFin(fin);
        
        ViajeRecurrenteEntity result = viajeRecurrenteLogic.createViajeRecurrente(newEntity); 
    }
    
    @Test
    public void createViajeRecurrenteTestFechasValidas() throws BusinessLogicException
    {
        ViajeRecurrenteEntity newEntity = factory.manufacturePojo(ViajeRecurrenteEntity.class);
        Date inicio = new Date();
        Date fin = new Date();
        
        long actual = System.currentTimeMillis();
        
        inicio.setTime(actual + 9999*9000);
        
        fin.setTime(actual + 9999*999999 );
        
        newEntity.setFechaInicio(inicio);
        newEntity.setFechaFin(fin);
        
        ViajeRecurrenteEntity result = viajeRecurrenteLogic.createViajeRecurrente(newEntity); 
    }
}
