package com.libertech.erp.model.product

class ProductDeliverySkillRequirement {

    Date startedUsingDate
    Double yearsExperience
    String skillLevel
    SkillType skillType

    static belongsTo=[product:Product]

    static constraints = {
      
    }
}
