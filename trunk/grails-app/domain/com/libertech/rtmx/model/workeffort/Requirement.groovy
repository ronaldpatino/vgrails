package com.libertech.rtmx.model.workeffort

import com.libertech.rtmx.model.project.Project

class Requirement {
 
    String description
    Date requirementCreationDate
    Date requiredByDate
    BigDecimal estimatedBudget
    Integer quantity
    String reason
    Requirement partOf
    
    static constraints = {
    }
}
