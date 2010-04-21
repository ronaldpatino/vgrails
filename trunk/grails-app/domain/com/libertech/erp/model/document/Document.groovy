package com.libertech.erp.model.document

/**
 * Document Entity
 */
class Document {

    String name                           //document name
    DocumentType documentType             //document type

    String comment                        //comment
    Date created = new Date()             //document creation date
    String location                       //url location of document
    String text                           //document text
    String image                          //document image

    static constraints = {
      name(blank:false)
    }

}
