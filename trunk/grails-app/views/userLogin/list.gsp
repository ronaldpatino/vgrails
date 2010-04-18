
<%@ page import="com.libertech.rtmx.model.common.UserLogin" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'userLogin.label', default: 'UserLogin')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'userLogin.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="userName" title="${message(code: 'userLogin.userName.label', default: 'User Name')}" />
                        
                            <g:sortableColumn property="userPassword" title="${message(code: 'userLogin.userPassword.label', default: 'User Password')}" />
                        
                            <g:sortableColumn property="userRole" title="${message(code: 'userLogin.userRole.label', default: 'User Role')}" />
                        
                            <g:sortableColumn property="email" title="${message(code: 'userLogin.email.label', default: 'Email')}" />
                        
                            <th><g:message code="userLogin.party.label" default="Party" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${userLoginInstanceList}" status="i" var="userLoginInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${userLoginInstance.id}">${fieldValue(bean: userLoginInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: userLoginInstance, field: "userName")}</td>
                        
                            <td>${fieldValue(bean: userLoginInstance, field: "userPassword")}</td>
                        
                            <td>${fieldValue(bean: userLoginInstance, field: "userRole")}</td>
                        
                            <td>${fieldValue(bean: userLoginInstance, field: "email")}</td>
                        
                            <td>${fieldValue(bean: userLoginInstance, field: "party")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${userLoginInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
