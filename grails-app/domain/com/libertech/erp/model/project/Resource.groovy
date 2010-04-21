package com.libertech.erp.model.project

import com.libertech.erp.model.party.Party

class Resource {

    Party person
    String task
    String description
    
    static belongsTo=[project:Project]

    static constraints = {
      person(nullable:false)
      task(blank:false)
      description(blank:true)
    }
}
