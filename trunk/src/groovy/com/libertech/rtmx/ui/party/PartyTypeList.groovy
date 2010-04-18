package com.libertech.rtmx.ui.party

import com.libertech.rtmx.service.CommonService
import com.libertech.rtmx.service.LoggerService
import com.libertech.rtmx.ui.MainApp
import org.zhakimel.vgrails.component.GrailsForm
import org.zhakimel.vgrails.component.GrailsFormWindow
import org.zhakimel.vgrails.component.GrailsTable
import org.zhakimel.vgrails.component.Spacer
import com.libertech.rtmx.ui.util.AppConstant
import com.libertech.rtmx.ui.util.AppManager
import org.zhakimel.vgrails.util.UIBuilder
import com.vaadin.data.Property
import com.vaadin.data.Property.ValueChangeEvent
import com.vaadin.data.util.BeanItem
import com.vaadin.terminal.ThemeResource
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.Button.ClickListener
import com.vaadin.ui.Window.Notification
import com.vaadin.ui.*
import com.libertech.rtmx.service.PartyService
import com.libertech.rtmx.model.party.PartyType

class PartyTypeList extends Panel implements UIBuilder, Button.ClickListener,Property.ValueChangeListener {

  MainApp app
  AppManager manager
  CommonService commonService
  LoggerService loggerService
  PartyService partyService

  boolean built;
  def groupList
  NativeSelect cbo;

  def partyTypeShowFields = ["name", "description"]

  def partyTypeFieldMap = [
              "name": String.class,
              "description": String.class
      ]


  PartyType partyType;

  GrailsTable gTable;
  GrailsForm gForm;

  GrailsFormWindow editorWindow;
  BeanItem partyTypeItem;
  final Form partyTypeForm;

  def PartyTypeList(String caption, AppManager manager) {
    super(caption)
   this.app = manager.app
   this.manager = manager
   commonService = this.manager.serviceFactory.getCommonService()
   loggerService = this.manager.serviceFactory.getLoggerService()
   partyService = this.manager.serviceFactory.getPartyService()

    setWidth "100%"
    //setIcon(new ThemeResource(AppConstant.ICON_DOCUMENT_TXT_32))
  }

  void build() {

    def grd = new GridLayout(2, 1)
    grd.setColumnExpandRatio 0, 1.0f
    grd.setSizeFull()
    grd.addComponent new Spacer()

    def hl2 = new HorizontalLayout()
    hl2.setSpacing true

    def addButton = new Button(AppConstant.STR_NEW, this)
    addButton.setIcon(new ThemeResource(AppConstant.ICON_DOCUMENT))
    hl2.addComponent addButton
    hl2.setComponentAlignment addButton, Alignment.MIDDLE_RIGHT

    def importButton = new Button("Import..", this)
    importButton.setIcon(new ThemeResource(AppConstant.ICON_FOLDER_ADD))
    hl2.addComponent importButton
    hl2.setComponentAlignment importButton, Alignment.MIDDLE_RIGHT

    def separator = new Label("|")
    hl2.addComponent separator
    hl2.setComponentAlignment separator, Alignment.MIDDLE_RIGHT


    def refreshButton = new Button("Refresh", this)
    hl2.addComponent refreshButton
    hl2.setComponentAlignment refreshButton, Alignment.MIDDLE_RIGHT
    refreshButton.setIcon(new ThemeResource(AppConstant.ICON_REFRESH))
    grd.addComponent hl2

    addComponent grd
    addComponent new Spacer()

    gTable = new GrailsTable(partyTypeFieldMap)
    addComponent gTable

    refreshUIData()
    this.built = true
  }

  boolean isBuilt() {
    return this.built;
  }

  void refreshUIData() {
    def partyTypeList = partyService.listAllPartyType()
    gTable.entityListData = partyTypeList

    gTable.editButtonClickListener={Button.ClickEvent event ->
              def button = event.button
              def id = new Integer(button.description.substring(5))
              showEditor(id)
         } as Button.ClickListener

    gTable.refreshTableData()
    partyType=new PartyType()
  }


  void buttonClick(ClickEvent clickEvent) {
    def button = clickEvent.button
    if (button.caption.equals(AppConstant.STR_NEW)) {
      showEditor(0)
    }else if(button.description.startsWith("edit")){
       def id = new Integer(button.description.substring(5))
      showEditor(id)
    }
  }


  void valueChange(ValueChangeEvent valueChangeEvent) {
  }



  void showEditor(int id) {
    def s="edit"
    if(id==0){
      s="add"
      partyType=new PartyType()
    }else{
      partyType = partyService.getPartyType(id)
    }

    editorWindow=new GrailsFormWindow(partyType,partyTypeShowFields,s)
    editorWindow.caption="Party Type"
    editorWindow.build()

    def onCancel={ClickEvent event ->
             getWindow().removeWindow editorWindow
    }as ClickListener

   editorWindow.btnCancel.addListener(onCancel)

    def onSave={ClickEvent event ->
       try{
           editorWindow.form.commit()
           getWindow().removeWindow editorWindow
           partyService.savePartyType partyType
           getWindow().showNotification "PartyType Saved"
           refreshUIData()
        }catch(e){

          getWindow().showNotification e.getMessage(),Notification.TYPE_ERROR_MESSAGE

       }
    }as ClickListener

    editorWindow.btnSave.addListener(onSave)


    def onDelete={ClickEvent event ->
          try{
              editorWindow.form.commit()
              getWindow().removeWindow editorWindow
              partyService.deletePartyType partyType
              getWindow().showNotification "CodeDecode Deleted"
              refreshUIData()
           }catch(e){

             getWindow().showNotification e.getMessage(),Notification.TYPE_ERROR_MESSAGE

          }
       }as ClickListener

     editorWindow.btnDelete.addListener(onDelete)

    getWindow().addWindow editorWindow

  }
}