package com.libertech.rtmx.service

import com.libertech.rtmx.model.common.EntityLock
import com.libertech.rtmx.model.common.UserLogin

class LockService {

    boolean transactional = true

    LoggerService loggerService=new LoggerService()
    UserLogin userLogin

  /**
   * locking data
   */
  Boolean isLocked(String entityName, Long entityId){
    def cnt = EntityLock.countByEntityNameAndEntityId(entityName,entityId)
    if(cnt>0) return true
    return false
  }

  def lockEntity(String entityName, Long entityId){
   def locke = new EntityLock()
    locke.entityName=entityName
    locke.entityId = entityId
    locke.lockedBy = userLogin
    locke.save()
    loggerService.logInfo entityName+" has been locked",userLogin

  }

  def removeLock(String entityName, Long entityId){
    def item = EntityLock.find("from EntityLock as el where el.entityName=? and el.entityId=? ",[entityName, entityId])
    if(item){
      item.delete()
      loggerService.logInfo "lock for"+entityName+" has been deleted",userLogin
    }

  }

  def clearAllLocks(){
    EntityLock.executeUpdate("delete from EntityLock")
    loggerService.logInfo "All locks has been deleted",userLogin
  }

}
