package com.libertech.rtmx.ui.dashboard

import org.zhakimel.vgrails.util.UIBuilder
import com.vaadin.ui.CustomLayout
import com.vaadin.ui.VerticalLayout

class DashboardPanel extends VerticalLayout implements UIBuilder {

  boolean built=false
  def DashboardPanel(caption) {
   
  }

  def void build() {
    def cl=new CustomLayout("dashboardlayout")
    addComponent cl
    built=true
  }

  def boolean isBuilt() {
    return built;
  }

  def void refreshUIData() {

  }


}
