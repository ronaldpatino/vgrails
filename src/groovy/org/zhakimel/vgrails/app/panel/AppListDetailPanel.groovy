package org.zhakimel.vgrails.app.panel

import com.vaadin.ui.SplitPanel
import com.vaadin.ui.Table
import com.vaadin.ui.VerticalLayout

/**
 * creates generic list-detail panel, table on the top, and  detail panel where you add components on bottom
 *
 * @author Abiel Hakeem
 */
class AppListDetailPanel  extends SplitPanel{


  final Table table
  final VerticalLayout detailPanel

  AppListDetailPanel(){
    super()
    table = new Table()
    table.setSizeFull()
    detailPanel = new VerticalLayout()
    detailPanel.setSizeFull()
    setSplitPosition 200, SplitPanel.UNITS_PIXELS
    setFirstComponent table
    setSecondComponent detailPanel
    setWidth "100%"
    setHeight "100%"

  }

}
