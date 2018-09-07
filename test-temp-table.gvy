import groovy.sql.Sql
import javax.sql.DataSource

def sql = new Sql(my)

//Register a temp table , and get it odometer
def insertSql = 'INSERT INTO temp_odometer (name) VALUES (?)'
def params = ['person(name, city)']
def keys = sql.executeInsert insertSql, params
def tPerson = keys[0][0]
println tPerson //odometer!!!

//insert temp data
sql.executeInsert """
	INSERT INTO temp_table (odometer, worker) VALUES (${tPerson}, '{"name":"John","city": "Beijing"}')
"""

//pull from other table to temp
sql.executeInsert """
	INSERT INTO temp_table (odometer, worker) 
	SELECT ${tPerson} AS odometer
		, JSON_OBJECT(
			'name', s.hello, 
			'city',  s.world
		) 
	FROM sps_demo s
"""	

//retrieve the data from temp
def fr1 = sql.firstRow """
	SELECT t.worker->'\$.name' AS name
		, t.worker->'\$.city' AS city 
	FROM temp_table t
	WHERE t.odometer = ${tPerson}
		and t.worker->>'\$.city' = 'Beijing'
"""	

//query data join temp and other table
def er2 = '<br> eachRow ->'
sql.eachRow ("""
	SELECT t.worker->'\$.name' AS name
		, t.worker->'\$.city' AS city 
		, s.greeting
	FROM temp_table t , sps_demo s
	WHERE t.odometer = ${tPerson}
		and t.worker->>'\$.city' = s.world
	ORDER BY 
		t.worker->'\$.name'
"""){ row ->
	er2 += ('<br>' +  row.name + ' ha ha ' + row.greeting + ' <br>' )
}

//clean temp data
sql.executeUpdate """
	DELETE FROM temp_table WHERE odometer = ${tPerson}
"""

sql.close()

//return data
'<br> from groovy file!! <br>' + ' temp table person odometer: '+ tPerson + ('<br> firstRow ->' + fr1.name + ' ha ha ' + fr1.city + '' ) + er2

