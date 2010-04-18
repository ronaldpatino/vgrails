package com.libertech.rtmx.model.project

import com.libertech.rtmx.model.workeffort.Requirement
import com.libertech.rtmx.model.party.Party

class Project {

    String name =""
    String description =""
    String projectCode=""

    Party client = new Party()
    Party sponsor = new Party()

    Date fromDate = new Date()
    Date thruDate = new Date()

    Requirement requirement

    static hasMany = [
                       milestones:Milestone
                     ]

    static constraints = {

       name(blank:false)
       description(blank:false)
       projectCode(blank:false)
       client(nullable:false)
       sponsor(nullable:false)
       fromDate(nullable:false)
       thruDate(nullable:false)
       requirement(nullable:true)

    }

  static mapping = {
    client lazy:false
    sponsor lazy:false
    milestones lazy:false
  }

}
