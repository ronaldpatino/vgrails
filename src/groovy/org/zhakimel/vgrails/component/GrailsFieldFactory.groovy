package org.zhakimel.vgrails.component

import com.vaadin.data.Item
import com.vaadin.data.validator.DoubleValidator
import com.vaadin.data.validator.IntegerValidator
import com.vaadin.data.validator.RegexpValidator
import com.vaadin.data.validator.EmailValidator
import com.vaadin.ui.*
import com.vaadin.data.validator.StringLengthValidator
import com.vaadin.data.util.IndexedContainer
import com.vaadin.data.util.BeanItemContainer

/**
 * Vaadin FieldFactory that reads Grails entity field types and constraints
 * @author Abil Hakim
 */
class GrailsFieldFactory extends DefaultFieldFactory {

  def entity
  List entityFieldIsList = []

  Map entityListValues = [:]
  Map entityObjectValues = [:]
  Map customCaptionMap = [:]
  Map fieldCaptionMap=[:]
  Map fieldWidthMap=[:]
  Map fieldDisableMap = [:]

  Locale locale
  String dateFormat

  def GrailsFieldFactory(entity) {
    this.entity = entity
  }

  def setEntityObjectValues(Map map){
    println("setEntityObjectValues"+map)
    entityObjectValues = map
  }

  def Field createField(Item item, Object propertyId,
                        Component uiContext) {

    Field f = super.createField(item, propertyId, uiContext);

    String title

    if (fieldCaptionMap[propertyId]) {
      title = fieldCaptionMap[propertyId]
    } else {
      title = DefaultFieldFactory.createCaptionByPropertyId(propertyId)
    }

    if (item != null) {

      def constraint = entity.constraints[propertyId]
      def entityField = entity[propertyId]

      if(entityObjectValues[propertyId]){
        println "has Entity Object Values"
      }

      //date field
      if (f instanceof DateField) {
          ((DateField) f).setWidth 100,DateField.UNITS_PIXELS
        if (locale) {
          ((DateField) f).setLocale(locale)
        } else if (dateFormat) {
          ((DateField) f).setDateFormat(dateFormat)
        } else {
          ((DateField) f).setDateFormat("d-M-yyyy")
        }
      }

      //select field
      if (checkEntityFieldIsList(propertyId)) {

        Select select = new Select(title)
        select.setColumns 20
        select.setImmediate true
        select.setContainerDataSource getSelectValues(propertyId)
        select.setItemCaptionPropertyId("name")
        if (!constraint.nullable ||
                !constraint.blank) {
          select.setRequired(true)
          select.setRequiredError title + " must not be blank"
        }

        println propertyId + " SELECT created"
        return select
      }

      //textfield
      if (constraint && (entityField instanceof String)) {
        ((TextField) f).setColumns 20


        if (!constraint.nullable ||
                !constraint.blank) {
          f.setRequired(true)
          f.setRequiredError title + " must not be blank"
        }


        if (constraint.password) {
          ((TextField) f).setSecret true

        }

        if (constraint.size) {
          def min = ((Range) constraint.size).min()
          def max = ((Range) constraint.size).max()
          println propertyId + " has size"
          f.addValidator(new StringLengthValidator(title + " should have characters min:" + min + " max:" + max, min, max, false))
          ((TextField) f).setImmediate true


        }

        if (constraint.email) {
          println propertyId + " is email"
          f.addValidator(new EmailValidator(title + " should a valid email address"))

          ((TextField) f).setImmediate true

        }

        if (constraint.creditCard) {
          //add CC validator
        }

        if (constraint.format) {
          //add format validator
        }

        if (constraint.url) {
          println propertyId + " is url"
          String urlRegex = "(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?"
          f.addValidator(new RegexpValidator(urlRegex), title + " should a valid URL")
          ((TextField) f).setImmediate true
        }


        print " inList=" + entity.constraints[propertyId].inList


      } else if (constraint
              && entityField instanceof Integer
      ) {
        ((TextField) f).setImmediate true
        f.addValidator new IntegerValidator(title + " value should be an integer")

      } else if (constraint
                && ((entityField instanceof Double)) || (entityField instanceof BigDecimal)
        ) {
          ((TextField) f).setImmediate true
          f.addValidator new DoubleValidator(title + " value should be a decimal")

        } else if (constraint
                  && (entityObjectValues[propertyId])
          ) {
            Select select = new Select(title,getBeanValues(propertyId))
            select.filteringMode=AbstractSelect.Filtering.FILTERINGMODE_OFF
            select.setNullSelectionAllowed false
            select.setImmediate true

            if (!constraint.nullable ||
                    !constraint.blank) {
              select.setRequired(true)
              select.setRequiredError title + " must not be blank"
            }

            println "INIT="+entityField
            select.setValue(entityField)

            println "GV="+select.getValue()

            return select
          }

      if (propertyId.toString().equals("description") && f instanceof TextField) {

        //((TextField) f).setColumns 25
        ((TextField) f).setRows 4

      }

      if (propertyId.toString().equals("comment") && f instanceof TextField) {
        //((TextField) f).setColumns 25
        ((TextField) f).setRows 2
      }
    }

    return f
  }

  private boolean checkEntityFieldIsList(String entityFieldName) {
    boolean isTrue = false

    for (it in entityFieldIsList) {
      if (entityFieldName.equals(it)) {
        isTrue = true
        break
      }
    }
    return isTrue
  }

  private int checkObjectIndex(Object value,String efn){
    int i=0;
    for(item in entityObjectValues[efn]){
      if(item.id==value.id) break;
      i++
    }
    return i;
  }

  private IndexedContainer getSelectValues(String entityFieldName) {

    IndexedContainer container = new IndexedContainer()
    container.addContainerProperty("name", String.class, null)
    container.addContainerProperty("short", String.class, null)

    entityListValues[entityFieldName].each {
      String id = it.key
      String name = it.value
      Item item = container.addItem(id)
      item.getItemProperty("short").setValue id
      item.getItemProperty("name").setValue name
    }

    return container
  }



   private BeanItemContainer getBeanValues(String entityFieldName) {
    BeanItemContainer container = new BeanItemContainer((List)entityObjectValues[entityFieldName])
    return container
  }
}