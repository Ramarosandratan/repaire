CREATE TABLE computer (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(255),
    model VARCHAR(255),
    serialNumber VARCHAR(255)
);

CREATE TABLE component (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    type VARCHAR(255),
    serialNumber VARCHAR(255),
    status VARCHAR(255),
    computer_id INTEGER,
    CONSTRAINT fk_computer FOREIGN KEY(computer_id) REFERENCES computer(id)
);

CREATE TABLE repair (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255),
    status VARCHAR(255),
    dateCreated TIMESTAMP,
    dateCompleted TIMESTAMP,
    cost NUMERIC,
    computer_id INTEGER,
    CONSTRAINT fk_computer FOREIGN KEY(computer_id) REFERENCES computer(id)
);

CREATE TABLE repair_components (
    repair_id INTEGER,
    component_id INTEGER,
    CONSTRAINT fk_repair FOREIGN KEY(repair_id) REFERENCES repair(id),
    CONSTRAINT fk_component FOREIGN KEY(component_id) REFERENCES component(id),
    PRIMARY KEY (repair_id, component_id)
);
