-- Populating the mortgage rates table.

INSERT INTO mortgage_rates (
    maturity_period_months,
    yearly_interest_percentage_rate,
    created,
    created_by,
    last_updated,
    last_updated_by
)
SELECT
    120, -- 10 years or more [10-15)
    3.7500,
    '2025-01-09 10:30:00',
    'ING_NL_MMORCIL1',
    '2025-01-09 10:30:00',
    'ING_NL_MMORCIL1'
WHERE NOT EXISTS (
    SELECT 1 FROM mortgage_rates WHERE maturity_period_months = 120
);


INSERT INTO mortgage_rates (
    maturity_period_months,
    yearly_interest_percentage_rate,
    created,
    created_by,
    last_updated,
    last_updated_by
)
SELECT
    180, -- 15 years or more [15-20)
    4.0000,
    '2025-01-09 10:30:00',
    'ING_NL_MMORCIL1',
    '2025-01-09 10:30:00',
    'ING_NL_MMORCIL1'
WHERE NOT EXISTS (
    SELECT 1 FROM mortgage_rates WHERE maturity_period_months = 180
);


INSERT INTO mortgage_rates (
    maturity_period_months,
    yearly_interest_percentage_rate,
    created,
    created_by,
    last_updated,
    last_updated_by
)
SELECT
    240, -- 20 years or more [20-25)
    4.2500,
    '2025-01-09 10:30:00',
    'ING_NL_MMORCIL1',
    '2025-01-09 10:30:00',
    'ING_NL_MMORCIL1'
WHERE NOT EXISTS (
    SELECT 1 FROM mortgage_rates WHERE maturity_period_months = 240
);


INSERT INTO mortgage_rates (
    maturity_period_months,
    yearly_interest_percentage_rate,
    created,
    created_by,
    last_updated,
    last_updated_by
)
SELECT
    300, -- 25 years or more [25-30)
    4.5000,
    '2025-01-09 10:30:00',
    'ING_NL_MMORCIL1',
    '2025-01-09 10:30:00',
    'ING_NL_MMORCIL1'
WHERE NOT EXISTS (
    SELECT 1 FROM mortgage_rates WHERE maturity_period_months = 300
);


INSERT INTO mortgage_rates (
    maturity_period_months,
    yearly_interest_percentage_rate,
    created,
    created_by,
    last_updated,
    last_updated_by
)
SELECT
    360, -- 30 years or more [30-35)
    4.7500,
    '2025-01-09 10:30:00',
    'ING_NL_MMORCIL1',
    '2025-01-09 10:30:00',
    'ING_NL_MMORCIL1'
WHERE NOT EXISTS (
    SELECT 1 FROM mortgage_rates WHERE maturity_period_months = 360
);


INSERT INTO mortgage_rates (
    maturity_period_months,
    yearly_interest_percentage_rate,
    created,
    created_by,
    last_updated,
    last_updated_by
)
SELECT
    420, -- 35 years or more [35-40]
    5.0000,
    '2025-01-09 10:30:00',
    'ING_NL_MMORCIL1',
    '2025-01-09 10:30:00',
    'ING_NL_MMORCIL1'
WHERE NOT EXISTS (
    SELECT 1 FROM mortgage_rates WHERE maturity_period_months = 420
);