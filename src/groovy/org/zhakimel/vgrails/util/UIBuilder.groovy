package org.zhakimel.vgrails.util

/**
 * interface for application content panels
 * @author Abil Hakim
 */
public interface UIBuilder {

  //private boolean built

  /**
   *  build / addComponent calls here
   *  at the end of routine, set built=true
   */
  void build();

  /**
  * returns boolean built private property
  */
  boolean isBuilt();

  /**
  * put your set value or set data container of your data components here
  */
  void refreshUIData();
}