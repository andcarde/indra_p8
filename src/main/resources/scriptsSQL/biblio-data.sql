USE biblioteca;

-- ==========================================
-- Limpiar datos existentes (reinicio desde 0)
-- ==========================================
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE multas;
TRUNCATE TABLE prestamos;
TRUNCATE TABLE copias;
TRUNCATE TABLE libros;
TRUNCATE TABLE lectores;
TRUNCATE TABLE autores;

SET FOREIGN_KEY_CHECKS = 1;

-- ==========================
-- Datos de autores
-- ==========================
INSERT INTO autores (id, nombre, nacionalidad, fecha_nac) VALUES
(1, 'Gabriel García Márquez', 'Colombiana', '1927-03-06'),
(2, 'Jane Austen', 'Inglesa', '1775-12-16'),
(3, 'Pablo Neruda', 'Chilena', '1904-07-12'),
(4, 'Haruki Murakami', 'Japonesa', '1949-01-12'),
(5, 'Isabel Allende', 'Chilena', '1942-08-02'),
(6, 'Miguel de Cervantes', 'Española', '1547-09-29'),
(7, 'William Shakespeare', 'Inglesa', '1564-04-26'),
(8, 'Michel de Montaigne', 'Francesa', '1533-02-28'),
(9, 'Autor Desconocido', 'Desconocida', NULL);

-- ==========================
-- Datos de lectores
-- ==========================
INSERT INTO lectores (id, nombre, direccion, telefono) VALUES
(1, 'Carlos López', 'Calle Falsa 123', '555-1234'),
(2, 'María Fernández', 'Av. Siempre Viva 742', '555-5678'),
(3, 'Ana Gómez', 'Calle Luna 45', '555-8765'),
(4, 'Luis Pérez', 'Plaza Mayor 1', '555-1111'),
(5, 'Elena Ruiz', 'Calle Sol 9', '555-2222'),
(6, 'Jorge Martín', 'Av. del Mar 33', '555-3333'),
(7, 'Lucía Sánchez', 'Calle Río 7', '555-4444');

-- ==========================
-- Datos de libros
-- ==========================
INSERT INTO libros (id, titulo, tipo, anyo, editorial, id_autor) VALUES
(1, 'Cien años de soledad', 'NOVELA', 1967, 'Editorial Sudamericana', 1),
(2, 'Orgullo y Prejuicio', 'NOVELA', 1813, 'T. Egerton', 2),
(3, 'Veinte poemas de amor y una canción desesperada', 'POESIA', 1924, 'Nerbada', 3),
(4, 'Tokio blues', 'NOVELA', 1987, 'Kodansha', 4),
(5, 'La casa de los espíritus', 'NOVELA', 1982, 'Editorial Sudamericana', 5),
(6, 'Don Quijote de la Mancha', 'NOVELA', 1605, 'Francisco de Robles', 6),
(7, 'Hamlet', 'TEATRO', 1603, 'N. Ling', 7),
(8, 'Ensayos', 'ENSAYO', 1580, 'Simon Millanges', 8),
(9, 'Residencia en la tierra', 'POESIA', 1935, 'Editorial Cruz del Sur', 3),
(10, 'Kafka en la orilla', 'NOVELA', 2002, 'Shinchosha', 4),
(11, 'Antología anónima', 'ENSAYO', 2020, 'Editorial Desconocida', 9); -- autor sin fecha_nac

-- ==========================
-- Datos de copias
-- ==========================
INSERT INTO copias (id, id_libro, estado) VALUES
(1, 1, 'BIBLIOTECA'),
(2, 1, 'PRESTADO'),
(3, 2, 'BIBLIOTECA'),
(4, 3, 'REPARACION'),
(5, 2, 'PRESTADO'),
(6, 2, 'RETRASO'),
(7, 3, 'BIBLIOTECA'),
(8, 4, 'BIBLIOTECA'),
(9, 4, 'PRESTADO'),
(10, 5, 'BIBLIOTECA'),
(11, 5, 'REPARACION'),
(12, 6, 'BIBLIOTECA'),
(13, 7, 'BIBLIOTECA'),
(14, 8, 'BIBLIOTECA'),
(15, 9, 'BIBLIOTECA'),
(16, 10, 'BIBLIOTECA'),
(17, 11, 'BIBLIOTECA');

-- ==========================
-- Datos de préstamos
-- ==========================
INSERT INTO prestamos (id, fecha_retiro, fecha_devolucion, copia_id, lector_id) VALUES
-- Préstamos originales
(1, '2025-11-20', '2025-12-03', 2, 1),
(2, '2025-11-25', '2025-12-05', 3, 2),

-- Préstamos adicionales
(3, '2025-11-30', NULL, 5, 3),          -- activo, copia 5 PRESTADO
(4, '2025-11-15', '2025-11-20', 1, 2),  -- histórico
(5, '2025-11-10', '2025-11-25', 7, 1),  -- histórico
(6, '2025-11-01', '2025-11-10', 8, 4),  -- histórico lector 4
(7, '2025-11-01', NULL, 6, 5),          -- activo y en retraso (copia 6 RETRASO)
(8, '2024-10-10', '2024-10-20', 12, 4), -- histórico año anterior
(9, '2023-05-05', '2023-05-18', 13, 6), -- histórico antiguo
(10, '2025-12-01', NULL, 9, 7);         -- préstamo activo reciente

-- ==========================
-- Datos de multas
-- ==========================
-- Relación 1:1 Lector <-> Multa (una multa por lector)
INSERT INTO multas (id, f_inicio, f_fin, id_lector) VALUES
(1, '2025-12-04', '2025-12-10', 1),       -- multa actual lector 1
(2, '2025-11-01', '2025-11-05', 2),       -- multa pasada lector 2
(3, '2025-12-03', '2025-12-20', 3),       -- multa activa/larga lector 3
(4, '2025-10-10', '2025-10-25', 4),       -- multa expirada lector 4
(5, '2025-09-15', '2025-09-30', 5);       -- multa antigua lector 5
