package com.libertech.rtmx.model.party

/**
 *The PARTY RELATIONSHIP entity allows parties to be related to other parties
 * and maintains the respective roles in the relationship.
 */
class PartyRelationship {
    Date fromDate       = new Date()
    Date thruDate       = new Date()

    String description  = ""
    PartyRole fromPartyRole
    PartyRole toPartyRole

    static belongsTo=[partyRelationshipType:PartyRelationshipType]
  
    static constraints = {

    }

   static mapping = {
       cache true

       fromPartyRole lazy:false
       toPartyRole lazy:false
       partyRelationshipType lazy:false

   }
   
}
