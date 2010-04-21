package com.libertech.rtmx.service

import org.zhakimel.vgrails.model.core.UserLogin
import org.zhakimel.vgrails.model.core.CodeDecode

class CommonService {

  boolean transactional = true

  LoggerService loggerService = new LoggerService()
  UserLogin xUserLogin


  UserLogin doLogin(String userName, String password) {
    UserLogin user = UserLogin.findByUserName(userName)

    if (user == null) {
      loggerService.logWarning "Login failed from user=" + userName + " password=" + password, null
      return null
    } else {
      if (user.userPassword.equals(password)) {
        loggerService.logInfo "Login success", user
        return user
      }

    }
    return null
  }

  List<CodeDecode> listCodeDecode(String codeGroup) {
    if (codeGroup.equals(""))
      return CodeDecode.findAll()
    else
      return CodeDecode.findAllByCdGroup(codeGroup)
  }

  CodeDecode getCodeDecode(long id) {
    return CodeDecode.get(id)
  }

  CodeDecode getCodeDecode(String codeGroup, String code) {
    CodeDecode codeDecode = CodeDecode.find("cdGroup=? and cdCode=?", [codeGroup, code])
    return codeDecode
  }

  void saveCodeDecode(CodeDecode codeDecode) throws Exception {
    try {
      codeDecode.save()
      loggerService.logInfo "CodeDecode Saved ID=" + codeDecode.id, null
    } catch (e) {
      loggerService.logInfo e.getMessage(), xUserLogin
    }
  }

  void deleteCodeDecode(CodeDecode codeDecode) throws Exception {

    try {
      def id = codeDecode.id
      codeDecode.delete()
      loggerService.logInfo "CodeDecode ID" + id + " deleted", this.userLogin
    } catch (e) {
      loggerService.logInfo e.getMessage(), xUserLogin
    }
  }

  List<CodeDecode> listPartyCodeDecode() {
    return CodeDecode.findAllByCdGroup("PARTY")
  }

  List<CodeDecode> listOrgRoleCodeDecode() {
    return CodeDecode.findAll("from CodeDecode where cdGroup=? and cdCode like ?",["PARTYROLE","ORGANIZATION%"])
  }

  List<CodeDecode> listPersonRoleCodeDecode() {
    return CodeDecode.findAll("from CodeDecode where cdGroup=? and cdCode like ?",["PARTYROLE","PERSON%"])
  }

  List<CodeDecode> listClassificationCodeDecode() {
    return CodeDecode.findAllByCdGroup("CLASSIFICATION")
  }

  List<CodeDecode> listDocFormatCodeDecode() {
    return CodeDecode.findAllByCdGroup("DOCFORMAT")
  }

  List listCodeDecodeGroup() {
    return CodeDecode.executeQuery("select distinct cdc.cdGroup from CodeDecode cdc")
  }


  UserLogin getUserLogin(int id) {
    return UserLogin.get(id)
  }

  def deleteUserLogin(UserLogin userLogin) throws Exception {
    try {
      userLogin.delete()
      loggerService.logInfo "User "+userLogin+" deleted",this.xUserLogin
    } catch (e) {
      loggerService.logError e.getMessage(), this.xUserLogin

    }
  }

  List findAllUserLogin() {
    return UserLogin.findAll()
  }

  def addUser(UserLogin userLogin) throws Exception {
    boolean isSave=userLogin.id
    if (!userLogin.validate()) {
      throw new Exception("Save Cancelled: User Login is Invalid")
    }
    try{
      
      userLogin.save(flush:true)
      
    }catch(e){
      loggerService.logInfo "Save failed, exception",xUserLogin
      e.printStackTrace() //not suggested
    }
    loggerService.logInfo 'User Saved',this.xUserLogin
  }


 
}
