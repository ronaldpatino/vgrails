package org.zhakimel.vgrails.component.generator

import com.vaadin.data.Property
import com.vaadin.ui.Component

import com.vaadin.ui.Table
import com.vaadin.ui.Button
import com.vaadin.terminal.ThemeResource

import com.vaadin.data.util.BeanItem
import org.zhakimel.vgrails.util.VGrailsConstant

class ActionColumnGenerator implements Table.ColumnGenerator {
   def editButtonClickListener = {Button.ClickEvent event ->
   
    } as Button.ClickListener

  def ActionColumnGenerator(){

  }

  Component generateCell(Table source, Object itemId, Object columnId) {

    Property prop = source.getItem(itemId).getItemProperty("id");
    println "ID="+prop
    println "Bean Item="+(BeanItem) source.getItem(itemId)
    String sid=prop.toString()
    def btn = new Button()
    btn.setDescription "edit " + sid
    btn.addListener editButtonClickListener
    btn.setStyleName Button.STYLE_LINK
    btn.setIcon new ThemeResource(VGrailsConstant.ICON_EDIT)
    return btn;
}
  }

