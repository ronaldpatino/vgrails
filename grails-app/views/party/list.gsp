
<%@ page import="com.libertech.rtmx.model.party.Party" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'party.label', default: 'Party')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'party.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="creditRating" title="${message(code: 'party.creditRating.label', default: 'Credit Rating')}" />
                        
                            <g:sortableColumn property="subTypeCode" title="${message(code: 'party.subTypeCode.label', default: 'Sub Type Code')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${partyInstanceList}" status="i" var="partyInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${partyInstance.id}">${fieldValue(bean: partyInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: partyInstance, field: "creditRating")}</td>
                        
                            <td>${fieldValue(bean: partyInstance, field: "subTypeCode")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${partyInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
