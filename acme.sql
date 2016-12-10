DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS proveedores;
DROP TABLE IF EXISTS productos;
DROP TABLE IF EXISTS venta;
DROP TABLE IF EXISTS compras;
DROP TABLE IF EXISTS detalle_venta;
DROP TABLE IF EXISTS detalle_compra;
DROP TABLE IF EXISTS users;

CREATE TABLE users(
    id_user int(10) NOT NULL AUTO_INCREMENT,
    nombre varchar(100) not null,
    apellido_mat varchar(100) not null,
    apellido_pat varchar(100) not null,
    usuario varchar(100) not null,
    contrasena varchar(100) not null,
    nivel varchar(100) not null,
    estado varchar(100) not null,
    PRIMARY KEY (id_user)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE clientes(
    id_cliente int(10) NOT NULL AUTO_INCREMENT,
    nombre varchar(100) not null,
    apellido_pat varchar(100) NOT NULL,
    apellido_mat varchar(100) NOT NULL,
    telefono varchar(15) NOT NULL,
    email varchar(100) NOT NULL,
    rfc varchar(20) NOT NULL,
    calle varchar(100) NOT NULL,
    num int(10) NOT NULL,
    colonia varchar(100) not null,
    ciudad varchar(100) NOT NULL,
    estado varchar(100) NOT NULL,
    PRIMARY KEY (id_cliente)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE proveedores(
    id_proveedor int(10) NOT NULL AUTO_INCREMENT,
    nombre varchar(100) not null,
    rfc varchar(20) not null,
    calle varchar(100) not null,
    num int(10) NOT NULL,
    colonia varchar(100) not null,
    ciudad varchar(100) not null,
    estado varchar(100) not null,
    nombre_contacto varchar(100) not null,
    telefono varchar(20) not null,
    email varchar(100) not null,
    PRIMARY KEY (id_proveedor)
)ENGINE=InnodB DEFAULT CHARSET=latin1;

CREATE TABLE productos(
    id_producto int(10) NOT NULL AUTO_INCREMENT,
    producto varchar(100) not null,
    descripcion varchar(100) not null,
    precio_compra float NOT NULL,
    precio_venta float NOT NULL,
    existencia int(4) NOT NULL,
    PRIMARY KEY (id_producto)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE venta(
    id_venta int(10) NOT NULL AUTO_INCREMENT,
    fecha date NOT NULL,
    id_cliente int(10) NOT NULL,
    subtotal float NOT NULL,
    iva float NOT NULL,
    total float NOT NULL,
    PRIMARY KEY(id_venta),
    FOREIGN KEY(id_cliente) REFERENCES clientes(id_cliente)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE compras(
    id_compra int(10) NOT NULL AUTO_INCREMENT,
    fecha date NOT NULL,
    id_proveedor int(10) NOT NULL,
    subtotal float NOT NULL,
    iva float NOT NULL,
    total float NOT NULL,
    PRIMARY KEY(id_compra),
    FOREIGN KEY(id_proveedor) REFERENCES proveedores(id_proveedor)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE detalle_venta(
    id_detalle_venta int(10) NOT NULL AUTO_INCREMENT,
    id_venta int(10) NOT NULL,
    id_producto int(10) NOT NULL,
    cantidad int(4) NOT NULL,
    total_producto float NOT NULL,
    precio float NOT NULL,
    PRIMARY KEY(id_detalle_venta),
    FOREIGN KEY(id_venta) REFERENCES venta(id_venta),
    FOREIGN KEY(id_producto) REFERENCES productos(id_producto)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE detalle_compra(
    id_detalle_compra int(10) NOT NULL AUTO_INCREMENT,
    id_compra int(10) NOT NULL,
    id_producto int(10) NOT NULL,
    cantidad int(4) NOT NULL,
    total_producto float NOT NULL,
    precio float NOT NULL,
    PRIMARY KEY(id_detalle_compra),
    FOREIGN KEY(id_compra) REFERENCES compras(id_compra),
    FOREIGN KEY(id_producto) REFERENCES productos(id_producto)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO users(id_user,nombre,apellido_mat,apellido_pat,usuario,contrasena,nivel,estado) VALUES
(1,"daniel","hernandez","cortes","dmhodst","1234","administrador","Activo"),
(2,"luis","marquez","morales","lamm13","wuis","vendedor","Activo"),
(3,"oswaldo","garcia","lemus","valdo123","1234","administrador","Inactivo");

INSERT INTO clientes(id_cliente,nombre,apellido_pat,apellido_mat,telefono,email,rfc,calle,num,colonia,ciudad,estado) VALUES
(1,"elias","pujol","rodriguez","7753343555","elias@yo","elnm1122123","la morena",25,"la morena","tulancingo","hidalgo"),
(2,"juan","lopez","perez","7712323454","juanito_banana@lol","jmnk113312","benito juarez",115,"centro","pachuca","hidalgo");

INSERT INTO proveedores(id_proveedor,nombre,rfc,calle,num,colonia,ciudad,estado,nombre_contacto,telefono,email) VALUES 
(1,"iluis","luuu123123","las flores",12,"centro","pachuca","hidalco","luis apple","7712342555","email@aplle"),
(2,"ipepe","marco34665","la calle 1",45,"centro","tulancingo","hidalgo","pepe mac","7751223443","mac@pepe.com");

INSERT INTO productos(id_producto,producto,descripcion,precio_compra,precio_venta,existencia) VALUES 
(1,"memoria usb","memoria kingston 64gb",24.10,35.60,15),
(2,"memoria micro sd","memoria hyperx 16gb",24.20,37.99,15);

INSERT INTO venta(id_venta,fecha,id_cliente,subtotal,iva,total) VALUES
(1,"2016-12-08",1,77.98,16,90.46);

INSERT INTO compras(id_compra,fecha,id_proveedor,subtotal,iva,total) VALUES
(1,"2016-12-08",1,48.20,16,55.91);

INSERT INTO detalle_venta(id_detalle_venta,id_venta,id_producto,cantidad,total_producto,precio) VALUES (1,1,2,2,77.98,37.99);

UPDATE productos SET existencia=13 WHERE id_producto = 2;

INSERT INTO detalle_compra(id_detalle_compra,id_compra,id_producto,cantidad,total_producto,precio) VALUES (1,1,1,2,48.20,24.10);

UPDATE productos SET existencia=17 WHERE id_producto = 1;
