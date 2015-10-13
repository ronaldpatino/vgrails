# Getting Started #

Before using the framework, let us introduce what VGrails actually. VGrails is created during development of an ERP system with tight schedules. Before using Grails we had already using Spring framework, and it's very complicated for most of Java developers especially new developers who are never use the Spring framework before. After we evaluate Grails then we think about user interface. The issue is security(users can't see the logic of application by clicking 'view source' in their browser),  consistency and fast Ajax development then after reviewing many UI frameworks, we choose Vaadin. The integration of Vaadin with Grails is smoothly achieved by using Vaadin Grails Plugin (http://vaadin.com/wiki/-/wiki/Main/Grails%20plugin).

# Details #

# Create Grails application:

> ` grails create-app [Your app] `

# Checkout the source from http://code.google.com/p/vgrails/source/checkout

# copy all checked out files to your created grails application folder

# install Vaadin Grails plugin:

```
  grails install-plugin vaadin 1.0
  grails install-plugin dbunit-operator
```

# Prepare your MySQL database, create a database as described in conf/DataSource.groovy

# in DataSource.groovy and Bootstrap.groovy find and uncomment these lines

> Bootstrap.groovy:
> ` // DbUnitOperator.create() `

> Datasource.groovy:
```
   //dbunitXmlType = "flat"
   //initialData = "data/dev/data.xml" // dbunit-operator Flat-XML or XML data file
   //initialOperation = "CLEAN_INSERT" // dbunit-operator operation
```
# Now run your app:

> grails run-app


Enjoy the day.