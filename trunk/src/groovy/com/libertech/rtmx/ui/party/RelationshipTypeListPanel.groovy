package com.libertech.rtmx.ui.party

import org.zhakimel.vgrails.component.GrailsListPanel
import org.zhakimel.vgrails.util.UIBuilder
import com.libertech.rtmx.service.PartyService
import org.zhakimel.vgrails.util.AppManager


class RelationshipTypeListPanel extends GrailsListPanel implements UIBuilder {


  boolean built
  AppManager manager
  final PartyService partyService

  RelationshipTypeListPanel(String caption, AppManager manager) {
    super(caption)
    this.manager = manager
    this.partyService = manager.serviceFactory.getPartyService()

  }

  def void build() {

    refreshUIData()
    built = true
  }

  def boolean isBuilt() {
    return built;
  }

  def void refreshUIData() {
    grailsTable.fieldMap = ["name": String.class, "description": String.class]
    grailsTable.entityListData = partyService.listAllPartyRelationshipType()
    grailsTable.refreshTableData()
  }


}
