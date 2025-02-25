create DATABASE ClinicaPrivada2;
USE ClinicaPrivada2;
-- Creación de la tabla Usuarios
CREATE TABLE Usuarios (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    User VARCHAR(100) UNIQUE NOT NULL,
    contrasenia VARCHAR(255) NOT NULL, -- Se aumentó la longitud para mayor seguridad
    rol ENUM('médico', 'paciente') NOT NULL
);

-- Creación de la tabla Médicos
CREATE TABLE Medicos (
    idMedico INT PRIMARY KEY AUTO_INCREMENT,
    idUsuario INT NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    activo BOOLEAN NOT NULL,
    apellidoPaterno VARCHAR(100) NOT NULL,
    apellidoMaterno VARCHAR(100),
    cedula VARCHAR(50) UNIQUE NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    FOREIGN KEY (idUsuario) REFERENCES Usuarios(idUsuario)
);

-- Creación de la tabla Pacientes
CREATE TABLE Pacientes (
    idPaciente INT PRIMARY KEY AUTO_INCREMENT,
    idUsuario INT NOT NULL,
    email VARCHAR(100) NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidoPaterno VARCHAR(100) NOT NULL,
    apellidoMaterno VARCHAR(100),
    fechaNacimiento DATE NOT NULL,
    telefono VARCHAR(10) NOT NULL,
    FOREIGN KEY (idUsuario) REFERENCES Usuarios(idUsuario)
);

-- Creación de la tabla DireccionesPacientes
CREATE TABLE DireccionesPacientes (
    idDireccion INT PRIMARY KEY AUTO_INCREMENT,
    idPaciente INT NOT NULL UNIQUE,
    calle VARCHAR(50) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    codigoPostal VARCHAR(10) NOT NULL,
    FOREIGN KEY (idPaciente) REFERENCES Pacientes(idPaciente)
);

-- Creación de la tabla HorariosMedicos
CREATE TABLE HorariosMedicos (
    idHorario INT PRIMARY KEY AUTO_INCREMENT,
    idMedico INT NOT NULL,
    horaEntrada TIME NOT NULL,
    horaSalida TIME NOT NULL,
    diaSemana ENUM('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY') NOT NULL,
    FOREIGN KEY (idMedico) REFERENCES Medicos(idMedico)
);

-- Creación de la tabla Estados de Citas
CREATE TABLE EstadosCita (
    idEstado INT PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO EstadosCita (descripcion) VALUES ('Agendada'), ('Cancelada'), ('No asistió paciente'), ('No atendida');

-- Creación de la tabla Citas
CREATE TABLE Citas (
    idCita INT PRIMARY KEY AUTO_INCREMENT,
    idPaciente INT NOT NULL,
    idMedico INT NOT NULL,
    idEstado INT NOT NULL DEFAULT 1, -- 'Agendada' por defecto
    tipo ENUM('EMERGENCIA', 'PROGRAMADA') NOT NULL,
    fechaHora DATETIME NOT NULL,
    FOREIGN KEY (idPaciente) REFERENCES Pacientes(idPaciente),
    FOREIGN KEY (idMedico) REFERENCES Medicos(idMedico),
    FOREIGN KEY (idEstado) REFERENCES EstadosCita(idEstado)
);


-- Creación de la tabla CitasEmergencias
CREATE TABLE CitasEmergencias (
    idCitaEmergencia INT PRIMARY KEY AUTO_INCREMENT,
    idCita INT NOT NULL UNIQUE,
    folio VARCHAR(8) NOT NULL,
    fechaExpiracion DATETIME NOT NULL,
    FOREIGN KEY (idCita) REFERENCES Citas(idCita)
);

-- Creación de la tabla CitasNormales
CREATE TABLE CitasNormales (
    idCitaNormal INT PRIMARY KEY AUTO_INCREMENT,
    idCita INT NOT NULL UNIQUE,
    FOREIGN KEY (idCita) REFERENCES Citas(idCita)
);

-- Creación de la tabla Consultas
CREATE TABLE Consultas (
    idConsulta INT PRIMARY KEY AUTO_INCREMENT,
    diagnostico VARCHAR(300) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    fechaHora DATETIME NOT NULL,
    tratamiento VARCHAR(300),
    idCita INT NOT NULL,
    FOREIGN KEY (idCita) REFERENCES Citas(idCita)
);

-- Creación de la tabla Auditorias
CREATE TABLE Auditorias (
    idAuditoria INT PRIMARY KEY AUTO_INCREMENT,
    tipoOperacion VARCHAR(300),
    fechaHora DATETIME NOT NULL,
    detalles VARCHAR(300),
    idCita INT NOT NULL,
    FOREIGN KEY (idCita) REFERENCES Citas(idCita)
);

-- Trigger para evitar modificar datos de pacientes con citas activas
DELIMITER //
CREATE TRIGGER BeforeUpdatePaciente BEFORE UPDATE ON Pacientes
FOR EACH ROW
BEGIN
    IF (SELECT COUNT(*) FROM Citas WHERE idPaciente = OLD.idPaciente AND idEstado = 1) > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se pueden modificar datos con citas activas';
    END IF;
END //
DELIMITER ;

-- Trigger para cambiar el estado de citas no atendidas después de 10 minutos
DELIMITER //
CREATE TRIGGER AfterCitaEmergencia BEFORE UPDATE ON CitasEmergencias
FOR EACH ROW
BEGIN
    IF NOW() > OLD.fechaExpiracion THEN
        UPDATE Citas SET idEstado = (SELECT idEstado FROM EstadosCita WHERE descripcion = 'No atendida')
        WHERE idCita = OLD.idCita;
    END IF;
END //
DELIMITER ;

-- Procedimiento almacenado para cancelar cita con validación de 24 horas
DELIMITER //
CREATE PROCEDURE CancelarCita(IN citaID INT)
BEGIN
    DECLARE citaFecha DATETIME;
    SELECT fechaHora INTO citaFecha FROM Citas WHERE idCita = citaID;
    
    IF TIMESTAMPDIFF(HOUR, NOW(), citaFecha) < 24 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Las citas solo pueden cancelarse con 24 horas de anticipación';
    ELSE
        UPDATE Citas SET idEstado = (SELECT idEstado FROM EstadosCita WHERE descripcion = 'Cancelada') WHERE idCita = citaID;
    END IF;
END //
DELIMITER ;


-- Vista para historial de consultas filtrado por fecha y especialidad
CREATE VIEW HistorialConsultas AS
SELECT Citas.idPaciente, Citas.fechaHora, Medicos.especialidad, Consultas.diagnostico, Consultas.tratamiento
FROM Consultas
JOIN Citas ON Consultas.idCita = Citas.idCita
JOIN Medicos ON Citas.idMedico = Medicos.idMedico;


INSERT INTO usuarios (User, contrasenia, rol) VALUES 
("elAbrahama", "password123", "médico"),
("raulMaster", "unicornio123", "paciente"),
("sebas", "diamantepuro", "paciente"),
("drMengueche", "perrochilo", "medico");

INSERT INTO medicos (idUsuario, nombres, apellidoPaterno, apellidoMaterno, cedula, especialidad, activo) VALUES 
(1, "Abraham", "Johnson", "Bringas", "1231456788", "Cardiologia", true),
(4, "Peter", "Peterson", "Patin", "79456988", "Urologia", true);

INSERT INTO pacientes (idUsuario, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, email) VALUES
(2, "Jaime Eduardo", "Lerma", "Cuevas", "2000-06-19", "6442595242", "jaime@gmail.com"),
(3, 'María', 'Pérez', 'Ramírez', '1990-05-15', '5551234567', "maria@hotmail.com");

INSERT INTO DireccionesPacientes (idPaciente, Calle, Numero, codigoPostal) VALUES 
(1, "Calle Principal", "3210", "85150"),
(2, "Calle Guerrero", "2008", "80138");

INSERT INTO HorariosMedicos (idMedico, horaEntrada, horaSalida, diaSemana)VALUES 
(1, '09:00:00', '17:00:00', "MONDAY"),
(1, '09:00:00', '17:00:00', "TUESDAY"),
(1, '09:00:00', '17:00:00', "WEDNESDAY"),
(1, '09:00:00', '17:00:00', "THURSDAY"),
(1, '09:00:00', '17:00:00', "FRIDAY");

INSERT INTO HorariosMedicos (idMedico, horaEntrada, horaSalida, diaSemana) VALUES 
(2, '07:00:00', '15:00:00', 'SATURDAY'),
(2, '07:00:00', '15:00:00', 'SUNDAY');
INSERT INTO EstadosCita (descripcion) VALUES ('Pendiente'), ('Confirmada');

INSERT INTO Citas (idPaciente, idMedico, idEstado, FechaHora)  VALUES  
(1, 1, (SELECT idEstado FROM EstadosCita WHERE descripcion = 'Pendiente'), '2025-02-20 10:30:00'), 
(1, 1, (SELECT idEstado FROM EstadosCita WHERE descripcion = 'Confirmada'), '2025-02-21 15:00:00'),
(1, 1, (SELECT idEstado FROM EstadosCita WHERE descripcion = 'Pendiente'), '2025-02-28 10:30:00');

INSERT INTO Citas (idPaciente, idMedico, idEstado, FechaHora)  VALUES  
(1, 1, (SELECT idEstado FROM EstadosCita WHERE descripcion = 'Pendiente'), '2025-02-28 10:30:00');

select * from citasEmergencias;
INSERT INTO CitasEmergencias (idCita, folio,fechaExpiracion) VALUES 
(3, "18673962",current_date());

INSERT INTO CitasNormales (idCita) VALUES 
(2);

INSERT INTO Consultas (Diagnostico, Estado, FechaHora, Tratamiento, idCita)
VALUES ("Hipertensión", "En progreso", "2025-02-20 11:00:00", "Recomendación de dieta baja en sal", 2);



DELIMITER $$
CREATE PROCEDURE ObtenerCitasPorMedico(IN p_idMedico INT)
BEGIN
	IF NOT EXISTS (SELECT 1 FROM Medicos WHERE idMedico = p_idMedico) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El médico especificado no existe.';
    END IF;
    SELECT 
        c.idCita,
        c.fechaHora,
        p.idPaciente,
        p.nombres AS nombrePaciente,
        p.apellidoPaterno AS apellidoPaternoPaciente,
        p.apellidoMaterno AS apellidoMaternoPaciente,
        m.idMedico,
        m.nombres AS nombreMedico,
        m.apellidoPaterno AS apellidoPaternoMedico,
        m.apellidoMaterno AS apellidoMaternoMedico,
        ec.descripcion AS estadoCita,
        ec.idEstado
    FROM Citas c
    JOIN Pacientes p ON c.idPaciente = p.idPaciente
    JOIN Medicos m ON c.idMedico = m.idMedico
    JOIN EstadosCita ec ON c.idEstado = ec.idEstado
    LEFT JOIN CitasEmergencias ce ON c.idCita = ce.idCita
	LEFT JOIN CitasNormales cn ON c.idCita = cn.idCita
    WHERE m.idMedico = p_idMedico;
END$$

DELIMITER ;


-- PROCEDIMIENTOS PARA HISTORIALES -- --------------------------------------------------------------------------------------------------------
DELIMITER $$

CREATE PROCEDURE ObtenerHistorialConsultasPorPaciente(
    IN idPacienteParam INT
)
BEGIN
    -- Verificar si el paciente existe
    IF NOT EXISTS (SELECT 1 FROM Pacientes WHERE idPaciente = idPacienteParam) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El paciente no existe';
    END IF;

    -- Obtener el historial de consultas del paciente
    SELECT 
        c.idConsulta,
        c.diagnostico,
        c.estado,
        c.fechaHora AS fechaHoraConsulta,
        c.tratamiento,
        ci.fechaHora AS fechaHoraCita,
        m.nombres AS nombreMedico,
        m.apellidoPaterno AS apellidoMedico,
        m.especialidad
    FROM 
        Consultas c
    INNER JOIN 
        Citas ci ON c.idCita = ci.idCita
    INNER JOIN 
        Medicos m ON ci.idMedico = m.idMedico
    WHERE 
        ci.idPaciente = idPacienteParam
    ORDER BY 
        c.fechaHora DESC; -- Ordenar por fecha de consulta (más reciente primero)
END$$

DELIMITER ;

-- --------------------------------------------------------------------------------------------------------
DELIMITER $$

CREATE PROCEDURE ObtenerCitasProximasPorPaciente(
    IN idPacienteParam INT
)
BEGIN
    -- Verificar si el paciente existe
    IF NOT EXISTS (SELECT 1 FROM Pacientes WHERE idPaciente = idPacienteParam) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El paciente no existe';
    END IF;

    -- Obtener las citas próximas del paciente
    SELECT 
        ci.idCita,
        ci.fechaHora AS fechaHoraCita,
        m.nombres AS nombreMedico,
        m.apellidoPaterno AS apellidoMedico,
        m.especialidad,
        ec.descripcion AS estadoCita,
        CASE 
            WHEN ce.idCita IS NOT NULL THEN 'EMERGENCIA'
            WHEN cn.idCita IS NOT NULL THEN 'PROGRAMADA'
            ELSE 'Desconocido'
        END AS tipoCita,
        ce.folio AS folioEmergencia,
        ce.fechaExpiracion AS fechaExpiracionEmergencia -- Campo agregado
    FROM 
        Citas ci
    INNER JOIN 
        Medicos m ON ci.idMedico = m.idMedico
    INNER JOIN 
        EstadosCita ec ON ci.idEstado = ec.idEstado
    LEFT JOIN 
        CitasEmergencias ce ON ci.idCita = ce.idCita
    LEFT JOIN 
        CitasNormales cn ON ci.idCita = cn.idCita
    WHERE 
        ci.idPaciente = idPacienteParam
        AND ci.fechaHora > NOW()
    ORDER BY 
        ci.fechaHora ASC;
END$$

-- --------------------------------------------------------------------------------------------------------


DROP PROCEDURE IF EXISTS AgendarCita;
-- CREAR PROCEDIMIENTO PARA REGISTRAR UNA CITA


DELIMITER $$

CREATE PROCEDURE AgendarCita(
    IN p_idPaciente INT,
    IN p_idMedico INT,
    IN p_fechaHora DATETIME,
    IN p_tipo ENUM('EMERGENCIA', 'PROGRAMADA'),
    OUT p_folio VARCHAR(8),
    OUT p_idCita INT
)
BEGIN
    DECLARE v_diaSemana VARCHAR(10);
    DECLARE v_horaCita TIME;
    DECLARE v_horaEntrada TIME;
    DECLARE v_horaSalida TIME;
    DECLARE v_duracionCita INT DEFAULT 30; -- Duración mínima entre citas en minutos
    DECLARE v_horaFinCita TIME;
    
    -- Obtener día de la semana y hora de la cita
    SET v_diaSemana = DAYNAME(p_fechaHora);
    SET v_horaCita = TIME(p_fechaHora);
    
    -- 1. Validar horario médico
    SELECT horaEntrada, horaSalida INTO v_horaEntrada, v_horaSalida
    FROM HorariosMedicos
    WHERE idMedico = p_idMedico
    AND diaSemana = v_diaSemana;
    
    IF v_horaEntrada IS NULL THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El médico no tiene horario para este día';
    END IF;
    
    -- Calcular hora de fin de cita
    SET v_horaFinCita = ADDTIME(v_horaCita, SEC_TO_TIME(v_duracionCita * 60));
    
    -- 2. Validar que la cita esté dentro del horario laboral
    IF v_horaCita < v_horaEntrada OR v_horaFinCita > v_horaSalida THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La cita está fuera del horario del médico';
    END IF;
    
    -- 3. Validar disponibilidad del médico
    IF EXISTS (
        SELECT 1
        FROM Citas
        WHERE idMedico = p_idMedico
        AND fechaHora BETWEEN SUBTIME(p_fechaHora, SEC_TO_TIME(v_duracionCita * 30))
                          AND ADDTIME(p_fechaHora, SEC_TO_TIME(v_duracionCita * 30))
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El médico ya tiene una cita en ese horario';
    END IF;
    
    -- 4. Validar disponibilidad del paciente
    IF EXISTS (
        SELECT 1
        FROM Citas
        WHERE idPaciente = p_idPaciente
        AND fechaHora BETWEEN SUBTIME(p_fechaHora, SEC_TO_TIME(v_duracionCita * 30))
                          AND ADDTIME(p_fechaHora, SEC_TO_TIME(v_duracionCita * 30))
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El paciente ya tiene una cita en ese horario';
    END IF;
    
    IF (SELECT activo FROM Medicos WHERE idMedico = p_idMedico) = 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Médico inactivo';
END IF;
    
    -- 5. Insertar la cita
    START TRANSACTION;
    
    -- Insertar en Citas
    INSERT INTO Citas (idPaciente, idMedico, fechaHora, tipo, idEstado)
    VALUES (p_idPaciente, p_idMedico, p_fechaHora, 'PROGRAMADA', 1);

    SET p_idCita = LAST_INSERT_ID();

    INSERT INTO CitasNormales (idCita) VALUES (p_idCita);
    
    COMMIT;
END$$

DELIMITER ;


-- -----------------------------------------------------------------------------------------------------
-- CREACION DEL PROCEDIMIENTO DE AGENDAR CITA DE EMERGENCIA
DELIMITER $$

CREATE PROCEDURE AgendarCitaEmergencia(
    IN  p_idPaciente INT,
    OUT p_folio VARCHAR(8),
    OUT p_idCita INT,
    OUT p_fechaExpiracion DATETIME
)
BEGIN
    DECLARE v_idMedico INT;
    DECLARE v_diaSemana VARCHAR(10);
    DECLARE v_horaActual TIME;
    DECLARE v_numAleatorio INT;

    -- 1. Validar que el paciente exista
    IF (SELECT COUNT(*) FROM Pacientes WHERE idPaciente = p_idPaciente) = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El paciente no existe en el sistema.';
    END IF;

    -- 2. Determinar día y hora actuales
    SET v_diaSemana  = DAYNAME(NOW());
    SET v_horaActual = TIME(NOW());

    -- 3. Buscar el primer médico activo que esté disponible
    SELECT h.idMedico
      INTO v_idMedico
      FROM HorariosMedicos h
      JOIN Medicos m ON m.idMedico = h.idMedico
     WHERE m.activo = 1
       AND h.diaSemana = v_diaSemana
       AND v_horaActual BETWEEN h.horaEntrada AND h.horaSalida
     ORDER BY h.idMedico
     LIMIT 1;

    IF v_idMedico IS NULL THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No hay médicos disponibles en este momento.';
    END IF;

    -- 4. Generar folio de 8 dígitos
    SET v_numAleatorio = FLOOR(RAND() * 100000000);
    SET p_folio        = LPAD(v_numAleatorio, 8, '0');

    -- 5. Insertar la cita como EMERGENCIA
    START TRANSACTION;

        INSERT INTO Citas (idPaciente, idMedico, fechaHora, tipo, idEstado)
        VALUES (p_idPaciente, v_idMedico, NOW(), 'EMERGENCIA', 1);

        SET p_idCita = LAST_INSERT_ID();

        -- Calcular fecha de expiración (+10 minutos)
        SET p_fechaExpiracion = DATE_ADD(NOW(), INTERVAL 10 MINUTE);

        INSERT INTO CitasEmergencias (idCita, folio, fechaExpiracion)
        VALUES (p_idCita, p_folio, p_fechaExpiracion);

    COMMIT;
END$$

DELIMITER ;

-- ---------------------------------------------------------------------------------------------------------
-- Trigger para evitar duplicación de consultas en la misma cita
DELIMITER $$
CREATE TRIGGER before_insert_consulta
BEFORE INSERT ON Consultas
FOR EACH ROW
BEGIN
    DECLARE consulta_count INT;
    SELECT COUNT(*) INTO consulta_count FROM Consultas WHERE idCita = NEW.idCita;
    IF consulta_count > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Ya existe una consulta para esta cita';
    END IF;
END $$
DELIMITER ;

-- ============================
-- PROCEDIMIENTOS ALMACENADOS
-- ============================

-- Procedimiento para insertar una consulta
DELIMITER $$
CREATE PROCEDURE InsertarConsulta(
    IN p_diagnostico TEXT,
    IN p_estado VARCHAR(20),
    IN p_fechaHora DATETIME,
    IN p_tratamiento TEXT,
    IN p_idCita INT,
    OUT p_idConsulta INT
)
BEGIN
    INSERT INTO Consultas (diagnostico, estado, fechaHora, tratamiento, idCita)
    VALUES (p_diagnostico, p_estado, p_fechaHora, p_tratamiento, p_idCita);
    SET p_idConsulta = LAST_INSERT_ID();
END $$
DELIMITER ;

-- Procedimiento para obtener consultas por paciente
DELIMITER $$
CREATE PROCEDURE ObtenerConsultasPorPaciente(IN p_idPaciente INT)
BEGIN
    SELECT c.* FROM Consultas c
    JOIN Citas ci ON c.idCita = ci.idCita
    WHERE ci.idPaciente = p_idPaciente;
END $$
DELIMITER ;

-- Procedimiento para actualizar una consulta
DELIMITER $$
CREATE PROCEDURE ActualizarConsulta(
    IN p_idConsulta INT,
    IN p_diagnostico TEXT,
    IN p_estado VARCHAR(20),
    IN p_fechaHora DATETIME,
    IN p_tratamiento TEXT
)
BEGIN
    UPDATE Consultas SET 
        diagnostico = p_diagnostico,
        estado = p_estado,
        fechaHora = p_fechaHora,
        tratamiento = p_tratamiento
    WHERE idConsulta = p_idConsulta;
END $$
DELIMITER ;

-- Procedimiento para cancelar una consulta
DELIMITER $$
CREATE PROCEDURE CancelarConsulta(IN p_idConsulta INT)
BEGIN
    UPDATE Consultas SET estado = 'Cancelada' WHERE idConsulta = p_idConsulta;
END $$
DELIMITER ;

-- Procedimiento para verificar si existe consulta para una cita
DELIMITER $$
CREATE PROCEDURE ExisteConsultaParaCita(IN p_idCita INT, OUT p_existe INT)
BEGIN
    SELECT COUNT(*) INTO p_existe FROM Consultas WHERE idCita = p_idCita;
END $$
DELIMITER ;

SELECT * FROM MEDICOS;
SELECT * FROM USUARIOS;

SELECT * FROM CITAS;
SELECT * FROM CITASNORMALES;
SELECT * FROM ESTADOSCITA;

