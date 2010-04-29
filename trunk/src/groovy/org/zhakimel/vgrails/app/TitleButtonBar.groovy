package org.zhakimel.vgrails.app

import com.vaadin.ui.Button
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label
import com.vaadin.addon.chameleon.ChameleonTheme

/**
 * Generates button bar for App Panel where it will be located at top of the panel
 * and contains caption, save button, delete button and cancel button
 *
 * @author Abiel Hakeem
 */

class TitleButtonBar extends HorizontalLayout {

  final HorizontalLayout hleft= new HorizontalLayout();
  final HorizontalLayout hright=new HorizontalLayout();
  final Label lblCaption

  // save buton
  final Button btnSave

  //delete button
  final Button btnDelete

  //cancel button
  final Button btnCancel

  //auxiliary layout for adding other buttons before cancel button
  HorizontalLayout auxLayout=new HorizontalLayout();


  def TitleButtonBar(String caption){

    setWidth "100%"
    //setMargin true

    lblCaption=new Label("<div style=\"font-size:16px;font-weight:bold;padding:5px\">"+caption+"</div>",Label.CONTENT_XHTML)
    lblCaption.addStyleName "color"

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
    hright.addStyleName("right");

    setExpandRatio hleft,1
  }

  def addButton(Button button){
    auxLayout.addComponent button
  }

}