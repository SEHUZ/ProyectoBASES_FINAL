create DATABASE ClinicaPrivada;
USE ClinicaPrivada;
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
ALTER TABLE CitasEmergencias ADD COLUMN fechaExpiracion DATETIME NOT NULL;
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

-- Transacción para asignación segura de citas
DELIMITER //
CREATE PROCEDURE AgendarCita(IN pacienteID INT, IN medicoID INT, IN fecha DATETIME)
BEGIN
    START TRANSACTION;
    INSERT INTO Citas (idPaciente, idMedico, fechaHora) VALUES (pacienteID, medicoID, fecha);
    COMMIT;
END //
DELIMITER ;




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

SELECT * FROM PACIENTES;

SELECT * FROM USUARIOS WHERE IDUSUARIO = 8;

SELECT * FROM DIRECCIONESPACIENTES WHERE IDPACIENTE = 4;

SELECT * FROM citasnormales;
SELECT * FROM citas;
SELECT * FROM citasEmergencias;

SELECT * FROM HORARIOSMEDICOS;

SELECT * FROM MEDICOS;

DELIMITER $$
CREATE PROCEDURE ObtenerCitasPorMedico(IN p_idMedico INT)
BEGIN
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

CALL ObtenerCitasPorMedico(1);

CALL historialconsultas(1);







-- PROCEDIMIENTOS PARA HISTORIALES
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
            WHEN ce.idCita IS NOT NULL THEN 'Emergencia'
            WHEN cn.idCita IS NOT NULL THEN 'Normal'
            ELSE 'Desconocido'
        END AS tipoCita,
        ce.folio AS folioEmergencia
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
        AND ci.fechaHora > NOW() -- Filtra solo citas futuras
    ORDER BY 
        ci.fechaHora ASC; -- Ordenar por fecha de cita (más próxima primero)
END$$

DELIMITER ;




CALL ObtenerHistorialConsultasPorPaciente(2);

CALL ObtenerCitasProximasPorPaciente(1);








-- CREAR PROCEDIMIENTO PARA REGISTRAR UNA CITA

DELIMITER $$

CREATE PROCEDURE AgendarCita(
    IN p_idPaciente INT,
    IN p_idMedico INT,
    IN p_fechaHora DATETIME,
    IN p_tipo ENUM('EMERGENCIA', 'PROGRAMADA')
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
        AND fechaHora BETWEEN SUBTIME(p_fechaHora, SEC_TO_TIME(v_duracionCita * 60))
                          AND ADDTIME(p_fechaHora, SEC_TO_TIME(v_duracionCita * 60))
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El médico ya tiene una cita en ese horario';
    END IF;
    
    -- 4. Validar disponibilidad del paciente
    IF EXISTS (
        SELECT 1
        FROM Citas
        WHERE idPaciente = p_idPaciente
        AND fechaHora BETWEEN SUBTIME(p_fechaHora, SEC_TO_TIME(v_duracionCita * 60))
                          AND ADDTIME(p_fechaHora, SEC_TO_TIME(v_duracionCita * 60))
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El paciente ya tiene una cita en ese horario';
    END IF;
    
    -- 5. Insertar la cita
    START TRANSACTION;
    
    INSERT INTO Citas (idPaciente, idMedico, fechaHora, tipo, idEstado)
    VALUES (p_idPaciente, p_idMedico, p_fechaHora, p_tipo, 1); -- Estado 1 = Agendada
    
    -- Si es emergencia, generar folio
    IF p_tipo = 'EMERGENCIA' THEN
        INSERT INTO CitasEmergencias (idCita, folio)
        VALUES (LAST_INSERT_ID(), CONCAT('EMER-', LPAD(LAST_INSERT_ID(), 4, '0')));
    ELSE
        INSERT INTO CitasNormales (idCita)
        VALUES (LAST_INSERT_ID());
    END IF;
    
    COMMIT;
END$$

DELIMITER ;

