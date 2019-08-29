/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.NotificacionEntity;
import co.edu.uniandes.csw.carpooling.persistence.NotificacionPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import static org.glassfish.pfl.basic.tools.argparser.ElementParser.factory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author le.perezl
 */
//preg si es con 3 sss
@RunWith(Arquillian.class)
public class NotificacionPersistenceTest {
    
    private NotificacionPersistence NotificacionPersistence;
    private NotificacionEntity NotificacionEntity;
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
        .addClass(NotificacionEntity.class)
        .addClass(NotificacionPersistence.class)
        .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
        .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Inject
    NotificacionPersistence np;
    
    @PersistenceContext(unitName = "carpoolingPU")
    protected EntityManager em;
      
      @Test
      public void createNotificacionTest(){
         // Falta crear notif
          PodamFactory factory = new PodamFactoryImpl();
          NotificacionEntity notificacion = factory.manufacturePojo(NotificacionEntity.class);
          NotificacionEntity result= np.create(notificacion);
          Assert.assertNotNull(result);
          
          NotificacionEntity entity= em.find(NotificacionEntity.class, result.getId());
          
          Assert.assertEquals(notificacion.getMensaje(),entity.getMensaje());
      }
}
