package org.zhakimel.vgrails.component

import com.vaadin.ui.Window

import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Button

import com.vaadin.ui.HorizontalLayout
import com.vaadin.terminal.ThemeResource
import com.libertech.rtmx.ui.util.AppConstant
import com.vaadin.ui.Alignment

/**
 * Creates Grails Form in a modal window
 */
class GrailsFormWindow extends Window {

  List showFields = []
  def entity
  String editorMode

  GrailsForm form
  Button btnCancel
  Button btnSave
  Button btnDelete


  def GrailsFormWindow(Object entity, List showFields, String editorMode) {

    this.entity = entity
    this.showFields = showFields
    setBorder Window.BORDER_MINIMAL
    modal = true
    ((VerticalLayout) getContent()).setSizeUndefined()

    form = new GrailsForm(this.showFields, this.entity)
    form.formCaption = caption
    form.formMode = editorMode
    form.hasCrudButton = false

    btnSave = new Button("Save")
    btnSave.setIcon new ThemeResource(AppConstant.ICON_SAVE)

    btnDelete = new Button("Delete")
    btnDelete.enabled = form.formMode.equals("edit")
    btnDelete.setIcon new ThemeResource(AppConstant.ICON_DELETE)

    btnCancel = new Button("Cancel")
    btnCancel.setIcon new ThemeResource(AppConstant.ICON_CANCEL)

  }

  def setFieldCaptionMap(Map map) {
    form.setCustomCaptionMap(map)
  }

  def setEntityListValues(Map m) {
    form.setEntityListValues(m)
  }
  /**
   * CALL THIS AFTER INSTANTIATE: MANDATORY FOR BUILDING FORM WIDGETS
   */
  def build() {
    form.buildForm()
    getContent().addComponent form
    addComponent buildCrudButton()
  }

  private def buildCrudButton() {
    def hl = new HorizontalLayout()
    hl.setSpacing true
    hl.setWidth "100%"

    def hright = new HorizontalLayout()
    hright.setSpacing true


    def hleft = new HorizontalLayout()
    hleft.setSpacing true

    hleft.addComponent btnSave

    hleft.addComponent btnDelete

    hright.addComponent btnCancel
    hright.setComponentAlignment btnCancel, Alignment.MIDDLE_RIGHT

    hl.addComponent hleft
    hl.addComponent hright
    hl.setExpandRatio hleft, 1

    return hl
  }


}
