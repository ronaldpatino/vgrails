package com.libertech.rtmx.model.common

import com.libertech.rtmx.model.party.Party

/**
 * UserLogin stores user information in the system
 */
class UserLogin {

    String userName = ""                          //user name
    String userPassword =""                       //password
    String userRole=""                            //user role
    String email=""                               //user email
    String status="OPEN"                          //status

    Party party                                   //party of the user

    static constraints = {
      userName(size:5..15, blank:false)
      userPassword(size:5..15, blank:false,password:true)
      userRole(blank:false)
      email(email:true)
      status(blank:false)
      party(nullable:true)
    }

  static mapping = {
       party lazy:false
   }

  String toString(){
    return userName+" as "+userRole
  }
}
