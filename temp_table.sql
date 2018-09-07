CREATE DATABASE `test`;

USE `test`;

DROP TABLE IF EXISTS `sps_demo`;

/*
SPS one table 
*/
CREATE TABLE `sps_demo` (
  `hello` varchar(15) NOT NULL ,
  `world` int(11) NOT NULL ,
  `greeting` datetime(3),
  PRIMARY KEY (`hello`)
) ENGINE=InnoDB COMMENT='SPS Demo';


INSERT INTO `sps_demo` VALUES ('SPS', '1',  '2018-09-06 08:08:08');
select * from sps_demo;

/*
Two tables on odometer and data for temp 
*/
CREATE TABLE `temp_odometer` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'auto increment id to tag a temp table',
  `name` VARCHAR(255) NOT NULL COMMENT 'temp table schema on columns info',
  `entry_datetime` datetime NULL COMMENT 'Entry datetime',
  PRIMARY KEY (`id`)
) COMMENT='temp table odometer to work globally';

CREATE TABLE `temp_table` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'auto increment id to tag a temp table line',
  `odometer` INT NOT NULL COMMENT 'tag a temp table',
  `worker` JSON  NULL COMMENT 'hold data for a temp table',
  `entry_datetime` datetime NULL COMMENT 'Entry datetime',
  PRIMARY KEY (`id`),
  KEY `temp_tableI2` (`odometer`)
) COMMENT='temp table worker to hold data';

/*
demo a case on person
*/

/*
Register a temp table , named person 
*/

INSERT INTO temp_odometer (name) VALUES ("person(name, city)");


/*
then get it odometer :@temp_person, may be 1 ??

SET @temp_person= 1;

select @@IDENTITY; not work in tidb
*/
SET @temp_person = LAST_INSERT_ID();

/*
pull from other table to temp
*/
INSERT INTO temp_table (odometer, worker) 
SELECT @temp_person
	, JSON_OBJECT('name', s.hello, 'city',  s.world) 
FROM sps_demo s;

/*
insert temp data
*/
INSERT INTO temp_table (odometer, worker) VALUES (@temp_person, '{"name":"John","city": "Beijing"}');

/*
retrieve the data from temp 
*/
SELECT t.worker->>'\$.name' AS name
	, t.worker->>'\$.city' AS city 
FROM temp_table t
WHERE t.odometer = @temp_person
	and t.worker->>'\$.city' = "Beijing";

/*
query data join temp and other table 
*/
SELECT t.worker->>'\$.name' AS name
	, t.worker->>'\$.city' AS city 
	, s.greeting
FROM temp_table t , sps_demo s
WHERE t.odometer = @temp_person
	and t.worker->>'\$.city' = s.world;
	
/*
clean temp data 
*/	
DELETE FROM temp_table
WHERE odometer = @temp_person;	

/*
check temp data 
*/
SELECT  @temp_person;
SELECT * FROM temp_odometer;
SELECT * FROM temp_table;
