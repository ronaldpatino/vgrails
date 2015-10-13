# Intro #
VGrails is an application framework utilizing Vaadin and Grails.  By using the power of both sides via Vaadin Grails plugin, you can develop enterprise solutions faster than before with a great user interface.

This framework requires latest Grails and Vaadin, also Vaadin Grails plugin and Vaadin Chameleon theme.

To start using VGrails, just read the <a href='http://code.google.com/p/vgrails/wiki/GettingStarted?ts=1271603513&updated=GettingStarted'>Getting started</a> document.

By the way, the sample cases here is using data model patterns for professional service company (consulting firm, law firm etc) so feel free to improve and contribute the code, this model is taken from studying books called "Data Model Resource Book volume 1 and 2" by Len Silverston

<a href='http://amzn.com/0471380237'><img src='http://ecx.images-amazon.com/images/I/51t5kyuWBDL._SL500_PIsitb-sticker-arrow-big,TopRight,35,-73_OU01_SS75_.jpg' /> </a>

<img src='http://vgrails.googlecode.com/svn/trunk/screenshot/logging.jpg' width='800' />

## Features ##

Main components had been created are:

- GrailsTable: An extended Vaadin Table that reads list of Grails domain objects and display it into table

- GrailsForm: A form generator that reads a Grails domain object and generates the UI fields and validators based on your domain class constraints

- GrailsFieldFactory/JGrailsFieldFactory: reads a Grails domain field information and generates a field and validator based on the constraint

- Plus many more


## Info ##
**Current status:
- Alpha update 21-04-2010
> - now you should extends your application to VGrailsApplication instead Application**

```

class MainApp extends VGrailsApplication {

 void init(){

 }
}

```

> -  VGrails now independent from your application including the Domain models for user login, logging and entity locking, the Domain model now placed in <i>grails-app/domain/org/zhakimel/vgrails/model/core</i>

> - Common application panels now placed in package <i>org.zhakimel.vgrails.app</i> while utilities and Application Manager placed in package <i>org.zhakimel.vgrails.util</i>

Happy Kartini day..:)

- Alpha 18-04-2010

**Issues:**

- Eager initialization of Grails domain classes (a.k.a lazy:false)

- Combobox for listing Grails domain objects, still unable to display the value, even toString() method has been added to the domain objects and the ComboBox value is set explicitly.

## Contribution needed ##
To speed up the development and also to ensure the robustness and stability of VGrails, we welcome the Vaadin and Grails developer to join this project.