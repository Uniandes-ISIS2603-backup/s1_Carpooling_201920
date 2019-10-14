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

insert into CalificacionEntity(id, puntuacion, comentarios,USUARIO_ID,CONDUCTOR_ID)
values (1, 4, 'OK',1,1);

insert into VehiculoEntity(id, soat, placa, aseguradora, vigenciaSoat, modelo, sillas, CONDUCTOR_ID)
values( 1,'esteSoat','ABC 123','estaAseguradora','estaVigencia','esteModelo',5,1);

insert into ViajeRecurrenteEntity(id,fechaInicio,fechaFin,frecuencia, CONDUCTOR_ID)
values(1,'2020-11-10 10:20:20','2020-12-11 10:20:20','1,2,4',1);

insert into ConductorEntity(id)
values (2);     

insert into ViajeroEntity(id)
values (3);

insert into CalificacionEntity(id, puntuacion, comentarios, USUARIO_ID, VIAJERO_ID)
values (3, 4, 'OKEY',3,3);

insert into ViajeroEntity(id)
values (2);

insert into ViajeEntity (id, destino, origen, 
fechaDeLlegada, fechaDeSalida, cupos, costoViaje,
estadoViaje) values (100, 'Bogota', 'Bucaramanga', '11/2/2019', '11/12/2019' , 4, 30000, 0);

insert into ViajeEntity (id, destino, origen, 
fechaDeLlegada, fechaDeSalida, cupos, costoViaje,
estadoViaje) values (200, 'Chia', 'Santa Marta', '12/5/2019', '12/6/2019' , 4, 30000, 0);

insert into ViajeEntity (id, destino, origen, 
fechaDeLlegada, fechaDeSalida, cupos, costoViaje,
estadoViaje) values (300, 'Medellin', 'Cali', '10/8/2019', '10/8/2019' , 4, 30000, 0);

