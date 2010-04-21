package com.libertech.rtmx.ui.admin

import org.zhakimel.vgrails.model.core.UserLogin
import com.libertech.rtmx.service.CommonService
import com.libertech.rtmx.service.LoggerService
import com.libertech.rtmx.ui.MainApp
import org.zhakimel.vgrails.component.GrailsForm
import org.zhakimel.vgrails.component.GrailsFormWindow
import org.zhakimel.vgrails.component.GrailsTable
import org.zhakimel.vgrails.component.Spacer
import com.libertech.rtmx.ui.util.AppConstant
import org.zhakimel.vgrails.util.AppManager

import org.zhakimel.vgrails.util.UIBuilder
import com.vaadin.terminal.ThemeResource
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.Button.ClickListener
import com.vaadin.ui.Window.Notification
import com.vaadin.ui.*

class UserLoginList extends Panel implements UIBuilder, Button.ClickListener {
  MainApp app
  AppManager manager
  CommonService commonService
  LoggerService loggerService 

  boolean built;
  def groupList = ["SYSTEM", "LOGGER", "PARTY", "PARTYROLE"]

  def userLoginShowFields = ["userName", "userPassword", "userRole", "email"]

  def userLoginFieldMap = [
          "userName": String.class,
          "userRole": String.class,
          "email": String.class
  ]


  UserLogin userLogin;

  GrailsTable gTable;
  GrailsForm gForm;

  GrailsFormWindow editorWindow

  def UserLoginList(caption, app) {
    super(caption);
    this.app=app
    this.manager = app.manager
    commonService = manager.serviceFactory.getCommonService()
    loggerService = manager.serviceFactory.getLoggerService()


    setIcon new ThemeResource("../runo/icons/32/haxor.png")
    setWidth "100%"
  }

  def void build() {

    loggerService.userLogin = manager.userLogin
    commonService.xUserLogin = manager.userLogin


    def grd = new GridLayout(2, 1)
    grd.setColumnExpandRatio 0, 1.0f
    grd.setSizeFull()

    ComboBox cbo = new ComboBox();
    cbo.setWidth "150px"

    groupList.each {
      cbo.addItem it
    }

    grd.addComponent cbo
    grd.setComponentAlignment cbo, Alignment.MIDDLE_LEFT

    def hl2 = new HorizontalLayout()
    hl2.setSpacing true

    def addButton = new Button(AppConstant.STR_NEW, this)
    addButton.setIcon(new ThemeResource(AppConstant.ICON_DOCUMENT))
    hl2.addComponent addButton
    hl2.setComponentAlignment addButton, Alignment.MIDDLE_RIGHT


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

    gTable = new GrailsTable(userLoginFieldMap)
    addComponent gTable

    refreshUIData()
    this.built = true


  }

  def boolean isBuilt() {
    return built;

  }

  def void refreshUIData() {

    def lst = commonService.findAllUserLogin()
    gTable.entityListData = lst

    gTable.editButtonClickListener = {Button.ClickEvent event ->
      def button = event.button
      def id = new Integer(button.description.substring(5))
      showEditor(id)
    } as Button.ClickListener


    gTable.refreshTableData()
    userLogin = new UserLogin()

  }

  void buttonClick(ClickEvent clickEvent) {
    Button button = clickEvent.button

    if (button.caption.equals(AppConstant.STR_NEW)) {
      showEditor(0)
    }

  }

  def showEditor(int id) {

    String s = "edit"
    if (id == 0) {
      s = "add"
      userLogin = new UserLogin()
    } else {
      userLogin = commonService.getUserLogin(id)
    }

    editorWindow = new GrailsFormWindow(userLogin, userLoginShowFields, s)
    editorWindow.caption = "User Login"
    editorWindow.setEntityListValues(["userRole":["ADMIN":"Administrator","MANAGER":"Manager","USER":"User"]])

    def onCancel = {ClickEvent event ->
      getWindow().removeWindow editorWindow
    } as ClickListener

    editorWindow.btnCancel.addListener(onCancel)

    def onSave = {ClickEvent event ->
      try {
        editorWindow.form.commit()
        getWindow().removeWindow(editorWindow)
        commonService.addUser(userLogin)
        getWindow().showNotification "User Login Saved"
        refreshUIData()
      } catch (e) {
        getWindow().showNotification e.getMessage(), Notification.TYPE_WARNING_MESSAGE
      }
    } as ClickListener

    editorWindow.btnSave.addListener(onSave)


    def onDelete = {ClickEvent event ->
      try {
        editorWindow.form.commit()
        getWindow().removeWindow editorWindow
        commonService.deleteUserLogin(userLogin)
        getWindow().showNotification "User login Deleted"
        refreshUIData()
      } catch (e) {

        getWindow().showNotification e.getMessage(), Notification.TYPE_WARNING_MESSAGE

      }
    } as ClickListener

    editorWindow.btnDelete.addListener(onDelete)
    editorWindow.build()
    getWindow().addWindow editorWindow

  }


}
