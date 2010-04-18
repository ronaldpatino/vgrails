package com.libertech.rtmx.ui.party

import com.libertech.rtmx.model.party.PartyRelationship
import com.libertech.rtmx.service.CommonService
import com.libertech.rtmx.service.LoggerService
import com.libertech.rtmx.service.PartyService
import com.libertech.rtmx.ui.MainApp
import org.zhakimel.vgrails.component.GrailsTable
import com.libertech.rtmx.ui.util.AppConstant
import com.libertech.rtmx.ui.util.AppManager
import org.zhakimel.vgrails.util.UIBuilder
import com.vaadin.terminal.ThemeResource
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.*
import org.zhakimel.vgrails.component.GrailsCustomTable
import com.libertech.rtmx.service.LockService
import com.vaadin.ui.Window.Notification

class RelationshipList extends Panel implements UIBuilder, Button.ClickListener {

  MainApp app
  AppManager manager

  CommonService commonService
  LoggerService loggerService
  PartyService partyService
  LockService lockService

  TabSheet tabSheet
  boolean built = false

  GrailsCustomTable orgToOrgTable
  GrailsTable personToPersonTable
  GrailsTable orgToPersonTable

  List<PartyRelationship> lstOrgToOrg
  List<PartyRelationship> lstPersonToPerson
  List<PartyRelationship> lstPersonToOrg


  def orgToOrgFieldMap = [
          "partyRelationshipType": String.class, "fromPartyRole": String.class, "toPartyRole": String.class, "fromDate": Date.class, "thruDate": Date.class
  ]

  def personToPersonFieldMap = [
          "partyRelationshipType": String.class, "fromPartyRole": String.class, "toPartyRole": String.class, "fromDate": Date.class, "thruDate": Date.class
  ]

  def orgToPersonFieldMap = [
          "partyRelationshipType": String.class, "fromPartyRole": String.class, "toPartyRole": String.class, "fromDate": Date.class, "thruDate": Date.class
  ]

  Button btnNew, btnEdit, btnRefresh

  def RelationshipList(caption, MainApp app) {

    super(caption);
    this.app = app
    this.manager = this.app.manager

    commonService = manager.serviceFactory.getCommonService()
    loggerService = manager.serviceFactory.getLoggerService()
    partyService = manager.serviceFactory.getPartyService()
    lockService = manager.serviceFactory.getLockService()

    setWidth "100%"
  }

  def void build() {

    VerticalLayout vl = (VerticalLayout) this.getContent()
    vl.setSpacing true

    def hl = new HorizontalLayout()
    def hleft = new HorizontalLayout()

    def hright = new HorizontalLayout()
    hright.setSpacing true
    btnNew = new Button(AppConstant.STR_NEW, this)
    btnNew.setIcon(new ThemeResource(AppConstant.ICON_DOCUMENT))
    hright.addComponent btnNew

    btnEdit = new Button(AppConstant.STR_EDIT, this)
    btnEdit.setIcon(new ThemeResource(AppConstant.ICON_EDIT))
    hright.addComponent btnEdit

    hright.addComponent new Label("|", Label.CONTENT_XHTML)
    hright.addComponent new Button(AppConstant.STR_REFRESH)

    hl.addComponent hleft
    hl.addComponent hright
    hl.setSpacing true
    hl.setWidth "100%"
    hl.setExpandRatio hleft, 1

    orgToOrgTable = buildOrgToOrgTable()
    personToPersonTable = buildPersonToPersonTable()
    orgToPersonTable = buildOrgToPersonTable()

    tabSheet = new TabSheet()
    tabSheet.setWidth "100%"
    tabSheet.setSizeFull()
    tabSheet.addTab(orgToOrgTable, "Organization to Organization", null)
    tabSheet.addTab(personToPersonTable, "Person To Person", null)
    tabSheet.addTab(orgToPersonTable, "Person To Organization", null)

    vl.addComponent hl
    vl.addComponent tabSheet

    built = true
  }

  def boolean isBuilt() {

    return built
  }

  def void refreshUIData() {
    orgToOrgTable.entityListData = partyService.findAllOrgToOrgRelationship()

    orgToOrgTable.editButtonClickListener = {Button.ClickEvent event ->
      def button = event.button
      def id = new Integer(button.description.substring(5))
      if(!lockService.isLocked("PartyRelationship",id)){
        def relForm = new RelationshipForm("Edit Relationship", id,manager)
        manager.setContent relForm
        relForm.build()
        relForm.refreshUIData()
      }else{
        getWindow().showNotification "Party Relationship is being locked by another user",Notification.TYPE_WARNING_MESSAGE
      }

    } as Button.ClickListener


    orgToOrgTable.refreshTableData()

    orgToPersonTable.entityListData = partyService.findAllOrgToOrgRelationship()
    orgToPersonTable.refreshTableData()

    personToPersonTable.entityListData = partyService.findAllOrgToOrgRelationship()
    personToPersonTable.refreshTableData()
  }


  Component buildOrgToOrgTable() {

    GrailsCustomTable table = new GrailsCustomTable(orgToOrgFieldMap)
    table.selectable=true
    table.hasEditButton=true
    table.setSizeFull()

    return table
  }

  Component buildPersonToPersonTable() {
    GrailsTable table = new GrailsTable(personToPersonFieldMap)
    table.selectable=true
    table.hasEditButton=false

    table.setSizeFull()

    return table
  }

  Component buildOrgToPersonTable() {
    GrailsTable table = new GrailsTable(orgToPersonFieldMap)
    table.selectable=true
    table.hasEditButton=false

    table.setSizeFull()

    return table
  }

  void buttonClick(ClickEvent clickEvent) {
    Button btn = clickEvent.button
    if (btn.caption == AppConstant.STR_NEW) {

      app.contentPanel.removeAllComponents()
      def relForm = new RelationshipForm("New Relationship", 0, manager)
      app.contentPanel.addComponent relForm
      relForm.build()
      relForm.refreshUIData()
    }
  }
}
