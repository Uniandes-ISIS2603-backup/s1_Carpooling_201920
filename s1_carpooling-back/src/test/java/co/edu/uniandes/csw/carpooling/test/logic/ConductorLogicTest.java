/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.logic;

import co.edu.uniandes.csw.carpooling.entities.ConductorEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.ConductorPersistence;
import co.uniandes.csw.carpooling.ejb.ConductorLogic;
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
 * @author Nicolas Fajardo
 */
@RunWith(Arquillian.class)
public class ConductorLogicTest {
    
    @Inject
    private ConductorLogic conductorLogic;
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<ConductorEntity> data = new ArrayList<ConductorEntity>();
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ConductorEntity.class.getPackage())
                .addPackage(ConductorLogic.class.getPackage())
                .addPackage(ConductorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
    @Before
    public void ConfigTest(){
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
            catch(Exception e1){
                e1.printStackTrace();
            }
        }
    }
    
    private void clearData(){
        em.createQuery("delete from ConductorEntity").executeUpdate();
    }
    
    private void insertData(){
        for(int i =0;i<3;i++){
            ConductorEntity entity = factory.manufacturePojo(ConductorEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void crearConductorTest()throws BusinessLogicException{
        ConductorEntity newEntity = factory.manufacturePojo(ConductorEntity.class);
        ConductorEntity result = conductorLogic.crearConductor(newEntity);
        Assert.assertNotNull(result);
        ConductorEntity entity = em.find(ConductorEntity.class, result.getId());
        Assert.assertEquals(newEntity.getApellido(),entity.getApellido());
        Assert.assertEquals(newEntity.getContrasenha(),entity.getContrasenha());
        Assert.assertEquals(newEntity.getCorreo(),entity.getCorreo());
        Assert.assertEquals(newEntity.getFechaDeNacimiento(),entity.getFechaDeNacimiento());
        Assert.assertEquals(newEntity.getId(),entity.getId());
        Assert.assertEquals(newEntity.getNombre(),entity.getNombre());
        Assert.assertEquals(newEntity.getNumDocumento(),entity.getNumDocumento());
        Assert.assertEquals(newEntity.getTelefono(), entity.getTelefono());
        Assert.assertEquals(newEntity.getTipoDocumento(),entity.getTipoDocumento());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void crearConductorConCorreoExistente()throws BusinessLogicException{
     ConductorEntity newEntity = factory.manufacturePojo(ConductorEntity.class);
     newEntity.setCorreo(data.get(0).getCorreo());
     conductorLogic.crearConductor(newEntity);
    }
    
    @Test
    public void getConductoresTest(){
        List<ConductorEntity> list = conductorLogic.getConductores();
        Assert.assertEquals(data.size(),list.size());
        for(ConductorEntity entity : list){
            boolean found = false;
            for(ConductorEntity storedEntity : data){
                if(entity.getId().equals(storedEntity.getId())){
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getConductorTest(){
        ConductorEntity entity = data.get(0);
        ConductorEntity resultEntity = conductorLogic.getConductor(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getApellido(),entity.getApellido());
        Assert.assertEquals(resultEntity.getContrasenha(),entity.getContrasenha());
        Assert.assertEquals(resultEntity.getCorreo(),entity.getCorreo());
        Assert.assertEquals(resultEntity.getFechaDeNacimiento(),entity.getFechaDeNacimiento());
        Assert.assertEquals(resultEntity.getId(),entity.getId());
        Assert.assertEquals(resultEntity.getNombre(),entity.getNombre());
        Assert.assertEquals(resultEntity.getNumDocumento(),entity.getNumDocumento());
        Assert.assertEquals(resultEntity.getTelefono(), entity.getTelefono());
        Assert.assertEquals(resultEntity.getTipoDocumento(),entity.getTipoDocumento());
    }
    
    @Test
    public void updateConductorTest() throws BusinessLogicException{
        ConductorEntity entity = data.get(0);
        ConductorEntity pojoEntity = factory.manufacturePojo(ConductorEntity.class);
        pojoEntity.setId(entity.getId());
        conductorLogic.actualizarConductor(pojoEntity);
        ConductorEntity resp = em.find(ConductorEntity.class,entity.getId());
        Assert.assertEquals(pojoEntity.getApellido(),resp.getApellido());
        Assert.assertEquals(pojoEntity.getContrasenha(),resp.getContrasenha());
        Assert.assertEquals(pojoEntity.getCorreo(),resp.getCorreo());
        Assert.assertEquals(pojoEntity.getFechaDeNacimiento(),resp.getFechaDeNacimiento());
        Assert.assertEquals(pojoEntity.getId(),resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(),resp.getNombre());
        Assert.assertEquals(pojoEntity.getNumDocumento(),resp.getNumDocumento());
        Assert.assertEquals(pojoEntity.getTelefono(), resp.getTelefono());
        Assert.assertEquals(pojoEntity.getTipoDocumento(),resp.getTipoDocumento());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void actualizarLibroConCorreoInvalido() throws BusinessLogicException {
        ConductorEntity entity = data.get(0);
        ConductorEntity pojoEntity = factory.manufacturePojo(ConductorEntity.class);
        pojoEntity.setCorreo("");
        conductorLogic.actualizarConductor(pojoEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void actualizarLibroConCorreoInvalido2() throws BusinessLogicException {
        ConductorEntity entity = data.get(0);
        ConductorEntity pojoEntity = factory.manufacturePojo(ConductorEntity.class);
        pojoEntity.setCorreo(null);
        conductorLogic.actualizarConductor(pojoEntity);
    }
    
    @Test
    public void deleteConductorTest() throws BusinessLogicException{
        ConductorEntity entity = data.get(0);
        conductorLogic.deleteConductor(entity.getId());
        ConductorEntity deleted = em.find(ConductorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
