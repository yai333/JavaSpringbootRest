DROP TABLE IF EXISTS "user";

CREATE TABLE "user"
(
 id SERIAL PRIMARY KEY,
 firstName VARCHAR(100) NOT NULL,
 lastName VARCHAR(100) NOT NULL
);