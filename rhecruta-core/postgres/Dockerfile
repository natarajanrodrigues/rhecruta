FROM postgres
MAINTAINER natarajan <natarajanrodrigues@gmail.com>
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD 12345
ENV POSTGRES_DB rhecruta
COPY rhecruta-sql.sql /docker-entrypoint-initdb.d/
