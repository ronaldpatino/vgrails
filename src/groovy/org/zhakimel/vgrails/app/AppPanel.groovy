package org.zhakimel.vgrails.app

import com.vaadin.ui.Panel
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.SplitPanel

/**
 * Standard Crud form application panel
 * you can access the buttons by calling reference for example this.appButtonBar.btnSave,
 * to add listener on each button, for example simply call this.appButtonBar.btnSave.addListener saveListener
 *
 * @author Abiel Hakeem
 */
class AppPanel extends SplitPanel {

  //STATES
  static final String STATE_EDIT="edit"
  static final String STATE_VIEW="view"
  static final String STATE_NEW="view"

    
  //the button bar
  TitleButtonBar appButtonBar

  //the panel where your components should be added
  Panel innerPanel=new Panel()

  String currentState

  //The costructor, caption will be displayed on the left of appButtonBar
  AppPanel(String caption){
    setOrientation SplitPanel.ORIENTATION_VERTICAL
    appButtonBar = new TitleButtonBar(caption)
    setWidth "100%"
    setHeight "100%"
    locked=true
    setSplitPosition(35,SplitPanel.UNITS_PIXELS);
    setStyleName "small"
    firstComponent= appButtonBar
    ((VerticalLayout)innerPanel.getContent()).setSpacing true
    secondComponent=  innerPanel

  }



}
