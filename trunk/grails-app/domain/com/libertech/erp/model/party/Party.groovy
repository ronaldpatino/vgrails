package com.libertech.erp.model.party

import com.libertech.erp.model.hr.PositionFulfillment
import com.libertech.erp.model.hr.EmploymentPosition
import com.libertech.erp.model.party.commevent.CommunicationEventRole

/**
 * PARTY entity will enable storage of some of the core characteristics and relationships that people and
 * organizations share.
 */
class Party {

    String subTypeCode="PARTY"           //PERSON, ORG, PERSON-EMPLOYEE, ORG-LEGAL, ORG-INFORMAL
    Double creditRating= new Double(0.0)

    static hasMany = [
                      partyRoles:PartyRole, 
                      partyClassifications:PartyClassification,
                      partyPostalAddresses:PartyPostalAddress,
                      partyContacts:PartyContact
                     ,employmentPositions:EmploymentPosition        //positions available within ORG
                     ,positionFulfillments:PositionFulfillment      //accepted from PERSON
                     ,communicationEventRoles:CommunicationEventRole]

  static mapping = {
       cache true

       partyRoles lazy:false
       partyClassifications lazy:false
       partyPostalAddresses lazy:false
       partyContacts lazy:false
       employmentPositions lazy:false
       positionFulfillments lazy:false
       communicationEventRoles lazy:false
   }


    static constraints = {
      subTypeCode (blank:false)
      creditRating(blank:false)
    }

  String toString(){
    return subTypeCode
  }
}
