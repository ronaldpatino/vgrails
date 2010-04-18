
<%@ page import="com.libertech.rtmx.model.common.SysLog" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sysLog.label', default: 'SysLog')}" />
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
            <g:hasErrors bean="${sysLogInstance}">
            <div class="errors">
                <g:renderErrors bean="${sysLogInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="sysLog.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sysLogInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${sysLogInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="severity"><g:message code="sysLog.severity.label" default="Severity" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sysLogInstance, field: 'severity', 'errors')}">
                                    <g:textField name="severity" value="${sysLogInstance?.severity}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="logDate"><g:message code="sysLog.logDate.label" default="Log Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sysLogInstance, field: 'logDate', 'errors')}">
                                    <g:datePicker name="logDate" precision="day" value="${sysLogInstance?.logDate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="userLogin"><g:message code="sysLog.userLogin.label" default="User Login" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sysLogInstance, field: 'userLogin', 'errors')}">
                                    <g:select name="userLogin.id" from="${com.libertech.rtmx.model.common.UserLogin.list()}" optionKey="id" value="${sysLogInstance?.userLogin?.id}"  />
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
