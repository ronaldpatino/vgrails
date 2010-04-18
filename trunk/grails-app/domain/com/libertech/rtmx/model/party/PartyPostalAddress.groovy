package com.libertech.rtmx.model.party

/**
 * Stores postal address of a party
 */
class PartyPostalAddress {

    Date fromDate         = new Date()
    Date thruDate         = new Date()
    String comment        =""
    String address1       =""
    String address2       =""
    String city           =""
    String postalCode     =""
    String province       =""
    String country        =""


     static constraints = {

        fromDate(blank:false)
        thruDate(blank:false)
        comment(blank:true)
        address1(blank:false)
        address2(blank:true)
        city(blank:false)
        postalCode(blank:true)
        province(blank:true)
        country(blank:true)

    }

}
