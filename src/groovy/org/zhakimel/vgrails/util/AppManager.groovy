package org.zhakimel.vgrails.util

import com.vaadin.ui.Component
import org.zhakimel.vgrails.model.core.UserLogin

import org.zhakimel.vgrails.app.VGrailsApplication

/**
 * Manager for application
 */
class AppManager {

  Map<String,Map<String,Object>> NAVIGATION_APP_PANEL

  /**
   * Main application
   */
  VGrailsApplication app


   /**
    * Service factory
    */
  VGrailsServiceFactory serviceFactory
  

   /**
   *User login
   */
  UserLogin userLogin

  def AppManager(VGrailsApplication app) {
    this.app = app
  }


  @Deprecated
  def initContents(){

  }


  def setNavigationPanelContents(Map<String,Map<String,Object>> contentMaps){
    NAVIGATION_APP_PANEL = contentMaps
  }

  Component getContentPanel(String moduleName,subModuleName ){
     return NAVIGATION_APP_PANEL[moduleName][subModuleName]
  }

  def addContentPanel(String primaryMenuTitle, String menuTitle, Component component){
    NAVIGATION_APP_PANEL[primaryMenuTitle][menuTitle]=component
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
