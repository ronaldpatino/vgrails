package org.zhakimel.vgrails.component

import org.zhakimel.vgrails.java.component.generator.JActionColumnGenerator
import org.zhakimel.vgrails.java.component.generator.JDateColumnGenerator
import com.vaadin.data.util.BeanItem
import com.vaadin.data.util.BeanItemContainer
import com.vaadin.ui.Button
import com.vaadin.ui.Table
import com.vaadin.data.Container

/**
 * Implements table that reads Grails domain objects also adds features to it
 * @author Abiel Hakeem
 */
class GrailsTable extends Table {

  Map<String,Class> fieldMap
  Map<String,String> customCaptionMap                          //for translating default caption to custom caption
  BeanItemContainer container
  List<Object> entityListData
  String emptyListMessage = "List is empty"
  String nullListMessage = "List is null"
  Button.ClickListener editButtonClickListener
  boolean hasEditButton = true
  List<String> visibles = []


  def GrailsTable(Map<String,Class> fieldMap) {
    super()
    addStyleName "striped"
    addStyleName "strong"
        
    this.fieldMap = fieldMap
    setWidth "100%"
    selectable = false
    setSizeFull()
    editButtonClickListener = {Button.ClickEvent event ->
      //none please define
    } as Button.ClickListener


  }

  def refreshTableData() {
    def temp = []
    visibles = []
    fieldMap.each {
      visibles.add(it.key)
    }

    if (entityListData) {
      def prev = new Object()

      for (i in entityListData) {
        if (!prev.equals(i)) temp.add i
        prev = i
      }

      println temp
      container = new BeanItemContainer(temp)
      container.getItemIds().each {
        println "container>>" + it
      }

      def tempMap
      removeGeneratedXColumn()

      fieldMap.each {
        if (it.value == Date.class) {
          addGeneratedColumn(it.key, new JDateColumnGenerator("dd-MM-yyyy"))
        }
      }

      if (hasEditButton) {
        visibles.add("Action")
        JActionColumnGenerator acg = new JActionColumnGenerator()
        acg.editButtonClickListener = this.editButtonClickListener
        addGeneratedColumn("Action", acg)
      }

      setContainerDataSource container
      this.visibleColumns = visibles
    } else {
      setContainerDataSource null
    }
  }


  private Container getTableData() {

    for (entity in entityListData) {
      container.addItem(transformToObject(entity))
    }
    return container
  }

  private def removeGeneratedXColumn() {
    for (item in fieldMap) {
      removeGeneratedColumn(item.key)
    }
    if (hasEditButton) {
      removeGeneratedColumn("Action")
    }

  }

  private List transformToListObject(List entityListData) {
    List l = []
    for (entity in entityListData) {
      l.add(transformToObject(entity))
    }
    return l
  }

  private Object transformToObject(Object entity) {

    List<String> props = []
    props.add("id")
    for (f in fieldMap) {
      props.add(f.key)
    }

    def obj = new BeanItem(entity, props)
    return obj
  }

}
