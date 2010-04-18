package com.libertech.rtmx.model.hr

import com.libertech.rtmx.model.party.Party
import com.libertech.rtmx.model.party.RoleType
import com.libertech.rtmx.model.party.Person

/**
 * Employee class 
 */
class Employee {

    Person person
    RoleType roleType
    String employeeCode
    
    static constraints = {

      person(nullable:false)
      roleType(nullable:true)
      employeeCode(blank:false)

    }


}
