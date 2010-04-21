package org.zhakimel.vgrails.component

import com.vaadin.ui.Window
import com.vaadin.ui.Button

import com.vaadin.terminal.ThemeResource
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.HorizontalLayout
import org.zhakimel.vgrails.util.VGrailsConstant


class LookupTableWindow extends Window {

  private Map fieldMap
  private List entityListData
  private GrailsCustomTable table

  Button btnSelect = new Button(VGrailsConstant.STR_SELECT)
  Button btnCancel = new Button(VGrailsConstant.STR_CANCEL)

  def LookupTableWindow(String caption, Map fieldMap) {
    super(caption)
    this.fieldMap = fieldMap

    btnSelect.setIcon(new ThemeResource(VGrailsConstant.ICON_OK))
    btnCancel.setIcon(new ThemeResource(VGrailsConstant.ICON_CANCEL))
    table = new GrailsCustomTable(this.fieldMap)
    table.selectable=true
    table.hasEditButton = false
    table.setWidth("100%")
    table.setHeight("300px")
    build()
  }

  void setEntityListData(List entityListData) {
    this.entityListData = entityListData
    table.entityListData = this.entityListData
    table.refreshTableData()
  }
  def getEntityListData(){
    return table.entityListData
  }
  def getSelectedValue(){
    return table.value-1
  }

  def build() {
    setBorder Window.BORDER_MINIMAL
    modal = true
    VerticalLayout vl = (VerticalLayout) getContent()
    vl.setSizeUndefined()

    HorizontalLayout hl=new HorizontalLayout()
    hl.setSpacing true
    hl.addComponent  table
    hl.setExpandRatio table,1

    VerticalLayout vlb = new VerticalLayout()
    vlb.setSpacing true
    btnSelect.setWidth("80px")
    btnCancel.setWidth("80px")

    vlb.addComponent btnSelect
    vlb.addComponent btnCancel
    hl.addComponent  vlb

    vl.addComponent  hl
  }
}
