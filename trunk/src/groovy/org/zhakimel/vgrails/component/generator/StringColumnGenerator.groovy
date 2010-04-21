package org.zhakimel.vgrails.component.generator

import com.vaadin.data.Property
import com.vaadin.ui.Component
import com.vaadin.ui.Label
import com.vaadin.ui.Table

class StringColumnGenerator implements Table.ColumnGenerator {
  String format

  def StringColumnGenerator(){

  }

  Component generateCell(Table source, Object itemId, Object columnId) {

    Property prop = source.getItem(itemId).getItemProperty(columnId);

    if (prop.getType().equals(String.class)) {
         Label label = new Label(prop.getValue());

   label.addStyleName("column-type-value");
   label.addStyleName("column-" + (String) columnId);
  return label;
}
  }

}