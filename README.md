
## Creating database tables

* Create a database with the name auth. 

`create database auth`

* Import the sql dump auth-db.sql to auth database. Script can be found in <Project_source>/scripts/

`mysql -u username -p auth < path_to_sql_dump`

## Strating Auth service

* Start authenticator service (with default parameteres). 

`java -jar path_to_authenticator-1.0-SNAPSHOT-jar-with-dependencies.jar org.shelan
.client.AuthClient`

you can provide following optional parameters

```
-dbhost database host
-dbport database port
-dbname database name
-port application port
-u database username
-p database password
```
