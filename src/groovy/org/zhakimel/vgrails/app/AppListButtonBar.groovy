package org.zhakimel.vgrails.app

import com.vaadin.ui.Button
import com.vaadin.ui.Label
import com.vaadin.terminal.ThemeResource
import com.vaadin.ui.HorizontalLayout
import org.zhakimel.vgrails.util.VGrailsConstant


class AppListButtonBar extends HorizontalLayout {
  HorizontalLayout hleft= new HorizontalLayout();
  HorizontalLayout hright=new HorizontalLayout();
  Label lblCaption
  Button btnNew
  Button btnRefresh

  def AppListButtonBar(String caption){
    setSpacing true
    setMargin true
    addStyleName "toolbar"
    setWidth "100%"
    
    
    lblCaption=new Label("<div style=\"font-size:16px;font-weight:bold\">"+caption+"</div>",Label.CONTENT_XHTML)
    lblCaption.addStyleName "color"
   
    
    btnNew = new Button(VGrailsConstant.STR_NEW)
    btnNew.setDescription "Entry new data to form"
    btnNew.icon = new ThemeResource(VGrailsConstant.ICON_ADD)
    btnNew.setWidth "80px"

    btnRefresh = new Button(VGrailsConstant.STR_REFRESH)
     btnRefresh.icon = new ThemeResource(VGrailsConstant.ICON_REFRESH)
    btnRefresh.setDescription "Refresh list of data"

    btnRefresh.setWidth "80px"

    build()
 }

   def build(){
    addComponent  hleft
    hleft.setSizeUndefined();
    hleft.addComponent  lblCaption
    hright.setSpacing true
    addComponent  hright
    hright.addComponent  btnNew
    hright.addComponent  btnRefresh
    hright.addStyleName("right");
    hright.setSizeUndefined();
    setExpandRatio hleft,1
   }
}
