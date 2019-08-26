/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.persistence;

import co.edu.uniandes.csw.carpooling.entities.ViajeroEntity;
import co.edu.uniandes.csw.carpooling.persistence.ViajeroPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
 * @author Santiago Ballesteros
 */
@RunWith(Arquillian.class)
public class ViajeroPersistenceTest {
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(ViajeroEntity.class)
                .addClass(ViajeroPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Inject
    private ViajeroPersistence vp;
    
     @PersistenceContext
    private EntityManager em;
    
    @Test
    public void createTest(){
        // Se construye una fabrica de objetos
        PodamFactory factory = new PodamFactoryImpl();
        
        //A la fabrica de objetos se le pide que nos de un objeto del tipo que le pasamos por parametro
        ViajeroEntity viajero = factory.manufacturePojo(ViajeroEntity.class);
        
        ViajeroEntity result = vp.create(viajero);
        // Pruebo que el resultado del metodo create no sea null
        Assert.assertNotNull(result);
        
        // El entity manager busca en la base de datos si hay una entidad que coincida con la 
        // entidad que acabo de crear por su id
        ViajeroEntity entity =
          em.find(ViajeroEntity.class, result.getId());
        
        // Verifico que para cada entidad creada por podam,
        // en la base de datos se reflejen esos mismos datos
        Assert.assertEquals(viajero.getApellido(),entity.getApellido());
        Assert.assertEquals(viajero.getContrasenha(), entity.getContrasenha());
        Assert.assertEquals(viajero.getCorreo(), entity.getCorreo());
        //Assert.assertEquals(viajero.getFechaDeNacimiento(), entity.getFechaDeNacimiento());
        Assert.assertEquals(viajero.getNombre(), entity.getNombre());
        Assert.assertEquals(viajero.getNumDocumento(), entity.getNumDocumento());
        Assert.assertEquals(viajero.getTelefono(), entity.getTelefono());
        Assert.assertEquals(viajero.getTipoDocumento(), entity.getTipoDocumento());
    }
}
