dataSource {
	pooled = true
	dbCreate = "update"
	url = "jdbc:mysql://localhost/rtmx"
	driverClassName = "com.mysql.jdbc.Driver"
	username = "root"
	password = ""
    //dbunitXmlType = "flat"
}

hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='net.sf.ehcache.hibernate.EhCacheProvider'
}

// environment specific settings
environments {
	development {
		dataSource {
			dbCreate = "update" // one of 'create', 'create-drop','update'
			url = "jdbc:mysql://localhost/rtmx"
            //initialData = "data/dev/data.xml" // dbunit-operator Flat-XML or XML data file
            //initialOperation = "CLEAN_INSERT" // dbunit-operator operation
		}
	}
	test {
		dataSource {
			dbCreate = "update"
			url = "jdbc:hsqldb:mem:testDb"
		}
	}
	production {
		dataSource {
			dbCreate = "update"
			url = "jdbc:hsqldb:file:prodDb;shutdown=true"
		}
	}
}