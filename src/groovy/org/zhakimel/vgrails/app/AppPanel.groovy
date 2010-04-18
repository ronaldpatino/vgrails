package org.zhakimel.vgrails.app

import com.vaadin.ui.Panel
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.SplitPanel


class AppPanel extends SplitPanel {

  AppButtonBar appButtonBar
  Panel innerPanel=new Panel()

  AppPanel(String caption){
    setOrientation SplitPanel.ORIENTATION_VERTICAL
    appButtonBar = new AppButtonBar(caption)
    setWidth "100%"
    setHeight "100%"
    setSplitPosition(53,SplitPanel.UNITS_PIXELS);
    setStyleName "small"
    firstComponent= appButtonBar
    ((VerticalLayout)innerPanel.getContent()).setSpacing true
    secondComponent=  innerPanel
  }


}
