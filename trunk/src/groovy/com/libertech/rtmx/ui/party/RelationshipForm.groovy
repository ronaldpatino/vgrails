package com.libertech.rtmx.ui.party

import org.zhakimel.vgrails.util.UIBuilder
import org.zhakimel.vgrails.util.AppManager
import com.libertech.rtmx.ui.MainApp
import com.libertech.rtmx.service.PartyService
import com.libertech.rtmx.service.LoggerService
import com.libertech.rtmx.service.CommonService
import com.vaadin.ui.Panel
import com.vaadin.ui.Button
import org.zhakimel.vgrails.component.GrailsForm
import org.zhakimel.vgrails.component.GrailsTable
import com.vaadin.ui.Component
import com.vaadin.ui.GridLayout
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Alignment
import com.vaadin.ui.Button.ClickEvent
import com.libertech.rtmx.ui.util.AppConstant
import com.vaadin.ui.Button.ClickListener
import com.vaadin.ui.Window.Notification
import com.vaadin.terminal.ThemeResource
import com.libertech.erp.model.party.Party
import com.libertech.erp.model.party.relationship.PartyRelationship
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.TextField
import com.libertech.erp.model.party.Person
import org.zhakimel.vgrails.component.Ruler
import com.vaadin.ui.Label
import org.zhakimel.vgrails.component.Spacer
import org.zhakimel.vgrails.component.LookupTableWindow

import com.libertech.erp.model.party.PartyRole

class RelationshipForm extends Panel implements UIBuilder, Button.ClickListener {

  int recordId
  MainApp app
  AppManager manager
  CommonService commonService
  LoggerService loggerService
  PartyService partyService

  Button btnSave, btnDelete, btnCancel
  Button btnSelectFromParty, btnSelectToParty
  GrailsForm formFromParty, formToParty
  GrailsForm formRelationship
  GrailsTable tblFromPartyRole, tblToPartyRole

  Party fromPartyBean, toPartyBean
  PartyRelationship partyRelationshipBean
  List partyRelationshipTypes
  List fromPartyRoles
  List toPartyRoles

  LookupTableWindow ltwParty;
  Party selectedParty;
  TextField txtFromParty, txtToParty
  String strRelationshipType

  def fdfFromParty = [:], fdfToParty = [:], fdfRelationship = [:]
  def tdfFromRole = ["roleType": String.class], tdfToRole = ["roleType": String.class]

  def RelationshipForm(String caption, int recordId, AppManager manager) {
    super(caption);
    this.recordId = recordId
    this.app = manager.app
    this.manager = manager

    commonService = manager.serviceFactory.getCommonService()
    loggerService = manager.serviceFactory.getLoggerService()
    partyService = manager.serviceFactory.getPartyService()


    partyRelationshipTypes = partyService.listAllPartyRelationshipType()
    //feed data
    if (recordId) {


       partyRelationshipBean = partyService.editPartyRelationship(recordId)
      fromPartyBean = partyRelationshipBean.fromPartyRole.party
      toPartyBean = partyRelationshipBean.toPartyRole.party
      fromPartyRoles = fromPartyBean.partyRoles.asList()
      toPartyRoles = toPartyBean.partyRoles.asList()

    } else {
      fromPartyBean = new Person()
      toPartyBean = new Person()
      partyRelationshipBean = new PartyRelationship()
      partyRelationshipBean.description="A Relationship"
      fromPartyRoles = partyService.listAllPartyRole()
      toPartyRoles = partyService.listAllPartyRole()
    }
    manager.disableNavigation()
  }

  def void build() {
    VerticalLayout vl = (VerticalLayout) getContent()
    vl.setSizeFull()
    vl.setSpacing true
    vl.addComponent buildCrudButton()
    vl.addComponent new Spacer()

    GridLayout gl = new GridLayout(3, 1)
    gl.setColumnExpandRatio 1, 1.0f
    gl.setSpacing true

    def p1 = buildPartyForm("fromParty")
    def p2 = buildRelationshipForm()
    def p3 = buildPartyForm("toParty")

    gl.addComponent p1
    gl.addComponent p2
    gl.addComponent p3

    vl.addComponent gl
  }

  private Component buildCrudButton() {
    def grd = new GridLayout(2, 1)

    grd.setColumnExpandRatio 0, 1.0f
    grd.setSizeFull()

    def hl1 = new HorizontalLayout()
    grd.addComponent hl1
    grd.setComponentAlignment hl1, Alignment.MIDDLE_LEFT

    def hl2 = new HorizontalLayout()
    hl2.setSpacing true


    btnSave = new Button(AppConstant.STR_SAVE, this)
    btnSave.icon = new ThemeResource(AppConstant.ICON_SAVE)
    hl2.addComponent btnSave
    hl2.setComponentAlignment btnSave, Alignment.MIDDLE_RIGHT


    btnDelete = new Button(AppConstant.STR_DELETE, this)
    btnDelete.icon = new ThemeResource(AppConstant.ICON_DELETE)
    hl2.addComponent btnDelete
    hl2.setComponentAlignment btnDelete, Alignment.MIDDLE_RIGHT
    def onDelete = {ClickEvent event ->

    } as ClickListener
    btnDelete.addListener onDelete

    btnCancel = new Button(AppConstant.STR_CANCEL, this)
    btnCancel.icon = new ThemeResource(AppConstant.ICON_CANCEL)
    hl2.addComponent btnCancel
    hl2.setComponentAlignment btnCancel, Alignment.MIDDLE_RIGHT

    def onCancel = {ClickEvent event ->
      /* if(orgBean.id){
        partyService.cancelEditOrganization(orgBean.id.intValue())
      }*/
      manager.enableNavigation()
      app.contentPanel.removeAllComponents()
      app.contentPanel.addComponent manager.getContentPanel(AppConstant.NAV_PARTY, "Relationship")

    } as ClickListener

    btnCancel.addListener onCancel

    grd.addComponent hl2
    return grd
  }

  private Component buildRelationshipForm() {

    fdfRelationship = [
            "bean": partyRelationshipBean,
            "fields": ["partyRelationshipType", "description", "fromDate", "thruDate"],
            "listobjectfields": ["partyRelationshipType": partyRelationshipTypes]
    ]

    formRelationship = new GrailsForm(fdfRelationship)
    formRelationship.hasCrudButton = false
    formRelationship.buildForm()

    return formRelationship
  }

  private Component buildPartyForm(String forWhat) {

    VerticalLayout vl = new VerticalLayout()
    vl.setSpacing true
    vl.setMargin true
    vl.setStyleName "blue"
    def title = (forWhat == "fromParty" ? "From Party" : "To Party")
    vl.addComponent new Label(title)
    HorizontalLayout hl = new HorizontalLayout()
    hl.setSpacing true
    if(forWhat=="fromParty"){
      txtFromParty = new TextField()
      txtFromParty.value=fromPartyBean
      hl.addComponent txtFromParty
    }else{
      txtToParty = new TextField()
      txtToParty.value = toPartyBean
      hl.addComponent txtToParty
    }

    def onSelect = {ClickEvent event ->


      try {
        formRelationship.commit()

      strRelationshipType = partyRelationshipBean.partyRelationshipType.name
      println "strRelationshipType="+strRelationshipType
      showPartyLookupWindow forWhat
      } catch (e) {
        getWindow().showNotification  e.getMessage(),Notification.TYPE_WARNING_MESSAGE
      }


    } as ClickListener
    def btnSelect = new Button("Select..")
    btnSelect.addListener onSelect
    hl.addComponent btnSelect

    vl.addComponent hl
    vl.addComponent new Ruler()

    if (forWhat == "fromParty") {

      vl.addComponent new Label("<i>Select Role:</i>", Label.CONTENT_XHTML)
      tblFromPartyRole = new GrailsTable(tdfFromRole)
      tblFromPartyRole.setHeight "150px"
      tblFromPartyRole.setWidth "200px"
      tblFromPartyRole.hasEditButton = false
      tblFromPartyRole.selectable = true
      tblFromPartyRole.entityListData = fromPartyRoles
      tblFromPartyRole.refreshTableData()
      vl.addComponent tblFromPartyRole

    } else if (forWhat == "toParty") {

      vl.addComponent new Label("<i>Select Role:</i>", Label.CONTENT_XHTML)

      tblToPartyRole = new GrailsTable(tdfToRole)
      tblToPartyRole.setHeight "150px"
      tblToPartyRole.setWidth "200px"
      tblToPartyRole.selectable = true
      tblToPartyRole.hasEditButton = false
      tblToPartyRole.entityListData = toPartyRoles
      tblToPartyRole.refreshTableData()
      vl.addComponent tblToPartyRole
    }
    return vl
  }

  void showPartyLookupWindow(String forWhat) {


    if (forWhat == "fromParty") {


      if(strRelationshipType.equals("PERSONTOPERSON") ||strRelationshipType.equals("PERSONTOORG") ){
        ltwParty = new LookupTableWindow("Select From Person", ["firstName": String.class, "lastName": String.class])
        ltwParty.setEntityListData(partyService.findAllPerson())
      }else if(strRelationshipType.equals("ORGTOORG")){
         ltwParty = new LookupTableWindow("Select From Organization", ["orgName": String.class, "legalTaxId": String.class])
         ltwParty.setEntityListData(partyService.findAllOrganization())
      }

      def onCancel = {Button.ClickEvent event ->
        getWindow().removeWindow ltwParty
      } as Button.ClickListener

      def onSelect = {Button.ClickEvent event ->
        selectedParty = ltwParty.getEntityListData()[ltwParty.getSelectedValue()]
        if (selectedParty) fromPartyBean = selectedParty
        getWindow().removeWindow ltwParty
        txtFromParty.value=fromPartyBean
         tblFromPartyRole.entityListData = fromPartyBean.partyRoles.asList()
         tblFromPartyRole.refreshTableData()
      } as Button.ClickListener

      ltwParty.btnSelect.addListener onSelect
      ltwParty.btnCancel.addListener onCancel
      ltwParty.build()


      getWindow().addWindow ltwParty

    } else if (forWhat == "toParty") {
    
      if(strRelationshipType=="PERSONTOPERSON"  ){
        ltwParty = new LookupTableWindow("Select From Person", ["firstName": String.class, "lastName": String.class])
        ltwParty.setEntityListData(partyService.findAllPerson())
      }else if(strRelationshipType.equals("ORGTOORG")|| strRelationshipType.equals("PERSONTOORG") ){
         ltwParty = new LookupTableWindow("Select From Organization", ["orgName": String.class, "legalTaxId": String.class])
         ltwParty.setEntityListData(partyService.findAllOrganization())
      }


      def onCancel = {Button.ClickEvent event ->
        getWindow().removeWindow ltwParty
      } as Button.ClickListener

      def onSelect = {Button.ClickEvent event ->
        selectedParty = ltwParty.getEntityListData()[ltwParty.getSelectedValue()]
        if (selectedParty) toPartyBean = selectedParty
        getWindow().removeWindow ltwParty
        txtToParty.value=toPartyBean

        tblToPartyRole.entityListData = toPartyBean.partyRoles.asList()
        tblToPartyRole.refreshTableData()


      } as Button.ClickListener

      ltwParty.btnSelect.addListener onSelect
      ltwParty.btnCancel.addListener onCancel


      ltwParty.build()
      getWindow().addWindow ltwParty

    }
  }

  void buttonClick(ClickEvent clickEvent) {
    Button btn = clickEvent.button
    if(btn.caption.equals(AppConstant.STR_SAVE)){
      PartyRole selfrom=tblFromPartyRole.getValue()
      PartyRole selto=tblToPartyRole.getValue()

      if(selfrom==null || selto==null){
        getWindow().showNotification "Each Party Role must be selected",Notification.TYPE_WARNING_MESSAGE
        return
      }


      try {
        formRelationship.commit()
        partyRelationshipBean.fromPartyRole = selfrom
        partyRelationshipBean.toPartyRole = selto
        partyService.savePartyRelationship(partyRelationshipBean)
        manager.enableNavigation()
        app.contentPanel.removeAllComponents()
        def panel = manager.getContentPanel(AppConstant.NAV_PARTY, "Relationship")
        app.contentPanel.addComponent panel
        panel.refreshUIData()
      } catch (e) {
        e.printStackTrace()
        getWindow().showNotification e.getMessage(),Notification.TYPE_WARNING_MESSAGE
      }

    }else if(btn.caption.equals(AppConstant.STR_DELETE)){




  }else if(btn.caption.equals(AppConstant.STR_CANCEL)){
      partyService.cancelEditPartyRelationship(partyRelationshipBean.id)
      manager.enableNavigation()
      app.contentPanel.removeAllComponents()
      app.contentPanel.addComponent manager.getContentPanel(AppConstant.NAV_PARTY, "Relationship")
    }

  }

  def boolean isBuilt() {
    return false;
  }

  def void refreshUIData() {

  }

}
