package com.libertech.rtmx.ui.party

import com.libertech.rtmx.service.CommonService
import com.libertech.rtmx.service.LoggerService
import com.libertech.rtmx.service.PartyService
import org.zhakimel.vgrails.component.GrailsForm
import org.zhakimel.vgrails.component.GrailsFormWindow
import org.zhakimel.vgrails.component.GrailsTable
import org.zhakimel.vgrails.component.Spacer
import com.libertech.rtmx.ui.util.AppConstant

import org.zhakimel.vgrails.util.UIBuilder
import com.vaadin.terminal.ThemeResource
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.Button.ClickListener
import com.vaadin.ui.Window.Notification
import com.libertech.erp.model.party.*
import com.vaadin.ui.*
import com.libertech.rtmx.ui.MainApp
import org.zhakimel.vgrails.util.AppManager

class PersonForm extends Panel implements UIBuilder, Button.ClickListener {

  MainApp app
  AppManager manager

  CommonService commonService
  LoggerService loggerService
  PartyService partyService


  GrailsForm personForm
  GrailsTable roleTable
  GrailsTable classTable

  GrailsTable postalTable
  GrailsTable contactTable

  GrailsTable positionTable
  GrailsTable fulfillmentTable


  GrailsFormWindow editorWindow

  Button btnSave
  Button btnDelete
  Button btnCancel

  List personShowFields = ["subTypeCode", "firstName", "lastName", "personalTitle","nickName","gender","birthDate"
                           ,"birthPlace","maritalStatus","socialSecurityNo","passportNo","comment"]
  List roleShowFields = ["roleType", "fromDate", "thruDate"]
  Map roleTableShowFields = ["roleType": RoleType.class, "fromDate": Date.class, "thruDate": Date.class]

  List classShowFields = ["partyType", "fromDate", "thruDate"]
  Map classTableShowFields = ["partyType": PartyType.class, "fromDate": Date.class, "thruDate": Date.class]

  List postalShowFields = ["fromDate", "thruDate", "address1","address2","city","postalCode","province","country"]
  Map postalTableShowFields = ["fromDate": Date.class, "thruDate": Date.class, "address1":String.class, "city":String.class, "postalCode":String.class]

  List contactShowFields = ["fromDate", "thruDate", "homePhone","cellPhone","email","comment"]
  Map contactTableShowFields = ["fromDate": Date.class, "thruDate": Date.class, "homePhone":String.class, "cellPhone":String.class, "email":String.class]


  Map orgListValues = [:]
  Map listValuesForRole=[:]

  Person personBean

  int recordId = 0
  boolean built = false

  def PersonForm(String caption, int recordId,MainApp app) {
    super(caption);
    this.app=app
   manager = this.app.manager

   commonService = manager.serviceFactory.getCommonService()
   loggerService = manager.serviceFactory.getLoggerService()
   partyService = manager.serviceFactory.getPartyService()


    this.recordId = recordId

    if (recordId == 0) {
      personBean = new Person()
    } else {
      personBean = partyService.editPerson(recordId)
    }

    orgListValues = getListValuesForPerson()
    listValuesForRole = getListValuesForRole()

  }

  private def getListValuesForPerson(){

    def lst = commonService.listPartyCodeDecode()
       def vals = [:]
       lst.each {
         if (it.cdCode.startsWith("PERSON"))
           vals[it.cdCode] = it.cdValue
       }
       Map map=[:]
       map["subTypeCode"] = vals
       return map
  }

  private def getListValuesForRole(){

    def lst = commonService.listPersonRoleCodeDecode()
       def vals = [:]
       lst.each {
           vals[it.cdCode] = it.cdValue
       }
       Map map=[:]
       map["subTypeCode"] = vals
       return map
  }



  void build() {
    manager.disableNavigation()
    def vl = (VerticalLayout) getContent()
    vl.setSpacing true

    personForm = buildOrgForm()
    addComponent buildCrudButton()
    def hl = new HorizontalLayout()
    hl.setWidth "100%"

    def tabs = new TabSheet()
    tabs.setStyleName "minimal"

    hl.addComponent personForm
    tabs.addTab(buildRoleTable(), "Roles", null)
    tabs.addTab(buildClassificationTable(), "Classification", null)
    hl.addComponent tabs

    hl.setExpandRatio personForm,5
    hl.setExpandRatio tabs,5

    vl.addComponent hl

    def pnl1 = new Panel("Address and Contacts")
    def tabs1 = new TabSheet()
    tabs1.setStyleName "minimal"
    tabs1.addTab(buildPostalTable(postalTableShowFields,"partyPostalAddress"), "Postal", null)
    tabs1.addTab(buildContactTable(contactTableShowFields,"partyContact"), "Contact", null)
    pnl1.addComponent tabs1
    vl.addComponent  pnl1


    built = true
  }

  GrailsForm buildOrgForm() {


    GrailsForm form = new GrailsForm(personShowFields, personBean)
    form.setEntityListValues(orgListValues)
    form.hasCrudButton = false
    form.setStyleName "white"
    form.formCaption = caption

    if (recordId != 0)
      form.formMode = "edit"
    else
      form.formMode = "add"

    form.buildForm()


    return form
  }

  Component buildCrudButton() {
    def grd = new GridLayout(2, 1)
    grd.setColumnExpandRatio 0, 1.0f
    grd.setSizeFull()
    def spacer = new Spacer()
    grd.addComponent spacer
    grd.setComponentAlignment spacer, Alignment.MIDDLE_LEFT

    def hl2 = new HorizontalLayout()
    hl2.setSpacing true

    def onSave = {ClickEvent event ->

      personForm.commit()

      try {
        partyService.savePerson(personBean)
        getWindow().showNotification "Organization record saved"
        app.contentPanel.removeAllComponents()

        def panel = manager.getContentPanel(AppConstant.NAV_PARTY, "Person")
        app.contentPanel.addComponent panel
        panel.refreshUIData()
        manager.enableNavigation()
      }
      catch (e) {
        getWindow().showNotification e.getMessage(), Notification.TYPE_WARNING_MESSAGE
      }

    } as ClickListener

    btnSave = new Button(AppConstant.STR_SAVE, this)
    btnSave.icon = new ThemeResource(AppConstant.ICON_SAVE)
    hl2.addComponent btnSave
    hl2.setComponentAlignment btnSave, Alignment.MIDDLE_RIGHT
    btnSave.addListener onSave

    btnDelete = new Button(AppConstant.STR_DELETE, this)
    btnDelete.icon = new ThemeResource(AppConstant.ICON_DELETE)
    hl2.addComponent btnDelete
    hl2.setComponentAlignment btnDelete, Alignment.MIDDLE_RIGHT
    def onDelete = {ClickEvent event ->
       manager.enableNavigation()
    } as ClickListener
    btnDelete.addListener onDelete

    btnCancel = new Button(AppConstant.STR_CANCEL, this)
    btnCancel.icon = new ThemeResource(AppConstant.ICON_CANCEL)
    hl2.addComponent btnCancel
    hl2.setComponentAlignment btnCancel, Alignment.MIDDLE_RIGHT

    def onCancel = {ClickEvent event ->
      if(personBean.id){
        partyService.cancelEditPerson(personBean.id.intValue())
      }
      manager.enableNavigation()
      app.contentPanel.removeAllComponents()
      app.contentPanel.addComponent manager.NAVIGATION_APP_PANEL[AppConstant.NAV_PARTY]["Person"]

    } as ClickListener

    btnCancel.addListener onCancel

    grd.addComponent hl2
    return grd
  }




  Component buildRoleTable() {

    def vl = new VerticalLayout()
    vl.setMargin true
    vl.setWidth "100%"
    vl.setSpacing true

    def hl = new HorizontalLayout()
    hl.setWidth "100%"
    vl.addComponent hl

    def btnAdd = new Button(AppConstant.STR_NEW, this)
    btnAdd.setStyleName "small"
    hl.addComponent btnAdd
    hl.setComponentAlignment btnAdd, Alignment.MIDDLE_RIGHT

    def onAdd = {ClickEvent event ->
      showEditor(0, "partyRole")            //todo

    } as ClickListener

    btnAdd.addListener onAdd

    roleTable = new GrailsTable(roleTableShowFields)      //todo
    roleTable.editButtonClickListener = {Button.ClickEvent event ->
      def button = event.button
      def id = new Integer(button.description.substring(5))
      showEditor(id, "partyRole")              //todo
    } as Button.ClickListener
    roleTable.setHeight "150px"
    vl.addComponent roleTable                  //todo

    return vl

  }

  Component buildPostalTable(Map showFields, String entityName){

     def vl = new VerticalLayout()
      vl.setMargin true
      vl.setWidth "100%"
      vl.setSpacing true

      def hl = new HorizontalLayout()
      hl.setWidth "100%"
      vl.addComponent hl

      def btnAdd = new Button(AppConstant.STR_NEW, this)
      btnAdd.setStyleName "small"
      hl.addComponent btnAdd
      hl.setComponentAlignment btnAdd, Alignment.MIDDLE_RIGHT

      def onAdd = {ClickEvent event ->
        showEditor(0, entityName)            //todo

      } as ClickListener

      btnAdd.addListener onAdd

      postalTable = new GrailsTable(showFields)      //todo
      postalTable.editButtonClickListener = {Button.ClickEvent event ->
        def button = event.button
        def id = new Integer(button.description.substring(5))
        showEditor(id, entityName)              //todo
      } as Button.ClickListener
      postalTable.setHeight "150px"
      vl.addComponent postalTable                  //todo

      return vl

   }

  Component buildContactTable(Map showFields, String entityName){

      def vl = new VerticalLayout()
       vl.setMargin true
       vl.setWidth "100%"
       vl.setSpacing true

       def hl = new HorizontalLayout()
       hl.setWidth "100%"
       vl.addComponent hl

       def btnAdd = new Button(AppConstant.STR_NEW, this)
       btnAdd.setStyleName "small"
       hl.addComponent btnAdd
       hl.setComponentAlignment btnAdd, Alignment.MIDDLE_RIGHT

       def onAdd = {ClickEvent event ->
         showEditor(0, entityName)            //todo

       } as ClickListener

       btnAdd.addListener onAdd

       contactTable = new GrailsTable(showFields)      //todo
       contactTable.editButtonClickListener = {Button.ClickEvent event ->
         def button = event.button
         def id = new Integer(button.description.substring(5))
         showEditor(id, entityName)              //todo
       } as Button.ClickListener
       contactTable.setHeight "150px"
       vl.addComponent contactTable                  //todo

       return vl

    }


  Component buildClassificationTable() {

    def vl = new VerticalLayout()
    vl.setMargin true
    vl.setWidth "100%"
    vl.setSpacing true

    def hl = new HorizontalLayout()
    hl.setWidth "100%"
    vl.addComponent hl

    def btnAdd = new Button(AppConstant.STR_NEW, this)
    btnAdd.setStyleName "small"
    hl.addComponent btnAdd
    hl.setComponentAlignment btnAdd, Alignment.MIDDLE_RIGHT

    def onAdd = {ClickEvent event ->
      showEditor(0, "partyClassification")

    } as ClickListener

    btnAdd.addListener onAdd

    classTable = new GrailsTable(classTableShowFields)

    classTable.setHeight "150px"
    vl.addComponent classTable


    return vl

  }


  boolean isBuilt() {
    return built;
  }

  void refreshUIData() {

    if (personBean.partyRoles) {
      roleTable.entityListData = personBean.partyRoles.asList()
      roleTable.refreshTableData()
    }

    if (personBean.partyClassifications) {
      classTable.entityListData = personBean.partyClassifications.asList()
      classTable.refreshTableData()
    }

    if (personBean.partyPostalAddresses) {
        println "POSTAL TABLE="+postalTable
        postalTable.entityListData = personBean.partyPostalAddresses.asList()
        postalTable.refreshTableData()
      }

   if (personBean.partyContacts) {
        contactTable.entityListData = personBean.partyContacts.asList()
        contactTable.refreshTableData()
      }

  }

  /**
   * Shows Editor for current record
   */
  void showEditor(int id, String entityName) {
    String mode = "edit"
    def entityItem
    List entityShowFields=[]
    String title = DefaultFieldFactory.createCaptionByPropertyId(entityName)

    if (id == 0) mode = "add"

    if (entityName == "partyRole") {
      entityItem = (id == 0 ? new PartyRole() : getPartyRole(id))
      entityShowFields = roleShowFields
    } else if (entityName == "partyClassification") {
      entityItem = (id == 0 ? new PartyClassification() : getPartyClass(id))
      entityShowFields = classShowFields
    } else if (entityName == "partyPostalAddress") {
      entityItem = (id == 0 ? new PartyPostalAddress() : getPartyPostal(id))
      entityShowFields =postalShowFields
    } else if (entityName == "partyContact") {
      entityItem = (id == 0 ? new PartyContact() : getPartyContact(id))
      entityShowFields = contactShowFields
    }

    editorWindow = new GrailsFormWindow(entityItem, entityShowFields, mode)

    if (entityName == "partyRole") {
      List<RoleType> lst = partyService.findAllPersonRoleType()
      editorWindow.form.setEntityObjectValues(["roleType":lst])
    }else if(entityName == "partyClassification"){
      List<PartyType> partyTypeEOV = partyService.findAllPersonPartyType()
      editorWindow.form.setEntityObjectValues(["partyType":partyTypeEOV] )
    }
    editorWindow.caption = mode + " " + title
    editorWindow.build()

    def onCancel = {ClickEvent event ->
      getWindow().removeWindow editorWindow
    } as ClickListener

    editorWindow.btnCancel.addListener(onCancel)

    def onSave = {ClickEvent event ->
      try {
        editorWindow.form.commit()

        if (entityName == "partyRole") {
          if(id==0) personBean.addToPartyRoles(entityItem)

        }else if(entityName=="partyClassification"){
          if(id==0) personBean.addToPartyClassifications(entityItem)


        }else if(entityName=="partyPostalAddress"){
          if(id==0) personBean.addToPartyPostalAddresses(entityItem)


        }else if(entityName=="partyContact"){
          if(id==0) personBean.addToPartyContacts(entityItem)

        }
        personForm.commit()
        //partyService.savePerson(personBean)
        this.recordId = personBean.id
        getWindow().removeWindow editorWindow

        refreshUIData()
      } catch (e) {

        getWindow().showNotification e.getMessage(), Notification.TYPE_ERROR_MESSAGE
        e.printStackTrace()
      }
    } as ClickListener

    editorWindow.btnSave.addListener(onSave)


    def onDelete = {ClickEvent event ->
      try {
        editorWindow.form.commit()
        //commonService.deleteCodeDecode codeDecode
        getWindow().removeWindow editorWindow
        getWindow().showNotification "Entity Deleted"
        refreshUIData()
      } catch (e) {

        getWindow().showNotification e.getMessage(), Notification.TYPE_WARNING_MESSAGE
      }
    } as ClickListener

    editorWindow.btnDelete.addListener(onDelete)

    getWindow().addWindow editorWindow

  }

  private PartyRole getPartyRole(int id) {
    for (item in personBean.partyRoles) {
      if (item.id == id) return item
    }
    return new PartyRole()
  }

  private PartyClassification getPartyClass(int id) {
    for (item in personBean.partyClassifications) {
      if (item.id == id) return item
    }
    return new PartyClassification()
  }

  private PartyPostalAddress getPartyPostal(int id) {
    for (item in personBean.partyPostalAddresses) {
      if (item.id == id) return item
    }
    return new PartyPostalAddress()
  }

  private PartyContact getPartyContact(int id){
    for (item in personBean.partyContacts) {
      if (item.id == id) return item
    }
    return new PartyContact()

  }

  void buttonClick(ClickEvent clickEvent) {

  }

}