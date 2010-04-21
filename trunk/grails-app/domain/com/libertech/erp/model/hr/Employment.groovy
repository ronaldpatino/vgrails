package com.libertech.erp.model.hr

import com.libertech.erp.model.party.Party

class Employment {
    Party partyFrom
    Party partyTo
    Date fromDate
    TerminationType terminationType
    TerminationReason terminationReason
  
    static constraints = {
    }
}
