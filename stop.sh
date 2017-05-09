# PARANDO CONTAINERS
docker-compose down

# REMOVENDO IMAGENS
docker rmi -f npw/rhecruta-db
docker rmi -f npw/rhecruta-share
docker rmi -f npw/rhecruta-core
docker rmi -f npw/rhecruta-web


# REMOVENDO VOLUME
#docker volume remove rhecruta_postgres-volume-rhecruta
