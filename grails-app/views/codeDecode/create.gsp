
<%@ page import="com.libertech.rtmx.model.common.CodeDecode" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'codeDecode.label', default: 'CodeDecode')}" />
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
            <g:hasErrors bean="${codeDecodeInstance}">
            <div class="errors">
                <g:renderErrors bean="${codeDecodeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="cdGroup"><g:message code="codeDecode.cdGroup.label" default="Cd Group" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: codeDecodeInstance, field: 'cdGroup', 'errors')}">
                                    <g:textField name="cdGroup" value="${codeDecodeInstance?.cdGroup}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="cdCode"><g:message code="codeDecode.cdCode.label" default="Cd Code" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: codeDecodeInstance, field: 'cdCode', 'errors')}">
                                    <g:textField name="cdCode" value="${codeDecodeInstance?.cdCode}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="cdValue"><g:message code="codeDecode.cdValue.label" default="Cd Value" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: codeDecodeInstance, field: 'cdValue', 'errors')}">
                                    <g:textField name="cdValue" value="${codeDecodeInstance?.cdValue}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="codeDecode.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: codeDecodeInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${codeDecodeInstance?.description}" />
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
