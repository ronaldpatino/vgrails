package org.zhakimel.vgrails.app

import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Label
import com.libertech.rtmx.ui.util.AppConstant
import com.vaadin.ui.Button

import com.vaadin.ui.Button.ClickEvent

import com.vaadin.ui.Component

import com.vaadin.ui.CssLayout
import com.vaadin.ui.NativeButton
import com.vaadin.terminal.ThemeResource
import com.libertech.rtmx.ui.MainApp

import com.vaadin.ui.HorizontalLayout
import org.zhakimel.vgrails.util.AppManager


class SubNavigationPanel extends CssLayout implements Button.ClickListener {

  Label lblNavTitle = new Label("System Login")

  def vlayout = new VerticalLayout()

  Button currentButton;
  String currentMenuTitle;

  private MainApp app
  private AppManager manager

  def SubNavigationPanel(MainApp app){
     super()
     this.app = app
     this.manager = app.manager

     setStyleName "sidebar-menu"
     setHeight "100%"
     setWidth  "250px"
  }

  def buildMenu(String menuTitle){

    currentMenuTitle = menuTitle
    removeAllComponents()
    lblNavTitle.setValue menuTitle

    addComponent  lblNavTitle

    //iterate each element in map list

    manager.NAVIGATION_APP_PANEL[menuTitle].each{
         def k=it.getKey()
        
         def b=new NativeButton(k,this)
         b.setIcon new ThemeResource(AppConstant.ICON_DOCUMENT)
         addComponent b
    }

    Label label =  new Label("<div>"+AppConstant.MAP_NAV_INFO[menuTitle]+"</div>",Label.CONTENT_XHTML)
    label.addStyleName "tiny"
    HorizontalLayout hl = new HorizontalLayout()
    hl.setWidth "100%"
    hl.addComponent label
    addComponent hl

    app.contentPanel.removeAllComponents()

    for (it in manager.NAVIGATION_APP_PANEL[menuTitle]) {

      if(!it.value.isBuilt()){
          it.value.build()
      }else{
        it.value.refreshUIData()
      }

      app.contentPanel.addComponent it.value
      break
    }
  }

  void buttonClick(ClickEvent clickEvent) {
    Button clickedButton = clickEvent.button
    String subMenuTitle = clickEvent.getButton().getCaption()

    if(!clickedButton.equals(currentButton)){
       clickedButton.addStyleName("selected")
        if(currentButton!=null)
           currentButton.removeStyleName("selected")

      app.contentPanel.removeAllComponents()
      Component  pnl = ((MainApp)getApplication()).manager.NAVIGATION_APP_PANEL[currentMenuTitle][subMenuTitle]
      app.contentPanel.addComponent pnl

      if(!pnl.isBuilt()){
          pnl.build()
      }else{
        pnl.refreshUIData()
      }
      manager.NAVIGATION_APP_PANEL[currentMenuTitle][subMenuTitle]=pnl
     currentButton = clickedButton
    }

  }

 


}
