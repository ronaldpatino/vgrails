package com.libertech.rtmx.ui.project

import com.vaadin.ui.Panel
import org.zhakimel.vgrails.util.UIBuilder

import com.libertech.rtmx.service.LoggerService
import com.libertech.rtmx.service.CommonService
import com.libertech.erp.model.project.Project
import org.zhakimel.vgrails.component.GrailsTable
import org.zhakimel.vgrails.component.Spacer
import com.vaadin.ui.Alignment
import com.vaadin.ui.Button
import com.vaadin.ui.Label
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.GridLayout
import com.vaadin.ui.ComboBox
import com.vaadin.ui.Button.ClickEvent
import com.libertech.rtmx.service.ProjectService
import com.vaadin.ui.Window
import com.vaadin.ui.VerticalLayout

import com.vaadin.ui.Button.ClickListener
import org.zhakimel.vgrails.component.GrailsOneToManyForm
import com.vaadin.data.util.BeanItem
import com.libertech.rtmx.ui.util.AppConstant
import com.vaadin.terminal.ThemeResource
import com.libertech.rtmx.service.PartyService
import com.vaadin.ui.Window.Notification
import com.libertech.rtmx.ui.MainApp
import org.zhakimel.vgrails.util.AppManager

class ProjectList extends Panel implements UIBuilder , Button.ClickListener {

  MainApp app
  AppManager manager

  CommonService commonService
  LoggerService loggerService
  ProjectService projectService
  PartyService partyService

  //main table data
  def projectShowFields = ["name":String.class, "description":String.class, "projectCode":String.class,
          "client":String.class,"sponsor":String.class,"fromDate":Date.class,"thruDate":Date.class]

  //form data
  def projectOneShowFields = ["name",  "projectCode", "description", "client","sponsor","fromDate","thruDate"]
  def projectManyShowFields = ["milestones":["name":String.class,"description":String.class,
                                             "fromDate":Date.class,"thruDate":Date.class,"percentComplete":Long.class,
                                             "status":String.class]]


  Map objectValues=[:]

  BeanItem projectItem
  //entity data
  Project project;

  //widget data
  GrailsTable gTable;
  GrailsOneToManyForm gForm
  Window editorWindow

  boolean built=false
  def ProjectList(caption,MainApp app) {
    super(caption);

    this.app =app
    manager = this.app.manager

    commonService = manager.serviceFactory.getCommonService()
    loggerService = manager.serviceFactory.getLoggerService()
    projectService = manager.serviceFactory.getProjectService()
    partyService = manager.serviceFactory.getPartyService()

    setWidth "100%"


  }

  void build() {

    def grd = new GridLayout(2, 1)
    grd.setColumnExpandRatio 0, 1.0f
    grd.setSizeFull()

    ComboBox cbo = new ComboBox();
    cbo.setWidth "150px"

    grd.addComponent cbo
    grd.setComponentAlignment cbo, Alignment.MIDDLE_LEFT

    def hl2 = new HorizontalLayout()
    hl2.setSpacing true

    def addButton = new Button(AppConstant.STR_NEW, this)
    addButton.setIcon new ThemeResource(AppConstant.ICON_DOCUMENT)
    hl2.addComponent addButton
    hl2.setComponentAlignment addButton, Alignment.MIDDLE_RIGHT

    def separator = new Label("|")
    hl2.addComponent separator
    hl2.setComponentAlignment separator, Alignment.MIDDLE_RIGHT


    def refreshButton = new Button(AppConstant.STR_REFRESH, this)
    refreshButton.setIcon new ThemeResource(AppConstant.ICON_REFRESH)
    hl2.addComponent refreshButton
    hl2.setComponentAlignment refreshButton, Alignment.MIDDLE_RIGHT

    grd.addComponent hl2

    addComponent grd
    addComponent new Spacer()

    gTable = new GrailsTable(projectShowFields)
    addComponent gTable

    refreshUIData()

     this.built = true
  }

  boolean isBuilt() {
    return this.built;
  }

  void refreshUIData() {
    def projectList = projectService.findAllProject()
    gTable.entityListData = projectList
    gTable.refreshTableData()
    project= new Project()

  }


   void buttonClick(ClickEvent clickEvent) {
    def button = clickEvent.button
    if (button.caption.equals(AppConstant.STR_NEW)) {
      showEditor(0)
    }

  }

  void showEditor(int id) {
    setEditorWindowValues(id)
    createEditorWindow()
    getWindow().addWindow editorWindow
  }

  void createEditorWindow() {

    editorWindow = new Window("Project Editor")
    editorWindow.center()
    ((VerticalLayout) editorWindow.getContent()).setSizeUndefined()
    gForm = new GrailsOneToManyForm(projectOneShowFields,projectManyShowFields,project)
    gForm.formCaption = "Project"
    gForm.setEntityObjectValues(getObjectValuesForProject())
    gForm.saveClickListener = {ClickEvent event ->

      //IMPORTANT USE TRY-CATCH TO COMMIT VALUES
      try{
           gForm.commit()
           projectService.saveProject(project)
           getWindow().showNotification "Project Saved"
           getWindow().removeWindow editorWindow
           refreshUIData()
        }catch(e){
          getWindow().showNotification e.getMessage(), Notification.TYPE_ERROR_MESSAGE
       }


     } as ClickListener

    gForm.cancelClickListener = {ClickEvent event ->
        getWindow().removeWindow editorWindow


     } as ClickListener

    gForm.buildForm()
    editorWindow.addComponent gForm

  }

  void setEditorWindowValues(int id) {

    if (id == 0) {
      projectItem = new BeanItem(project)
    } else {
      project = projectService.getProject(id)
      projectItem = new BeanItem(project)
    }

  }

  Map getObjectValuesForProject(){
    Map map = [:]
    List lst = []

     partyService.findAllPerson().each{
       lst.add(it)
     }

    map["client"]=lst
    map["sponsor"]=lst
    return map
  }

}
