package com.libertech.rtmx.model.party

/**
 * Stores contact information of a party
 */
class PartyContact {
    Date fromDate = new Date()
    Date thruDate = new Date()

    String homePhone = ""
    String cellPhone = ""
    String email     = ""

    String comment   = ""
    
    static constraints = {
      fromDate(nullable:false)
      thruDate(nullable:false)
      homePhone(blank:false)
      cellPhone(blank:false)
      email(blank:false)
    }
}
