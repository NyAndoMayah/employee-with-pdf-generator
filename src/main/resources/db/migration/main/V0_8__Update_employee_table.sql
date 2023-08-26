ALTER TABLE "employee"
DROP CONSTRAINT IF EXISTS employee_cnaps_check;

ALTER TABLE "employee"
    ALTER COLUMN cnaps DROP NOT NULL;
