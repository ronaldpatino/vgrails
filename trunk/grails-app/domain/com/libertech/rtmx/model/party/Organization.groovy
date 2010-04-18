package com.libertech.rtmx.model.party

/**
 * an entity called ORGANIZATION that stores information about a group of people with a common purpose
 * such as a corporation, department, division, government agency, or nonprofit organization.
 * Basic organizational information, such as its name and federal tax ID num (for legal entities),
 * is stored once within this entity, reducing redundancy of information and eliminating possible update discrepancies.
 */
class Organization extends Party {

    String orgName ="An Organization"                     //organization name
    String legalTaxId = ""
    
    static constraints = {

      orgName (blank:false)
      legalTaxId (blank:true)

    }
  
  String toString(){
    return orgName
  }

}
