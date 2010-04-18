package org.zhakimel.vgrails.app

import com.libertech.rtmx.ui.util.AppConstant
import com.vaadin.terminal.ThemeResource
import com.vaadin.ui.Button
import com.vaadin.ui.CssLayout
import com.vaadin.ui.Label
import com.vaadin.ui.HorizontalLayout

class AppButtonBar extends HorizontalLayout {

  final HorizontalLayout hleft= new HorizontalLayout();
  final HorizontalLayout hright=new HorizontalLayout();
  final Label lblCaption
  final Button btnSave
  final Button btnDelete
  final Button btnCancel
  CssLayout auxLayout=new CssLayout();


  def AppButtonBar(String caption){
    
    setStyleName "blue"
    setWidth "100%"
    setMargin true
    

    lblCaption=new Label("<div style=\"font-size:16px;font-weight:bold\">"+caption+"</div>",Label.CONTENT_XHTML)
    lblCaption.addStyleName "color"
    btnSave = new Button(AppConstant.STR_SAVE)
    btnSave.setDescription "Save data and back to previous page"
    btnSave.icon = new ThemeResource(AppConstant.ICON_SAVE)
    btnSave.addStyleName "primary"
    btnSave.setWidth "80px"

    btnDelete = new Button(AppConstant.STR_DELETE)
     btnDelete.icon = new ThemeResource(AppConstant.ICON_DELETE)
    btnDelete.setDescription "Delete data and back to previous page"

    btnDelete.setWidth "80px"

    btnCancel = new Button(AppConstant.STR_CANCEL)
     btnCancel.icon = new ThemeResource(AppConstant.ICON_CANCEL)
    btnCancel.setDescription "Cancel edit and back to previous page"

    btnCancel.setWidth "80px"

    build()
  }

  def build(){
    addComponent  hleft
    hleft.addStyleName("left");
    hleft.setSizeUndefined();
    hleft.addComponent  lblCaption
    
    hright.setSizeUndefined()
    hright.setSpacing true
    addComponent  hright
    hright.addComponent  btnSave
    hright.addComponent  btnDelete
    hright.addComponent  btnCancel
    hright.addStyleName("right");

    setExpandRatio hleft,1
  }

  def addButton(Button button){
    auxLayout.addComponent button
  }

}
