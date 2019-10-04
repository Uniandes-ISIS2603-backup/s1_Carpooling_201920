/*
MIT License

Copyright (c) 2017 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.carpooling.dtos;


import co.edu.uniandes.csw.carpooling.entities.ViajeEntity;
import co.edu.uniandes.csw.carpooling.entities.ViajeRecurrenteEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que extiende de {@link ViajeRecurrenteDTO} para manejar las relaciones entre los
 * ViajeRecurrenteDTO y otros DTOs. Para conocer el contenido de la un ViajeRecurrente vaya a la
 * documentacion de {@link ViajeRecurrenteDTO}
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 "id": number,
 *      "fechaInicio": date,
 *      "fechaFin": date,
 *      "frecuencia": string,
 *      "conductor": {@link ConductorDTO}
 *      "viajes": {@Link ViajeDTO}
 *   }
 * </pre> Por ejemplo un libro se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1,
 *      "fechaInicio": "2000-08-20T00:00:00-05:00"
 *      "fechaFin": "2001-08-20T00:00:00-05:00",
 *      "frecuencia": "!"
 *      "conductor":
 *      {
 * 
 *      }
 *      "viajes":
 *      {
 *      }
 *   }
 *
 * </pre>
 *
 * @author ISIS2603
 */
public class ViajeRecurrenteDetailDTO extends ViajeRecurrenteDTO implements Serializable {

    // relación  uno a muchos viajes 
    private List<ViajeDTO> viajes;

    // relación  cero o muchos author

    public ViajeRecurrenteDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param viajeRecurrenteEntity La entidad de la cual se construye el DTO
     */
    public ViajeRecurrenteDetailDTO(ViajeRecurrenteEntity viajeRecurrenteEntity) {
        super(viajeRecurrenteEntity);
        if (viajeRecurrenteEntity.getViajes() != null) {
            viajes = new ArrayList<>();
            for (ViajeEntity entityViaje : viajeRecurrenteEntity.getViajes()) {
                viajes.add(new ViajeDTO(entityViaje));
            }
        }
    }

    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa el libro.
     */
    @Override
    public ViajeRecurrenteEntity toEntity() {
        ViajeRecurrenteEntity viajeRecurrenteEntity = super.toEntity();
        if (viajes != null) {
            List<ViajeEntity> viajeEntity = new ArrayList<>();
            for (ViajeDTO dtoViaje : getViajes()) {
                viajeEntity.add(dtoViaje.toEntity());
            }
            viajeRecurrenteEntity.setViajes(viajeEntity);
        }
        return viajeRecurrenteEntity;
    }

    /**
     * Devuelve las reseñas asociadas a este libro
     *
     * @return Lista de DTOs de Reseñas
     */
    public List<ViajeDTO> getViajes() {
        return viajes;
    }

    /**
     * Modifica las reseñas de este libro.
     *
     * @param viaje Las nuevas reseñas
     */
    public void setViajes(List<ViajeDTO> viaje) {
        this.viajes = viaje;
    }
}
