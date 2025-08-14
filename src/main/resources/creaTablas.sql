/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  adryhd
 * Created: 13 ago 2025
 */



DROP SCHEMA IF EXISTS techshop;
DROP USER IF EXISTS 'usuario_prueba'@'%';

-- 2)  esquema y seleccionar base
CREATE SCHEMA techshop;
USE techshop;

-- 3) Crear usuario de aplicación (opcional: requiere permisos de admin)
--    Si no tienes permisos, COMENTA este bloque y usa tu usuario propio
CREATE USER 'usuario_prueba'@'%' IDENTIFIED BY 'Usuar1o_Clave.';
GRANT ALL PRIVILEGES ON techshop.* TO 'usuario_prueba'@'%';
FLUSH PRIVILEGES;

-- 4) Tablas
-- Tabla: categoria
CREATE TABLE categoria (
  id_categoria INT NOT NULL AUTO_INCREMENT,
  descripcion  VARCHAR(30) NOT NULL,
  ruta_imagen  VARCHAR(1024),
  activo       BOOLEAN,
  PRIMARY KEY (id_categoria)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: producto
CREATE TABLE producto (
  id_producto  INT NOT NULL AUTO_INCREMENT,
  id_categoria INT NOT NULL,
  descripcion  VARCHAR(30) NOT NULL,
  detalle      VARCHAR(1600) NOT NULL,
  precio       DOUBLE,
  existencias  INT,
  ruta_imagen  VARCHAR(1024),
  activo       BOOLEAN,
  PRIMARY KEY (id_producto),
  CONSTRAINT fk_producto_categoria
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
      ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: usuario
CREATE TABLE usuario (
  id_usuario   INT NOT NULL AUTO_INCREMENT,
  username     VARCHAR(20)  NOT NULL,
  password     VARCHAR(512) NOT NULL,
  nombre       VARCHAR(20)  NOT NULL,
  apellidos    VARCHAR(30)  NOT NULL,
  correo       VARCHAR(50)  NULL,
  telefono     VARCHAR(20)  NULL,
  ruta_imagen  VARCHAR(1024),
  activo       BOOLEAN,
  PRIMARY KEY (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: factura
CREATE TABLE factura (
  id_factura INT NOT NULL AUTO_INCREMENT,
  id_usuario INT NOT NULL,
  fecha      DATE,
  total      DOUBLE,
  estado     INT,           -- (1=Activa, 2=Pagada, 3=Anulada) por ejemplo
  PRIMARY KEY (id_factura),
  CONSTRAINT fk_factura_usuario
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
      ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: venta
CREATE TABLE venta (
  id_venta    INT NOT NULL AUTO_INCREMENT,
  id_factura  INT NOT NULL,
  id_producto INT NOT NULL,
  precio      DOUBLE,
  cantidad    INT,
  PRIMARY KEY (id_venta),
  CONSTRAINT fk_venta_factura
    FOREIGN KEY (id_factura) REFERENCES factura(id_factura)
      ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fk_venta_producto
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
      ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: rol
CREATE TABLE rol (
  id_rol     INT NOT NULL AUTO_INCREMENT,
  nombre     VARCHAR(20),
  id_usuario INT,
  PRIMARY KEY (id_rol),
  CONSTRAINT fk_rol_usuario
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
      ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5) Datos de ejemplo ------------------------------------------------

-- Usuarios (passwords ya encriptados con BCrypt)
INSERT INTO usuario
(id_usuario, username, password, nombre, apellidos, correo, telefono, ruta_imagen, activo) VALUES
(1,'juan'  ,'$2a$10$P1.w58XvnaYQUQgZUCk4aO/RTRl8EValluCqB3S2VMLTbRt.tlre.','Juan'  ,'Castro Mora','jcastro@gmail.com','4556-8978','https://upload.wikimedia.org/wikipedia/commons/thumb/2/2a/Juan_Diego_Madrigal.jpg/250px-Juan_Diego_Madrigal.jpg',true),
(2,'rebeca','$2a$10$GkEj.ZzmQa/aEfDmtLIh3udIH5fMphx/35d0EYeqZL5uzgCJ0lQRi','Rebeca','Contreras Mora','acontreras@gmail.com','5456-8789','https://upload.wikimedia.org/wikipedia/commons/0/06/Photo_of_Rebeca_Arthur.jpg',true),
(3,'pedro' ,'$2a$10$koGR7eS22Pv5KdaVJKDcge04ZB53iMiw76.UjHPY.XyVYlYqXnPbO','Pedro' ,'Mena Loria','lmena@gmail.com','7898-8936','https://upload.wikimedia.org/wikipedia/commons/thumb/f/fd/Eduardo_de_Pedro_2019.jpg/480px-Eduardo_de_Pedro_2019.jpg?20200109230854',true);

-- Categorías
INSERT INTO categoria (id_categoria, descripcion, ruta_imagen, activo) VALUES
(1,'Monitores','https://d2ulnfq8we0v3.cloudfront.net/cdn/695858/media/catalog/category/MONITORES.jpg',true),
(2,'Teclados' ,'https://cnnespanol.cnn.com/wp-content/uploads/2022/04/teclado-mecanico.jpg',true),
(3,'Tarjeta Madre','https://static-geektopia.com/storage/thumbs/784x311/788/7884251b/98c0f4a5.webp',true),
(4,'Celulares','https://www.monumental.co.cr/wp-content/uploads/2022/03/X4J2Z6XQUZDO7O6QTDF4DIJ3VE.jpeg',false);

-- Productos
INSERT INTO producto
(id_producto,id_categoria,descripcion,detalle,precio,existencias,ruta_imagen,activo) VALUES
(1,1,'Monitor AOC 19','Lorem ipsum...',23000,5,'https://c.pxhere.com/images/ec/fd/d67b367ed6467eb826842ac81d3b-1453591.jpg!d',true),
(2,1,'Monitor MAC','Lorem ipsum...',27000,2,'https://c.pxhere.com/photos/17/77/Art_Calendar_Cc0_Creative_Design_High_Resolution_Mac_Stock-1622403.jpg!d',true),
(3,1,'Monitor Flex 21','Lorem ipsum...',24000,5,'https://www.trustedreviews.com/wp-content/uploads/sites/54/2022/09/LG-OLED-Flex-7-scaled.jpg',true),
(4,1,'Monitor Flex 36','Lorem ipsum...',27600,2,'https://www.lg.com/us/images/tvs/md08003300/gallery/D-01.jpg',true),
(5,2,'Teclado español everex','Lorem ipsum...',45000,5,'https://http2.mlstatic.com/D_NQ_NP_984317-MLA43206062255_082020-O.webp',true),
(6,2,'Teclado fisico gamer','Lorem ipsum...',57000,2,'https://psycatgames.com/magazine/party-games/gaming-trivia/feature-image_hu1c2b511a5a2ca80ffc557d83cb5157c1_380853_1200x1200_fill_q100_box_smart1.jpg',true),
(7,2,'Teclado usb compacto','Lorem ipsum...',25000,5,'https://live.staticflickr.com/7010/26783973491_3e2043edda_b.jpg',true),
(8,2,'Teclado Monitor Flex','Lorem ipsum...',27600,2,'https://hardzone.es/app/uploads-hardzone.es/2020/10/Mejores-KVM.jpg',true),
(9,3,'CPU Intel 7i','Lorem ipsum...',15780,5,'https://live.staticflickr.com/7391/9662276651_f4aa27d5ca_b.jpg',true),
(10,3,'CPU Intel Core 5i','Lorem ipsum...',15000,2,'https://live.staticflickr.com/1473/24714440462_31a0fcdfba_b.jpg',true),
(11,3,'AMD 7500','Lorem ipsum...',25400,5,'https://upload.wikimedia.org/wikipedia/commons/0/0c/AMD_Ryzen_9_3900X_-_ISO.jpg',true),
(12,3,'AMD 670','Lorem ipsum...',45000,3,'https://upload.wikimedia.org/wikipedia/commons/a/a0/AMD_Duron_850_MHz_D850AUT1B.jpg',true),
(13,4,'Samsung S22','Lorem ipsum...',285000,0,'https://www.trustedreviews.com/wp-content/uploads/sites/54/2022/08/S22-app-drawer-scaled.jpg',true),
(14,4,'Motorola X23','Lorem ipsum...',154000,0,'https://www.trustedreviews.com/wp-content/uploads/sites/54/2021/10/motorola-2.jpg',true),
(15,4,'Nokia 5430','Lorem ipsum...',330000,0,'https://www.trustedreviews.com/wp-content/uploads/sites/54/2021/08/nokia-xr20-1.jpg',true),
(16,4,'Xiami x45','Lorem ipsum...',273000,0,'https://www.trustedreviews.com/wp-content/uploads/sites/54/2022/03/20220315_104812-1-scaled.jpg',true);

-- Facturas
INSERT INTO factura (id_factura,id_usuario,fecha,total,estado) VALUES
(1,1,'2022-01-05',211560,2),
(2,2,'2022-01-07',554340,2),
(3,3,'2022-01-07',871000,2),
(4,1,'2022-01-15',244140,1),
(5,2,'2022-01-17',414800,1),
(6,3,'2022-01-21',420000,1);

-- Ventas
INSERT INTO venta (id_venta,id_factura,id_producto,precio,cantidad) VALUES
(1,1,5,45000,3),
(2,1,9,15780,2),
(3,1,10,15000,3),
(4,2,5,45000,1),
(5,2,14,154000,3),
(6,2,9,15780,3),
(7,3,14,154000,1),
(8,3,6,57000,1),
(9,3,15,330000,2),
(10,1,6,57000,2),
(11,1,8,27600,3),
(12,1,9,15780,3),
(13,2,8,27600,3),
(14,2,14,154000,2),
(15,2,3,24000,1),
(16,3,15,330000,1),
(17,3,12,45000,1),
(18,3,10,15000,3);

-- Roles
INSERT INTO rol (id_rol, nombre, id_usuario) VALUES
 (1,'ROLE_ADMIN',1), (2,'ROLE_VENDEDOR',1), (3,'ROLE_USER',1),
 (4,'ROLE_VENDEDOR',2), (5,'ROLE_USER',2),
 (6,'ROLE_USER',3);




