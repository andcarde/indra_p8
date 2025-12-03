USE `biblioteca`;

-- ==========================
-- Datos de autores
-- ==========================
INSERT INTO `autores` (`id`, `nombre`, `nacionalidad`, `fecha_nac`) VALUES
(1, 'Gabriel García Márquez', 'Colombiana', '1927-03-06'),
(2, 'Jane Austen', 'Inglesa', '1775-12-16'),
(3, 'Pablo Neruda', 'Chilena', '1904-07-12');

-- ==========================
-- Datos de bibliotecarios
-- ==========================
INSERT INTO `bibliotecarios` (`id`, `username`, `password`) VALUES
(1, 'admin1', '$2a$10$hashedpassword1'),
(2, 'admin2', '$2a$10$hashedpassword2');

-- ==========================
-- Datos de lectores
-- ==========================
INSERT INTO `lectores` (`id`, `nombre`, `direccion`, `telefono`) VALUES
(1, 'Carlos López', 'Calle Falsa 123', '555-1234'),
(2, 'María Fernández', 'Av. Siempre Viva 742', '555-5678'),
(3, 'Ana Gómez', 'Calle Luna 45', '555-8765');

-- ==========================
-- Datos de libros
-- ==========================
INSERT INTO `libros` (`id`, `titulo`, `tipo`, `anyo`, `editorial`, `id_autor`) VALUES
(1, 'Cien años de soledad', 'NOVELA', 1967, 'Editorial Sudamericana', 1),
(2, 'Orgullo y Prejuicio', 'NOVELA', 1813, 'T. Egerton', 2),
(3, 'Veinte poemas de amor y una canción desesperada', 'POESIA', 1924, 'Nerbada', 3);

-- ==========================
-- Datos de copias
-- ==========================
INSERT INTO `copias` (`id`, `id_libro`, `estado`) VALUES
(1, 1, 'BIBLIOTECA'),
(2, 1, 'PRESTADO'),
(3, 2, 'BIBLIOTECA'),
(4, 3, 'REPARACION');

-- ==========================
-- Datos de préstamos
-- ==========================
INSERT INTO `prestamos` (`id`, `fecha_retiro`, `fecha_devolucion`, `copia_id`, `lector_id`) VALUES
(1, '2025-11-20', '2025-12-03', 2, 1),
(2, '2025-11-25', '2025-12-05', 3, 2);

-- ==========================
-- Datos de multas
-- ==========================
INSERT INTO `multas` (`id`, `f_inicio`, `f_fin`, `id_lector`) VALUES
(1, '2025-12-04', '2025-12-10', 1);