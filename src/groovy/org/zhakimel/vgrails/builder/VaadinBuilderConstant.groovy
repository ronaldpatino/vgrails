package org.zhakimel.vgrails.builder

/**
 * Constants for Vaadin Builder
 *
 * usage: In developing Vaadin UI, you just describe the nodes of vaadin components need to be generated
 * then call render() method to render the nodes into Vaadin component. You can access the generated component
 * refference by getting it's refference from VaadinBuilder.components property as a map, but you should set
 * node(s) name for example:
 *
 *  <code> label(name:"myLabel", caption:"Here a label") </code>
 *
 * then you can get the refference by calling for example :
 *
 *  <code>Label myLabel = myVaadinBuilder.components["myLabel"]</code>
 *
 * you should do this after the component is rendered by calling render method, for example:
 * <code>
 * def builder = new VaadinBuilder()
 * def ui = builder.panel(caption:"here a panel"){
 *
 *    label(name:"myLabel", caption:"Here a label")
 *  }
 *
 *  myPanel.addComponent builder.render(ui)  //assumed you write this in a panel building routine
 *  Label myLabel = myVaadinBuilder.components["myLabel"]
 *  </code>
 *
 *  standard parameters for component node:
 *  style                 set style name
 *  addstyle              add style name
 *  width                 set component width
 *  height                set component height
 *
 * standard parameters for layout component/node
 *  margin                set layout margin
 *  spacing               set layout spacing
 *  cols                  set columns for gridlayout / GridLayout
 *  rows                  set rows for gridlayout/GridLayout
 *
 *  @author Abiel Hakeem
 */
class VaadinBuilderConstant {


  /**
   * Generates a window, you should call this using getWindow().addWindow() method
   * usage : window(caption:"a caption",...){ [sub components here] }
   *
   * */
  static final String WINDOW = 'window'

  /**
   * Generates a panel
   * usage : panel(caption:"a caption",...){ [sub components here] }*/
  static final String PANEL = 'panel'

  /**
   * Generates a vertical layout
   * usage : vlayout(spacing:true,...){ [sub components here] }*/
  static final String VLAYOUT = 'vlayout'

  /**
   * generates a horizontal layout
   * usage: hlayout(spacing:true,...) { [sub components here] }*/
  static final String HLAYOUT = 'hlayout'

  /**
   * generates a CSS layout
   * usage: csslayout(style:"my-style",...) { [sub components here] }*/
  static final String CSSLAYOUT = 'csslayout'

  /**
   * generates a grid layout
   * usage: gridlayout(cols:2, rows:2, spacing:true,...) { [sub components here] }*/
  static final String GRIDLAYOUT = 'gridlayout'


  /**
   * generates a split panel
   * usage: splitpanel(orientation:SplitPanel.ORIENTATION_VERTICAL){ first{ [sub components here] }
   *                      second{ [sub components here]  }
   *                   }
   * */
  static final String SPLITPANEL = 'splitpanel'

  /**
   * generates a tabsheet
   * usage: tabsheet(...) {
   *                tab(caption){[sub components here]}
   *                tab(caption){ }
   *                tab(caption){ }
   *             }
   * */

  static final String TABSHEET = 'tabsheet'

  /**
   * generates an accordion
   * usage: accordion(...)
   *             { 
   *               tab(caption){[sub components here]}
   *                tab(caption){[sub components here] }
   *                tab(caption){[sub components here] }
   *             }
   * */
  static final String ACCORDION = 'accordion'



  /**
   * generates a form
   * usage: form(datasource:myItemDataSource,fieldfactory:myFormFieldFactory...)
   */
  static final String FORM = 'form'

  /**
   *  put a user component
   * usage: component(ref:myComponent,...)
   */
  static final String COMPONENT = 'component'

  /**
   * generates a label
   *  usage: label(caption:"a caption", value:myValue)
   */
  static final String LABEL = 'label'

  /**
   * generates a textfield
   *  usage: textfield(caption:"a caption", value:myValue)
   */
  static final String TEXTFIELD = 'textfield'
  /**
   * generates a date field
   *  usage: datefield(caption:"a caption", value:myValue)
   */
  static final String DATEFIELD = 'datefield'

  /**
   * generates a button
   *  usage: button(caption:"a caption", onclick:myClickListener)
   */
  static final String BUTTON = 'button'

  /**
   * generates a native button (standard html button)
   *  usage: nativebutton(caption:"a caption", onclick:myClickListener)
   */
  static final String NATIVEBUTTON = 'nativebutton'

  /**
   * generates a select field
   *  usage: select(caption:"a caption", datasource:myDataSource, onselect:myChangeEvent)
   */
  static final String SELECT = 'select'

 /**
   * generates a combobox field
   *  usage: combobox(caption:"a caption", datasource:myDataSource, onselect:myChangeEvent)
   */
  static final String COMBOBOX = 'combobox'


  /**
   * generates a table
   *  usage: table(caption:"a caption", datasource:myDataSource, onselect:myChangeEvent)
   */
  static final String TABLE = 'table'

}
