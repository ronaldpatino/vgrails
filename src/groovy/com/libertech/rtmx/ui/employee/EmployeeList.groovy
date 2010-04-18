package com.libertech.rtmx.ui.employee

import com.vaadin.ui.Panel
import org.zhakimel.vgrails.util.UIBuilder
import com.libertech.rtmx.ui.MainApp

class EmployeeList extends Panel implements UIBuilder {

  def EmployeeList(caption,MainApp app) {
    super(caption);
  }

  void build() {

  }

  boolean isBuilt() {
    return false;
  }

  void refreshUIData() {

  }


}
