package com.libertech.erp.model.product.feature

import com.libertech.erp.model.product.Product

class ProductFeatureApplicability {

    Date fromDate
    Date thruDate

    Product product
    ProductFeature productFeature

    static constraints = {
    }
}
