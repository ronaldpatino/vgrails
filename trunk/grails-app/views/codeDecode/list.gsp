
<%@ page import="com.libertech.rtmx.model.common.CodeDecode" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'codeDecode.label', default: 'CodeDecode')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'codeDecode.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="cdGroup" title="${message(code: 'codeDecode.cdGroup.label', default: 'Cd Group')}" />
                        
                            <g:sortableColumn property="cdCode" title="${message(code: 'codeDecode.cdCode.label', default: 'Cd Code')}" />
                        
                            <g:sortableColumn property="cdValue" title="${message(code: 'codeDecode.cdValue.label', default: 'Cd Value')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'codeDecode.description.label', default: 'Description')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${codeDecodeInstanceList}" status="i" var="codeDecodeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${codeDecodeInstance.id}">${fieldValue(bean: codeDecodeInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: codeDecodeInstance, field: "cdGroup")}</td>
                        
                            <td>${fieldValue(bean: codeDecodeInstance, field: "cdCode")}</td>
                        
                            <td>${fieldValue(bean: codeDecodeInstance, field: "cdValue")}</td>
                        
                            <td>${fieldValue(bean: codeDecodeInstance, field: "description")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${codeDecodeInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
