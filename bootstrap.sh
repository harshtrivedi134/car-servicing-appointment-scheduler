# This script will start the postgres database with configurations required to run the service

PORT=5432
DATABASE_NAME=appointmentscheduler
USER=htrivedi
PASSWORD=H@r!1102


echo "Starting Postgres server"
mkdir -p /tmp/postgres-data/
docker run -d \
  --name dev-postgres \
  -e POSTGRES_USER=${USER} \
  -e POSTGRES_DB=${DATABASE_NAME} \
  -e POSTGRES_PASSWORD=${PASSWORD} \
  -p ${PORT}:${PORT} postgres:14.1

exit $?