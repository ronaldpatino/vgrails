package com.libertech.rtmx.ui

import com.libertech.rtmx.ui.common.LoginPanel
import org.zhakimel.vgrails.util.AppManager
import com.vaadin.ui.SplitPanel
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Window
import org.zhakimel.vgrails.app.*
import com.libertech.rtmx.ui.dashboard.DashboardPanel
import com.libertech.rtmx.ui.project.ProjectList

import com.libertech.rtmx.ui.party.RelationshipTypeListPanel
import com.libertech.rtmx.ui.party.RoleTypeListPanel
import com.libertech.rtmx.ui.party.PartyTypeList
import com.libertech.rtmx.ui.party.RelationshipList
import com.libertech.rtmx.ui.party.OrganizationList
import com.libertech.rtmx.ui.party.PersonList
import com.libertech.rtmx.ui.common.SyslogPanel
import com.libertech.rtmx.ui.admin.UserLoginList
import com.libertech.rtmx.ui.common.CodeDecodePanel
import com.libertech.rtmx.ui.util.ServiceFactory

/**
 *  Main application layout
 *   and initializer
 */

class MainApp extends VGrailsApplication {


  VerticalLayout main = new VerticalLayout();
  Map<String, Map<String, Object>> NAVIGATION_APP_PANEL

  /**
   * Call this method to initialize
   */
  void init() {

    setTheme("latte");
    //initialize manager
    manager = new AppManager(this)
    //MANDATORY Set service factory here
    manager.serviceFactory=new ServiceFactory()

    //call manager to init application contents
    initContents(manager)

    //set application CSS theme

    //define window for this app
    def window = new Window("RTMX 01", main)
    setMainWindow window

    //initialize application panels
    brandingPanel = new BrandingPanel(this)
    navigationPanel = new NavigationPanel(this)
    subNavigationPanel = new SubNavigationPanel(this)
    contentPanel = new ContentPanel()
    copyrightPanel = new CopyrightPanel()

    //define layout
    main.setSizeFull()
    main.addComponent brandingPanel
    main.addComponent navigationPanel

    final SplitPanel split = new SplitPanel(
            SplitPanel.ORIENTATION_HORIZONTAL);
    split.setSplitPosition(20);
    split.setStyleName "small"
    main.addComponent(split);
    main.setExpandRatio(split, 1);

    split.setFirstComponent(subNavigationPanel);
    split.setSecondComponent(contentPanel);

    //initiate login panels
    def login = new LoginPanel(this)
    contentPanel.addComponent login


  }

  void initContents(AppManager manager) {
    NAVIGATION_APP_PANEL = [
            "Home": ["Dashboard": new DashboardPanel()],
            "Project": ["Projects": new ProjectList("Projects", this)],
            "Participant": ["Person": new PersonList("Person", this)
                    , "Organization": new OrganizationList("Organization", this)
                    , "Relationship": new RelationshipList("Relationship", this)
                    , "Party Types": new PartyTypeList("Party Types", this.manager)
                    , "Role Types": new RoleTypeListPanel("Role Types", this.manager)
                    , "Relationship Types": new RelationshipTypeListPanel("Relationship Types", this.manager)],

            "Administrator": ["Code Decode": new CodeDecodePanel("Code Decode", this)
                    , "Users": new UserLoginList("User Login", this)
                    , "Logs": new SyslogPanel("Logs", this)
            ]

    ]
    manager.setNavigationPanelContents(NAVIGATION_APP_PANEL)

  }


}
