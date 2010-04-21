package com.libertech.erp.model.product

import com.libertech.erp.model.product.feature.ProductFeatureApplicability
import com.libertech.erp.model.product.category.ProductCategoryClassification

class Product {

  String name
  Date introductionDate
  Date salesDiscontinuationDate
  Date supportDiscontinuationDate
  String comment


  static hasMany=[productDeliverySkillRequirements:ProductDeliverySkillRequirement
                  ,productFeatureApplicabilities:ProductFeatureApplicability
                  ,productCategoryClassifications:ProductCategoryClassification]
  
    static constraints = {
    }

  String toString(){
    return name
  }
}
