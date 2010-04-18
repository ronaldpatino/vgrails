
<%@ page import="com.libertech.rtmx.model.party.Party" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'party.label', default: 'Party')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${partyInstance}">
            <div class="errors">
                <g:renderErrors bean="${partyInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${partyInstance?.id}" />
                <g:hiddenField name="version" value="${partyInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="creditRating"><g:message code="party.creditRating.label" default="Credit Rating" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: partyInstance, field: 'creditRating', 'errors')}">
                                    <g:textField name="creditRating" value="${partyInstance?.creditRating}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="partyRoles"><g:message code="party.partyRoles.label" default="Party Roles" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: partyInstance, field: 'partyRoles', 'errors')}">
                                    <g:select name="partyRoles" from="${com.libertech.rtmx.model.party.PartyRole.list()}" multiple="yes" optionKey="id" size="5" value="${partyInstance?.partyRoles}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="positionFulfillments"><g:message code="party.positionFulfillments.label" default="Position Fulfillments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: partyInstance, field: 'positionFulfillments', 'errors')}">
                                    <g:select name="positionFulfillments" from="${com.libertech.rtmx.model.hr.PositionFulfillment.list()}" multiple="yes" optionKey="id" size="5" value="${partyInstance?.positionFulfillments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="subTypeCode"><g:message code="party.subTypeCode.label" default="Sub Type Code" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: partyInstance, field: 'subTypeCode', 'errors')}">
                                    <g:textField name="subTypeCode" value="${partyInstance?.subTypeCode}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="employeePositions"><g:message code="party.employeePositions.label" default="Employee Positions" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: partyInstance, field: 'employeePositions', 'errors')}">
                                    <g:select name="employeePositions" from="${com.libertech.rtmx.model.hr.EmployeePosition.list()}" multiple="yes" optionKey="id" size="5" value="${partyInstance?.employeePositions}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="partyClassifications"><g:message code="party.partyClassifications.label" default="Party Classifications" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: partyInstance, field: 'partyClassifications', 'errors')}">
                                    <g:select name="partyClassifications" from="${com.libertech.rtmx.model.party.PartyClassification.list()}" multiple="yes" optionKey="id" size="5" value="${partyInstance?.partyClassifications}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="partyPostalAddresses"><g:message code="party.partyPostalAddresses.label" default="Party Postal Addresses" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: partyInstance, field: 'partyPostalAddresses', 'errors')}">
                                    <g:select name="partyPostalAddresses" from="${com.libertech.rtmx.model.party.PartyPostalAddress.list()}" multiple="yes" optionKey="id" size="5" value="${partyInstance?.partyPostalAddresses}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="partyContacts"><g:message code="party.partyContacts.label" default="Party Contacts" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: partyInstance, field: 'partyContacts', 'errors')}">
                                    <g:select name="partyContacts" from="${com.libertech.rtmx.model.party.PartyContact.list()}" multiple="yes" optionKey="id" size="5" value="${partyInstance?.partyContacts}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
