-- ============================================================
-- Datos de ejemplo para la base de datos smartdevices
-- ============================================================

USE smartdevices;

-- ============================================================
-- Tipos de dispositivo
-- ============================================================
INSERT INTO tipo_dispositivo (nombre) VALUES
('Celular'),
('Portatil'),
('Tablet'),
('Smartwatch'),
('Audifonos');

-- ============================================================
-- Marcas
-- ============================================================
INSERT INTO marca (nombre, pais, logo_url) VALUES
('Apple', 'Estados Unidos', 'https://logo.clearbit.com/apple.com'),
('Samsung', 'Corea del Sur', 'https://logo.clearbit.com/samsung.com'),
('Lenovo', 'China', 'https://logo.clearbit.com/lenovo.com'),
('HP', 'Estados Unidos', 'https://logo.clearbit.com/hp.com'),
('Xiaomi', 'China', 'https://logo.clearbit.com/xiaomi.com'),
('Asus', 'Taiwan', 'https://logo.clearbit.com/asus.com'),
('Dell', 'Estados Unidos', 'https://logo.clearbit.com/dell.com'),
('Google', 'Estados Unidos', 'https://logo.clearbit.com/google.com');

-- ============================================================
-- Dispositivos (celulares y portátiles)
-- ============================================================
INSERT INTO dispositivo (marca_id, tipo_id, nombre, fecha_lanzamiento, precio, imagen_url, sinopsis, resena, especificaciones) VALUES
-- Celulares
(1, 1, 'iPhone 15 Pro Max', '2023-09-22', 1199.00,
 'https://images.unsplash.com/photo-1592286927505-1def25115558?w=400',
 'El smartphone más potente de Apple con chip A17 Pro y cámara de 48MP.',
 'El iPhone 15 Pro Max representa la máxima expresión de la tecnología móvil de Apple. Equipado con el revolucionario chip A17 Pro de 3nm, este dispositivo ofrece un rendimiento sin precedentes en juegos y aplicaciones profesionales. Su sistema de cámara triple de 48MP captura imágenes con un nivel de detalle extraordinario, mientras que el nuevo marco de titanio lo hace más ligero y resistente. La pantalla Super Retina XDR de 6.7 pulgadas con ProMotion y Always-On display proporciona una experiencia visual inmersiva. Además, cuenta con puerto USB-C para mayor versatilidad de conexión.',
 'Pantalla: 6.7" Super Retina XDR | Chip: A17 Pro | RAM: 8GB | Almacenamiento: 256GB/512GB/1TB | Cámara: 48MP+12MP+12MP | Batería: 4422mAh | Puerto: USB-C'),

(2, 1, 'Samsung Galaxy S24 Ultra', '2024-01-31', 1299.00,
 'https://images.unsplash.com/photo-1610945265064-0e34e5519bbf?w=400',
 'Galaxy S24 Ultra con S Pen, cámara de 200MP y Galaxy AI integrada.',
 'El Samsung Galaxy S24 Ultra redefine lo que un smartphone puede hacer. Con su pantalla Dynamic AMOLED 2X de 6.8 pulgadas y resolución QHD+, ofrece colores vibrantes y negros profundos. El chip Snapdragon 8 Gen 3 para Galaxy proporciona un rendimiento excepcional, mientras que el sistema de cámara cuádruple liderado por un sensor principal de 200MP captura fotos con claridad sorprendente en cualquier condición de luz. La integración de Galaxy AI permite funciones avanzadas como traducción en tiempo real y edición inteligente de fotos. El S Pen incluido permite tomar notas y dibujar con precisión.',
 'Pantalla: 6.8" Dynamic AMOLED 2X | Chip: Snapdragon 8 Gen 3 | RAM: 12GB | Almacenamiento: 256GB/512GB/1TB | Cámara: 200MP+50MP+12MP+10MP | Batería: 5000mAh | S Pen incluido'),

(5, 1, 'Xiaomi 14 Pro', '2023-11-01', 899.00,
 'https://images.unsplash.com/photo-1598327105666-5b89351aff97?w=400',
 'Xiaomi 14 Pro con cámara Leica y carga rápida de 120W.',
 'El Xiaomi 14 Pro es el resultado de la colaboración entre Xiaomi y Leica, ofreciendo un sistema de cámara profesional en un smartphone. Su pantalla LTPO AMOLED de 6.73 pulgadas con tasa de refresco adaptativa de 1-120Hz y brillo pico de 3000 nits garantiza una visibilidad perfecta incluso bajo la luz solar directa. El chip Snapdragon 8 Gen 3 ofrece un rendimiento de gama superior, y la batería de 4880mAh con carga rápida de 120W permite cargar completamente el dispositivo en menos de 20 minutos. El sistema de cámara triple con óptica Leica captura imágenes con un carácter artístico único.',
 'Pantalla: 6.73" LTPO AMOLED | Chip: Snapdragon 8 Gen 3 | RAM: 12GB/16GB | Almacenamiento: 256GB/512GB | Cámara: 50MP+50MP+50MP Leica | Batería: 4880mAh | Carga: 120W'),

-- Portátiles
(3, 2, 'Lenovo ThinkPad X1 Carbon Gen 11', '2023-03-01', 1899.00,
 'https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=400',
 'Portátil empresarial ultraligero con chip Intel de 13ª generación.',
 'El Lenovo ThinkPad X1 Carbon Gen 11 es el portátil empresarial por excelencia, combinando ligereza extrema con durabilidad militar. Su chasis de fibra de carbono pesa solo 1.12 kg, lo que lo hace ideal para profesionales en movimiento. La pantalla de 14 pulgadas con opciones hasta 2.8K OLED ofrece colores precisos y excelente legibilidad. El teclado legendario de ThinkPad proporciona la mejor experiencia de escritura del mercado, con retroiluminación y el icónico TrackPoint. Con procesadores Intel Core de 13ª generación, hasta 64GB de RAM y conectividad WiFi 6E, este equipo está preparado para las cargas de trabajo más exigentes.',
 'Pantalla: 14" 2.8K OLED | CPU: Intel Core i7-1365U vPro | RAM: 16GB/32GB/64GB | Almacenamiento: 512GB/1TB/2TB SSD | Peso: 1.12kg | Batería: 57Wh | Puertos: 2x Thunderbolt 4, USB-A, HDMI'),

(4, 2, 'HP Spectre x360 14', '2023-02-01', 1499.00,
 'https://images.unsplash.com/photo-1525547719571-a2d4ac8945e2?w=400',
 'Portátil convertible 2-en-1 con pantalla OLED táctil y batería de larga duración.',
 'El HP Spectre x360 14 es un portátil convertible que combina elegancia y potencia. Su diseño de aluminio mecanizado con acabado nocturno y acentos de cobre lo convierte en uno de los portátiles más bellos del mercado. La pantalla OLED táctil de 13.5 pulgadas con resolución 3K2K ofrece negros perfectos y colores vivos. Al ser convertible, se puede usar en modo portátil, tablet, tienda o presentación. El procesador Intel Core de 13ª generación proporciona un rendimiento eficiente, y la batería de hasta 17 horas permite trabajar todo el día sin buscar el cargador. Incluye lapicera HP M2 activa y cámara con obturador de privacidad.',
 'Pantalla: 13.5" 3K2K OLED táctil | CPU: Intel Core i7-1355U | RAM: 16GB/32GB | Almacenamiento: 512GB/1TB SSD | Peso: 1.35kg | Batería: 66Wh | Convertible 360 grados | Lapicera incluida'),

(1, 2, 'MacBook Air M3', '2024-03-08', 1099.00,
 'https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=400',
 'MacBook Air con chip M3 de Apple, ultradelgado y silencioso.',
 'El MacBook Air M3 lleva la legendaria línea de portátiles ultradelgados de Apple a un nuevo nivel. Equipado con el chip M3 de Apple construido con tecnología de 3nm, ofrece un rendimiento y eficiencia energética sin precedentes. Su diseño sin ventilador garantiza un funcionamiento totalmente silencioso, mientras que la batería de hasta 18 horas permite un día completo de trabajo. La pantalla Liquid Retina de 13.6 pulgadas muestra más de mil millones de colores con tecnología True Tone. El chasis de aluminio reciclado pesa solo 1.24 kg, lo que lo hace perfecto para estudiantes y profesionales que se desplazan constantemente.',
 'Pantalla: 13.6" Liquid Retina | Chip: Apple M3 | RAM: 8GB/16GB/24GB | Almacenamiento: 256GB/512GB/1TB SSD | Peso: 1.24kg | Batería: 18h | Puertos: 2x Thunderbolt/USB 4, MagSafe'),

(6, 2, 'ASUS ROG Zephyrus G14', '2023-01-15', 1799.00,
 'https://images.unsplash.com/photo-1603302576837-37561b2e2302?w=400',
 'Portátil gamer ultraportátil con GPU NVIDIA RTX y pantalla 165Hz.',
 'El ASUS ROG Zephyrus G14 revoluciona el mercado de portátiles gamer al combinar potencia de juego con un factor de forma ultraportátil. Su pantalla QHD de 14 pulgadas con tasa de refresco de 165Hz y cobertura 100% DCI-P3 ofrece una experiencia visual fluida y colorida. El procesador AMD Ryzen 9搭配 con la GPU NVIDIA GeForce RTX 4060 permite ejecutar los juegos más exigentes en configuraciones altas. El diseño único con matriz LED AniMe Matrix en la tapa permite personalizar el dispositivo con animaciones. A pesar de su potencia, el sistema de refrigeración液金 mantiene temperaturas óptimas con un ruido mínimo.',
 'Pantalla: 14" QHD 165Hz | CPU: AMD Ryzen 9 7940HS | GPU: NVIDIA RTX 4060 | RAM: 16GB/32GB | Almacenamiento: 1TB SSD | Peso: 1.65kg | Batería: 76Wh'),

(7, 2, 'Dell XPS 15', '2023-06-01', 1599.00,
 'https://images.unsplash.com/photo-1593642632823-8f785ba67e45?w=400',
 'Portátil premium con pantalla InfinityEdge y GPU dedicada NVIDIA.',
 'El Dell XPS 15 es el referente de los portátiles premium para creadores de contenido. Su icónico diseño InfinityEdge reduce los biseles al mínimo, ofreciendo una pantalla de 15.6 pulgadas en un chasis del tamaño de un portátil de 14 pulgadas. La pantalla OLED 3.5K ofrece negros perfectos y una cobertura 100% DCI-P3 ideal para edición de foto y video. El procesador Intel Core i7 de 13ª generación combinado con la GPU NVIDIA RTX 4050 permite ejecutar aplicaciones creativas con fluidez. El teclado con retroiluminación y el panel táctil de cristal de gran tamaño ofrecen una experiencia de uso premium.',
 'Pantalla: 15.6" 3.5K OLED | CPU: Intel Core i7-13700H | GPU: NVIDIA RTX 4050 | RAM: 16GB/32GB | Almacenamiento: 512GB/1TB SSD | Peso: 1.86kg | Batería: 86Wh'),

-- Tablets
(1, 3, 'iPad Pro 12.9 M2', '2022-10-26', 1099.00,
 'https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=400',
 'iPad Pro con chip M2 y pantalla Liquid Retina XDR con mini-LED.',
 'El iPad Pro 12.9 con chip M2 de Apple redefine lo que una tablet puede hacer. Su pantalla Liquid Retina XDR con tecnología mini-LED ofrece un contraste extraordinario con negros profundos y picos de brillo de 1600 nits. El chip M2, el mismo que equipa los Mac de escritorio, proporciona un rendimiento excepcional para edición de video 4K, modelado 3D y gaming. La compatibilidad con Apple Pencil de segunda generación y el Magic Keyboard lo convierten en un dispositivo versátil que puede reemplazar a un portátil para muchas tareas. El sistema de cámara con LiDAR permite aplicaciones de realidad aumentada avanzadas.',
 'Pantalla: 12.9" Liquid Retina XDR mini-LED | Chip: Apple M2 | Almacenamiento: 128GB/256GB/512GB/1TB | Cámara: 12MP+10MP | Conector: USB-C | Compatible con Apple Pencil 2 y Magic Keyboard'),

-- Smartwatch
(1, 4, 'Apple Watch Ultra 2', '2023-09-22', 799.00,
 'https://images.unsplash.com/photo-1551816230-ef5deaed4a26?w=400',
 'Apple Watch Ultra 2 con pantalla más brillante y chip S9.',
 'El Apple Watch Ultra 2 es el smartwatch más resistente y capaz de Apple, diseñado para aventureros y atletas extremos. Su caja de titanio de 49mm es robusta y ligera, resistente al agua hasta 100 metros. La pantalla LTPO OLED retina más brillante de Apple con 3000 nits garantiza visibilidad perfecta incluso bajo el sol directo. El chip S9 SiP permite nuevas funciones como Double Tap, que permite controlar el reloj con un gesto de los dedos. La batería de hasta 72 horas en modo bajo consumo lo hace ideal para expediciones largas. Incluye GPS de doble frecuencia para navegación precisa en cualquier terreno.',
 'Pantalla: 49mm LTPO OLED 3000 nits | Caja: Titanio | Chip: S9 SiP | Resistencia: 100m agua | Batería: 36h normal/72h bajo consumo | GPS doble frecuencia'),

-- Audífonos
(5, 5, 'Xiaomi Redmi Buds 5 Pro', '2023-09-01', 59.00,
 'https://images.unsplash.com/photo-1606220588913-b3aacb4d2f46?w=400',
 'Audífonos inalámbricos con cancelación de ruido de 52dB y audio Hi-Res.',
 'Los Xiaomi Redmi Buds 5 Pro ofrecen características premium a un precio accesible. Con cancelación de ruido activa de hasta 52dB, bloquean eficazmente el ruido del entorno, ideal para viajes y oficinas ruidosas. Los drivers duales de 11mm + 10mm proporcionan un sonido equilibrado con bajos profundos y agudos claros, con certificación Hi-Res Audio Wireless. La batería ofrece hasta 10 horas de reproducción con ANC desactivado y hasta 38 horas con el estuche de carga. El modo de baja latencia de 54ms es perfecto para gaming y visualización de videos sin desincronización.',
 'Tipo: In-ear TWS | ANC: 52dB | Drivers: 11mm+10mm dual | Batería: 10h (38h con estuche) | Bluetooth: 5.3 | Certificación: Hi-Res Audio Wireless | Latencia: 54ms');

-- ============================================================
-- Comentarios de ejemplo
-- ============================================================
INSERT INTO comentario (dispositivo_id, autor, comentario, calificacion) VALUES
(1, 'Carlos Mendez', 'Excelente dispositivo, la cámara es increíble y la batería dura todo el día. El cambio a USB-C fue muy esperado.', 5),
(1, 'Ana Rodriguez', 'Muy bueno pero bastante caro. El rendimiento justifica el precio si lo usas para trabajo profesional.', 4),
(2, 'Diego Torres', 'La Galaxy AI es una maravilla, la traducción en tiempo real funciona perfecto. El S Pen es muy útil para notas.', 5),
(3, 'Maria Lopez', 'La carga rápida de 120W es brutal, en 20 min tengo el celular al 100%. Las fotos con Leica tienen un toque artístico.', 5),
(4, 'Juan Perez', 'El mejor portátil para trabajo que he tenido. Ligero, potente y el teclado es excelente. La batería dura un día completo.', 5),
(5, 'Sofia Garcia', 'Hermoso diseño y la pantalla OLED es espectacular. El modo tablet es muy útil para presentaciones.', 4),
(6, 'Pedro Ramirez', 'Increíble que un portátil tan delgado sea tan potente. La batería de 18 horas es real, me dura todo el día.', 5),
(7, 'Laura Vega', 'Para gaming es excelente, pero se calienta un poco en sesiones largas. La pantalla de 165Hz hace diferencia.', 4),
(8, 'Miguel Castro', 'La pantalla OLED 3.5K es lo mejor, perfecto para edición de fotos. El rendimiento con la RTX 4050 es muy bueno.', 5),
(9, 'Carmen Diaz', 'El chip M2 lo hace potente como un Mac pero con la versatilidad de la tablet. Ideal para diseñadores.', 5);

-- ============================================================
-- Usuario administrador (usuario: admin, password: admin123)
-- NOTA: En producción se debe usar hash de password (BCrypt)
-- ============================================================
INSERT INTO admin_usuario (usuario, password, nombre) VALUES
('admin', 'admin123', 'Administrador del Sistema');
