docker run --rm --name pg-docker \
-e POSTGRES_PASSWORD=pwd \
-e POSTGRES_USER=usr \
-e POSTGRES_DB=demoDB \
-p 5430:5432 \
-d postgres:16

docker run --rm --name finshop-data-center \
--network="host" \
-d "papaandrew/finshop-data-center:latest"