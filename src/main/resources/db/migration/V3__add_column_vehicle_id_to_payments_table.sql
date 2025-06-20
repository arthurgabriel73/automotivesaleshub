ALTER TABLE payments
    ADD COLUMN vehicle_id UUID NOT NULL
        CONSTRAINT fk_vehicle_id REFERENCES vehicles (vehicle_id);
