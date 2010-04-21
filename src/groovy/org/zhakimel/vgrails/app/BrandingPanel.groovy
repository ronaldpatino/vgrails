package org.zhakimel.vgrails.app

import com.vaadin.ui.Label

import com.vaadin.ui.Button

import com.libertech.rtmx.ui.MainApp
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.Button.ClickListener
import com.vaadin.ui.HorizontalLayout

import com.vaadin.ui.GridLayout
import org.zhakimel.vgrails.component.Spacer
import org.zhakimel.vgrails.util.AppManager


class BrandingPanel extends HorizontalLayout {

  Label  lblUserName = new Label("Welcome User | ")
  Button lnkLogout = new Button("Logout")
  Button lnkSettings = new Button("Settings")
  
  Label title = new Label("Libertech RTM")

  private MainApp app
  private AppManager manager

  def BrandingPanel(MainApp app) {
    this.app = app
    this.manager = app.manager
    lnkLogout.visible = false

    setWidth "100%"
    HorizontalLayout left = new HorizontalLayout();
    left.setSizeUndefined();

    title.addStyleName "h2 color"
    left.addComponent  new Spacer()
    left.addComponent title

    addComponent(left);

    GridLayout right = new GridLayout(3,1)
    right.addComponent lblUserName
    right.setSpacing true
    right.setWidth  "200px"
    
    lnkSettings.addStyleName "link"
    right.addComponent lnkSettings

    lnkLogout.addStyleName "link"

    def logoutClickListener = {ClickEvent event ->
      getWindow().getApplication().close()
    } as ClickListener

    lnkLogout.addListener logoutClickListener
    right.addComponent lnkLogout
    right.setColumnExpandRatio 0,1

    addComponent right
    setExpandRatio left,1

  }

}
