package org.zhakimel.vgrails.app.panel

import com.vaadin.ui.SplitPanel

import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Button
import org.zhakimel.vgrails.util.VGrailsConstant
import com.vaadin.ui.Alignment
import org.zhakimel.vgrails.component.widget.Spacer
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.Label
import org.zhakimel.vgrails.component.GrailsTable

import com.vaadin.ui.Form
import com.vaadin.ui.Component
import com.vaadin.ui.Select
import com.vaadin.ui.TextField


import com.vaadin.data.Property
import com.vaadin.data.Property.ValueChangeEvent
import com.vaadin.data.util.IndexedContainer
import com.vaadin.data.Item
import com.vaadin.ui.CssLayout

import com.vaadin.terminal.ThemeResource
import com.vaadin.ui.VerticalLayout

/**
 * creates generic list-detail panel, table on the top, and  detail panel where you
 * add components on bottom
 *
 * @author Abiel Hakeem
 */

class GrailsListDetailPanel  extends SplitPanel implements Button.ClickListener, Property.ValueChangeListener{

  final SplitPanel listPanel
  final GrailsTable table
  final SplitPanel detailPanel
  private final CssLayout detailPanelButtonBar
  final Button btnSave = new Button(VGrailsConstant.STR_SAVE,this)
  final Button btnDelete = new Button(VGrailsConstant.STR_DELETE,this)
  final Button btnCancel = new Button(VGrailsConstant.STR_CANCEL,this)
  final Button btnEdit    = new Button(VGrailsConstant.STR_EDIT,this)
  final Button btnNew    = new Button(VGrailsConstant.STR_NEW,this)

  final String STATE_VIEW="VIEW"
  final String STATE_NEW="NEW"
  final String STATE_EDIT="EDIT"
  final int DEFAULT_SPLIT_POSITION=200
  final int DEFAULT_SPLIT_MIN_POSITION=0


  Form detailForm
  final Label tableCaptionLabel=new Label()
  final HorizontalLayout tableButtonBar
  final Select tableSelectField = new Select()
  final TextField tableSearchField = new TextField()
  final Button btnSearch = new Button(VGrailsConstant.STR_SEARCH)
  final VerticalLayout detailFormLayout = new VerticalLayout()

  String detailState=STATE_VIEW
  Object selectedTableValue
  boolean maximizeFormView

  /**
   * Constructor
   */
  GrailsListDetailPanel(){
    super()

    detailForm= new Form()
    detailForm.getLayout().setMargin true

    table = new GrailsTable([:])
    table.setSelectable true
    table.setImmediate true
    table.hasEditButton = false
    table.addListener this
    table.setSizeFull()

    listPanel = new SplitPanel(SplitPanel.ORIENTATION_VERTICAL)
 
    tableCaptionLabel.addStyleName("h3 color")

    listPanel.setStyleName "small"
    listPanel.setSplitPosition 55, SplitPanel.UNITS_PIXELS
    listPanel.setLocked true
    listPanel.setFirstComponent buildTableButtonBar()
    listPanel.setSecondComponent table
    listPanel.setWidth "100%"
    listPanel.setSizeFull()

    detailPanel = new SplitPanel(SplitPanel.ORIENTATION_VERTICAL)
    detailPanel.setSplitPosition 46, SplitPanel.UNITS_PIXELS
    detailPanel.setStyleName "small"
    detailPanel.locked = true


    detailPanelButtonBar = new CssLayout()
    detailPanelButtonBar.setStyleName "toolbar"
    detailPanelButtonBar.setSizeUndefined()
    detailPanelButtonBar.setWidth "100%"



    CssLayout left = new CssLayout()

    left.addStyleName  "left"
    left.setWidth "100%";
    detailPanelButtonBar.addComponent  left

    CssLayout right = new CssLayout()
    right.addStyleName  "right"
    right.setSizeUndefined();
    detailPanelButtonBar.addComponent  right
    btnSave.setIcon(new ThemeResource(VGrailsConstant.ICON_OK))
    btnCancel.setIcon(new ThemeResource(VGrailsConstant.ICON_CANCEL))
    btnDelete.setIcon(new ThemeResource(VGrailsConstant.ICON_DELETE))
    btnNew.setIcon(new ThemeResource(VGrailsConstant.ICON_ADD))
    btnEdit.setIcon(new ThemeResource(VGrailsConstant.ICON_EDIT))

    btnSave.visible=false
    btnCancel.visible=false
    btnDelete.visible=false
    btnNew.visible=true
    btnEdit.visible=true
    btnEdit.enabled = false
    right.addComponent  btnSave
    right.addComponent  btnDelete
    right.addComponent  btnCancel
    right.addComponent  btnEdit

    detailFormLayout.addComponent  detailForm
    detailFormLayout.setMargin true
    detailFormLayout.setSpacing true

    detailPanel.setFirstComponent detailPanelButtonBar
    detailPanel.setSecondComponent detailFormLayout

    setSplitPosition 200, SplitPanel.UNITS_PIXELS
    setFirstComponent listPanel
    setSecondComponent detailPanel
    setWidth "100%"
    setHeight "100%"

  }

  def setTableCaption(String caption){
    tableCaptionLabel.setContentMode Label.CONTENT_XHTML
    tableCaptionLabel.setValue "<h3>&nbsp;&nbsp;"+caption+"</h3>"
    tableCaptionLabel.setStyleName "color"
  }

  /**
   * set state of button display
   */
  def setState(String state){

    if(state.equals(STATE_VIEW)){
      btnSave.visible=false
      btnDelete.visible=false
      btnCancel.visible=false
      btnNew.visible=true
      btnEdit.visible=true

      listPanel.enabled = true
      detailForm.enabled=false
      detailState=state

     if(maximizeFormView){
      setSplitPosition DEFAULT_SPLIT_POSITION,SplitPanel.UNITS_PIXELS
     }

    }else if(state.equals(STATE_EDIT) || state.equals(STATE_NEW)){

      btnSave.visible=true
      
      btnDelete.enabled = !state.equals(STATE_NEW)

      btnDelete.visible=true
      btnCancel.visible=true
      btnEdit.visible=false
      btnNew.visible=false

      listPanel.enabled = false
      detailForm.enabled = true
      detailState=state

      if(maximizeFormView){
        setSplitPosition DEFAULT_SPLIT_MIN_POSITION,SplitPanel.UNITS_PIXELS
      }

    }

  }

  /**
   * clicking of buttons that changed state of button display
   */
  void buttonClick(ClickEvent clickEvent) {
    Button b = clickEvent.button
    if(b.caption.equals(VGrailsConstant.STR_NEW)
        || b.caption.equals(VGrailsConstant.STR_EDIT)){
      if(b.caption.equals(VGrailsConstant.STR_NEW))
         setState(STATE_NEW)
      else
        setState(STATE_EDIT)


    }else if(b.caption.equals(VGrailsConstant.STR_SAVE)
             || b.caption.equals(VGrailsConstant.STR_CANCEL)
             || b.caption.equals(VGrailsConstant.STR_DELETE)){

      setState(STATE_VIEW)

    }
  }

  void valueChange(ValueChangeEvent valueChangeEvent) {

    selectedTableValue=table.value
    if(selectedTableValue){
      btnEdit.enabled =true

    }else{
      btnEdit.enabled =false
    }

  }





  private Component buildTableButtonBar(){
    tableButtonBar = new HorizontalLayout()
    HorizontalLayout left = new HorizontalLayout()
    left.setSpacing true
    HorizontalLayout right = new HorizontalLayout()

    tableButtonBar.setWidth  "100%"
    tableButtonBar.setSpacing true
    tableButtonBar.setMargin true
    Label vline = new Label("|")
    Spacer spring = new Spacer()

    left.addComponent  tableCaptionLabel
    left.addComponent  vline
    left.addComponent  tableSelectField
    left.addComponent  tableSearchField
    left.addComponent  btnSearch

    right.addComponent btnNew

    left.setComponentAlignment tableCaptionLabel, Alignment.MIDDLE_LEFT
    left.setComponentAlignment vline, Alignment.MIDDLE_LEFT
    left.setComponentAlignment tableSelectField, Alignment.MIDDLE_LEFT
    left.setComponentAlignment tableSearchField, Alignment.MIDDLE_LEFT
    left.setComponentAlignment btnSearch, Alignment.MIDDLE_LEFT

    right.setComponentAlignment btnNew, Alignment.MIDDLE_RIGHT

    tableButtonBar.addComponent left
    tableButtonBar.addComponent spring
    tableButtonBar.addComponent right
    tableButtonBar.setExpandRatio spring,1

    return tableButtonBar
  }

  /**
   * Set select field value maps
   */
  def setSelectFieldValues(Map<String,String> valueMap){
    IndexedContainer container = new IndexedContainer()
    container.addContainerProperty("name", String.class, null)
    container.addContainerProperty("id", String.class, null)
    valueMap.each {
      String id = it.key
      String name = it.value
      Item item = container.addItem(id)
      item.getItemProperty("id").setValue id
      item.getItemProperty("name").setValue name
    }
    tableSelectField.setContainerDataSource container
  }
}