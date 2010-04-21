package com.libertech.erp.model.party.partycase

class PartyCase {
  
    String description
    Date startDate
    CaseStatusType caseStatusType

    static hasMany = [caseRoles:CaseRole]
   
}
