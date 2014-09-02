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

		<form role="form" action="updateUser" method="post">
			<div class="form-group">
				<label>User name</label>
				<input type="text" name="name" class="form-control" value="${usr.username}">
			</div>
			
			<div class="form-group">
				<label>Password</label>
				<input type="password" name="password" class="form-control">
			</div>
			
			<div class="checkbox">
				<label>
					<input type="checkbox" name="changePassword">Change Password
				</label>
			</div>
			
			<div class="checkbox">
				<label>
					<input type="checkbox" name="admin" <c:if test="${usr.admin}">checked</c:if>>Admin
				</label>
			</div>
			
			<input type="hidden" name="userId" value="${usr.id}">
			<button type="submit"class="btn btn-default pull-left"  name="${action}Button">${type}</button>
		</form>
	</div>
</t:template>