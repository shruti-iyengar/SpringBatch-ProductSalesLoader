# SpringBatch-ProductSalesLoader

Sample Spring Boot Application which loads a sales file and processes it using Spring batch framework .
We have single reader and multiple writers which write to different DBs, Mongo DB and MySql DBs.
Please refer to the Mysql script in the docs folder for table creation in MySql.

Please override the application.properties with local connection details and launch the application at 
http://localhost:8080/processSales
