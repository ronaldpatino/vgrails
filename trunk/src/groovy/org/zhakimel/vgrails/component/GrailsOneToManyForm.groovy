package org.zhakimel.vgrails.component

import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Form
import com.vaadin.ui.Button
import com.vaadin.ui.Button.ClickListener
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.data.util.BeanItem
import com.vaadin.ui.HorizontalLayout

import com.vaadin.ui.Label
import com.vaadin.ui.TabSheet

import com.vaadin.ui.TabSheet.SelectedTabChangeEvent

import com.vaadin.ui.CssLayout
import com.vaadin.ui.DefaultFieldFactory
import com.vaadin.ui.CustomLayout

/**
 * Create form based on grails object with one-to-many capability
 */
class GrailsOneToManyForm extends VerticalLayout implements
        TabSheet.SelectedTabChangeListener {

  def entityBeanItem
  def entityBean
  def subEntityBean=[:]
  def entityShowFields
  def entityListFields
  Map entityObjectValues=[:]

  Form form
  TabSheet tabSheet
  GrailsFieldFactory fieldFactory

  def tlayouts=[:]
  def subTables=[:]
  def subForms=[:]

  def formMode = "edit"
  def formCaption

  Button.ClickListener saveClickListener
  Button.ClickListener deleteClickListener
  Button.ClickListener cancelClickListener




  /**
  * entityShowFields = ["beanField1","beanField2","beanField3"]
   * entityListFields = ["listField1":["field1","field2","field3"],
   *                     "listField2":["field1","field2"]]
   */
  def GrailsOneToManyForm(entityShowFields, entityListFields, entityBean) {

    this.entityBeanItem = new BeanItem(entityBean)
    this.entityBean = entityBean
    this.entityShowFields = entityShowFields
    this.formCaption = caption
    this.entityListFields = entityListFields

    saveClickListener = {ClickEvent event ->
      //none please define
    } as ClickListener

    deleteClickListener = {ClickEvent event ->
      //none please define
    } as ClickListener

    cancelClickListener = {ClickEvent event ->
      //none please define
    } as ClickListener



    setSizeUndefined()
  }

  def setEntityObjectValues(Map entityObjectValues){
    this.entityObjectValues=entityObjectValues
  }


  def buildForm() {
    form = new Form()
    form.setWriteThrough false
    form.setInvalidCommitted false

    fieldFactory = new GrailsFieldFactory(entityBean)
    form.setFormFieldFactory fieldFactory
    fieldFactory.setEntityObjectValues(this.entityObjectValues)
    form.setItemDataSource entityBeanItem
    form.setVisibleItemProperties entityShowFields

    //parent form area
    buildParentForm()

    //tabbed list area
    buildTableTabs()

    //crud button area
    buildCrudButton()

  }


  private def buildParentForm() {
    def message = new Label(formCaption)
    message.setStyleName "h2"
    addComponent message
  


    /*
    int rows = (entityShowFields.size()/2)+1
    GridLayout gly= new GridLayout(2,rows)
    gly.setSizeUndefined()
    gly.setColumnExpandRatio 0,1
    gly.setColumnExpandRatio 1,1

    gly.setSpacing true
    */

    form.setLayout new CustomLayout("projectformlayout")

    addComponent form
  

  }

  private def buildTableTabs() {
    tabSheet = new TabSheet()
    tabSheet.setStyleName "bar"
    tabSheet.setSizeUndefined()

    for (it in entityListFields) {
      println "it.key="+it.key
      HorizontalLayout horiz = new HorizontalLayout();
      try{
        horiz.addComponent(getTableWidget(it.key))
        horiz.addComponent(getSubForm(it.key))
      } catch(e) {
        println e.getMessage()
      }

      tabSheet.addTab(horiz, DefaultFieldFactory.createCaptionByPropertyId(it.key),null)
    }

    addComponent tabSheet

  }

  def commit() {
    form.commit()
  }

  private def buildCrudButton() {

    if (!formMode.equals("view")) {
      def hl = new CssLayout()
      hl.setWidth  "100%"
      hl.addStyleName "toolbar"

      def left=new CssLayout()
       left.addStyleName("left");
      left.setSizeUndefined()


      Button btnSave = new Button("Save")
      btnSave.addListener saveClickListener
      left.addComponent btnSave

      Button btnDelete = new Button("Delete")
      btnDelete.enabled = formMode.equals("edit")
      btnDelete.addListener deleteClickListener
      left.addComponent btnDelete

      def right=new CssLayout()
      right.setSizeUndefined()
      right.addStyleName("right");

      Button btnCancel = new Button("Cancel")
      btnCancel.addListener cancelClickListener
      right.addComponent btnCancel

      hl.addComponent  left
      hl.addComponent  right


      addComponent hl
    }

  }

  void selectedTabChange(SelectedTabChangeEvent selectedTabChangeEvent) {

  }

  //generate with Grails Table
  private GrailsTable getTableWidget(listName){
     GrailsTable gtable = new GrailsTable(entityListFields[listName])
     gtable.setWidth "100%"
     gtable.setHeight  "150px"
     gtable.entityListData = entityBean[listName]
     gtable.refreshTableData()
     subTables[listName]=gtable

    return gtable
  }

  //generate with GrailsForm
  private GrailsForm getSubForm(formName) {
    GrailsForm gform=null

    if(entityBean[formName]!=null){
     subEntityBean[formName]= entityBean[formName][0]
     gform = new GrailsForm(entityListFields[formName],subEntityBean[formName])
     gform.buildForm()
     subForms[formName]=gform
    }

    return gform
  }


  //commit sub form value
  private def commitSubForm() {

  }


 }
