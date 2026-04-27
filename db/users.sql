CREATE ROLE myappuser WITH LOGIN PASSWORD 'mypassword';
CREATE DATABASE myappdb OWNER myappuser;
GRANT ALL PRIVILEGES ON DATABASE myappdb TO myappuser;
GRANT ALL PRIVILEGES ON DATABASE postgresml TO myappuser;
GRANT USAGE ON SCHEMA public TO myappuser;
GRANT CREATE ON SCHEMA public TO myappuser;
GRANT USAGE ON SCHEMA public TO myappuser;
GRANT CREATE ON SCHEMA pgml TO myappuser;
GRANT USAGE ON SCHEMA pgml TO myappuser;