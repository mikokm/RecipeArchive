<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template page="createUser">
	<div class="container-fluid">
		<p><a href="admin">Back to administration</a></p>
	
		<h1>Create a new user</h1>

		<div class="form-group">
			<form role="form" action="createUser" method="post">
				<label>User name</label>
				<input type="text" name="name" class="form-control">
				<label>Password</label>
				<input type="password"name="password" class="form-control"><br>
				<input type="submit" name="addButton" class="btn btn-default pull-right" value="Add the user">
			</form>
		</div>
	</div>
</t:template>