<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template page="admin">
	<div class="container-fluid">
		<div class="page-header">
			<h1>Administration</h1>
		</div>

		<p>
			<a href="addDrinkList">Import drinklist</a>
		</p>
		<p>
			<a href="createUser">Create a new user</a>
		</p>

		<form class="form-inline" role="form" action="modifyUser" method="post">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>User name</th>
						<th>User id</th>
						<th>Admin</th>
						<th>Last visit</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="usr" items="${users}">
						<tr>
							<td><c:out value="${usr.username}" /></td>
							<td>${usr.id}</td>
							<td><c:if test="${usr.admin == true}">
									<span class="glyphicon glyphicon-check"></span>
								</c:if> <c:if test="${usr.admin != true}">
									<span class="glyphicon glyphicon-unchecked"></span>
								</c:if></td>
							<td><c:out value="${usr.lastLogin}" /></td>
							<td>
								<div class="form-group">
									<button class="btn btn-default" type="submit" name="modifyButton" value="${usr.id}">Modify</button>
									<button class="btn btn-default" type="submit" name="removeButton" value="${usr.id}">Remove</button>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
</t:template>