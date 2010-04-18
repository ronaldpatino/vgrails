
<%@ page import="com.libertech.rtmx.model.party.Party" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'party.label', default: 'Party')}" />
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
            <g:hasErrors bean="${partyInstance}">
            <div class="errors">
                <g:renderErrors bean="${partyInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
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
                                    <label for="subTypeCode"><g:message code="party.subTypeCode.label" default="Sub Type Code" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: partyInstance, field: 'subTypeCode', 'errors')}">
                                    <g:textField name="subTypeCode" value="${partyInstance?.subTypeCode}" />
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
