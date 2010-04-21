package com.libertech.erp.model.party.relationship

import com.libertech.erp.model.basis.StatusType

class PartyRelationshipStatusType extends StatusType {

  static hasMany=[partyRelationships:PartyRelationship]

}
