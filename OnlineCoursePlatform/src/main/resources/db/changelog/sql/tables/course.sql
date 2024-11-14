CREATE TABLE IF NOT EXISTS course (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    company_id UUID NOT NULL REFERENCES company(id) ON DELETE CASCADE,
    company_rep_id UUID REFERENCES users (id) ON DELETE SET NULL,
    subject VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) DEFAULT 0.00 CHECK (price >= 0),
    status VARCHAR(50) NOT NULL,
    rating DECIMAL(3, 2) CHECK (rating >= 0 AND rating <= 5)
    );
