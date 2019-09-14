/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.logic;

import co.edu.uniandes.csw.carpooling.ejb.PublicidadLogic;
import co.edu.uniandes.csw.carpooling.entities.PublicidadEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.PublicidadPersistence;
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
 * @author Nicolas Fajardo Ramirez
 */
@RunWith(Arquillian.class)
public class PublicidadLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private PublicidadLogic publicidadLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<PublicidadEntity> data = new ArrayList<PublicidadEntity>();
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PublicidadEntity.class.getPackage())
                .addPackage(PublicidadLogic.class.getPackage())
                .addPackage(PublicidadPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
    @Before
    public void configTest(){
        try{
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        }
        catch(Exception e){
            e.printStackTrace();
            try{
                utx.rollback();
            }
            catch(Exception e2){
                e2.printStackTrace();
            }
        }
    }
    
    private void clearData(){
        em.createQuery("delete from PublicidadEntity").executeUpdate();
    }
    
    private void insertData(){
        for(int i =0;i<3;i++){
            PublicidadEntity entity = factory.manufacturePojo(PublicidadEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createPublicidadTest() throws BusinessLogicException{
        PublicidadEntity newEntity = factory.manufacturePojo(PublicidadEntity.class);
        PublicidadEntity result = publicidadLogic.createPublicidad(newEntity);
        Assert.assertNotNull(result);
        PublicidadEntity entity = em.find(PublicidadEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(),entity.getId());
        Assert.assertEquals(newEntity.getCosto(),entity.getCosto(),0);
        Assert.assertEquals(newEntity.getFechaDeInicio(),entity.getFechaDeInicio());
        Assert.assertEquals(newEntity.getFechaDeSalida(),entity.getFechaDeSalida());
        Assert.assertEquals(newEntity.getMensaje(),entity.getMensaje());
        Assert.assertEquals(newEntity.getNombre(),entity.getNombre());
    }
    
    @Test
    public void getPublicidadesTest(){
        List<PublicidadEntity> list = publicidadLogic.getPublicidades();
        Assert.assertEquals(data.size(),list.size());
        for(PublicidadEntity entity : list){
            boolean found = false;
            for(PublicidadEntity storedEntity : data){
                if(entity.getId().equals(storedEntity.getId())){
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getPublicidadTest(){
        PublicidadEntity entity = data.get(0);
        PublicidadEntity result = publicidadLogic.getPublicidad(entity.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getId(),entity.getId());
        Assert.assertEquals(result.getCosto(),entity.getCosto(),0);
        Assert.assertEquals(result.getFechaDeInicio(),entity.getFechaDeInicio());
        Assert.assertEquals(result.getFechaDeSalida(),entity.getFechaDeSalida());
        Assert.assertEquals(result.getMensaje(),entity.getMensaje());
        Assert.assertEquals(result.getNombre(),entity.getNombre());
    }
    
    @Test
    public void updatePublicidadTest() throws BusinessLogicException{
        PublicidadEntity entity = data.get(0);
        PublicidadEntity pojoEntity = factory.manufacturePojo(PublicidadEntity.class);
        pojoEntity.setId(entity.getId());
        publicidadLogic.updatePublicidad(pojoEntity);
        PublicidadEntity result = em.find(PublicidadEntity.class,pojoEntity.getId());
        Assert.assertEquals(result.getId(),pojoEntity.getId());
        Assert.assertEquals(result.getCosto(),pojoEntity.getCosto(),0);
        Assert.assertEquals(result.getFechaDeInicio(),pojoEntity.getFechaDeInicio());
        Assert.assertEquals(result.getFechaDeSalida(),pojoEntity.getFechaDeSalida());
        Assert.assertEquals(result.getMensaje(),pojoEntity.getMensaje());
        Assert.assertEquals(result.getNombre(),pojoEntity.getNombre());
    }
    
    @Test
    public void deletePublicidadTest() throws BusinessLogicException{
        PublicidadEntity entity = data.get(0);
        publicidadLogic.deletePublicidad(entity.getId());
        PublicidadEntity deleted = em.find(PublicidadEntity.class,entity.getId());
        Assert.assertNull(deleted);
    }
}
