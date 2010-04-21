package com.libertech.erp.model.product.feature

class ProductFeature {
    String description

    static hasMany = [productFeatureApplicabilities:ProductFeatureApplicability]
    static constraints = {
    }
}
