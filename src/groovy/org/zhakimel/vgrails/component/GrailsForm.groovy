package org.zhakimel.vgrails.component


import com.vaadin.data.util.BeanItem
import com.vaadin.terminal.ThemeResource
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.Button.ClickListener
import com.vaadin.ui.*
import org.zhakimel.vgrails.java.component.JGrailsFieldFactory
import org.zhakimel.vgrails.util.VGrailsConstant

/**
 * Create Form based on Grails Domain Object
 * @author Abiel Hakeem
 */
class GrailsForm extends VerticalLayout {

  BeanItem entityBeanItem
  Object entityBean
  List entityShowFields
  Form form
  String formMode = "edit"
  String formCaption
  private Map fieldCaptionMap

  Button.ClickListener saveClickListener
  Button.ClickListener deleteClickListener
  Button.ClickListener cancelClickListener

  Button btnSave
  Button btnDelete
  Button btnCancel

  private List entityFieldIsList=[]
  private Map entityListValues=[:]

  GrailsFieldFactory grailsFormFieldFactory
  boolean hasCrudButton = true        //form has CRUD button
  boolean topCrudButton = false       //crud button placed on top



   /**
    * constructor
    * entityShowFields : list of field names
    * entityBean : Pogo bean for editing
    */
  def GrailsForm(List entityShowFields, Object entityBean) {
    this.entityBeanItem = new BeanItem(entityBean)
    this.entityBean = entityBean
    this.entityShowFields = entityShowFields
    this.formCaption = caption
    form = new Form()
    grailsFormFieldFactory=new GrailsFieldFactory(entityBean)
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

  /**
   * Using form definition
   * Map parameter
   * example:
   *   def formDef =[ "bean":Object                               //MANDATORY
   *                 ,"fields":["fieldA","fieldB","fieldC"]       //MANDATORY
   *                 ,"listfields":["fieldA":Map<k,v>]
   *                 ,"listobjectfields" :["fieldB":[Object,Object,Object]]
   *                 ,"captions":["fieldA":"A Field","fieldB":"B Field","fieldC":"C Field"]
   *                 ,"widths":["fieldA":10]
   *                 ,"disables":["fieldA":true]
   *                 ,"mode":"edit" ,"options":["crud":true,"topcrud":false]
   *                ]
   */
  def GrailsForm(Map formDef){
    
    this(formDef["fields"],formDef["bean"])
    if(formDef["listfields"]) setEntityListValues((Map)formDef["listfields"])
    if(formDef["listobjectfields"]) setEntityObjectValues((Map)formDef["listobjectfields"])
    if(formDef["captions"]) setCustomCaptionMap((Map)formDef["captions"])
    if(formDef["mode"]) formMode = formDef["mode"]

    if(formDef["options"]){
      hasCrudButton = (boolean)formDef["options"]["crud"]
      topCrudButton = (boolean)formDef["options"]["topcrud"]
    }
     form = new Form()
  }

  /**
   * set entity list values
   */
  def setEntityListValues(Map entityListValues){

    entityListValues.each {
        entityFieldIsList.add(it.key)
    }
    this.entityListValues = entityListValues
    grailsFormFieldFactory.entityFieldIsList=entityFieldIsList
    grailsFormFieldFactory.entityListValues=entityListValues
  }

  /**
   * entity Objects
   */
  def setEntityObjectValues(Map entityObjectValues){
    grailsFormFieldFactory.entityObjectValues=entityObjectValues
  }


  /**
   * field caption map
   */
  def setCustomCaptionMap(Map map){
    grailsFormFieldFactory.fieldCaptionMap = map
  }

  /**
   * build form
   */
  def buildForm() {
    setWidth "100%"

    form.setWriteThrough false
    form.setInvalidCommitted false
    form.setFormFieldFactory grailsFormFieldFactory
    form.setItemDataSource entityBeanItem
    form.setVisibleItemProperties entityShowFields
    form.setWidth "100%"
    if(topCrudButton){
      addComponent buildCrudButton()
    }
    addComponent form
    
    if(!topCrudButton){
      addComponent buildCrudButton()
    }

  }

  private Component buildCrudButton(){
    HorizontalLayout hl = new HorizontalLayout()

    if (hasCrudButton) {

      hl.setSpacing true
      hl.setWidth "100%"

      def hright = new HorizontalLayout()
      hright.setSpacing true

      def hleft = new HorizontalLayout()
      hleft.setSpacing true

      btnSave = new Button("Save")
      btnSave.setIcon new ThemeResource(VGrailsConstant.ICON_SAVE)
      btnSave.addListener saveClickListener
      hleft.addComponent btnSave

      btnDelete = new Button("Delete")
      btnDelete.enabled = formMode.equals("edit")
      btnDelete.setIcon new ThemeResource(VGrailsConstant.ICON_DELETE)
      btnDelete.addListener deleteClickListener
      hleft.addComponent btnDelete

      btnCancel = new Button("Cancel")
      btnCancel.setIcon new ThemeResource(VGrailsConstant.ICON_CANCEL)
      btnCancel.addListener cancelClickListener
      hright.addComponent btnCancel
      hright.setComponentAlignment btnCancel, Alignment.MIDDLE_RIGHT

      hl.addComponent hleft
      hl.addComponent hright
      hl.setExpandRatio hleft, 1
    }

    return hl
  }


  def commit() {
    form.commit()
  }

}
