package com.libertech.rtmx.ui.util

import org.codehaus.groovy.grails.commons.ApplicationHolder
import com.libertech.rtmx.service.PartyService
import com.libertech.rtmx.service.EmployeeService
import com.libertech.rtmx.service.LoggerService

import com.libertech.rtmx.service.CommonService
import com.libertech.rtmx.service.ProjectService
import com.libertech.rtmx.service.LockService

/**
 * Access Grails services
 */
class ServiceFactory {
  
    CommonService getCommonService(){
            return this.getBean("commonService")
      }

     PartyService getPartyService(){
         return this.getBean("partyService")
     }

     EmployeeService getEmployeeService(){
        return this.getBean("employeeService")
     }

     LoggerService getLoggerService(){
          return this.getBean("loggerService")
     }

     ProjectService getProjectService(){
            return this.getBean("projectService")
     }

     LockService getLockService(){
       return this.getBean("lockService")
     }

    private def getBean(beanName) {
       return ApplicationHolder.getApplication().getMainContext().getBean(beanName)
    }
}
