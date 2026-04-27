docker run \
    -it \
    -v postgresml_data:/var/lib/postgresql \
    -p 5433:5432 \
    -p 8000:8000 \
    ghcr.io/postgresml/postgresml:2.9.3 \
    sudo -u postgresml psql -d postgresml
    # && CREATE ROLE myappuser WITH LOGIN PASSWORD 'mypassword';