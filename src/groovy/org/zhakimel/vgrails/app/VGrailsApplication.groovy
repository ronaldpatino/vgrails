package org.zhakimel.vgrails.app

import com.vaadin.Application
import org.zhakimel.vgrails.util.AppManager

class VGrailsApplication extends Application {

  AppManager manager  
  BrandingPanel brandingPanel
  NavigationPanel navigationPanel
  SubNavigationPanel subNavigationPanel
  ContentPanel contentPanel
  CopyrightPanel copyrightPanel

  void init() {
  }


}
