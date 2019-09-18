/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carpooling.ejb;
import co.edu.uniandes.csw.carpooling.entities.ReservaEntity;
import co.edu.uniandes.csw.carpooling.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.carpooling.persistence.ReservaPersistence;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
/**
 *
 * @author Estudiante
 */
@Stateless
public class ReservaLogic {
    
    @Inject
    private ReservaPersistence persistence;
    
    public ReservaEntity createReserva(ReservaEntity reserva) throws BusinessLogicException{
        if(reserva.getFecha().compareTo(Calendar.getInstance().getTime())<0){
            throw new BusinessLogicException("La reserva tiene una fecha menor a la actual");
        }
        // preguntar si existe el viaje (not null) no se como hacerlo, se debe hacer la relacion, manyToOne en el entity con la referencia a viajes?
        
        if(reserva.getEstado().equals(null)){
            throw new BusinessLogicException("La reserva no tiene estado");
        }
        if(persistence.find(reserva.getId())!=null){
            throw new BusinessLogicException("La reserva ya existe");
        }
        reserva = persistence.create(reserva);
        return reserva;
    }
    
       public List<ReservaEntity> getReservas(){
        return persistence.findAll();
       }
       
//       public List<ReservaEntity> getReservas(){
//        return persistence.findAll();
//       }
}
