package com.libertech.rtmx.ui.party

import com.vaadin.ui.Button
import org.zhakimel.vgrails.util.UIBuilder
import com.vaadin.ui.Panel
import com.vaadin.ui.Button.ClickEvent
import org.zhakimel.vgrails.component.GrailsForm

import com.libertech.rtmx.service.LoggerService
import com.libertech.rtmx.service.CommonService
import com.libertech.rtmx.service.PartyService
import com.libertech.rtmx.model.party.Organization
import com.vaadin.ui.Button.ClickListener
import com.libertech.rtmx.ui.util.AppConstant
import com.vaadin.ui.Window.Notification

import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Alignment
import com.vaadin.ui.Component
import org.zhakimel.vgrails.component.GrailsTable
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.TabSheet
import com.libertech.rtmx.model.party.PartyRole
import com.libertech.rtmx.model.party.PartyClassification
import org.zhakimel.vgrails.component.GrailsFormWindow
import com.vaadin.ui.DefaultFieldFactory
import com.libertech.rtmx.model.party.PartyPostalAddress
import com.libertech.rtmx.model.party.PartyContact
import com.libertech.rtmx.model.party.RoleType
import com.libertech.rtmx.model.party.PartyType
import com.libertech.rtmx.ui.MainApp
import com.libertech.rtmx.ui.util.AppManager
import org.zhakimel.vgrails.app.AppPanel


class OrganizationForm extends AppPanel implements UIBuilder, Button.ClickListener {
   MainApp app
  AppManager manager

  CommonService commonService
  LoggerService loggerService
  PartyService partyService


  GrailsForm orgForm
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

  List orgShowFields = ["subTypeCode", "orgName", "legalTaxId", "creditRating"]
  List roleShowFields = ["roleType", "fromDate", "thruDate"]
  Map roleTableShowFields = ["roleType": RoleType.class, "fromDate": Date.class, "thruDate": Date.class]

  List classShowFields = ["partyType", "fromDate", "thruDate"]
  Map classTableShowFields = ["partyType": PartyType.class, "fromDate": Date.class, "thruDate": Date.class]

  List postalShowFields = ["fromDate", "thruDate", "address1","address2","city","postalCode","province","country"]
  Map postalTableShowFields = ["fromDate": Date.class, "thruDate": Date.class, "address1":String.class, "city":String.class, "postalCode":String.class]

  List contactShowFields = ["fromDate", "thruDate", "homePhone","cellPhone","email","comment"]
  Map contactTableShowFields = ["fromDate": Date.class, "thruDate": Date.class, "homePhone":String.class, "cellPhone":String.class, "email":String.class]

  List positionShowFields      = ["estimatedFromDate", "estimatedThruDate", "salaryFlag","exemptFlag","fulltimeFlag","temporaryFlag","actualFromDate","actualThruDate"]
  Map positionTableShowFields = ["estimatedFromDate": Date.class, "estimatedThruDate": Date.class, "salaryFlag":Boolean.class, "exemptFlag":Boolean.class, "fulltimeFlag":Boolean.class,"temporaryFlag":Boolean.class]

  List fulfillmentShowFields = ["fromDate", "thruDate", "description"]
  Map fulfillmentTableShowFields = ["fromDate": Date.class, "thruDate": Date.class, "description":String.class]


  Map orgListValues = [:]
  Map listValuesForRole=[:]

  Organization orgBean

  int recordId = 0
  boolean built = false

  def OrganizationForm(caption, recordId,MainApp app) {
    super(caption);
     this.app = app
    manager = this.app.manager
  manager.disableNavigation()

  commonService = manager.serviceFactory.getCommonService()
  loggerService = manager.serviceFactory.getLoggerService()
  partyService = manager.serviceFactory.getPartyService()

    this.recordId = recordId

    if (recordId == 0) {
      orgBean = new Organization()
    } else {
      orgBean = partyService.getOrganization(recordId)
    }

    orgListValues = getListValuesForOrg()
    listValuesForRole = getListValuesForRole()

  }

  private def getListValuesForOrg(){

    def lst = commonService.listPartyCodeDecode()
       def vals = [:]
       lst.each {
         if (it.cdCode.startsWith("ORGANIZATION"))
           vals[it.cdCode] = it.cdValue
       }
       Map map=[:]
       map["subTypeCode"] = vals
       return map
  }

  private def getListValuesForRole(){

    def lst = commonService.listOrgRoleCodeDecode()
       def vals = [:]
       lst.each {
           vals[it.cdCode] = it.cdValue
       }
       Map map=[:]
       map["subTypeCode"] = vals
       return map
  }



  void build() {
    def vl = innerPanel

    orgForm = buildOrgForm()
    buildCrudButton()
    def hl = new HorizontalLayout()
    hl.setWidth "100%"

    def tabs = new TabSheet()
    tabs.setStyleName "minimal"

    hl.addComponent orgForm
    tabs.addTab(buildRoleTable(), "Roles", null)
    tabs.addTab(buildClassificationTable(), "Classification", null)
    hl.addComponent tabs

    hl.setExpandRatio orgForm,5
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


    GrailsForm form = new GrailsForm(orgShowFields, orgBean)
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

   def buildCrudButton() {
    def onSave = {ClickEvent event ->

      orgForm.commit()

      try {
        partyService.saveOrganization(orgBean)
        getWindow().showNotification "Organization record saved"
        app.contentPanel.removeAllComponents()

        def panel = manager.getContentPanel(AppConstant.NAV_PARTY, "Organization")
        manager.setContent panel
        panel.refreshUIData()
        manager.enableNavigation()
      }
      catch (e) {
        getWindow().showNotification e.getMessage(), Notification.TYPE_WARNING_MESSAGE
      }

    } as ClickListener


    appButtonBar.btnSave.addListener onSave

    def onDelete = {ClickEvent event ->

    } as ClickListener
    appButtonBar.btnDelete.addListener onDelete


    def onCancel = {ClickEvent event ->
      if(orgBean.id){
        partyService.cancelEditOrganization(orgBean.id.intValue())
      }
       def panel = manager.getContentPanel(AppConstant.NAV_PARTY, "Organization")
       manager.setContent panel
       manager.enableNavigation()

    } as ClickListener

    appButtonBar.btnCancel.addListener onCancel

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

    if (orgBean.partyRoles) {
      roleTable.entityListData = orgBean.partyRoles.asList()
      roleTable.refreshTableData()
    }

    if (orgBean.partyClassifications) {
      classTable.entityListData = orgBean.partyClassifications.asList()
      classTable.refreshTableData()
    }

    if (orgBean.partyPostalAddresses) {
        println "POSTAL TABLE="+postalTable
        postalTable.entityListData = orgBean.partyPostalAddresses.asList()
        postalTable.refreshTableData()
      }

   if (orgBean.partyContacts) {
        contactTable.entityListData = orgBean.partyContacts.asList()
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
      List<RoleType> roleTypeEOV = partyService.findAllOrganizationRoleType()
      editorWindow.form.setEntityObjectValues(["roleType":roleTypeEOV] )
    }else if(entityName == "partyClassification") {
      List<PartyType> partyTypeEOV = partyService.findAllOrganizationPartyType()
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
          if(id==0) orgBean.addToPartyRoles(entityItem)

        }else if(entityName=="partyClassification"){
          if(id==0) orgBean.addToPartyClassifications(entityItem) 


        }else if(entityName=="partyPostalAddress"){
          if(id==0) orgBean.addToPartyPostalAddresses(entityItem)


        }else if(entityName=="partyContact"){
          if(id==0) orgBean.addToPartyContacts(entityItem)

        }
        orgForm.commit()
        partyService.saveOrganization(orgBean)
        this.recordId = orgBean.id
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
    for (item in orgBean.partyRoles) {
      if (item.id == id) return item
    }
    return new PartyRole()
  }

  private PartyClassification getPartyClass(int id) {
    for (item in orgBean.partyClassifications) {
      if (item.id == id) return item
    }
    return new PartyClassification()
  }

  private PartyPostalAddress getPartyPostal(int id) {
    for (item in orgBean.partyPostalAddresses) {
      if (item.id == id) return item
    }
    return new PartyPostalAddress()
  }

  private PartyContact getPartyContact(int id){
    for (item in orgBean.partyContacts) {
      if (item.id == id) return item
    }
    return new PartyContact()

  }
  
  void buttonClick(ClickEvent clickEvent) {

  }

}
