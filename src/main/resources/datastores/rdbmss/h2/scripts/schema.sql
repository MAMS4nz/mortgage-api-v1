-- Creation of the mortgage rates table.

CREATE TABLE mortgage_rates (
    id INT IDENTITY(1,1) PRIMARY KEY,
    maturity_period_months INT NOT NULL UNIQUE,
    yearly_interest_percentage_rate DECIMAL(7, 4) NOT NULL, -- DECIMAL in order to be financially precise.
    created DATETIME NOT NULL,
    created_by NVARCHAR(60) NOT NULL,
    last_updated DATETIME NOT NULL,
    last_updated_by NVARCHAR(60) NOT NULL
);
