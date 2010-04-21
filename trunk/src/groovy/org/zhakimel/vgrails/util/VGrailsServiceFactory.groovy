package org.zhakimel.vgrails.util

import org.codehaus.groovy.grails.commons.ApplicationHolder


class VGrailsServiceFactory {

    public def getServiceBean(String beanName){
      return getBean(beanName)
    }

    private def getBean(String beanName) {
       return ApplicationHolder.getApplication().getMainContext().getBean(beanName)
    }
}
