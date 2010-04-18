package org.zhakimel.vgrails.app

import com.vaadin.ui.Button

import com.libertech.rtmx.ui.util.AppConstant
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.CssLayout
import com.libertech.rtmx.ui.MainApp
import com.libertech.rtmx.ui.util.AppManager
import com.vaadin.ui.HorizontalLayout


class NavigationPanel extends CssLayout implements Button.ClickListener {

  Button btnAdmin    = new Button(AppConstant.NAV_ADMINISTRATOR,this)
  Button btnParty    = new Button(AppConstant.NAV_PARTY,this)
  Button btnEmployee = new Button(AppConstant.NAV_EMPLOYEE,this)
  Button btnProject  = new Button(AppConstant.NAV_PROJECT,this)
  Button btnHome  = new Button(AppConstant.NAV_HOME,this)

  Button selectedButton;
  private MainApp app
  private AppManager manager

  def NavigationPanel(MainApp app){
    this.app = app
    this.manager = app.manager
    setStyleName "toolbar"
    
    setWidth "100%"
    setHeight "28px"

    def hLayout = new CssLayout()
    hLayout.addStyleName("right");
    hLayout.setSizeUndefined();

    btnHome.setWidth "110px"
    hLayout.addComponent btnHome


    btnProject.setWidth "110px"
    hLayout.addComponent btnProject

    btnEmployee.setWidth "110px"
    hLayout.addComponent btnEmployee

    btnParty.setWidth "110px"
    hLayout.addComponent btnParty

    btnAdmin.setWidth "110px"
    hLayout.addComponent btnAdmin

    addComponent  hLayout
  }


  void buttonClick(ClickEvent clickEvent) {
     String caption = clickEvent.getButton().getCaption()
     app.subNavigationPanel.buildMenu caption


    if(clickEvent.button!=selectedButton){
      if(selectedButton) selectedButton.removeStyleName("default")
      selectedButton=clickEvent.button
      selectedButton.addStyleName "default"

    }

  }


}
