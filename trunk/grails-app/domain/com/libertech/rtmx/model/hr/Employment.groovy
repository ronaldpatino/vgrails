package com.libertech.rtmx.model.hr

import com.libertech.rtmx.model.party.Party

class Employment {
    Party partyFrom
    Party partyTo
    Date fromDate
    TerminationType terminationType
    TerminationReason terminationReason
  
    static constraints = {
    }
}
