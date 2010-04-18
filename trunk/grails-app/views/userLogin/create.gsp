
<%@ page import="com.libertech.rtmx.model.common.UserLogin" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'userLogin.label', default: 'UserLogin')}" />
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
            <g:hasErrors bean="${userLoginInstance}">
            <div class="errors">
                <g:renderErrors bean="${userLoginInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="userName"><g:message code="userLogin.userName.label" default="User Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userLoginInstance, field: 'userName', 'errors')}">
                                    <g:textField name="userName" maxlength="15" value="${userLoginInstance?.userName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="userPassword"><g:message code="userLogin.userPassword.label" default="User Password" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userLoginInstance, field: 'userPassword', 'errors')}">
                                    <g:textField name="userPassword" maxlength="15" value="${userLoginInstance?.userPassword}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="userRole"><g:message code="userLogin.userRole.label" default="User Role" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userLoginInstance, field: 'userRole', 'errors')}">
                                    <g:textField name="userRole" value="${userLoginInstance?.userRole}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="email"><g:message code="userLogin.email.label" default="Email" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userLoginInstance, field: 'email', 'errors')}">
                                    <g:textField name="email" value="${userLoginInstance?.email}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="party"><g:message code="userLogin.party.label" default="Party" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userLoginInstance, field: 'party', 'errors')}">
                                    <g:select name="party.id" from="${com.libertech.rtmx.model.party.Party.list()}" optionKey="id" value="${userLoginInstance?.party?.id}"  />
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
