
<%@ page import="com.libertech.rtmx.model.party.Person" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'organization.label', default: 'Person')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${personInstance}">
            <div class="errors">
                <g:renderErrors bean="${personInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="firstName"><g:message code="person.firstName.label" default="First Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'firstName', 'errors')}">
                                    <g:textField name="firstName" value="${personInstance?.firstName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lastName"><g:message code="person.lastName.label" default="Last Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'lastName', 'errors')}">
                                    <g:textField name="lastName" value="${personInstance?.lastName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="personalTitle"><g:message code="person.personalTitle.label" default="Personal Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'personalTitle', 'errors')}">
                                    <g:textField name="personalTitle" value="${personInstance?.personalTitle}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="nickName"><g:message code="person.nickName.label" default="Nick Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'nickName', 'errors')}">
                                    <g:textField name="nickName" value="${personInstance?.nickName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="gender"><g:message code="person.gender.label" default="Gender" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'gender', 'errors')}">
                                    <g:textField name="gender" value="${personInstance?.gender}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="birthDate"><g:message code="person.birthDate.label" default="Birth Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'birthDate', 'errors')}">
                                    <g:datePicker name="birthDate" precision="day" value="${personInstance?.birthDate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="maritalStatus"><g:message code="person.maritalStatus.label" default="Marital Status" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'maritalStatus', 'errors')}">
                                    <g:textField name="maritalStatus" value="${personInstance?.maritalStatus}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="socialSecurityNo"><g:message code="person.socialSecurityNo.label" default="Social Security No" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'socialSecurityNo', 'errors')}">
                                    <g:textField name="socialSecurityNo" value="${personInstance?.socialSecurityNo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="passportNo"><g:message code="person.passportNo.label" default="Passport No" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'passportNo', 'errors')}">
                                    <g:textField name="passportNo" value="${personInstance?.passportNo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="comment"><g:message code="person.comment.label" default="Comment" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'comment', 'errors')}">
                                    <g:textField name="comment" value="${personInstance?.comment}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="subTypeCode"><g:message code="person.subTypeCode.label" default="Sub Type Code" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'subTypeCode', 'errors')}">
                                    <g:textField name="subTypeCode" value="${personInstance?.subTypeCode}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="birthPlace"><g:message code="person.birthPlace.label" default="Birth Place" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'birthPlace', 'errors')}">
                                    <g:textField name="birthPlace" value="${personInstance?.birthPlace}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="creditRating"><g:message code="person.creditRating.label" default="Credit Rating" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'creditRating', 'errors')}">
                                    <g:textField name="creditRating" value="${personInstance?.creditRating}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
