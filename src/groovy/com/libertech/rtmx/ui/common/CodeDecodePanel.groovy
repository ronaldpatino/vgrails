package com.libertech.rtmx.ui.common

import com.vaadin.ui.Panel
import org.zhakimel.vgrails.util.UIBuilder
import com.vaadin.ui.HorizontalLayout

import com.vaadin.ui.Button
import com.vaadin.ui.Alignment
import org.zhakimel.vgrails.component.Spacer
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.GridLayout

import com.vaadin.ui.Label

import com.libertech.rtmx.service.LoggerService
import com.libertech.rtmx.service.CommonService

import com.vaadin.data.util.BeanItem
import com.libertech.rtmx.model.common.CodeDecode
import com.vaadin.ui.Form
import com.vaadin.ui.Button.ClickListener

import com.vaadin.ui.Window.Notification

import org.zhakimel.vgrails.component.GrailsTable
import org.zhakimel.vgrails.component.GrailsForm
import com.vaadin.terminal.ThemeResource
import com.libertech.rtmx.ui.util.AppConstant

import com.vaadin.data.Property
import com.vaadin.data.Property.ValueChangeEvent
import org.zhakimel.vgrails.component.GrailsFormWindow
import com.vaadin.ui.NativeSelect
import com.libertech.rtmx.ui.util.AppManager
import com.libertech.rtmx.ui.MainApp

class CodeDecodePanel extends Panel implements UIBuilder, Button.ClickListener,Property.ValueChangeListener {

  MainApp app
  AppManager manager
  CommonService commonService
  LoggerService loggerService 

  boolean built;
  def groupList
  NativeSelect cbo;

  def codeDecodeShowFields = ["cdGroup", "cdCode", "cdValue", "description"]

  def codeDecodeFieldMap = [
              "cdGroup": String.class,
              "cdCode": String.class,
              "cdValue": String.class,
              "description": String.class
      ]


  CodeDecode codeDecode;

  GrailsTable gTable;
  GrailsForm gForm;

  GrailsFormWindow editorWindow;
  BeanItem codeDecodeItem;
  final Form codeDecodeForm;

  def CodeDecodePanel(String caption, MainApp app) {
    super(caption)
   this.app = app 
   this.manager = this.app.manager
   commonService = manager.serviceFactory.getCommonService()
   loggerService = manager.serviceFactory.getLoggerService()


    setWidth "100%"
    //setIcon(new ThemeResource(AppConstant.ICON_DOCUMENT_TXT_32))
  }

  void build() {

    def grd = new GridLayout(2, 1)
    grd.setColumnExpandRatio 0, 1.0f
    grd.setSizeFull()

    cbo = new NativeSelect();
    cbo.addListener this
    cbo.setImmediate true
    cbo.setWidth "200px"

    grd.addComponent cbo
    grd.setComponentAlignment cbo, Alignment.MIDDLE_LEFT

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

    gTable = new GrailsTable(codeDecodeFieldMap)
    addComponent gTable
    
    refreshUIData()
    this.built = true
  }

  boolean isBuilt() {
    return this.built;
  }

  void refreshUIData() {
    groupList = commonService.listCodeDecodeGroup()
    cbo.addItem "ALL"
    groupList.each {
      cbo.addItem it
    }
    def codeDecodeList = commonService.listCodeDecode("")
    gTable.entityListData = codeDecodeList

    gTable.editButtonClickListener={Button.ClickEvent event ->
              def button = event.button
              def id = new Integer(button.description.substring(5))
              showEditor(id)
         } as Button.ClickListener

    gTable.refreshTableData()
    codeDecode=new CodeDecode()
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
     String s = valueChangeEvent.getProperty().getValue()

     def codeDecodeList 
     if(s.equals("ALL")){
       codeDecodeList=commonService.listCodeDecode("")
     }else{
       codeDecodeList=commonService.listCodeDecode(s)
     }

    gTable.entityListData = codeDecodeList
    
    gTable.editButtonClickListener={Button.ClickEvent event ->
                 def button = event.button
                 def id = new Integer(button.description.substring(5))
                 showEditor(id)
            } as Button.ClickListener


    gTable.refreshTableData()
  }



  void showEditor(int id) {
    def s="edit"
    if(id==0){
      s="add"
      codeDecode=new CodeDecode()
    }else{
      codeDecode = commonService.getCodeDecode(id)
    }

    editorWindow=new GrailsFormWindow(codeDecode,codeDecodeShowFields,s)
    editorWindow.caption="Code Decode"
    editorWindow.build()
    
    def onCancel={ClickEvent event ->
             getWindow().removeWindow editorWindow
    }as ClickListener

   editorWindow.btnCancel.addListener(onCancel)

    def onSave={ClickEvent event ->
       try{
           editorWindow.form.commit()
           getWindow().removeWindow editorWindow
           commonService.saveCodeDecode codeDecode
           getWindow().showNotification "CodeDecode Saved"
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
              commonService.deleteCodeDecode codeDecode
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
