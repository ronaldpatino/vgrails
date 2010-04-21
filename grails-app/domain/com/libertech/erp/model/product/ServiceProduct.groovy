package com.libertech.erp.model.product

class ServiceProduct extends Product {
   String subTypeCode;  //DELIVERABLEBASEDSERVICE or TIMEANDMATERIALSERVICE
  
   String toString(){
    return name+" : Service"
  }
}
