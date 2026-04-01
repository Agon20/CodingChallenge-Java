CREATE TABLE IF NOT EXISTS insurance_application (
    id                  BIGSERIAL PRIMARY KEY,
    postal_code         VARCHAR(10)      NOT NULL,
    annual_mileage      INTEGER          NOT NULL,
    vehicle_type_factor VARCHAR(20)      NOT NULL,
    premium             DOUBLE PRECISION NOT NULL,
    created_date        TIMESTAMP        NOT NULL
);