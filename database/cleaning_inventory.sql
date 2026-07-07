--Tables in DB
--User table
CREATE TABLE users (
    user_id       INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username      VARCHAR(100) UNIQUE NOT NULL,
    password      VARCHAR(255) NOT NULL,
    email         VARCHAR(100) UNIQUE NOT NULL,
    full_name     VARCHAR(100),
    role          VARCHAR(20) NOT NULL
);

--Supplier Table
CREATE TABLE suppliers (
    supplier_id     INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    contact_person  VARCHAR(100),
    phone           VARCHAR(10),
    email           VARCHAR(100),
    address         VARCHAR(255)
);

-- Materials table
CREATE TABLE materials (
    material_id    INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name           VARCHAR(50) NOT NULL,
    description    VARCHAR(255),
    quantity       INT NOT NULL DEFAULT 0,
    reorder_level  INT NOT NULL DEFAULT 0,
    unit           VARCHAR(20),
    supplier_id    INT REFERENCES suppliers(supplier_id),
    CONSTRAINT chk_quantity_notneg CHECK (quantity >= 0), -- Prevents negative stock values.
    CONSTRAINT chk_reorder_notneg CHECK (reorder_level >= 0) -- Prevents negative reorder level values.
);

--Cleaners Table
CREATE TABLE cleaners (
    cleaner_id      INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name            VARCHAR(20) NOT NULL,
    department      VARCHAR(50),
    contact_number  VARCHAR(20)
);

--Issuances Table
CREATE TABLE issuances (
    issuance_id       INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    material_id       INT NOT NULL REFERENCES materials(material_id),
    cleaner_id        INT NOT NULL REFERENCES cleaners(cleaner_id),
    issued_by         INT NOT NULL REFERENCES users(user_id),
    quantity_issued   INT NOT NULL,
    issue_date        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_qty_issued_positive CHECK (quantity_issued > 0) --checks that the value entered for quantity_issued is not a negative number or a zero
);