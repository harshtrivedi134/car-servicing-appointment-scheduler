# This script stops the postgres server started by bootstrap.sh

echo "Stopping dev-postgres  container"
docker stop dev-postgres
echo "Remvoing dev-postgres  container"
docker rm dev-postgres