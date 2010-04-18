package org.zhakimel.vgrails.component

import com.vaadin.ui.Panel
import org.zhakimel.vgrails.app.AppListButtonBar
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.SplitPanel


class GrailsListPanel extends SplitPanel {

  AppListButtonBar appButtonBar
  Panel innerPanel=new Panel()
  GrailsTable grailsTable
  Panel filterPanel= new Panel("Filter")

  GrailsListPanel(String caption){
    setOrientation SplitPanel.ORIENTATION_VERTICAL
    setWidth "100%"
    setHeight "100%"
    setSplitPosition(53,SplitPanel.UNITS_PIXELS);
    setStyleName "small"
    appButtonBar = new AppListButtonBar(caption)
     firstComponent = appButtonBar
    ((VerticalLayout)innerPanel.getContent()).setSpacing true
    secondComponent =  innerPanel

    filterPanel.visible=false
    ((VerticalLayout) filterPanel.getContent()).setSpacing true
    ((VerticalLayout)innerPanel.getContent()).setSpacing true
    innerPanel.setHeight  "100%"
    grailsTable = new GrailsTable([:])
    grailsTable.setWidth "100%"
    innerPanel.addComponent filterPanel
    innerPanel.addComponent grailsTable
  }




}
