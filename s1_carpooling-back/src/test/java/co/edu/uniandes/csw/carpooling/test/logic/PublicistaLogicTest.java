/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.test.logic;

import co.edu.uniandes.csw.carpooling.ejb.PublicistaLogic;
import co.edu.uniandes.csw.carpooling.entities.PublicidadEntity;
import co.edu.uniandes.csw.carpooling.entities.PublicistaEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.PublicistaPersistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Santiago Ballesteros
 */
@RunWith(Arquillian.class)
public class PublicistaLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private PublicistaLogic publicistaLogic;
    
     @PersistenceContext
    private EntityManager em;
     
    @Inject
    private UserTransaction utx;
    
    private List<PublicistaEntity> data = new ArrayList<PublicistaEntity>();
    
    @Deployment
    public static JavaArchive createDeployment(){
    return ShrinkWrap.create(JavaArchive.class)
            .addPackage(PublicistaEntity.class.getPackage())
            .addPackage(PublicistaLogic.class.getPackage())
            .addPackage(PublicistaPersistence.class.getPackage())
            .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
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

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from PublicidadEntity").executeUpdate();
        em.createQuery("delete from PublicistaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PublicistaEntity entity = factory.manufacturePojo(PublicistaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
        PublicidadEntity publicidad = factory.manufacturePojo(PublicidadEntity.class);
        publicidad.setPublicista(data.get(1));
//        em.persist(publicista);
 //       data.get(1).getPrizes().add(publicista);
    }
    
    @Test
    public void createPublicistaTest() throws BusinessLogicException{
    
        PublicistaEntity newEntity = factory.manufacturePojo(PublicistaEntity.class);
        PublicistaEntity result = publicistaLogic.createPublicista(newEntity);
        Assert.assertNotNull(result);
        
        PublicistaEntity entity = em.find(PublicistaEntity.class, result.getId());
        Assert.assertEquals(entity.getNombre(), result.getNombre());
        Assert.assertEquals(result.getApellido(), entity.getApellido());
        Assert.assertEquals(result.getCedula(), entity.getCedula());
        Assert.assertEquals(result.getCorreo(), entity.getCorreo());
        Assert.assertEquals(result.getContrasenha(),entity.getContrasenha());
        Assert.assertEquals(result.getNit(), entity.getNit());
        Assert.assertEquals(result.getRut(), entity.getRut());
        Assert.assertEquals(result.getTelefono(), entity.getTelefono());
        Assert.assertEquals(result.getTipoPublicista(), entity.getTipoPublicista());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createPublicistaTipoPublicistaNullTest() throws BusinessLogicException{
    
        PublicistaEntity newEntity = factory.manufacturePojo(PublicistaEntity.class);
        newEntity.setTipoPublicista(null);
        PublicistaEntity result = publicistaLogic.createPublicista(newEntity);  
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createPublicistaNombreNullTest() throws BusinessLogicException{
    
        PublicistaEntity newEntity = factory.manufacturePojo(PublicistaEntity.class);
        newEntity.setNombre(null);
        PublicistaEntity result = publicistaLogic.createPublicista(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createPublicistaApellidoNullTest() throws BusinessLogicException{
    
        PublicistaEntity newEntity = factory.manufacturePojo(PublicistaEntity.class);
        if(newEntity.getTipoPublicista().compareTo(PublicistaEntity.TIPO_PUBLICISTA.PERSONA_NATURAL_CON_EMPRESA)==0){
        newEntity.setApellido(null);
        PublicistaEntity result = publicistaLogic.createPublicista(newEntity);
        }
        else{
            throw new BusinessLogicException("El publicista " + newEntity.getTipoPublicista()+ " no debe arrojar excepcion");
        }
    }
    
        @Test(expected = BusinessLogicException.class)
    public void createPublicistaContrasenhaNullTest() throws BusinessLogicException{
    
        PublicistaEntity newEntity = factory.manufacturePojo(PublicistaEntity.class);
        newEntity.setContrasenha(null);
        PublicistaEntity result = publicistaLogic.createPublicista(newEntity);

    }
    
    @Test(expected = BusinessLogicException.class)
    public void createPublicistaCorreoNullTest() throws BusinessLogicException{
    
        PublicistaEntity newEntity = factory.manufacturePojo(PublicistaEntity.class);
        newEntity.setCorreo(null);
        PublicistaEntity result = publicistaLogic.createPublicista(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createPublicistaNitNullTest() throws BusinessLogicException{
    
        PublicistaEntity newEntity = factory.manufacturePojo(PublicistaEntity.class);
        if(newEntity.getTipoPublicista().compareTo(PublicistaEntity.TIPO_PUBLICISTA.PERSONA_NATURAL_CON_EMPRESA)!=0){
           newEntity.setNit(null);
           PublicistaEntity result = publicistaLogic.createPublicista(newEntity);
        }
        else{
         throw new BusinessLogicException();
        }
    }
    
    /**
     * Prueba para crear un Publicista.
     *
     * @throws co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException .
     */
    @Test(expected = BusinessLogicException.class)
    public void createPublicistaConCorreoRepetidoTest() throws BusinessLogicException {
        PublicistaEntity newEntity = factory.manufacturePojo(PublicistaEntity.class);
        newEntity.setCorreo(data.get(0).getCorreo());
        publicistaLogic.createPublicista(newEntity);
    }
    
    /**
     * Prueba para crear un Publicista.
     *
     * @throws co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException .
     */
    @Test(expected = BusinessLogicException.class)
    public void createPublicistaConRutRepetidoTest() throws BusinessLogicException {
        PublicistaEntity newEntity = factory.manufacturePojo(PublicistaEntity.class);
        if(newEntity.getTipoPublicista().compareTo(PublicistaEntity.TIPO_PUBLICISTA.EMPRESA)!=0){
           newEntity.setRut(data.get(0).getRut());
           publicistaLogic.createPublicista(newEntity);
        }
        else{
           throw new BusinessLogicException();
        }
    }
    
    /**
     * Prueba para crear un Publicista.
     *
     * @throws co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException .
     */
    @Test(expected = BusinessLogicException.class)
    public void createPublicistaConNitRepetidoTest() throws BusinessLogicException {
        PublicistaEntity newEntity = factory.manufacturePojo(PublicistaEntity.class);
        if(newEntity.getTipoPublicista().compareTo(PublicistaEntity.TIPO_PUBLICISTA.PERSONA_NATURAL_CON_EMPRESA)!=0){
           newEntity.setNit(data.get(0).getNit());
           publicistaLogic.createPublicista(newEntity);
        }
        else{
           throw new BusinessLogicException();
        }
    }
    
    @Test
    public void getPublicistasTest(){
        List<PublicistaEntity> list = publicistaLogic.getPublicistas();
        Assert.assertEquals(data.size(),list.size());
        for(PublicistaEntity entity : list){
            boolean found = false;
            for(PublicistaEntity storedEntity : data){
                if(entity.getId().equals(storedEntity.getId())){
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getPublicistaTest(){
        PublicistaEntity entity = data.get(0);
        PublicistaEntity result = publicistaLogic.getPublicista(entity.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals(entity.getNombre(), result.getNombre());
        Assert.assertEquals(result.getApellido(), entity.getApellido());
        Assert.assertEquals(result.getCedula(), entity.getCedula());
        Assert.assertEquals(result.getContrasenha(),entity.getContrasenha());
        Assert.assertEquals(result.getCorreo(), entity.getCorreo());
        Assert.assertEquals(result.getNit(), entity.getNit());
        Assert.assertEquals(result.getRut(), entity.getRut());
        Assert.assertEquals(result.getTelefono(), entity.getTelefono());
        Assert.assertEquals(result.getTipoPublicista(), entity.getTipoPublicista());
    }
    
    @Test
    public void updatePublicistaTest() throws BusinessLogicException{
        PublicistaEntity entity = data.get(0);
        PublicistaEntity pojoEntity = factory.manufacturePojo(PublicistaEntity.class);
        Date date = new Date();
        pojoEntity.setId(entity.getId());
        publicistaLogic.updatePublicista(entity.getId(),pojoEntity);
        PublicistaEntity resp = em.find(PublicistaEntity.class,entity.getId());
        Assert.assertEquals(pojoEntity.getContrasenha(),resp.getContrasenha());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getApellido(), resp.getApellido());
        Assert.assertEquals(pojoEntity.getCedula(), resp.getCedula());
        Assert.assertEquals(pojoEntity.getCorreo(), resp.getCorreo());
        Assert.assertEquals(pojoEntity.getNit(), resp.getNit());
        Assert.assertEquals(pojoEntity.getRut(), resp.getRut());
        Assert.assertEquals(pojoEntity.getTelefono(), resp.getTelefono());
        Assert.assertEquals(pojoEntity.getTipoPublicista(), resp.getTipoPublicista());
    }
    
    @Test
    public void deletePublicistaTest() throws BusinessLogicException{
        PublicistaEntity entity = data.get(0);
        publicistaLogic.deletePublicista(entity.getId());
        PublicistaEntity deleted = em.find(PublicistaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
