package com.libertech.rtmx.ui.party

import com.libertech.rtmx.service.CommonService
import com.libertech.rtmx.service.LoggerService
import com.libertech.rtmx.service.PartyService
import org.zhakimel.vgrails.component.GrailsTable
import org.zhakimel.vgrails.component.Spacer
import com.libertech.rtmx.ui.util.AppConstant

import org.zhakimel.vgrails.util.UIBuilder
import com.vaadin.data.util.BeanItem
import com.vaadin.terminal.ThemeResource
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.*
import com.libertech.rtmx.model.party.Organization
import com.libertech.rtmx.ui.MainApp
import com.libertech.rtmx.ui.util.AppManager

class OrganizationList extends Panel implements UIBuilder, Button.ClickListener {

  /**
   * services
   */

  MainApp app
  AppManager manager
  CommonService commonService
  LoggerService loggerService
  PartyService partyService

  /**
   * UI define
   */
  def orgShowFields = ["subTypeCode": String.class, "orgName": String.class, "legalTaxId": String.class]

  BeanItem orgItem

  /**
   * entity data
   */
  Organization organization;

  /**
   * widget data
   */

  GrailsTable gTable;

  private boolean built = false

  def OrganizationList(caption, MainApp app) {
    super(caption);

    this.app = app
    manager = this.app.manager
    commonService = manager.serviceFactory.getCommonService()
    loggerService = manager.serviceFactory.getLoggerService()
    partyService = manager.serviceFactory.getPartyService()

    setIcon new ThemeResource(AppConstant.ICON_ORGANIZATION_32)
  }

  def void build() {
    def grd = new GridLayout(2, 1)
    grd.setColumnExpandRatio 0, 1.0f
    grd.setSizeFull()

    ComboBox cbo = new ComboBox();
    cbo.setWidth "150px"

    grd.addComponent cbo
    grd.setComponentAlignment cbo, Alignment.MIDDLE_LEFT

    def hl2 = new HorizontalLayout()
    hl2.setSpacing true

    def addButton = new Button(AppConstant.STR_NEW, this)
    addButton.icon = new ThemeResource(AppConstant.ICON_DOCUMENT)
    hl2.addComponent addButton
    hl2.setComponentAlignment addButton, Alignment.MIDDLE_RIGHT

    def separator = new Label("|")
    hl2.addComponent separator
    hl2.setComponentAlignment separator, Alignment.MIDDLE_RIGHT


    def refreshButton = new Button(AppConstant.STR_REFRESH, this)
    refreshButton.icon = new ThemeResource(AppConstant.ICON_REFRESH)
    hl2.addComponent refreshButton
    hl2.setComponentAlignment refreshButton, Alignment.MIDDLE_RIGHT

    grd.addComponent hl2

    addComponent grd
    addComponent new Spacer()

    gTable = new GrailsTable(orgShowFields)
    addComponent gTable

    refreshUIData()

    built = true
  }

  def boolean isBuilt() {
    return built;
  }

  def void refreshUIData() {

    def lst = partyService.findAllOrganization()
    gTable.entityListData = lst

    gTable.editButtonClickListener = {Button.ClickEvent event ->
      def button = event.button
      def id = new Integer(button.description.substring(5))
      def orgForm = new OrganizationForm("Edit Organization", id,app)
      app.contentPanel.removeAllComponents()
      app.contentPanel.addComponent orgForm
      orgForm.build()
      orgForm.refreshUIData()


    } as Button.ClickListener

    gTable.refreshTableData()
    organization = new Organization()

  }

  void buttonClick(ClickEvent clickEvent) {
    def button = clickEvent.button
    if (button.caption == AppConstant.STR_NEW) {
      app.contentPanel.removeAllComponents()
      def orgForm = new OrganizationForm("Edit Organization", 0,app)
      app.contentPanel.addComponent orgForm
      orgForm.build()
      orgForm.refreshUIData()

    } else if (button.caption == AppConstant.STR_NEW) {
      refreshUIData()
      getWindow().showNotification "Organization list loaded"
    }
  }


}