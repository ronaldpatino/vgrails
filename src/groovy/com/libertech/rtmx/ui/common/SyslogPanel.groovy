package com.libertech.rtmx.ui.common

import com.libertech.rtmx.model.common.SysLog
import com.libertech.rtmx.model.common.UserLogin
import com.libertech.rtmx.service.CommonService
import com.libertech.rtmx.service.LoggerService
import com.libertech.rtmx.ui.MainApp
import org.zhakimel.vgrails.component.Spacer
import com.libertech.rtmx.ui.util.AppConstant
import com.libertech.rtmx.ui.util.AppManager

import org.zhakimel.vgrails.util.UIBuilder
import com.vaadin.data.Item
import com.vaadin.data.util.IndexedContainer
import com.vaadin.terminal.ThemeResource
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.Window.Notification
import com.vaadin.ui.*

class SyslogPanel extends Panel implements UIBuilder, Button.ClickListener{
  MainApp app
  AppManager manager

  CommonService commonService
  LoggerService loggerService
  boolean built = false;
  def severityList =["INFO","WARNING","ERROR"]

  Table table
  def SyslogPanel(caption, MainApp app) {
    super(caption);

    this.app = app
    this.manager = this.app.manager
    commonService = manager.serviceFactory.getCommonService()
    loggerService = manager.serviceFactory.getLoggerService()


    setWidth "100%"
    String s = AppConstant.ICON_DOCUMENT_TXT_32
    setIcon new ThemeResource(s)


 
  }

  void build(){

    commonService.xUserLogin = manager.userLogin
    loggerService.userLogin = manager.userLogin

    def hl = new HorizontalLayout()
    hl.setWidth "100%"
    hl.setSpacing true
    ComboBox cbo = new ComboBox("Please select severity");
   
    severityList.each{
      cbo.addItem it
    }

    hl.addComponent cbo
    def refreshButton = new Button("Refresh",this)
    refreshButton.setStyleName "small"
    hl.addComponent refreshButton
    hl.setComponentAlignment refreshButton,Alignment.MIDDLE_RIGHT

    addComponent hl
    addComponent new Spacer()

    table = new Table()
    table.setSelectable true
    table.setWidth("100%")
    addComponent table

    refreshUIData()
    this.built=true
  }

  boolean isBuilt() {
    return this.built;
  }

   void refreshUIData() {
     table.setContainerDataSource getTableData()
   }



  def getTableData(){
    def sysLogFieldMap=[
            "id":String.class,
            "logDate":String.class,
            "severity":String.class,
            "description":String.class,
            "userLogin":UserLogin.class,
            "ipAddress":String.class
    ]

    def sysLogList = loggerService.listAll()

    IndexedContainer container = new IndexedContainer()

    sysLogFieldMap.each{
      container.addContainerProperty(it.getKey(), it.getValue(),"")
    }

    if(sysLogList==null){
       getWindow().showNotification "Logs is NULL",Notification.TYPE_ERROR_MESSAGE
       loggerService.logError "Log list is NULL",null
       return container
    }else if(sysLogList.empty){

      getWindow().showNotification "Logs is Empty",Notification.TYPE_WARNING_MESSAGE
      loggerService.logError "Log list is empty",null
      return container

    }

     for(i in 0..sysLogList.size()-1){
      SysLog sysLog =  sysLogList.get(i)
      Item item = container.addItem(sysLogList.get(i).id);

      sysLogFieldMap.each{
        item.getItemProperty(it.getKey()).setValue sysLog[it.getKey()]
      }
    }


    return container

  }

  void buttonClick(ClickEvent clickEvent) {
    def button = clickEvent.button
    if(button.caption.equals("Refresh")){
      refreshUIData()
      getWindow().showNotification "Log data refreshed"
    }
  }


}
