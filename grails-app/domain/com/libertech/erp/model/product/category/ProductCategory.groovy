package com.libertech.erp.model.product.category

class ProductCategory {
    String name          //INDUSTRIAL-xxx , TECHNICAL-xxx
    String description

    static hasMany = [productCategoryClassifications:ProductCategoryClassification]
  
    static constraints = {

    }
}
