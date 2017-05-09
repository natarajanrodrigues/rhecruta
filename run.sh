mvn clean install
docker-compose up -d --build
docker logs -f $(docker ps -q -f name="rhecruta-core")