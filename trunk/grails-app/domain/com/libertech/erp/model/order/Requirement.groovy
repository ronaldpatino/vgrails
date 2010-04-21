package com.libertech.erp.model.order

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
