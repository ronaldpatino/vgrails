
<%@ page import="com.libertech.rtmx.model.common.SysLog" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sysLog.label', default: 'SysLog')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'sysLog.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'sysLog.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="severity" title="${message(code: 'sysLog.severity.label', default: 'Severity')}" />
                        
                            <g:sortableColumn property="logDate" title="${message(code: 'sysLog.logDate.label', default: 'Log Date')}" />
                        
                            <th><g:message code="sysLog.userLogin.label" default="User Login" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${sysLogInstanceList}" status="i" var="sysLogInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${sysLogInstance.id}">${fieldValue(bean: sysLogInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: sysLogInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: sysLogInstance, field: "severity")}</td>
                        
                            <td><g:formatDate date="${sysLogInstance.logDate}" /></td>
                        
                            <td>${fieldValue(bean: sysLogInstance, field: "userLogin")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${sysLogInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
