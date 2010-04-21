package com.libertech.erp.model.party.commevent

class CommunicationEventPurposeType {
    String description
    static hasMany =[communicationEventPurposes:CommunicationEventPurpose]
    static constraints = {
    }
}
