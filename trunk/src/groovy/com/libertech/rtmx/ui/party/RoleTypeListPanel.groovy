package com.libertech.rtmx.ui.party

import org.zhakimel.vgrails.component.GrailsListPanel
import org.zhakimel.vgrails.util.UIBuilder
import org.zhakimel.vgrails.util.AppManager
import com.libertech.rtmx.service.PartyService
import com.vaadin.ui.GridLayout
import com.vaadin.ui.Label
import com.vaadin.ui.TextField
import com.vaadin.ui.Button

class RoleTypeListPanel extends GrailsListPanel implements UIBuilder{

  boolean built
  AppManager manager
  final PartyService partyService

  RoleTypeListPanel(String caption, AppManager manager){
    super(caption)
    this.manager = manager
    this.partyService=manager.serviceFactory.getPartyService()

  }

  
  def void build() {
    buildFilterPanel()
    refreshUIData()
    built=true
  }

  def buildFilterPanel(){
    def grd= new GridLayout(2,2)
    grd.setSpacing true
    grd.addComponent new Label("Name")
    grd.addComponent new TextField()
    grd.addComponent new Label("Description")
    grd.addComponent new TextField()
    filterPanel.addComponent grd
    filterPanel.addComponent new Button("Search")
    filterPanel.visible = true

  }

  def boolean isBuilt() {
    return built;
  }

  def void refreshUIData() {
   grailsTable.fieldMap=["name":String.class, "description":String.class]
   grailsTable.entityListData = partyService.listAllRoleType()
   grailsTable.refreshTableData()
  }


}
