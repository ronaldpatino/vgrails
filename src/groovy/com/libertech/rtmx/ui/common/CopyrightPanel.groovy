package com.libertech.rtmx.ui.common

import com.vaadin.ui.Panel
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label


class CopyrightPanel extends HorizontalLayout {

  Label status=new Label("Status")

  def CopyrightPanel(){
    super()
    setWidth  "100%"
    setStyleName "black"
    addComponent status
  }

}
