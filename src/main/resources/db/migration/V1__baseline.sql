-- Create users table to store OAuth user information
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       auth0_subject_id VARCHAR(255) NOT NULL UNIQUE,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       picture VARCHAR(500),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create index on auth0_subject_id
CREATE INDEX idx_auth0_subject_id ON users(auth0_subject_id);

-- Create books table with user relationship
CREATE TABLE books (
                       id BIGSERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       author VARCHAR(255) NOT NULL,
                       description TEXT,
                       year INT NOT NULL,
                       user_id BIGINT NOT NULL,
                       CONSTRAINT fk_books_user
                           FOREIGN KEY (user_id)
                               REFERENCES users(id)
                               ON DELETE CASCADE
);

-- Create index on user_id
CREATE INDEX idx_books_user_id ON books(user_id);