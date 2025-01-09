-- Create database
CREATE DATABASE computer_repair;

\c computer_repair;

-- Create enum types
CREATE TYPE user_role AS ENUM ('CLIENT', 'STORE');
CREATE TYPE component_type AS ENUM ('CPU', 'RAM', 'STORAGE', 'MOTHERBOARD', 'GPU', 'POWER_SUPPLY', 'COOLING_SYSTEM', 'OTHER');
CREATE TYPE component_status AS ENUM ('FUNCTIONAL', 'DEFECTIVE', 'REPAIRED');
CREATE TYPE computer_status AS ENUM ('FUNCTIONAL', 'NEEDS_REPAIR', 'IN_REPAIR', 'REPAIRED', 'RETURNED');
CREATE TYPE repair_status AS ENUM ('WAITING_DIAGNOSTIC', 'DIAGNOSED', 'IN_REPAIR', 'REPAIRED', 'RETURNED');

-- Create users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role user_role NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create computers table
CREATE TABLE computers (
    id BIGSERIAL PRIMARY KEY,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    serial_number VARCHAR(255) UNIQUE,
    status computer_status NOT NULL DEFAULT 'FUNCTIONAL',
    owner_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (owner_id) REFERENCES users(id)
);

-- Create components table
CREATE TABLE components (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type component_type NOT NULL,
    status component_status NOT NULL DEFAULT 'FUNCTIONAL',
    computer_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (computer_id) REFERENCES computers(id) ON DELETE CASCADE
);

-- Create repairs table
CREATE TABLE repairs (
    id BIGSERIAL PRIMARY KEY,
    computer_id BIGINT NOT NULL,
    date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_diagnosed TIMESTAMP,
    date_completed TIMESTAMP,
    diagnosis TEXT,
    status repair_status NOT NULL DEFAULT 'WAITING_DIAGNOSTIC',
    cost DECIMAL(10,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (computer_id) REFERENCES computers(id)
);

-- Create repair_components junction table
CREATE TABLE repair_components (
    repair_id BIGINT NOT NULL,
    component_id BIGINT NOT NULL,
    PRIMARY KEY (repair_id, component_id),
    FOREIGN KEY (repair_id) REFERENCES repairs(id),
    FOREIGN KEY (component_id) REFERENCES components(id)
);

-- Create indexes
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_computers_owner ON computers(owner_id);
CREATE INDEX idx_computers_status ON computers(status);
CREATE INDEX idx_components_computer ON components(computer_id);
CREATE INDEX idx_components_status ON components(status);
CREATE INDEX idx_repairs_computer ON repairs(computer_id);
CREATE INDEX idx_repairs_status ON repairs(status);

-- Insert sample data
INSERT INTO users (name, email, password, role) VALUES
('John Doe', 'john@example.com', 'password123', 'CLIENT'),
('Tech Store', 'store@repair.com', 'store123', 'STORE');

INSERT INTO computers (brand, model, serial_number, status, owner_id) VALUES
('Dell', 'XPS 15', 'DL001', 'FUNCTIONAL', 1),
('HP', 'Pavilion', 'HP001', 'NEEDS_REPAIR', 1);

INSERT INTO components (name, type, status, computer_id) VALUES
('Intel i7', 'CPU', 'FUNCTIONAL', 1),
('Kingston 16GB', 'RAM', 'DEFECTIVE', 1),
('Samsung 1TB', 'STORAGE', 'FUNCTIONAL', 2),
('NVIDIA RTX 3060', 'GPU', 'DEFECTIVE', 2);