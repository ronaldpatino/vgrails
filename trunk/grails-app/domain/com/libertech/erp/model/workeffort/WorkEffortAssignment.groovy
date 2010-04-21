package com.libertech.erp.model.workeffort

class WorkEffortAssignment {

    Date fromDate = new Date()
    Date thruDate = new Date()
    String comment = "A work effort assignment"
    WorkEffort workEffort
    
    static constraints = {
      
    }
}
