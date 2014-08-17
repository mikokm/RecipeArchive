<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template page="landing">
	<div class="container-fluid">
		<h1>Welcome back ${sessionScope.username}!</h1>
		There are [count] new drink recipes that have been added since your last visit! See them <a href="latest.jsp">here</a>.

		<c:if test="${sessionScope.admin == true}">
		<h2>Administration</h2>
		Click <a href="users.jsp">here</a> to open administration page.
		</c:if>
	</div>
</t:template>
