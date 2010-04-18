package com.libertech.rtmx.model.party

import com.libertech.rtmx.model.hr.PositionFulfillment
import com.libertech.rtmx.model.hr.EmploymentPosition


/**
 * PARTY entity will enable storage of some of the common characteristics and relationships that people and
 * organizations share.
 */
class Party {

    String subTypeCode="PARTY"           //PERSON, ORG, PERSON-EMPLOYEE, ORG-LEGAL, ORG-INFORMAL
    Double creditRating= new Double(0.0)

    //Mandatory
    /*String createdBy
    Date createdOn
    String updatedBy
    String updatedOn
    */
  
    static hasMany = [
                      partyRoles:PartyRole, 
                      partyClassifications:PartyClassification,
                      partyPostalAddresses:PartyPostalAddress,
                      partyContacts:PartyContact,
                      employmentPositions:EmploymentPosition,        //positions available within ORG
                      positionFulfillments:PositionFulfillment      //accepted from PERSON
                     ]

  static mapping = {
       cache true

       partyRoles lazy:false
       partyRoles cache:true

       partyClassifications lazy:false
       partyPostalAddresses lazy:false
       partyContacts lazy:false
       employmentPositions lazy:false
       positionFulfillments lazy:false

   }


    static constraints = {
      subTypeCode (blank:false)
      creditRating(blank:false)
    }

  String toString(){
    return subTypeCode
  }
}
