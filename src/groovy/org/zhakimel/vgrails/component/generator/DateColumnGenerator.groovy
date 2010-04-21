package org.zhakimel.vgrails.component.generator

import com.vaadin.ui.Table
import com.vaadin.ui.Component
import com.vaadin.ui.Label
import com.vaadin.data.Property
import java.text.SimpleDateFormat

/**
 * Date Column Generator
 * Displays date with simple date format pattern
 * @author Abiel Hakeem
 */
class DateColumnGenerator implements Table.ColumnGenerator {
  String format = "d-M-yyyy"

  def DateColumnGenerator(String format){
    this.format = format
  }

  Component generateCell(Table source, Object itemId, Object columnId) {

    Property prop = source.getItem(itemId).getItemProperty(columnId);
    SimpleDateFormat sdf = new SimpleDateFormat(format)
    Label label= new Label()
    if(!prop) return label
        
    if (prop.getType().equals(Date.class)) {
         if(prop.getValue())
           label = new Label( sdf.format((Date)prop.getValue()));
         else
           label = new Label()

   label.addStyleName("column-type-value");
   label.addStyleName("column-" + (String) columnId);
  return label;
}
  }

}