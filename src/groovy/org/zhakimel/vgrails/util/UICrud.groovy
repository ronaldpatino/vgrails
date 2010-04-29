package org.zhakimel.vgrails.util

/**
 * Class UICrud
 * Description:
 *  interface for CRUD routines
 * Usage      :
 *
 * @author abiel
 */

public interface UICrud {

  //TODO get objects for UI
  def doGet();

  //TODO save objects for UI
  def doSave();

  //TODO delete object(s) for UI
  def doDelete();

  //TODO present list to objects
  def doList();

  //TODO process objects for UI
  def doProcess();

}