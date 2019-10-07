delete from PublicistaEntity;
delete from ViajeEntity;
delete from TrayectoEntity;

insert into ViajeEntity (id, destino, origen, 
fechaDeSalida, fechaDeLlegada, cupos, costoViaje, vehiculo,
estadoViaje) values (100, 'Bogota', 'Bucaramanga', '11/2/2019', '11/12/2019' , 4, 30000, 'DBS893', 0);

insert into ViajeEntity (id, destino, origen, 
fechaDeSalida, fechaDeLlegada, cupos, costoViaje, vehiculo,
estadoViaje) values (200, 'Chia', 'Santa Marta', '12/5/2019', '12/6/2019' , 4, 30000, 'DBS893', 0);

insert into ViajeEntity (id, destino, origen, 
fechaDeSalida, fechaDeLlegada, cupos, costoViaje, vehiculo,
estadoViaje) values (300, 'Medellin', 'Cali', '10/8/2019', '10/8/2019' , 4, 30000, 'DBS893', 0);

insert into TrayectoEntity(id, costoCombustible, duracion, numPeajes, origen, destino, viaje_id) values
(100, 30000, 140, 4, 'Calle 40 #11-50', 'Carrera 20 #15-04', 100);

insert into TrayectoEntity(id, costoCombustible, duracion, numPeajes, origen, destino, viaje_id) values
(200, 45000, 220, 6, 'Carrera 20 #20-49', 'Carrera 15 #30-77', 200);

