package com.libertech.rtmx.ui.util

import com.libertech.rtmx.model.common.UserLogin
import com.libertech.rtmx.ui.admin.UserLoginList
import com.libertech.rtmx.ui.common.CodeDecodePanel
import com.libertech.rtmx.ui.common.SyslogPanel
import com.libertech.rtmx.ui.dashboard.DashboardPanel
import com.libertech.rtmx.ui.employee.EmployeeList
import com.libertech.rtmx.ui.party.OrganizationList
import com.libertech.rtmx.ui.party.PersonList
import com.libertech.rtmx.ui.party.RelationshipList
import com.libertech.rtmx.ui.project.ProjectList
import com.vaadin.ui.Panel

import com.libertech.rtmx.ui.MainApp
import com.vaadin.ui.Component
import com.libertech.rtmx.ui.party.PartyTypeList
import com.libertech.rtmx.ui.party.RoleTypeListPanel
import com.libertech.rtmx.ui.party.RelationshipTypeListPanel

class AppManager {

  Map NAVIGATION_APP_PANEL

  MainApp app
  ServiceFactory serviceFactory = new ServiceFactory()
  UserLogin userLogin

  def AppManager(MainApp app) {
    this.app = app
  }

  def initContents(){
    NAVIGATION_APP_PANEL = [
    "Login"         : ["Help":new Panel("Help")],
    "Administrator" : ["Code Decode":new CodeDecodePanel("Code Decode",app)
                        ,"Users":new UserLoginList("User Login",app)
                        ,"Logs":new SyslogPanel("Logs",app)
                        ],

    "Participant"   : ["Person":new PersonList("Person",app)
                        ,"Organization":new OrganizationList("Organization",app)
                        ,"Relationship":new RelationshipList("Relationship",app)
                        ,"Party Types":new PartyTypeList("Party Types",this)
                        ,"Role Types":new RoleTypeListPanel("Role Types",this)
                        ,"Relationship Types":new RelationshipTypeListPanel("Relationship Types",this)],

    "Employee"      : ["Employees":new EmployeeList("Employees",app)],

    "Project"       : ["Projects":new ProjectList("Projects",app)],

    "Home"          : ["Dashboard":new DashboardPanel()]
  ]
  }

  Component getContentPanel(String moduleName,subModuleName ){
     return NAVIGATION_APP_PANEL[moduleName][subModuleName]
  }

  def setContent(Component panel){
   app.contentPanel.removeAllComponents()
   app.contentPanel.addComponent panel
  }

  def enableNavigation(){
   app.navigationPanel.enabled=true
   app.subNavigationPanel.enabled=true
  }

  def disableNavigation(){
   app.navigationPanel.enabled=false
   app.subNavigationPanel.enabled=false

  }

}
