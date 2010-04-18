package com.libertech.rtmx.service

import com.libertech.rtmx.model.common.UserLogin
import com.libertech.rtmx.model.common.SysLog

/**
 * Logging service
 */
class LoggerService {

    boolean transactional = true

    static final String SEVERITY_ERROR = "ERROR"
    static final String SEVERITY_INFO = "INFO"
    static final String SEVERITY_WARNING = "WARNING"


    UserLogin userLogin

    /**
     * log error
     */
    void logError(String description, UserLogin userLogin){
      SysLog sysLog = new SysLog(userLogin:userLogin, severity:SEVERITY_ERROR, description:description)
      sysLog.save()
    }

    /**
    * log information
    */
    void logInfo(String description, UserLogin userLogin){
      SysLog sysLog = new SysLog(userLogin:userLogin, severity:SEVERITY_INFO, description:description)
      sysLog.save()
    }


    /**
    * log warning
    */
    void logWarning(String description, UserLogin userLogin){
      SysLog sysLog = new SysLog(userLogin:userLogin, severity:SEVERITY_WARNING, description:description)
      sysLog.save()
    }


    /**
    * list all log items
    */
    List<SysLog> listAll(){
       return SysLog.findAll()
    }

   /**
    * list by severity
   */
    List<SysLog> listBySeverity(String s){
      return SysLog.findAllBySeverity(s)
    }

}
