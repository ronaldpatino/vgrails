package com.libertech.erp.model.party.commevent

import com.libertech.erp.model.party.Party

class CommunicationEventRole {

    CommunicationEvent ofCommunicationEvent
    CommunicationEventRoleType describedBy
    Party forParty

    static constraints = {
    }
}
