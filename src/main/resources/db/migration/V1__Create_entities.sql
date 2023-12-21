-- Criação da tabela Customer
CREATE TABLE Customer (
    customerID BIGSERIAL PRIMARY KEY,
    login VARCHAR(124) NOT NULL,
    password VARCHAR(60) NOT NULL,
    lastname VARCHAR(60),
    firstname VARCHAR(60),
    address1 VARCHAR(200),
    address2 VARCHAR(200),
    email VARCHAR(124) NOT NULL,
    phone VARCHAR(24)
);

-- Criação da tabela Vehicle
CREATE TABLE Vehicle (
    VehicleID BIGSERIAL PRIMARY KEY,
    LicensePlate VARCHAR(60) NOT NULL UNIQUE,
    CustomerID BIGINT REFERENCES Customer(CustomerID)
);

-- Criação da tabela ParkingSession
CREATE TABLE ParkingSession (
    sessionID SERIAL PRIMARY KEY,
    startTime TIMESTAMP NOT NULL,
    endTime TIMESTAMP NOT NULL,
    fixedDuration INTEGER,
    variableDuration INTEGER,
    customerID BIGINT REFERENCES Customer(CustomerID),
    vehicleID BIGINT REFERENCES Vehicle(VehicleID)
);

-- Criação da tabela PaymentBySession
CREATE TABLE PaymentBySession (
    Payment VARCHAR(20) NOT NULL,
    SessionID INTEGER REFERENCES ParkingSession(SessionID)
);

-- Criação da tabela OrderStatus
CREATE TABLE OrderStatus (
    orderStatusID SERIAL PRIMARY KEY,
    status VARCHAR(30) UNIQUE NOT NULL
);

-- Criação da tabela Orders
CREATE TABLE Orders (
    orderID SERIAL PRIMARY KEY,
    customerID BIGINT REFERENCES Customer(CustomerID),
    totalAmount NUMERIC(10,2),
    orderStatusID INTEGER REFERENCES OrderStatus(OrderStatusID),
    sessionID INTEGER REFERENCES ParkingSession(SessionID)
);