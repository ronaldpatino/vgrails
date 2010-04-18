package com.libertech.rtmx.model.party

/**
 * PERSON entity that stores a particular organization's
 * information, independent of his or her jobs or roles. Attributes
 * of the PERSON entity may include current last name, current first name, current
 * middle initial, gender, birth date, height, weight, and many other attributes
 */

class Person extends Party {

    String firstName=""
    String lastName=""
    String personalTitle=""
    String nickName=""
    String gender=""
    Date birthDate=new Date()
    String birthPlace=""
    String maritalStatus=""
    String socialSecurityNo=""
    String passportNo=""
    String comment=""


    static constraints = {

      firstName(blank:false)
      lastName(blank:true)
      personalTitle(blank:true)
      nickName(blank:true)
      gender(blank:false)
      birthDate(blank:false)
      birthPlace(blank:true)
      maritalStatus(blank:false)
      socialSecurityNo(blank:true)
      passportNo(blank:true)
      comment(blank:true)

    }

    String toString(){
      return personalTitle+". "+firstName+" "+lastName
    }

}
