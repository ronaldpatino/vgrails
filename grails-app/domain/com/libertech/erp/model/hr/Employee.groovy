package com.libertech.erp.model.hr

import com.libertech.erp.model.party.RoleType
import com.libertech.erp.model.party.Person

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
