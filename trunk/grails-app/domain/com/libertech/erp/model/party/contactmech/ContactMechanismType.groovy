package com.libertech.erp.model.party.contactmech

import com.libertech.erp.model.party.commevent.CommunicationEvent

class ContactMechanismType {
    String description
    static hasMany =[communicationEvents:CommunicationEvent]
    static constraints = {
    }
}
