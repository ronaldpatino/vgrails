package org.zhakimel.vgrails.app

import com.vaadin.ui.Label

import com.vaadin.ui.Button

import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.Button.ClickListener
import com.vaadin.ui.HorizontalLayout

import com.vaadin.ui.GridLayout
import org.zhakimel.vgrails.component.widget.Spacer
import org.zhakimel.vgrails.util.AppManager
import com.vaadin.ui.Component
import com.vaadin.ui.Panel
import com.vaadin.ui.Select
import com.vaadin.ui.PopupView
import com.vaadin.data.Property

/**
 * Creates panel for branding, on the left are for application title or logo,
 * and on the right are buttons for settings and login/logout with LINK_STYLE
 *
 * @author Abiel Hakeem
 */
class BrandingPanel extends HorizontalLayout {

  //User name label
  Label  lblUserName = new Label("Welcome User | ")

  //login/logout button
  final Button lnkLogout = new Button("Logout")

  //settings button
  final PopupView lnkThemes = new PopupView("Themes",buildThemeSelectComponent())

  //title for your application
  Label title = new Label("Libertech RTM")

  //applica
  private VGrailsApplication app
  private AppManager manager

  def BrandingPanel(AppManager manager) {
    this.manager = manager
    this.app = manager.app

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
    
    
    right.addComponent lnkThemes

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

  private Component buildThemeSelectComponent() {
    def themes = [
      "chameleon-blue" : "Blue",
      "chameleon-green" : "Green",
      "chameleon-dark" : "Dark",
      "corporate":"Corporate",
      "fengshui":"Feng Shui",
      "greyly":"Grey",
      "latte":"Latte Factor",
      "pinkely":"Pinky",
      "thematrix":"The Matrix"
    ]

    Panel panel = new Panel()
    panel.setSizeUndefined()
    Select select = new Select("Select your theme")
    
    themes.each{
      select.addItem(it.value)
    }

    def onselect = {Property.ValueChangeEvent event ->
      String selected=""
      for(x in themes){
        if(x.value.equals(select.value)){
          selected = x.key
          break;
        }
      }

      manager.app.setTheme selected

    }as Property.ValueChangeListener
    
    select.addListener onselect


    panel.addComponent select

    return panel;
  }

}
