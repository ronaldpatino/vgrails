package com.libertech.rtmx.ui.common

import com.vaadin.ui.Panel

import com.vaadin.ui.TextField
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Button
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Label
import com.vaadin.ui.Alignment
import com.vaadin.ui.Button.ClickEvent
import com.libertech.rtmx.model.common.UserLogin
import com.vaadin.ui.Window.Notification
import com.libertech.rtmx.ui.dashboard.DashboardPanel
import com.libertech.rtmx.ui.util.AppConstant
import com.libertech.rtmx.ui.MainApp
import com.libertech.rtmx.ui.util.AppManager


class LoginPanel extends Panel implements Button.ClickListener {

   private MainApp app
   private AppManager manager 

   TextField txtUser = new TextField("User Name")
   TextField txtPassword = new TextField("Password")
   Button btnLogin = new Button("Login",this)

   def LoginPanel(MainApp app){

     super()
     this.app = app
     this.manager = app.manager


     setCaption "System Login"
     def vl = new VerticalLayout()
     vl.setSpacing true
     
     vl.addComponent txtUser
     vl.addComponent txtPassword
     txtPassword.setSecret true

     addComponent vl

     addComponent new Label("<br/>",Label.CONTENT_XHTML)

     def hl = new HorizontalLayout()
     hl.addComponent  btnLogin
     hl.setComponentAlignment btnLogin,Alignment.MIDDLE_RIGHT

     addComponent hl
     app.navigationPanel.enabled = false

   }


  void buttonClick(ClickEvent clickEvent) {
     UserLogin userLogin = manager.getServiceFactory().getCommonService().doLogin(txtUser.value, txtPassword.value)

     if(userLogin==null){
       getWindow().showNotification "User name is not found may be you forgot your password?",Notification.TYPE_ERROR_MESSAGE
     }else{

       manager.userLogin=userLogin
       app.brandingPanel.lnkLogout.visible=true
       app.navigationPanel.enabled = true
       getWindow().showNotification userLogin.userName+" has logged in"
       app.contentPanel.removeAllComponents()
       app.contentPanel.addComponent new DashboardPanel("RTMX Home")
       app.subNavigationPanel.buildMenu AppConstant.NAV_HOME

     }
  }


}
