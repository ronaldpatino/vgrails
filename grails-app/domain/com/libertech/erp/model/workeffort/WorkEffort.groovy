package com.libertech.erp.model.workeffort

import com.libertech.erp.model.party.commevent.CommunicationEventWorkEffort

class WorkEffort {
    String name
    String description
    Date scheduledStartDate
    Date ScheduledCompletionDate
    BigDecimal totalMoneyAllowed
    Double totalHoursAllowed
    Double estimatedHours
  
    static hasMany=[communicationEventWorkEfforts:CommunicationEventWorkEffort]
    static constraints = {
    }
}
