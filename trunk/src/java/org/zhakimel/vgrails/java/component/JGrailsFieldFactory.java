package org.zhakimel.vgrails.java.component;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.validator.*;
import com.vaadin.ui.*;
import groovy.lang.GroovyObject;
import org.codehaus.groovy.grails.commons.DefaultGrailsClass;
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass;


import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty;
import org.codehaus.groovy.grails.validation.ConstrainedProperty;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.springframework.beans.BeanWrapper;


import java.math.BigDecimal;
import java.util.*;


/**
 * Grails field factory
 * reads fields description from a grails domain objects
 * and set the validators based on it's constraints
 *
 * @author abiel hakeem
 */
public class JGrailsFieldFactory extends DefaultFieldFactory {


    private Object entity;
    private List<String> entityFieldIsList = new ArrayList<String>();
    private Map<String,Map> entityListValues = new HashMap<String,Map>();
    private Map<String,List> entityObjectValues = new HashMap<String,List>();
    private Map<String,String> customCaptionMap = new HashMap<String,String>();
    private Map<String,String> fieldCaptionMap = new HashMap<String,String>();
    private Map fieldWidthMap = new HashMap();
    private Map fieldDisableMap = new HashMap();

    private Locale locale;
    private String dateFormat;
    private final DefaultGrailsDomainClass domainClass;


    JGrailsFieldFactory(Object entity) {
        this.entity = entity;
        domainClass=new DefaultGrailsDomainClass(entity.getClass());
    }

    public Field createField(Item item, Object propertyId, Component uiContext) {

        Field f = super.createField(item, propertyId, uiContext);
        ((AbstractField)f).setImmediate(true);
        String title;

        if (fieldCaptionMap.get(propertyId.toString()) != null) {
            title = fieldCaptionMap.get(propertyId.toString());
        } else {
            title = DefaultFieldFactory.createCaptionByPropertyId(propertyId);
        }


        if (item != null) {
            GrailsDomainClassProperty entityField;

            try {
                entityField = domainClass.getPropertyByName((String) propertyId);
                
                System.out.println("EntityField ="+entityField.getName());
                System.out.println("Type       ="+domainClass.getPropertyType(propertyId.toString()));
                System.out.println("Value       ="+item.getItemProperty(propertyId.toString()).getValue());

            } catch (Exception e) {
                return null;
            }

            Map constrainedProperties = domainClass.getConstrainedProperties();
            ConstrainedProperty constraint = (ConstrainedProperty) constrainedProperties.get(propertyId);

            if (f instanceof DateField) {
                ((DateField) f).setWidth(100,DateField.UNITS_PIXELS);
                if (locale != null) {
                    ((DateField) f).setLocale(locale);
                } else if (dateFormat != null) {
                    ((DateField) f).setDateFormat(dateFormat);
                } else {
                    ((DateField) f).setDateFormat("d-M-yyyy");
                }
            }

            if (checkEntityFieldIsList((String) propertyId)) {
                return createListValueField(title, (String) propertyId, constraint);
            }

            if (constraint != null && (entityField.getType().equals(String.class))) {
                   ((TextField) f).setColumns(20);
                  try {
                        if (constraint.isPassword()) {
                             ((TextField) f).setSecret(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                if (!constraint.isNullable() || !constraint.isBlank()) {
                    f.setRequired(true);
                    f.setRequiredError(title + " must not be blank");
                }

               /* if (constraint.getSize() != null) {
                    long min = ((Range) constraint.getSize()).min();
                    long max = ((Range) constraint.getSize()).max();

                    f.addValidator(new StringLengthValidator(title + " should have characters min:" + min + " max:" + max, (int) min, (int) max, false));
                    ((TextField) f).setImmediate(true);
                }*/

                if (constraint.isEmail()) {
                    f.addValidator(new EmailValidator(title + " should a valid email address"));
                }

                if (constraint.isCreditCard()) {
                    //add CC validator
                }

                if (constraint.getFormat() != null) {
                    //add format validator
                }

                if (constraint.isUrl()) {
                    String urlRegex = "(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";
                    f.addValidator(new RegexpValidator(urlRegex, title + " should a valid URL"));
                }
            } else if (constraint != null && entityField.getType().equals(Integer.class)) {
                 ((TextField) f).setColumns(20);
                f.addValidator(new IntegerValidator(title + " value should be an integer"));

            } else if (constraint != null && ((entityField.getType().equals(Double.class))
                    || (entityField.getType().equals(BigDecimal.class)))
                    ) {
                 ((TextField) f).setColumns(20);
                f.addValidator(new DoubleValidator(title + " value should be a decimal"));

            } else if (constraint != null && (entityObjectValues.get(propertyId.toString()) != null)) {

                ComboBox cbo= createListObjectField(title, propertyId.toString(), constraint);
                try {
                    cbo.setInputPrompt(item.getItemProperty(propertyId.toString()).getValue().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cbo.setValue(item.getItemProperty(propertyId.toString()).getValue());

                return cbo;


               /* JLookupField luf=new JLookupField(title,entityObjectValues.get(propertyId.toString()));
                luf.setValue(item.getItemProperty(propertyId.toString()).getValue());
                return luf;*/

            }

            if (propertyId.toString().equals("description") && f instanceof TextField) {
                ((TextField) f).setRows(4);
            }

            if (propertyId.toString().equals("comment") && f instanceof TextField) {
                ((TextField) f).setRows(2);
            }
        }
        return f;
    }

    private boolean checkEntityFieldIsList(String entityFieldName) {
        boolean isTrue = false;
        for (int i = 0; i < entityFieldIsList.size(); i++) {
            String s = entityFieldIsList.get(i);
            if (entityFieldName.equals(s)) {
                isTrue = true;
                break;
            }
        }
        return isTrue;
    }

    private Select createListValueField(String title, String propertyId, ConstrainedProperty constraint) {
        Select select = new Select(title);
        select.setImmediate(true);
        select.setContainerDataSource(getSelectValues(propertyId));
        select.setItemCaptionPropertyId("name");
        if (!constraint.isNullable() || !constraint.isBlank()) {
            select.setRequired(true);
            select.setRequiredError(title + " must not be blank");
        }
        select.setColumns(20);
        return select;
    }

    private ComboBox createListObjectField(String title, String propertyId, ConstrainedProperty constraint) {
        ComboBox select = new ComboBox(title);
        select.setNullSelectionAllowed(false);
        select.setImmediate(true);
        select.setReadThrough(true);
        select.setItemCaptionMode(ComboBox.ITEM_CAPTION_MODE_ID);
        List lst = entityObjectValues.get(propertyId);

        for (Object o : lst) {
            select.addItem(o);
        }

        if (!constraint.isNullable() || !constraint.isBlank()) {
            select.setRequired(true);
            select.setRequiredError(title + " must not be blank");
        }
        select.addStyleName("select-button");
        select.setColumns(20);
        return select;
    }

    private IndexedContainer getSelectValues(String entityFieldName) {

        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("short", String.class, null);

        Map m = entityListValues.get(entityFieldName);
        Iterator<String> iterator = m.keySet().iterator();

        while (iterator.hasNext()) {
            String k = iterator.next();
            String name =(String) m.get(k);
            Item item = container.addItem(k);
            item.getItemProperty("short").setValue(k);
            item.getItemProperty("name").setValue(name);
        }
        return container;
    }

    private BeanItemContainer getBeanValues(String entityFieldName) {
        if(entityObjectValues.get(entityFieldName)!=null){
            return new BeanItemContainer(entityObjectValues.get(entityFieldName));
        }
       return null;
    }


    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public List<String> getEntityFieldIsList() {
        return entityFieldIsList;
    }

    public void setEntityFieldIsList(List<String> entityFieldIsList) {
        this.entityFieldIsList = entityFieldIsList;
    }

    public Map getEntityListValues() {
        return entityListValues;
    }

    public void setEntityListValues(Map<String,Map> entityListValues) {
        this.entityListValues = entityListValues;
    }

    public Map getEntityObjectValues() {
        return entityObjectValues;
    }

    public void setEntityObjectValues(Map<String,List> entityObjectValues) {
        this.entityObjectValues = entityObjectValues;
    }

    public Map getCustomCaptionMap() {
        return customCaptionMap;
    }

    public void setCustomCaptionMap(Map<String,String> customCaptionMap) {
        this.customCaptionMap = customCaptionMap;
    }

    public Map getFieldCaptionMap() {
        return fieldCaptionMap;
    }

    public void setFieldCaptionMap(Map<String,String> fieldCaptionMap) {
        this.fieldCaptionMap = fieldCaptionMap;
    }

    public Map getFieldWidthMap() {
        return fieldWidthMap;
    }

    public void setFieldWidthMap(Map fieldWidthMap) {
        this.fieldWidthMap = fieldWidthMap;
    }

    public Map getFieldDisableMap() {
        return fieldDisableMap;
    }

    public void setFieldDisableMap(Map fieldDisableMap) {
        this.fieldDisableMap = fieldDisableMap;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

}
