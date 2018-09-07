# sps-boot-groovy
sps (SPS) :  Stored Procedures Service

When plan migrate our Enterprise Database to cloud database TiDB, We were blocked.
Root reason is not the big data , but, are many SP(stored procedures), which are key and complex.
So, firstly, jump to split these SP from database server to cloud service.

The project is a solution , key stack:
1. Spring Boot - great tools for cloud service
2. Groovy - Java script,  and package (groovy.sql) is more powerful than SQL
3. JSON data type in MySQL - apply to mockup TEMPORARY table in TiDB, which do not support. 


#### The demo how to mockup TEMPORARY table in TiDB
* Firstly, reference file (temp_table.sql), try it in MySQL(5.7+) or TiDB
* JSON data type, and function JSON_OBJECT / JSON_EXTRACT, is key feature
* Two tables on TEMPORARY, temp_odometer to register a temp table globally, temp_table is worker to hold data
* Groovy file (test-temp-table.gvy) need set the file path locally in SPScontroller.loadGvy(), when run the micro service , url: http://localhost:8080/
* Groovy support hot deployment, same as SP. 

== See Also

The following guides may also be helpful:

* https://github.com/spring-guides/gs-spring-boot [Spring Boot]
