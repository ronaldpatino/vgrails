package com.libertech.erp.model.product.category

import com.libertech.erp.model.product.Product

class ProductCategoryClassification {
    Date fromDate
    Date thruDate
    Boolean primaryFlag
    String comment

    Product product
    ProductCategory productCategory

    static constraints = {
    }
}
