<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template page="landing">
	<div class="container-fluid">
		<h1>Welcome back ${sessionScope.username}!</h1>
		<c:if test="${sessionScope.admin == true}">
		<h2>Administration</h2>
		Click <a href="admin">here</a> to open administration page.
		</c:if>
	</div>
</t:template>
