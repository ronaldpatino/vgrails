package com.libertech.rtmx.model.project

/**
 *  Milestone described project targets per PROJECT
**/

class Milestone {

    String name            = "A Milestone"
    String description     = "None"
    Date fromDate          = new Date()
    Date thruDate          = new Date()
    Double percentComplete = new Double(0.0)
    String status          = "START"

    Milestone parent                        //the parent of this Milestone

  
    static belongsTo = [project:Project]

    static hasMany   = [
                        resources:Resource,
                        deliverables:Deliverable
                       ]


    static constraints = {
       name(blank:false)
       description(blank:false)
       fromDate(nullable:false,blank:false)
       thruDate(nullable:false,blank:false)
       percentComplete(nullable:false)
       status(blank:false)
       parent(nullable:true)
    }

}
