package com.libertech.erp.model.party.relationship

import com.libertech.erp.model.party.PartyRole
import com.libertech.erp.model.party.commevent.CommunicationEvent

/**
 *The PARTY RELATIONSHIP entity allows parties to be related to other parties
 * and maintains the respective roles in the relationship.
 */
class PartyRelationship {
    Date fromDate       = new Date()
    Date thruDate       = new Date()

    String description  = "A relationship"
    PartyRole fromPartyRole
    PartyRole toPartyRole
    PartyRelationshipStatusType partyRelationshipStatusType

    static belongsTo=[partyRelationshipType:PartyRelationshipType]

    static hasMany=[communicationEvents:CommunicationEvent]

    static constraints = {

    }

   static mapping = {
       cache true

       fromPartyRole lazy:false
       toPartyRole lazy:false
       partyRelationshipStatusType lazy:false
       partyRelationshipType lazy:false
       communicationEvents lazy:false

   }
   
}
