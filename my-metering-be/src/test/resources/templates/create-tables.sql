-- Create tables
-- Table: customers
DROP TABLE IF EXISTS customers CASCADE;
CREATE TABLE IF NOT EXISTS customers (
    customer_id BIGINT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(255)
);

-- Table: metering_points
DROP TABLE IF EXISTS metering_points CASCADE;
CREATE TABLE IF NOT EXISTS metering_points (
    metering_point_id BIGINT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    address VARCHAR(255),
    CONSTRAINT fk_customer
        FOREIGN KEY (customer_id)
            REFERENCES customers (customer_id)
            ON DELETE CASCADE
);

-- Table: consumptions
DROP TABLE IF EXISTS consumption CASCADE;
CREATE TABLE IF NOT EXISTS consumption (
    consumption_id BIGINT PRIMARY KEY,
    metering_point_id BIGINT NOT NULL,
    amount NUMERIC,
    amount_unit VARCHAR(255),
    consumption_time TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_metering_point
        FOREIGN KEY (metering_point_id)
            REFERENCES metering_points (metering_point_id)
            ON DELETE CASCADE
);

