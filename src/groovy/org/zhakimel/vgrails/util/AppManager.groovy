package org.zhakimel.vgrails.util

import com.vaadin.ui.Component
import org.zhakimel.vgrails.model.core.UserLogin

import org.zhakimel.vgrails.app.VGrailsApplication

/**
 * Manager for application
 */
class AppManager {
  /**
   * Holds application navigation panels selected through
   */
  Map<String,Map<String,Object>> NAVIGATION_APP_PANEL


  /**
   * holds menu information 
   */
  Map<String,String> NAVIGATION_APP_INFO

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

  /**
   * Instantiate application manager, app referenced to your VGrails Application
   */
  def AppManager(VGrailsApplication app) {
    this.app = app
  }


  @Deprecated
  def initContents(){

  }

  /**
   * Sets navigation panel information
   */
  def setNavigationPanelInfo(Map<String,String> infoMap){
    NAVIGATION_APP_INFO = infoMap
  }

  /**
   * sets contents for navigation
   */
  def setNavigationPanelContents(Map<String,Map<String,Object>> contentMaps){
    NAVIGATION_APP_PANEL = contentMaps
  }

  /**
   * get available content
   */
  Component getContentPanel(String moduleName,subModuleName ){
     return NAVIGATION_APP_PANEL[moduleName][subModuleName]
  }


  /**
  * add content panel
   */
  def addContentPanel(String primaryMenuTitle, String menuTitle, Component component){
    NAVIGATION_APP_PANEL[primaryMenuTitle][menuTitle]=component
  }



  /**
   * set content to content panel
   */
  def setContent(Component panel){
   app.contentPanel.removeAllComponents()
   app.contentPanel.addComponent panel
  }

  /**
   * enable menu navigation
   */
  def enableNavigation(){
   app.navigationPanel.enabled=true
   app.subNavigationPanel.enabled=true
  }

  /**
   * disable menu navigation
   */
  def disableNavigation(){
   app.navigationPanel.enabled=false
   app.subNavigationPanel.enabled=false

  }

}
