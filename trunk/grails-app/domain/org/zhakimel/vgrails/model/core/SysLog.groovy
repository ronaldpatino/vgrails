package org.zhakimel.vgrails.model.core

/**
 * Stores logging information for application operation
 */
class SysLog {

    Date logDate = new Date()                    //logging date time
    UserLogin userLogin                          //user login
    String severity = "INFO"                     //severity type
    String description = ""                      //description of operation/event being logged
    String ipAddress="127.0.0.1"                 //ip address

    static constraints = {

      logDate(blank:false)
      severity (blank:false)
      description (blank:false)
      ipAddress(blank:true)
      userLogin(nullable:true)
    }


   static mapping = {
       userLogin lazy:false
   }

}
