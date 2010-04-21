package com.libertech.erp.model.product

class SkillType {

    String name
    String description

    static hasMany=[productDeliverySkillRequirements:ProductDeliverySkillRequirement]

    static constraints = {
    }
}
