package com.libertech.rtmx.ui.util

import com.libertech.rtmx.service.PartyService
import com.libertech.rtmx.service.EmployeeService
import com.libertech.rtmx.service.LoggerService

import com.libertech.rtmx.service.CommonService
import com.libertech.rtmx.service.ProjectService
import com.libertech.rtmx.service.LockService
import org.zhakimel.vgrails.util.VGrailsServiceFactory


/**
 * Access Grails services
 */
class ServiceFactory extends VGrailsServiceFactory {
  
    CommonService getCommonService(){
            return (CommonService) this.getServiceBean("commonService")
      }

     PartyService getPartyService(){
         return (PartyService)this.getServiceBean("partyService")
     }

     EmployeeService getEmployeeService(){
        return (EmployeeService) this.getServiceBean("employeeService")
     }

     LoggerService getLoggerService(){
          return (LoggerService) this.getServiceBean("loggerService")
     }

     ProjectService getProjectService(){
            return (ProjectService) this.getServiceBean("projectService")
     }

     LockService getLockService(){
       return (LockService) this.getServiceBean("lockService")
     }


}
