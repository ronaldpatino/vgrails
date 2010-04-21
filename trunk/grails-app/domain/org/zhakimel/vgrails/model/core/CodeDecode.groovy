package org.zhakimel.vgrails.model.core

/**
 * CODE_DECODE will store code values for translating codes in entity into a value
 */
class CodeDecode {

    String cdGroup =""                            //grouped by domain model
    String cdCode =""                             //code
    String cdValue =""                            //value of code
    String description = ""                       //description of code
  
    static constraints = {

      cdGroup(blank:false)
      cdCode(blank:false)
      cdValue(blank:false)
      description(blank:false)

    }
}
