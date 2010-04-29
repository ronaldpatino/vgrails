package org.zhakimel.vgrails.component.widget

import com.vaadin.ui.CustomField
import com.vaadin.ui.Alignment
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Panel
import com.vaadin.ui.Component
import com.vaadin.ui.PopupView
import com.vaadin.data.Property
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label
import com.vaadin.ui.DefaultFieldFactory
import org.zhakimel.vgrails.component.GrailsTable


class LookupObjectField extends CustomField {

  private List lookupObjects
  private Class<?> type
  private String caption
  private PopupView popup
  private Map columnCaptions
  private Label labelField

  final String DEFAULT_NULL_CAPTION = ""



  LookupObjectField(String caption, List lookupObjects, Map columnCaptions) {
    this.caption = caption
    this.columnCaptions = columnCaptions
    this.lookupObjects = lookupObjects
    HorizontalLayout hl = new HorizontalLayout()

    popup = new PopupView("Lookup..", buildLookupComponent());
    labelField = new Label("?")
    labelField.setWidth  "100%"
    labelField.setContentMode Label.CONTENT_XHTML
    hl.setSpacing true
    hl.addComponent  labelField
    hl.addComponent  popup
    setCompositionRoot hl
  }

  LookupObjectField(Map parameters){
    this(parameters["caption"],parameters["objects"],parameters["columns"])
  }


  public String getCaption() {
    return DefaultFieldFactory.createCaptionByPropertyId(caption);
  }

  public void setCaption(String caption) {
    this.caption = DefaultFieldFactory.createCaptionByPropertyId(caption);
  }



  public void setValue(Object newValue) throws Property.ReadOnlyException, Property.ConversionException {
    super.setValue(newValue);
    String style="style=\"border-style:solid;border-color:#ccc;padding:2px;border-width:1px\"";
    if (newValue) {
      labelField.setValue "<div "+style+">"+newValue.toString()+"</div>"
    } else {
      labelField.setValue  "<div "+style+">"+DEFAULT_NULL_CAPTION+"</div>"
    }
  }

  public void setType(Class<?> type) {
    this.type = type
  }

  public List getLookupObjects() {
    return lookupObjects;
  }

  public void setLookupObjects(List lookupObjects) {
    this.lookupObjects = lookupObjects;
  }

  Class<?> getType() {
    return null;
  }

  private Component buildLookupComponent() {

    GrailsTable lookupTable = new GrailsTable(columnCaptions);
    lookupTable.hasEditButton=false
    lookupTable.entityListData = lookupObjects
    lookupTable.setSelectable true
    lookupTable.setImmediate true
    Property.ValueChangeListener onSelect = {Property.ValueChangeEvent event ->

      this.setValue(lookupTable.value)
  

    } as Property.ValueChangeListener
    lookupTable.addListener onSelect
    lookupTable.setSizeUndefined()
    lookupTable.refreshTableData()
   

    Panel panel = new Panel(DefaultFieldFactory.createCaptionByPropertyId(this.caption));
    panel.description = "Please select one of these value, then move mouse out of panel"
    panel.setSizeUndefined()
    panel.setWidth("300px");
    VerticalLayout vl = (VerticalLayout) panel.getContent();
    vl.setMargin(false);
    vl.addComponent(lookupTable);
    vl.setComponentAlignment(lookupTable, Alignment.TOP_CENTER);
    return panel;
  }

}
