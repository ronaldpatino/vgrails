package com.libertech.erp.model.hr

class PositionType {

    String name
    String description
    float benefitPercent


    static hasMany = [employeePositions:EmploymentPosition,      //used to describe
                      positionTypeClasses:PositionTypeClass
                      ]

    static constraints = {
    }
}
