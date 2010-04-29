package org.zhakimel.vgrails.component.widget

import com.vaadin.ui.Label


class Spacer extends Label{

  def Spacer() {
     super("<div>&nbsp;</div>",Label.CONTENT_XHTML)
  }
}
