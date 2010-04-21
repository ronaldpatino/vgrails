package com.libertech.erp.model.party.contactmech

import com.libertech.erp.model.party.Party

class PartyContactMechanism {

    Date fromDate
    Date thruDate
    Boolean nonSolicitationInd
    String comment

    Party party

    static constraints = {
    }
}
