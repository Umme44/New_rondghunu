-- add new column year
ALTER TABLE INCOME_TAX_CHALLAN
    ADD COLUMN IF NOT EXISTS YEAR INTEGER NOT NULL DEFAULT 2022;
