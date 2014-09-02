<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template page="admin">
	<div class="container-fluid">
		<div class="page-header">
			<h1>Administration</h1>
		</div>

		<p><a href="addDrinkList">Import drinklist</a></p>
		<p><a href="createUser">Create a new user</a></p>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>User name</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${users}">
					<tr>
						<td>${user.username}</td>
						<td>
							<form class="form-inline" role="form" action="modifyUser" method="post">
								<div class="form-group">
									<input type="hidden" name="userId" value="${user.id}">
									<input type="submit" name="modifyButton" class="btn btn-default" value="Modify">
									<input type="submit" name="deleteButton" class="btn btn-default" value="Delete user">
								</div>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</t:template>