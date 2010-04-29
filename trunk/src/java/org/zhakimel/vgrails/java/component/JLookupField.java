package org.zhakimel.vgrails.java.component;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.util.List;


public class JLookupField extends CustomField  {

    private final HorizontalLayout layout = new HorizontalLayout();
    private final TextField textField = new TextField();
    private final Button lookupButton = new Button("...");
    private List lookupValues;
    private Class<?> type;
    private String caption;

    JLookupField(String caption, List lookupValues) {
        this.lookupValues = lookupValues;
        layout.setSizeUndefined();
        this.caption = caption;
        layout.addComponent(textField);
        PopupView popup = new PopupView("Lookup..", buildLookupComponent());
        layout.addComponent(popup);

        setCompositionRoot(layout);
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }


    public void setValue(Object newValue) throws ReadOnlyException, ConversionException {
        super.setValue(newValue);
        textField.setValue(newValue);
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public List getLookupValues() {
        return lookupValues;
    }

    public void setLookupValues(List lookupValues) {
        this.lookupValues = lookupValues;
    }


    public Class<?> getType() {
        return type;
    }



    private Component buildLookupComponent() {

        BeanItemContainer beanItemContainer = new BeanItemContainer(lookupValues);

        Table lookupTable = new Table("", beanItemContainer);
        lookupTable.setSelectable(true);
        lookupTable.setWidth("250px");
        lookupTable.setHeight("100%");
        Panel panel = new Panel(this.caption);
        panel.setWidth("270px");
        VerticalLayout vl = (VerticalLayout)panel.getContent();
        vl.setMargin(false);
        vl.addComponent(lookupTable);
        vl.setComponentAlignment(lookupTable,Alignment.TOP_CENTER);

        return panel;
    }


}
