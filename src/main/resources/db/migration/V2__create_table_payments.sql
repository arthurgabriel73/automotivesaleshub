CREATE TABLE payments
(
    payment_id UUID PRIMARY KEY,
    status     VARCHAR(50)              NOT NULL,
    "order"    UUID                     NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE
);
