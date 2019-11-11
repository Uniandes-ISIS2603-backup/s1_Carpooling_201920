delete from TrayectoEntity;
delete from ReservaEntity;
delete from CalificacionEntity;
delete from NotificacionEntity;
delete from ViajeEntity;
delete from VehiculoEntity;
delete from PublicidadEntity;
delete from PublicistaEntity;
delete from ViajeRecurrenteEntity;
delete from ConductorEntity;
delete from ViajeroEntity;



insert into ConductorEntity(id, contrasenha, correo, fechaDeNacimiento, nombre, numDocumento, tipoDocumento, telefono)
values (1, 'abcd', 'jd@patito.com', '11/10/2012', 'oniichan', '1032799133', 1 , '3214845777');

insert into CalificacionEntity(id, puntuacion, comentarios,CONDUCTOR_ID)
values (1, 4, 'OK',1);

insert into VehiculoEntity(id, soat, placa, aseguradora, vigenciaSoat, modelo, sillas, CONDUCTOR_ID)
values( 1,'esteSoat','ABC 123','estaAseguradora','estaVigencia','esteModelo',5,1);

insert into ViajeRecurrenteEntity(id,fechaInicio,fechaFin,frecuencia, CONDUCTOR_ID)
values(1,'2020-11-10 10:20:20','2020-12-11 10:20:20','1,2,4',1);

insert into ConductorEntity(id)
values (2);     

insert into VIAJEROENTITY (id, contrasenha, correo, fechaDeNacimiento, nombre, numDocumento, telefono, tipoDocumento) values (1, 'ttn34TMt', 'mmoxson0@blogspot.com', '11/15/1999', 'Malinda', '1548244263','431132131' ,0);
insert into VIAJEROENTITY (id, contrasenha, correo, fechaDeNacimiento, nombre, numDocumento, telefono, tipoDocumento) values (2, 'lCnL3q', 'ddeighton1@disqus.com', '12/26/1995', 'Dom', '1346078573', '213123121',0);
insert into VIAJEROENTITY (id, contrasenha, correo, fechaDeNacimiento, nombre, numDocumento, telefono, tipoDocumento) values (3, 'kas7qE', 'nlaugharne2@tiny.cc', '11/20/1957', 'Neda', '8089319231','32131223' ,0);

insert into CalificacionEntity(id, puntuacion, comentarios, VIAJERO_ID)
values (3, 4, 'OKEY',3);

insert into ReservaEntity(id, numeroDeReserva, confirmacion, fecha, estado) values (1, '112', 'Reserva Aprovaba','11/15/2019','A tiempo' );
insert into ReservaEntity(id, numeroDeReserva, confirmacion, fecha, estado) values (1, '113', 'Reserva Aprovaba','11/16/2019','A tiempo' );
insert into ReservaEntity(id, numeroDeReserva, confirmacion, fecha, estado) values (1, '114', 'Reserva Aprovaba','11/17/2019','A tiempo' );

insert into ViajeEntity (id, destino, origen, 
fechaDeLlegada, fechaDeSalida, cupos, costoViaje,
estadoViaje, conductor_id, vehiculo_id) values (100, 'Bogota', 'Bucaramanga', '11/2/2019', '11/12/2019' , 4, 30000, 0, 1, 1);

insert into ViajeEntity (id, destino, origen, 
fechaDeLlegada, fechaDeSalida, cupos, costoViaje,
estadoViaje, conductor_id, vehiculo_id) values (200, 'Chia', 'Santa Marta', '12/5/2019', '12/6/2019' , 4, 30000, 0, 1, 1);

insert into ViajeEntity (id, destino, origen, 
fechaDeLlegada, fechaDeSalida, cupos, costoViaje,
estadoViaje, conductor_id, vehiculo_id) values (300, 'Medellin', 'Cali', '10/8/2019', '10/8/2019' , 4, 30000, 0, 1, 1);

insert into TrayectoEntity (id, costoCombustible, destino, duracion, numpeajes, origen, viaje_id ) values (100, 60000, 'Calle 40 #120-3', 700, 14, 'Calle 40 #120-3', 100);

insert into TrayectoEntity (id, costoCombustible, destino, duracion, numpeajes, origen, viaje_id ) values (200, 80000, 'Calle 40 #120-3', 700, 14, 'Calle 40 #120-3', 100);

insert into TrayectoEntity (id, costoCombustible, destino, duracion, numpeajes, origen, viaje_id ) values (300, 90000, 'Calle 40 #120-3', 700, 14, 'Calle 40 #120-3', 200);

insert into TrayectoEntity (id, costoCombustible, destino, duracion, numpeajes, origen, viaje_id ) values (400, 50000, 'Calle 40 #120-3', 700, 14, 'Calle 40 #120-3', 300);

insert into PUBLICISTAENTITY (id, nombre, apellido, cedula, contrasenha, correo, rut, telefono, tipoPublicista) values (1, 'Bran', 'Usmar', '1492945854', 'GoXVn3xpanFc', 'busmar0@elpais.com', '3204987833', '+86 436 385 4341', 0);
insert into PUBLICISTAENTITY (id, nombre, apellido, cedula, contrasenha, correo, rut, telefono, tipoPublicista) values (2, 'Ham', 'Pickthorn', '2843867126', '6T5v8j59', 'hpickthorn1@ebay.com', '1626030723', '+1 415 328 4315',  0);
insert into PUBLICISTAENTITY (id, nombre, apellido, cedula, contrasenha, correo, rut, telefono, tipoPublicista) values (3, 'Marlie', 'Windmill', '3375486170', 'dhhQv7', 'mwindmill2@discovery.com', '0324424078', '+86 276 668 7184',  0);

insert into PUBLICIDADENTITY (id, nombre, costo, fechaDeInicio, fechaDeSalida, mensaje, disponibilidad, publicista_id) values (1, 'Jamie', 21.56, '11/10/2019', '5/1/2019', 'eOl16vt7+9vR3lclkCOc7GPsuykEw', 0, 1);
insert into PUBLICIDADENTITY (id, nombre, costo, fechaDeInicio, fechaDeSalida, mensaje, disponibilidad, publicista_id) values (2, 'Angelo', 23.2, '11/10/2019', '8/6/2019', 'data:image/png;base64,iVBORw0', 0, 2);
insert into PUBLICIDADENTITY (id, nombre, costo, fechaDeInicio, fechaDeSalida, mensaje, disponibilidad, publicista_id) values (3, 'Bobette', 3.66, '11/10/2019', '7/13/2019', 'data:image/png;base64,iVBOR', 0, 3);

