package com.libertech.rtmx.ui

import com.vaadin.Application
import com.vaadin.ui.Window
import org.zhakimel.vgrails.app.BrandingPanel
import org.zhakimel.vgrails.app.NavigationPanel

import org.zhakimel.vgrails.app.SubNavigationPanel
import org.zhakimel.vgrails.app.ContentPanel
import org.zhakimel.vgrails.app.CopyrightPanel
import com.libertech.rtmx.ui.common.LoginPanel

import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.SplitPanel
import com.libertech.rtmx.ui.util.AppManager

/**
 *  Main application layout
 *   and initializer
 */

class MainApp extends Application {

  VerticalLayout main = new VerticalLayout();

  AppManager manager

  BrandingPanel brandingPanel
  NavigationPanel navigationPanel
  SubNavigationPanel subNavigationPanel
  ContentPanel contentPanel
  CopyrightPanel copyrightPanel

  /**
   * Call this method to initialize
   */
  void init() {
    setTheme("chameleon-blue");
    //initialize manager
    manager = new AppManager(this)

    //initialize application panels
    brandingPanel = new BrandingPanel(this)
    navigationPanel = new NavigationPanel(this)
    subNavigationPanel = new SubNavigationPanel(this)
    contentPanel = new ContentPanel()
    copyrightPanel = new CopyrightPanel()

    //set application CSS theme


    //define window for this app
    def window = new Window("RTMX 01", main)
    setMainWindow window

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

    //call manager to init application contents
    manager.initContents()

  }


}
