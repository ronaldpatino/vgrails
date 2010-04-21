package com.libertech.erp.model.party.facility

class Facility {

    String description
    Double squareMeter
    FacilityType facilityType

    static constraints = {
    }

    String toString(){
      return description+" : Facility"
    }
}
