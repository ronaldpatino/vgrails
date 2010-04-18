package com.libertech.rtmx.model.party

class PartyRole {

    Date fromDate = new Date()
    Date thruDate = new Date()
    RoleType roleType
    static belongsTo = [party:Party]

   static mapping = {
       cache true
       party lazy:false
       roleType lazy:false
   }


    static constraints = {
      fromDate(blank:true)
      thruDate(blank:true)
    }


  String toString(){
    String s=""
    s+=party.toString() + " as "+roleType.toString()
  }

}
