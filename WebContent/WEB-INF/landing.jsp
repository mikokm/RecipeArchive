<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template page="landing">
	<div class="container-fluid">
		<div class="page-header">
			<h1>Welcome back <c:out value="${user.username}" />!</h1>
		</div>
		<p>You last visited here <c:out value="${user.lastLogin}" />.</p>
		<c:if test="${sessionScope.user.admin == true}">
		<h2>Administration</h2>
		Click <a href="admin">here</a> to open administration page.
		</c:if>
	</div>
</t:template>
