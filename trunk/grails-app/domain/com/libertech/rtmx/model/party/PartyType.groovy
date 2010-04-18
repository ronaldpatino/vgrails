package com.libertech.rtmx.model.party

/**
 * 
 */
class PartyType {

    String name
    String description

    static hasMany = [partyClassifications:PartyClassification]

    static constraints = {
    }

     static mapping = {
        cache true
        partyClassifications lazy:false
     }

  String toString(){
    return description
  }
}
