package org.zhakimel.vgrails.builder

import com.vaadin.ui.Component
import com.vaadin.ui.Panel
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.CssLayout

import com.vaadin.ui.Label
import com.vaadin.ui.TextField
import com.vaadin.ui.Select
import com.vaadin.ui.GridLayout
import com.vaadin.ui.DateField
import com.vaadin.ui.Button
import com.vaadin.ui.NativeButton
import com.vaadin.ui.Form
import com.vaadin.ui.Table
import com.vaadin.terminal.ThemeResource
import com.vaadin.ui.Window
import com.vaadin.ui.ComboBox
import com.vaadin.ui.SplitPanel
import com.vaadin.ui.TabSheet
import com.vaadin.ui.Accordion

/**
 * Builder for Vaadin components
 *
 * @author Abiel Hakeem
 */
class VaadinBuilder extends NodeBuilder {

  def components = [:]

  /**
   * renders component
   */
  def render(data) {
    components = [:]
    def component

    if(data.name.equals(VaadinBuilderConstant.VLAYOUT)){
      component = new VerticalLayout()
      if (data.'@spacing') ((VerticalLayout)component).setSpacing data.'@spacing'
      if (data.'@sizefull') component.setSizeFull()
      if (data.'@margin') ((VerticalLayout)component).setMargin data.'@margin'
    }else if(data.name.equals(VaadinBuilderConstant.HLAYOUT)){
      component = new HorizontalLayout()
    }else if(data.name.equals(VaadinBuilderConstant.PANEL)){
      component = new Panel()
      if (data.'@spacing') ((VerticalLayout)component.getLayout()).setSpacing data.'@spacing'
      if (data.'@caption') component.setCaption data.'@caption'
      if (data.'@sizefull') component.setSizeFull()
      if (data.'@margin') ((VerticalLayout)component.getLayout()).setMargin data.'@margin'

    }else if(data.name.equals(VaadinBuilderConstant.WINDOW)){
      component = new Window()
      if (data.'@caption')  ((Window)component).setCaption data.'@caption'
      if (data.'@modal')  ((Window)component).modal= data.'@modal'
      if (data.'@sizeundefined')  ((Window)component).getLayout().setSizeUndefined()
    }

    if (data.'@style') component.styleName = data.'@style'
    if (data.'@addstyle') component.addStyleName data.'@addstyle'
    if (data.'@width') component.setWidth data.'@width'
    if (data.'@height') component.setHeight data.'@height'

    component = renderLayout(data, component)

    components.each {key, value ->
      println "key=" + key + " value=" + value
    }

    return component

  }

  /**
   * render s widget
   */
  private Component renderWidget(data) {
    Component component
    if (data.name.equals(VaadinBuilderConstant.LABEL)) {

      component = new Label((String)data.'@caption')

    } else if (data.name.equals(VaadinBuilderConstant.TEXTFIELD)) {

      component = new TextField((String)data.'@caption')
      if(data.'@cols')((TextField) component).setColumns data.'@cols'
      if(data.'@rows')((TextField) component).setRows data.'@rows'


    } else if (data.name.equals(VaadinBuilderConstant.SELECT)) {

      component = new Select((String)data.'@caption')
      if(data.'@datasource') ((Select) component).containerDataSource = data.'@datasource'
      if(data.'@onselect') ((Select) component).addListener data.'@onselect'


    } else if (data.name.equals(VaadinBuilderConstant.COMBOBOX)) {

      component = new ComboBox((String)data.'@caption')
      if(data.'@datasource') ((ComboBox) component).containerDataSource = data.'@datasource'
      if(data.'@onselect') ((ComboBox) component).addListener data.'@onselect'

    }else if (data.name.equals(VaadinBuilderConstant.DATEFIELD)) {

      component = new DateField((String)data.'@caption')
      if(data.'@resolution')((DateField) component).setResolution data.'@resolution'
      if(data.'@format')((DateField) component).setDateFormat data.'@format'


    }else if (data.name.equals(VaadinBuilderConstant.BUTTON)) {

      component = new Button((String)data.'@caption')
      if (data.'@icon') ((Button)component).setIcon new ThemeResource(data.'@icon')
      if (data.'@onclick') ((Button) component).addListener data.'@onclick'
      if (data.'@ref') data.'@ref'=component


    }else if (data.name.equals(VaadinBuilderConstant.NATIVEBUTTON)) {

      component = new NativeButton((String)data.'@caption')
      if (data.'@icon') ((Button)component).setIcon new ThemeResource(data.'@icon')
      if (data.'@onclick') ((Button) component).addListener data.'@onclick'

    }

    if(component){
      if (data.'@value') component.value = data.'@value'
    }

    return component
  }

  /**
   * renders containing widget/ layout
   */
  private Component renderLayout(data, Component parent) {
    Component component

    data.each {item ->

     println item.name

      if (item.name.equals(VaadinBuilderConstant.PANEL)) {
        component = new Panel()
        if (item.'@spacing') ((VerticalLayout) component.getLayout()).setSpacing item.'@spacing'
        if (item.'@caption') component.setCaption item.'@caption'
        if (item.'@icon') ((Panel)component).setIcon new ThemeResource(item.'@icon')

        component = renderLayout(item, component)
      } else if (item.name.equals(VaadinBuilderConstant.HLAYOUT)) {
        component = new HorizontalLayout()
        if (item.'@spacing') component.spacing = item.'@spacing'
        if (item.'@margin') component.setMargin item."@margin"

        component = renderLayout(item, component)

      } else if (item.name.equals(VaadinBuilderConstant.VLAYOUT)) {
        component = new VerticalLayout()
        if (item.'@spacing') component.spacing = item."@spacing"
        if (item.'@margin') component.setMargin item."@margin"

        component = renderLayout(item, component)

      } else if (item.name.equals(VaadinBuilderConstant.CSSLAYOUT)) {
        component = new CssLayout()
        component = renderLayout(item, component)

      } else if (item.name.equals(VaadinBuilderConstant.GRIDLAYOUT)) {
        component = new GridLayout()
        if (item.'@spacing') component.spacing = item."@spacing"
        if (item.'@margin') component.setMargin item."@margin"
        if (item.'@cols') component.columns = item.'@cols'
        if (item.'@rows') component.rows = item.'@rows'

        component = renderLayout(item, component)

      } else if (item.name.equals(VaadinBuilderConstant.SPLITPANEL)) {

        component = new SplitPanel()
        if (item.'@orientation') ((SplitPanel)component).orientation = item.'@orientation'
        if (item.'@splitpos') ((SplitPanel)component).setSplitPosition((int) item.'@splitpos',SplitPanel.UNITS_PIXELS)
        if (item.'@locked') ((SplitPanel)component).locked = item.'@locked'

        component = renderLayout(item, component)

      } else if (item.name.equals(VaadinBuilderConstant.TABSHEET)) {

        component = new TabSheet()
        component = renderLayout(item, component)

      } else if (item.name.equals(VaadinBuilderConstant.ACCORDION)) {

        component = new Accordion()
        component = renderLayout(item, component)

      }  else if (item.name.equals(VaadinBuilderConstant.FORM)) {
        component = new Form()
        if (item.'@caption') ((Form) component).caption = item."@caption"
        if (item.'@description') ((Form) component).description = item."@description"
        if (item.'@fieldfactory') ((Form) component).formFieldFactory = item."@fieldfactory"
        if (item.'@datasource') ((Form) component).setItemDataSource item."@datasource"
        if (item.'@visibleitems') ((Form) component).setVisibleItemProperties item."@visibleitems"
        if (item.'@layout') ((Form) component).setLayout item."@layout"

      }else if (item.name.equals(VaadinBuilderConstant.TABLE)) {
          component = new Table()
          if (item.'@caption') ((Table) component).caption = item."@caption"
          if (item.'@description') ((Table) component).description = item."@description"
          if (item.'@datasource') ((Table) component).containerDataSource= item."@datasource"
          if (item.'@visiblecolumns') ((Table) component).visibleColumns= item."@visiblecolumns"
          if (item.'@selectable') ((Table) component).selectable= item."@selectable"


      }else if (item.name.equals(VaadinBuilderConstant.COMPONENT)) {
          component = item.'@ref'
     } else {
          component = renderWidget(item)
     }


      /**
            * decides whether just add to common layout
            */
           if(parent instanceof SplitPanel){
             println "parent is splitpanel"
             if (item.name.equals('first')) {
               VerticalLayout vl = new VerticalLayout()
               vl.setSizeFull()
               component = renderLayout(item, vl)
                ((SplitPanel) parent).setFirstComponent component
             }else if(item.name.equals('second')){
               VerticalLayout vl = new VerticalLayout()
               vl.setSizeFull()
               component = renderLayout(item, vl)
               ((SplitPanel) parent).setSecondComponent component
             }

           } else if(parent instanceof TabSheet ){
             if (item.name.equals('tab')) {
               VerticalLayout vl = new VerticalLayout()
               vl.setSizeFull()
               component = renderLayout(item, vl)
               ThemeResource icon
               if(item.'@icon') icon = new ThemeResource(item.'@icon')
               ((TabSheet) parent).addTab(component,item.'@caption',icon)
             }

           } else if(parent instanceof Accordion ){
             if (item.name.equals('tab')) {
               VerticalLayout vl = new VerticalLayout()
               vl.setSizeFull()
               component = renderLayout(item, vl)
               ThemeResource icon
               if(item.'@icon') icon = new ThemeResource(item.'@icon')
               ((Accordion) parent).addTab(component,item.'@caption',icon)
             }

           }else {

              parent.addComponent component

            }


      //alignment set
      //common style set
      if (item.'@style') component.styleName = item.'@style'
      if (item.'@addstyle') component.addStyleName item.'@addstyle'
      if (item.'@width') component.setWidth item.'@width'
      if (item.'@height') component.setHeight item.'@height'

      if (item.'@name' && !item.name.equals(VaadinBuilderConstant.COMPONENT)) {
        components[item.'@name'] = component
      }



      if (parent instanceof HorizontalLayout || parent instanceof VerticalLayout) {
        if (item.'@alignment') parent.setComponentAlignment component, item.'@alignment'
        if (item.'@expandratio') parent.setExpandRatio component, item.'@expandratio'

      }

    }
    return parent
  }

}

