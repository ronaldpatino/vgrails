package com.libertech.erp.model.party

class RoleType {

    String name
    String description

    static hasMany = [partyRoles:PartyRole]     //this ROLE_TYPE is described by ROLES

    static constraints = {
    }

     static mapping = {
       cache true
       partyRoles lazy:false
    }

   String toString(){
     return description
   }
}
