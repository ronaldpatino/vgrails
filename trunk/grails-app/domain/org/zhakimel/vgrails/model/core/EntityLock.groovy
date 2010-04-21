package org.zhakimel.vgrails.model.core

/**
 * provides entity record locking management which is used along lock method of grails
 * after the entity is committed, the corresponding entity-lock record will be deleted
 */
class EntityLock {

    Date lockTime = new Date()                   //time of locking
    String entityName                            //Grails entity name being locked
    Long entityId                                //entity id
    UserLogin lockedBy                           //locked by user login

    static constraints = {
      lockedBy (nullable:true)
    }
}
