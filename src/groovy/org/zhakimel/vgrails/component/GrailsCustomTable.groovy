package org.zhakimel.vgrails.component

import com.vaadin.ui.Table

import com.vaadin.ui.Button
import com.vaadin.data.util.IndexedContainer
import org.zhakimel.vgrails.java.component.generator.JDateColumnGenerator
import org.zhakimel.vgrails.java.component.generator.JActionColumnGenerator
import com.vaadin.data.Item

/**
 * Read Grails entity list and set to indexed container of table
 * 
 * @author Abiel Hakim
 */
class GrailsCustomTable extends Table {

  Map fieldMap
  Map customCaptionMap                          //for translating default caption to custom caption

  IndexedContainer container

  List entityListData
  String emptyListMessage = "List is empty"
  String nullListMessage = "List is null"
  Button.ClickListener editButtonClickListener
  boolean hasEditButton = true

  private def visibles = []

  def GrailsCustomTable(Map fieldMap) {
    super()
    addStyleName "striped"
    addStyleName "strong"

    this.fieldMap = fieldMap

    setWidth "100%"
    selectable = false
    setSizeFull()

    editButtonClickListener = {Button.ClickEvent event ->
    } as Button.ClickListener
  }

  /**
   *   refresh Table Data
   */
  void refreshTableData() {
    visibles=[]
     fieldMap.each {
      visibles.add(it.key)
    }

    if (entityListData) {

      container = new IndexedContainer()
      removeGeneratedXColumn()

      container.addContainerProperty("id", Integer.class, null)

      fieldMap.each {
        container.addContainerProperty(it.key, it.value, null)
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

      int i=1
      for (record in entityListData) {
        Item item = container.addItem(i)
        if (item) {
          item.getItemProperty("id").setValue(record["id"])

          for (f in fieldMap) {
            item.getItemProperty(f.key).setValue(record[f.key])
          }
        }
        i++
      }

      setContainerDataSource container
      this.visibleColumns = visibles
    }else{
      setContainerDataSource null
    }
  }


  private void removeGeneratedXColumn() {
    for (item in fieldMap) {
      removeGeneratedColumn(item.key)
    }
    if (hasEditButton) {
      removeGeneratedColumn("Action")
    }
  }

}
