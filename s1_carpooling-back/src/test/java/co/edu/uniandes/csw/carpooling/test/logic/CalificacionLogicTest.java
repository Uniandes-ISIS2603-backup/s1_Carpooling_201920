/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.logic;

import co.edu.uniandes.csw.carpooling.ejb.CalificacionLogic;
import co.edu.uniandes.csw.carpooling.entities.CalificacionEntity;
import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeroEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.CalificacionPersistence;
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
 * @author Juan David Alarcón
 */

@RunWith(Arquillian.class)
public class CalificacionLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext
    protected EntityManager em;
    
    @Inject
    private CalificacionLogic calificacionLogic;
        
    @Inject
    private UserTransaction utx;
    
    private List<CalificacionEntity> data = new ArrayList<CalificacionEntity>();
    private List<ViajeroEntity> dataViajero = new ArrayList<ViajeroEntity>();
    private List<ConductorEntity> dataConductor = new ArrayList<ConductorEntity>();
    @Deployment
    public static JavaArchive createDeployment()
    {
       
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(CalificacionLogic.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            CalificacionEntity entity = factory.manufacturePojo(CalificacionEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test (expected = BusinessLogicException.class )
    public void createCalificacionTest() throws BusinessLogicException{
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        CalificacionEntity result = calificacionLogic.createCalificacion(newEntity);
        Assert.assertNotNull(result);
        
        CalificacionEntity entity = em.find(CalificacionEntity.class, result.getId());
        Assert.assertEquals(entity.getComentarios(), result.getComentarios());
        Assert.assertEquals(entity.getPuntuacion(), result.getPuntuacion());
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createCalificacionTestComentarioInvalido() throws BusinessLogicException
    {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        
        newEntity.setPuntuacion(1);
        String comentarios = "0";
        
        for(int i = 1; i <= 510;i++)
        {
            comentarios = comentarios + "i";
        }
        
        newEntity.setComentarios(comentarios);
        
        CalificacionEntity result = calificacionLogic.createCalificacion(newEntity); 
    }
    
    @Test
    public void createCalificacionTestComentarioValido() throws BusinessLogicException
    {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setPuntuacion (4);
        
        String comentarios = "01234";
  
        newEntity.setComentarios(comentarios);
   
        CalificacionEntity result = calificacionLogic.createCalificacion(newEntity); 
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createCalificacionTestPuntuacionInValida() throws BusinessLogicException
    {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        
        int puntuacion = 6;
            
        newEntity.setPuntuacion(puntuacion);
        
        CalificacionEntity result = calificacionLogic.createCalificacion(newEntity);  
    }
    
     @Test (expected = BusinessLogicException.class)
    public void createCalificacionTestPuntuacionInValida2() throws BusinessLogicException
    {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        
        int puntuacion = -1;
            
        newEntity.setPuntuacion(puntuacion);
        
        CalificacionEntity result = calificacionLogic.createCalificacion(newEntity);  
    }
    
    
    @Test
    public void createCalificacionTestPuntuacionValida() throws BusinessLogicException
    {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        
        newEntity.setComentarios("abc");
        int puntuacion = 4;
            
        newEntity.setPuntuacion(puntuacion);
        
        CalificacionEntity result = calificacionLogic.createCalificacion(newEntity);  
    }
    
    
    
    
    
}
