CREATE INDEX idx_user_name ON users (name);
CREATE INDEX idx_user_company_id ON users (company_id);
CREATE INDEX idx_user_role ON users (role);
CREATE UNIQUE INDEX idx_user_email ON users (email);
