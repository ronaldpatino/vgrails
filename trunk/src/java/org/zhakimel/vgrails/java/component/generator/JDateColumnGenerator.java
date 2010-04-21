package org.zhakimel.vgrails.java.component.generator;

import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date Column Generator
 * Displays date with simple date format pattern
 * @author Abiel Hakeem
 */
public class JDateColumnGenerator implements Table.ColumnGenerator {
    private String format="dd-MM-yyyy";

    JDateColumnGenerator(String format) {
        if(format!=null){
           this.format = format;
        }
    }

    public Component generateCell(Table source, Object itemId, Object columnId) {
        Property prop = source.getItem(itemId).getItemProperty(columnId);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Label label = new Label();
        if (prop == null) return label;

        if (prop.getType().equals(Date.class)) {
            if (prop.getValue() != null) {
                label = new Label(sdf.format((Date) prop.getValue()));
            } else {
                label = new Label();
            }
            label.addStyleName("column-type-value");
            label.addStyleName("column-" + (String) columnId);
            return label;
        }
        return null;
    }
}