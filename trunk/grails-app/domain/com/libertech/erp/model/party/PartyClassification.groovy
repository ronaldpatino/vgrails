package com.libertech.erp.model.party

/**
 * Parties are classified into various categories using the entity PARTY CLASSIFICATION,
 * which stores each category into which parties may belong. There
 * are subtypes for ORGANIZATION CLASSIFICATION such as INDUSTRY
 * CLASSIFICATION, SIZE CLASSIFICATION, and MINORITY CLASSIFICATION
 * as well as subtypes to categorize people such as EEOC (Equal Employment
 * Opportunity Commission) CLASSIFICATION and INCOME CLASSIFICATION.
 * The ORGANIZATION CLASSIFICATION and PERSON CLASSIFICATION
 * could be related to ORGANIZATION and PERSON, respectively, if one wanted
 * to model them more specifically. For simplicity purposes, however, this model 
 * shows them as subtypes of PARTY CLASSIFICATION, which is related to
 * PARTY. These represent only a few possible types for illustration purposes, and
 * other possible values for categories are maintained in the PARTY TYPE entity.
 */
class PartyClassification {
    Date fromDate     =new Date()
    Date thruDate     =new Date()
    PartyType partyType =new PartyType();
    static belongsTo = [party:Party]

  static constraints = {

    }

   static mapping = {
        cache true
        partyType lazy:false
        party lazy:false
     }

}
