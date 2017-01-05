
## Creating database tables

* Create a database with the name auth. 

`create database auth`

* Import the sql dump auth-db.sql to auth database. Script can be found in <Project_source>/scripts/

`mysql -u username -p auth < path_to_sql_dump`

## Strating Auth service

* Start authenticator service. 

`java -jar path_to_authenticator-1.0-SNAPSHOT-jar-with-dependencies.jar org.shelan
.client.AuthClient -p root`
