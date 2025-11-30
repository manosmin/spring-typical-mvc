-- Create rentals table to track book rentals
CREATE TABLE rentals (
    id BIGSERIAL PRIMARY KEY,
    book_id BIGINT NOT NULL,
    owner_id BIGINT NOT NULL,
    renter_id BIGINT NOT NULL,
    rental_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    return_date TIMESTAMP,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    CONSTRAINT fk_rentals_book
        FOREIGN KEY (book_id)
            REFERENCES books(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_rentals_owner
        FOREIGN KEY (owner_id)
            REFERENCES users(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_rentals_renter
        FOREIGN KEY (renter_id)
            REFERENCES users(id)
            ON DELETE CASCADE,
    CONSTRAINT chk_rental_status
        CHECK (status IN ('ACTIVE', 'RETURNED'))
);

-- Create indexes for better query performance
CREATE INDEX idx_rentals_book_id ON rentals(book_id);
CREATE INDEX idx_rentals_owner_id ON rentals(owner_id);
CREATE INDEX idx_rentals_renter_id ON rentals(renter_id);
CREATE INDEX idx_rentals_status ON rentals(status);
