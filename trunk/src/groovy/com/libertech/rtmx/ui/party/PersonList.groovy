package com.libertech.rtmx.ui.party

import com.vaadin.ui.Panel
import org.zhakimel.vgrails.util.UIBuilder

import com.libertech.rtmx.service.LoggerService
import com.libertech.rtmx.service.CommonService
import com.libertech.rtmx.service.PartyService
import com.vaadin.data.util.BeanItem
import org.zhakimel.vgrails.component.GrailsTable
import com.libertech.erp.model.party.Person
import com.vaadin.ui.GridLayout
import com.vaadin.ui.ComboBox
import com.vaadin.ui.Alignment
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Button
import com.vaadin.ui.Label
import org.zhakimel.vgrails.component.Spacer
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.terminal.ThemeResource
import com.libertech.rtmx.ui.util.AppConstant
import com.libertech.rtmx.ui.MainApp
import org.zhakimel.vgrails.util.AppManager
import com.libertech.rtmx.service.LockService
import com.vaadin.ui.Window.Notification

class PersonList extends Panel implements UIBuilder, Button.ClickListener {

  /**
   * services
   */
  MainApp app
  AppManager manager
  CommonService commonService
  LoggerService loggerService
  PartyService partyService
  LockService lockService



  /**
  * UI define
   */
  def personShowFields = [ "firstName":String.class, "lastName":String.class, "gender":String.class,
          "birthDate":Date.class,"birthPlace":String.class,"socialSecurityNo":String.class,"passportNo":String.class]

  BeanItem personItem


  /**
  * entity data
  */
  Person person;

  /**
   * widget data
   */

  GrailsTable gTable;

  private boolean built = false

  def PersonList(caption, MainApp app) {
    super(caption);
     this.app = app
      manager = app.manager
      commonService = manager.serviceFactory.getCommonService()
      loggerService = manager.serviceFactory.getLoggerService()
      partyService = manager.serviceFactory.getPartyService()
      lockService = manager.serviceFactory.getLockService()



    setIcon new ThemeResource(AppConstant.ICON_PERSON_32)
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

       gTable = new GrailsTable(personShowFields)
       addComponent gTable

       refreshUIData()

    built = true
  }

  def boolean isBuilt() {
    return built;
  }

  def void refreshUIData() {

    def lst = partyService.findAllPersonInRole()
    gTable.entityListData = lst

    gTable.editButtonClickListener={Button.ClickEvent event ->
                 def button = event.button
                 def id = new Integer(button.description.substring(5))
                 if(id){
                   if(lockService.isLocked("Person",id)){
                     getWindow().showNotification "this person record is locked, cannot continue",Notification.TYPE_WARNING_MESSAGE
                      return
                   }
                 }
                 def personForm =  new PersonForm("Edit Person",id,this.app)
                 app.contentPanel.removeAllComponents()
                 app.contentPanel.addComponent personForm
                 personForm.build()
                 personForm.refreshUIData()

            } as Button.ClickListener

    gTable.refreshTableData()
    person= new Person()

  }

  void buttonClick(ClickEvent clickEvent) {

  }


}
