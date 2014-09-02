<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@attribute name="action"%>
<%@attribute name="type"%>

<t:template page="createUser">
	<div class="container-fluid">
		<p><a href="admin">Back to administration</a></p>
	
		<div class="page-header">
			<h1>${type}</h1>
		</div>

		<div class="form-group">
			<form role="form" class="form-horizontal" action="updateUser" method="post">
				<label>User name</label>
				<input type="text" name="name" class="form-control">
				<label>Password</label>
				<input type="password" name="password" class="form-control"><br>
				<input type="text" name="admin" class="form-control"> 
				<input type="submit" name="${action}Button" class="btn btn-default pull-right" value="${type}">
			</form>
		</div>
	</div>
</t:template>