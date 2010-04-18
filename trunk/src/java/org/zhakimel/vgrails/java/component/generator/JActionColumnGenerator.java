package org.zhakimel.vgrails.java.component.generator;

import com.vaadin.data.Property;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;

import java.util.Iterator;
import java.util.Map;

/**
 * Action Column Generator
 * Displays action button on corresponding row
 *
 * @author Abiel Hakeem
 */
public class JActionColumnGenerator implements Table.ColumnGenerator {
    private String ICON_EDIT = "../runo/icons/16/document-edit.png";

    private Map<String, Button> buttonMap;

    public Map<String, Button> getButtonMap() {
        return buttonMap;
    }

    public void setButtonMap(Map<String, Button> buttonMap) {
        this.buttonMap = buttonMap;
    }

    private Button.ClickListener editButtonClickListener = new Button.ClickListener() {
        public void buttonClick(Button.ClickEvent clickEvent) {
            //nothing
        }
    };

    public Button.ClickListener getEditButtonClickListener() {
        return editButtonClickListener;
    }

    public void setEditButtonClickListener(Button.ClickListener editButtonClickListener) {
        this.editButtonClickListener = editButtonClickListener;
    }

    public Component generateCell(Table source, Object itemId, Object columnId) {
        Property prop = source.getItem(itemId).getItemProperty("id");
        HorizontalLayout hl = new HorizontalLayout();
        hl.setSpacing(true);
        hl.setSizeUndefined();

        if (buttonMap != null) {
            Iterator<String> iter = buttonMap.keySet().iterator();

            while (iter.hasNext()) {
                Button btn = buttonMap.get(iter.next());
                btn.setDescription(iter.next() + " " + prop);
                btn.setStyleName(Button.STYLE_LINK);

                hl.addComponent(btn);
            }
        } else {
            Button btn = new Button();
            btn.setDescription("edit " + prop);
            btn.addListener(editButtonClickListener);
            btn.setStyleName(Button.STYLE_LINK);
            btn.setIcon(new ThemeResource(ICON_EDIT));
            hl.addComponent(btn);
        }

        return hl;
    }
}
