package com.libertech.erp.model.party.relationship

class PartyRelationshipType {
    String name
    String description

    static hasMany = [partyRelationships:PartyRelationship]
    static constraints = {
     
    }

    static mapping = {
       cache true
       partyRelationships lazy:false

   }

    String toString(){
      return description
    }
}
