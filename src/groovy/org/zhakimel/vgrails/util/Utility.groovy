package org.zhakimel.vgrails.util

import org.codehaus.groovy.grails.validation.ConstrainedProperty


class Utility {

  static def getOrderedConstraints(domainObject) {
    def visibles = []

    domainObject.constraints.each {
      ConstrainedProperty cp = (ConstrainedProperty) it.value
      if(it.key)
        visibles[cp.getOrder()-1] = it.key
    }
   println visibles
   return visibles
  }

}
