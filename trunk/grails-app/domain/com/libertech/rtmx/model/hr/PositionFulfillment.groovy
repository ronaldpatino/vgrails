package com.libertech.rtmx.model.hr

import com.libertech.rtmx.model.party.Party

/**
*  The POSITION FULFILLMENT entity provides a very flexible way to retain the history of this activity.
    The attributes from date and thru date will allow the enterprise to keep historically accurate
    information about this data. It is a convenient and effective way to resolve the many-to-many
   relationship that really exists between
   PERSON and POSITION.
  */
class PositionFulfillment {

   Date fromDate
   Date thruDate
   String description


   static constraints = {

     fromDate(nullable:false)
     thruDate(nullable:false)
     description(blank:false)

   }
}
