<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:template page="landing">
	<div class="container-fluid">
		<h1>Welcome back [name]!</h1>
		There has been [count] drink recipes added since your last visit! See them <a href="latest.jsp">here</a>.

		<h2>Administration</h2>
		Click <a href="users.jsp">here</a> to open administration page.
	</div>
</t:template>
