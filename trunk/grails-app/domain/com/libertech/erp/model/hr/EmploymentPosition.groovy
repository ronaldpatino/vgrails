package com.libertech.erp.model.hr

/**
 * Employment position registry in an organization
 */
class EmploymentPosition {

    String name=""
    Date estimatedFromDate= new Date()
    Date estimatedThruDate= new Date()
    Boolean salaryFlag
    Boolean exemptFlag
    Boolean fulltimeFlag
    Boolean temporaryFlag

    Date actualFromDate  = new Date()
    Date actualThruDate  = new Date()
    

    static hasMany = [ positionResponsibilities:PositionResponsibility]

    static mapping = {
       positionResponsibilities lazy:false
    }

    static constraints = {

    }
}
