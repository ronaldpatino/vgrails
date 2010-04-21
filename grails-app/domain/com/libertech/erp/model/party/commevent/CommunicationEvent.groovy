package com.libertech.erp.model.party.commevent

import com.libertech.erp.model.party.relationship.PartyRelationship
import com.libertech.erp.model.party.contactmech.ContactMechanismType

class CommunicationEvent {
    Date started
    Date ended
    String note

    PartyRelationship partyRelationship
    CommunicationEventStatusType communicationEventStatusType
    ContactMechanismType contactMechanismType

    static hasMany=[communicationEventPurposes:CommunicationEventPurpose
                    ,communicationWorkEfforts:CommunicationEventWorkEffort]

    static constraints = {

    }
}
