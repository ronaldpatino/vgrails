package com.libertech.rtmx.service

import org.zhakimel.vgrails.model.core.UserLogin
import com.libertech.erp.model.party.Organization
import com.libertech.erp.model.party.Person
import com.libertech.erp.model.party.Party
import com.libertech.erp.model.party.PartyType
import com.libertech.erp.model.party.RoleType
import com.libertech.erp.model.party.PartyRole
import com.libertech.erp.model.party.relationship.PartyRelationship
import com.libertech.erp.model.party.relationship.PartyRelationshipType

/**
 * Party Service, provides DAO service for Party, Organization and Person
 * @author Abil Hakim
 */
class PartyService {

  boolean transactional = true

  LoggerService loggerService = new LoggerService()
  LockService lockService = new LockService()

  UserLogin userLogin;

  def getParty(int id) {

  }

  def saveParty(int id) {

  }

  def deleteParty(int id) {

  }

  /**
   * ORGANIZATION DAO
   */
  Organization editOrganization(int id) throws Exception {
    if (!lockService.isLocked("Organization", id)) {

      Organization item = Organization.lock(id)
      if (item)
        lockService.lockEntity("Organization", id)
      return item
    } else {
      loggerService.logError "Organization record " + id + " is being locked, edit cancelled",null
      throw new Exception("Organization record is being locked, edit cancelled")
    }
  }

  def cancelEditOrganization(int id) throws Exception {
    if (lockService.isLocked("Organization", id)) {
      lockService.removeLock("Organization", id)
      loggerService.logInfo("Organization edit cancelled", userLogin)
    }
  }


  def getOrganization(int id) {
    return Organization.get(id)
  }

  def saveOrganization(Organization organization) throws Exception {

    if (organization.validate()) {
      def id = organization.id
      if (organization.id && lockService.isLocked("Organization", id)) lockService.removeLock("Organization", id)

      organization.save()
      loggerService.logInfo "Organization record " + organization.id + " is saved", this.userLogin

    } else {
      organization.errors.each {
        println it
      }
      loggerService.logError "Saving Organization record " + organization.id + " is failed", this.userLogin
      throw new Exception("Saving Organization is failed!")
    }

  }

  def deleteOrganization(Organization organization) throws Exception {

    if (!lockService.isLocked("Organization", organization.id)) {
      organization.delete()
      loggerService.logInfo "Organization record " + organization.id + " is deleted", this.userLogin

    } else {
      loggerService.logError "Organization record " + organization.id + " is failed to be deleted", this.userLogin
      throw new Exception("Delete failed, organization record is locked")
    }
  }

  /**
   * PERSON DAO
   */
  Person editPerson(int id) throws Exception {
    if (!lockService.isLocked("Person", id)) {
      println "Person "+id+" not locked"
      Person item = Person.lock(id)

      lockService.lockEntity("Person", id)

      loggerService.logInfo "Person record " + id + " is being edited", this.userLogin

      return item
    } else {
      println "Person "+id+" Locked"

      loggerService.logError "Person record " + id + " is locked, cannot edit", this.userLogin
      throw new Exception("Person record is being locked, cannot edit")
    }
  }

  def getPerson(int id) {
    return Person.get(id)
  }


  def cancelEditPerson(int id) throws Exception {
    if (lockService.isLocked("Person", id)) {
      lockService.removeLock("Person", id)
      loggerService.logInfo "Editing Person record " + id + " is cancelled", this.userLogin
    }
  }

  def savePerson(Person person) throws Exception {

    if (person.validate()) {
      def id = person.id
      if (person.id && lockService.isLocked("Person", id)) lockService.removeLock("Person", id)
      person.save()
      loggerService.logInfo "Person record " + person.id + " is saved", this.userLogin

    } else {
      person.errors.each {
        println it
      }
      loggerService.logError "Saving person record " + person.id + " is failed:validation error", this.userLogin
      throw new Exception("Saving Person is failed, validation error")
    }

  }


  def deletePerson(Person person) throws Exception {

    if (!lockService.isLocked("Person", person.id)) {
      person.delete()
    } else {
      loggerService.logError "Delete person record " + person.id + " is failed:being locked by others", this.userLogin
      throw new Exception("Delete failed, person record is locked")
    }
  }


  List<Party> findAllParty() {
    return Party.findAll()
  }

  List<Person> findAllPerson() {
    return Person.findAll()
  }

  List<Person> findAllPersonInRole() {
      def ret=[]
      def lst= Person.findAll("from Person p  join p.partyRoles rl  where rl.roleType.name LIKE 'PERSON%'")
      lst.each {
        println "findAllPersonInRole:"+it
        for(psn in it ){
          if(psn instanceof Person) ret.add(psn)
        }
      }
      return ret
  }


  List<Person> listAllPerson() {
    return Person.executeQuery("""select id, subTypeCode, firstName, lastName, gender, birthDate, birthPlace, socialSecurityNo,passportNo
                                  from Person """)
  }


  List<Organization> findAllOrganization() {
    return Organization.findAll()
  }

  /**
   * Party Types
   */
  List<PartyType> listAllPartyType() {

    return PartyType.list()

  }

  List<PartyType> findAllOrganizationPartyType() {
    List<PartyType> lst1 = PartyType.findAllByNameLike('ORGANIZATION%')
    return lst1
  }

  List<PartyType> findAllPersonPartyType() {
    List<PartyType> lst1 = PartyType.findAllByNameLike('PERSON%')
    return lst1
  }

  def getPartyType(int id){
    return PartyType.get(id)
  }

  def savePartyType(PartyType partyType){
    partyType.save()
  }

  def deletePartyType(PartyType partyType){
    partyType.delete()
  }


  /**
   *  Role Types
   */
  List<RoleType> listAllRoleType() {

    return RoleType.list()

  }

  List<RoleType> findAllOrganizationRoleType() {
    List<RoleType> lst1 = RoleType.findAllByNameLike('ORGANIZATION%')
    return lst1
  }

  List<RoleType> findAllPersonRoleType() {
    return RoleType.findAllByNameLike('PERSON%')
  }

  List<RoleType> findAllCustomerRoleType() {
    return RoleType.findAllByNameLike('CUSTOMER%')
  }

  List<RoleType> findAllOtherRoleType() {
    def results = RoleType.withCriteria {
    or{
      like('name','CUSTOMER%')
      like('name','PERSON%')
      like('name','ORGANIZATION%')
    }
   }
   // return RoleType.findAll("FROM RoleType rt WHERE rt.name NOT (LIKE 'CUSTOMER%' AND LIKE 'PERSON%' AND LIKE 'ORGANIZATION%'")
   return results

  }

  /**
   * Party Roles
   */
  List<PartyRole> listAllPartyRole() {
    List<PartyRole> lst1 = PartyRole.list()
    return lst1
  }


  /**
   * Relationships
   */

  List<PartyRelationshipType> listAllPartyRelationshipType(){
    return PartyRelationshipType.list()
  }

  List<PartyRelationship> findAllOrgToOrgRelationship(){
    return PartyRelationship.listOrderById()
  }

  List<PartyRelationship> findAllPersonToPersonRelationship(){
    return PartyRelationship.findAll().asList()
  }

  List<PartyRelationship> findAllPersonToOrgRelationship(){
    return PartyRelationship.findAll().asList()
  }


  PartyRelationship getPartyRelationship(int id){
    return PartyRelationship.get(id)
  }


  PartyRelationship editPartyRelationship(int id)throws Exception{
    if (!lockService.isLocked("PartyRelationship", id)) {

      println "PartyRelationship "+id+" not locked"
      PartyRelationship item = PartyRelationship.get(id)
      lockService.lockEntity("PartyRelationship", id)
      loggerService.logInfo "PartyRelationship record " + id + " is being edited", this.userLogin
      
      return item
    } else {
      println "PartyRelationship "+id+" Locked"

      loggerService.logError "PartyRelationship record " + id + " is locked, cannot edit", this.userLogin
      throw new Exception("PartyRelationship record is being locked, cannot edit")
    }
  }

  def deletePartyRelationship(PartyRelationship partyRelationship) throws Exception {

    if (!lockService.isLocked("PartyRelationship", partyRelationship.id)) {
      partyRelationship.delete()
    } else {
      loggerService.logError "Delete PartyRelationship record " + partyRelationship.id + " is failed:being locked by others", this.userLogin
      throw new Exception("Delete failed, PartyRelationship record is locked")
    }
  }


  def cancelEditPartyRelationship(long id) throws Exception {
    if(!id) return

    if (lockService.isLocked("PartyRelationship", id)) {
      lockService.removeLock("PartyRelationship", id)
      loggerService.logInfo "Editing PartyRelationship record " + id + " is cancelled", this.userLogin
    }
  }

   def savePartyRelationship(PartyRelationship partyRelationship) throws Exception {

    if (partyRelationship.validate()) {
      def id = partyRelationship.id
      if (partyRelationship.id && lockService.isLocked("PartyRelationship", id)) lockService.removeLock("PartyRelationship", id)
     
      partyRelationship.save(flush:true)
      loggerService.logInfo "partyRelationship record " + partyRelationship.id + " is saved", this.userLogin

    } else {
      partyRelationship.errors.each {
        println it
      }
      loggerService.logError "Saving partyRelationship record " + partyRelationship.id + " is failed:validation error", this.userLogin
      throw new Exception("Saving partyRelationship is failed, validation error")
    }

  }

}
